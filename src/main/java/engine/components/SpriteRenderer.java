package engine.components;
import java.util.ArrayList;
import java.util.Arrays;
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
import engine.renderer.Texture;
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
        super(Shader.SPRITE, VAO.SPRITE, EBO.QUAD);

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
        
        // System.out.println("-----------------");
        for(int i = 0; i < 4; i ++){
            // System.out.printf("(%s, %s) ", vertices[i].x, vertices[i].y);
            buffer[start + vao.vaoSize * i + 0] = vertices[i].x;
            buffer[start + vao.vaoSize * i + 1] = vertices[i].y;
            buffer[start + vao.vaoSize * i + 2] = vertices[i].z;

            buffer[start + vao.vaoSize * i + 3] = uvVertices[i].x; 
            buffer[start + vao.vaoSize * i + 4] = uvVertices[i].y;
            
            buffer[start + vao.vaoSize * i + 5] = texSlot; 

            // System.out.printf("(%s, %s) -> (%s, %s)\n", buffer[start + vao.vaoSize * i + 0], buffer[start + vao.vaoSize * i + 1], 
            // buffer[start + vao.vaoSize * i + 3], buffer[start + vao.vaoSize * i + 4]);
        }
        // System.out.println("-----------------");
        // System.out.println();
    }

    @Override
    public void UploadUniforms() {
        Shader.SPRITE.uploadMat4f("uProjection", Window.get().camera.getProjectionMatrix());
        Shader.SPRITE.uploadMat4f("uView",       Window.get().camera.getViewMatrix());
        Shader.SPRITE.uploadFloat("uTime",       (float)Time.getTime());  
        Shader.SPRITE.uploadInt("texSampler", 0); 

        
    }

    private static List<SpriteGroup> batches = new ArrayList<>();

    private class SpriteGroup{
        SpriteRenderer[] spr;
        Texture[] tex;

        int currentSpriteRenderer;
        int currentTexture;

        boolean hasRoom;

        public SpriteGroup(){
            spr = new SpriteRenderer[Renderer.maxBatchSize];
            tex = new Texture       [Renderer.maxTextures ];

            currentSpriteRenderer = 0;
            currentTexture = 0;
        }

        public boolean canAdd(SpriteRenderer r){
            // if there is no space left in the batch
            if(currentSpriteRenderer == spr.length) return false;
            // System.out.println("there is space"); // TODO test me
            // if there is space but no more texture slots
            if(currentTexture == tex.length){
                // if we do not need to allocate a new texture slot
                for (Texture t : tex) {
                    if(r.spriteMap.texture == t){
                        return true;
                    }
                }
                return false;
            }

            // System.out.println("there are textures");// TODO test me

            // if there is space for more textures and more spr
            return true;
        }

        public void addRenderable(SpriteRenderer r){
            spr[currentSpriteRenderer] = r;
            currentSpriteRenderer ++;

            boolean allocateTexture = true;
            for (int i = 0; i < tex.length; i++) {
                if(r.spriteMap.texture == tex[i]){
                    allocateTexture = false;
                    r.texSlot = i;
                    break;
                }
            }

            if(allocateTexture){
                tex[currentTexture] = r.spriteMap.texture;
                r.texSlot = currentTexture;
                currentTexture ++;
            }
        
        }
    }

    @Override
    public void render(List<Renderable> renderables) {
        //System.out.println("RENDERING " + renderables.size()+ " sprites"); // TODO: TEST me
        
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
            batchSprites:
            for (SpriteRenderer bunk : bunky) {
                for (SpriteGroup batch : batches) {
                    // System.out.printf("spr:%s, tex:%s %s\n", batch.currentSpriteRenderer, batch.currentTexture, batch.canAdd(bunk)); // TODO: TEST me
                    // if the batch is using the same shader and vao
                    // if the batch is not full
                    if(batch.canAdd(bunk)){
                        
                        // add this to the batch
                        batch.addRenderable(bunk);
                        // System.out.println("adding to existing with text slot: " + bunk.texSlot);// TODO: TEST me
                        break batchSprites;
                    }
                }
                
                // System.out.println("Layer: Adding renderable to new batch"); // TODO: TEST me
                SpriteGroup batch = new SpriteGroup();
                    batches.add(batch);
                    batch.addRenderable(bunk);
            }
        }
        
        // System.out.println("num batches: " + batches.size()); // TODO: TEST me


        for (SpriteGroup batch : batches) {
            
            float[] vertices = new float[Renderer.maxBatchSize * ebo.getNumberOfVertices() * vao.vaoSize];

            for (int i = 0; i < batch.tex.length - 1; i++) {
                if(batch.tex[i] == null) continue;
                // System.out.println(i); // TODO: TEST me
                GL20.glActiveTexture(GL20.GL_TEXTURE0 + i);
                batch.tex[i].bind();
            }

            int[] textureSlots = new int[8];
            for (int i = 0; i < textureSlots.length; i++) {
                textureSlots[i] = i;
            }

            Shader.SPRITE.uploadIntArray("uTextures", textureSlots);

            for (int i = 0; i < batch.spr.length; i ++){
                if(batch.spr[i] == null) continue;

                batch.spr[i].loadVertexData(vertices, i * ebo.getNumberOfVertices() * vao.vaoSize);
            }

            for(int i = 0; i < Math.ceil(vertices.length/6); i ++){
                // System.out.println(Arrays.toString(Arrays.copyOfRange(vertices, i*6, (i + 1)*6))); // TODO: TEST me
            }

            Renderer.bufferVertices(this, vertices);
            Renderer.drawVertices(this);

            for (int i = 0; i < batch.tex.length; i++) {
                if(batch.tex[i] == null) continue;
                batch.tex[i].unbind();
            }
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
