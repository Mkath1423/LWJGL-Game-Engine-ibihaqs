package engine.components;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

import engine.Window;
import engine.renderer.EBO;
import engine.renderer.Renderer;
import engine.renderer.Shader;
import engine.renderer.SpriteMap;
import engine.renderer.VAO;
import engine.util.Time;

public class SpriteRenderer extends Renderable{
    /** NOTE
     *  each implementation of renderable will only use a specific shader/vao
     * 
     * suport for shader switching will come if and only if you keep the same vao structure
     */

    private SpriteMap spriteMap;
    private int currentSprite;

    private Transform transform;

    private int texSlot = 0;


    public SpriteRenderer(SpriteMap spriteMap){
        super(Shader.SPRITE_RGB, VAO.SPRITE, EBO.QUAD);

        this.spriteMap = spriteMap;

        this.layerId = 0;
        
        this.renderableType = "sprite";
    }

    @Override
    public void Awake(){
        transform = gameObject.getComponent(Transform.class);
        System.out.println("Sprite Renderer: Transform is... " + transform);
        super.Awake();
    }

    @Override
    public void loadVertexData(float[] buffer, int start) {
        /**
         * pos       uv    texId
         * 0, 0, 0   0, 0, 0
         * 
         */
        // if(buffer.length >= start + vao.vaoSize * ebo.getNumberOfVertices()) return;

        Vector3f[] vertices = transform.getQuad().getVertices();
        Vector3f[] uvVertices = spriteMap.getSprite(currentSprite).uvCoordinates.getVertices();
        
        int texId = spriteMap.getSprite(currentSprite).texture.getTexId();
        // System.out.println("-----------------");
        for(int i = 0; i < 4; i ++){
            // System.out.printf("(%s, %s) ", vertices[i].x, vertices[i].y);
            buffer[start + vao.vaoSize * i + 0] = vertices[i].x;
            buffer[start + vao.vaoSize * i + 1] = vertices[i].y;
            buffer[start + vao.vaoSize * i + 2] = vertices[i].z;

            buffer[start + vao.vaoSize * i + 3] = uvVertices[i].x; 
            buffer[start + vao.vaoSize * i + 4] = uvVertices[i].y; 

            // System.out.printf("(%s, %s) -> (%s, %s)\n", buffer[start + vao.vaoSize * i + 0], buffer[start + vao.vaoSize * i + 1], 
            // buffer[start + vao.vaoSize * i + 3], buffer[start + vao.vaoSize * i + 4]);
        }
        // System.out.println("-----------------");
        // System.out.println();
    }

    @Override
    public void UploadUniforms() {
        Shader.SPRITE_RGB.uploadMat4f("uProjection", Window.get().camera.getProjectionMatrix());
        Shader.SPRITE_RGB.uploadMat4f("uView",       Window.get().camera.getViewMatrix());
        Shader.SPRITE_RGB.uploadFloat("uTime",       (float)Time.getTime());  
        Shader.SPRITE_RGB.uploadInt("texSampler", 0);
    }

    private class SpriteGroup{
        SpriteRenderer[] spr;
        int[] tex;

        int currentSpriteRenderer;
        int currentTexture;

        boolean hasRoom;

        public SpriteGroup(){
            spr = new SpriteRenderer[Renderer.maxBatchSize];
            tex = new int           [Renderer.maxTextures ];

            currentSpriteRenderer = 0;
            currentTexture = 0;
        }

        public boolean canAdd(SpriteRenderer r){
            // if there is no space left in the batch
            if(currentSpriteRenderer == spr.length) return false;

            // if there is space but no more texture slots
            if(currentTexture == tex.length){
                // if we do not need to allocate a new texture slot
                for (int texId : tex) {
                    if(r.spriteMap.texture.getTexId() == texId){
                        return true;
                    }
                }
                return false;
            }

            // if there is space for more textures and more spr
            return true;
        }

        public void addRenderable(SpriteRenderer r){
            spr[currentSpriteRenderer] = r;
            currentSpriteRenderer ++;

            boolean allocateTexture = false;
            for (int i = 0; i < tex.length; i++) {
                if(r.spriteMap.texture.getTexId() == tex[i]){
                    allocateTexture = true;
                    r.texSlot = i;
                    break;
                }
            }

            if(allocateTexture){
                tex[currentTexture] = r.spriteMap.texture.getTexId();
                r.texSlot = currentTexture;
                currentTexture ++;
            }
        }
    }

    @Override
    public void render(List<Renderable> renderables) {
        
        Map<Integer, List<SpriteRenderer>> sortedRenderables = new HashMap<>();

        for (Renderable r : renderables) {
            if(r instanceof SpriteRenderer){
                SpriteRenderer spr = (SpriteRenderer)r;
                if(!sortedRenderables.containsKey(spr.spriteMap.texture.getTexId())){
                    sortedRenderables.put(spr.spriteMap.texture.getTexId(), new ArrayList<>());
                }
                sortedRenderables.get(spr.spriteMap.texture.getTexId()).add(spr);
            }
        }

        List<SpriteGroup> batches = new ArrayList<>();

        for(List<SpriteRenderer> bunky : sortedRenderables.values()){
            for (SpriteRenderer bunk : bunky) {
                for (SpriteGroup batch : batches) {
                    // if the batch is using the same shader and vao
                    // if the batch is not full
                    if(batch.canAdd(bunk)){
                        
                        // add this to the batch
                        batch.addRenderable(bunk);
                        break;
                    }
                }
                
                System.out.println("Layer: Adding renderable to new batch");
                SpriteGroup batch = new SpriteGroup();
                    batch.addRenderable(bunk);
                    batches.add(batch);
            }
        }

        for (SpriteGroup batch : batches) {
            
            float[] vertices = new float[Renderer.maxBatchSize * ebo.getNumberOfVertices() * vao.vaoSize];

            for (int i = 0; i < batch.spr.length; i ++){
                if(batch.spr[i] == null) continue;

                batch.spr[i].loadVertexData(vertices, i * ebo.getNumberOfVertices() * vao.vaoSize);
            }
            System.out.println(vertices);
            Renderer.bufferVertices(this, vertices);
            Renderer.drawVertices(this);
        }



        // for(int batchNumber = 0; batchNumber < Math.ceil((float)renderables.size()/Renderer.maxBatchSize); batchNumber++){
        //     float[] vertices = new float[Renderer.maxBatchSize * ebo.getNumberOfVertices() * vao.vaoSize];

        //     for (int i = batchNumber*Renderer.maxBatchSize; 
        //          i < renderables.size() && i < (batchNumber+1)*Renderer.maxBatchSize; 
        //          i++) {
        //         if(renderables.get(i) == null) continue;
        //         renderables.get(i).loadVertexData(vertices, batchNumber * ebo.getNumberOfVertices() * vao.vaoSize);
        //     }

        //     Renderer.bufferVertices(this, vertices);
        //     Renderer.drawVertices(this);
        // }
        
    }
    
}
