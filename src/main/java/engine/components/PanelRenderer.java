package engine.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.BaseStream;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

import engine.AssetManager;
import engine.renderer.EBO;
import engine.renderer.Renderer;
import engine.renderer.Shader;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.renderer.VAO;
import engine.scenes.SceneManager;

public class PanelRenderer extends Renderable{

    public int leftBoarder;
    public int rightBoarder;
    public int topBoarder;
    public int bottomBoarder;

    SpriteMap spr;

    Transform t;

    float[] xOffsets, yOffsets;

    public PanelRenderer(Texture baseTexture, int leftBoarder, int rightBoarder, int topBoarder, int bottomBoarder){
        // TODO: changes these
        super(Shader.SPRITE_RGB, VAO.SPRITE, EBO.PANEL);

        this.leftBoarder = leftBoarder;
        this.rightBoarder = rightBoarder;
        this.topBoarder = topBoarder;
        this.bottomBoarder = bottomBoarder;

        
    }

    public void Awake(){
        t = gameObject.getComponent(Transform.class);

        if(t == null) return;

        xOffsets = new float[]{
            0, leftBoarder, rightBoarder, t.getQuad().getWidth()
        };

        yOffsets = new float[]{
            0, leftBoarder, rightBoarder, t.getQuad().getWidth()
        };
    }

    @Override
    public void loadVertexData(float[] buffer, int start) {
        try {
            Vector3f topLeft = t.getQuad().topLeft;

            for(int x = 0; x < 4; x ++){
                for(int y = 0; y < 4; y ++){
                    buffer[start + vao.vaoSize * (x + y*4) + 0] = topLeft.x + xOffsets[x];
                    buffer[start + vao.vaoSize * (x + y*4) + 1] = topLeft.y + yOffsets[y];
                    buffer[start + vao.vaoSize * (x + y*4) + 2] = topLeft.z;

                    buffer[start + vao.vaoSize * (x + y*4) + 3] = baseTexture.toNDCWidth(xOffsets[x]); 
                    buffer[start + vao.vaoSize * (x + y*4) + 4] = baseTexture.toNDCHeight(yOffsets[y]);
                }
            }


            
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void UploadUniforms() {
        Shader.SPRITE.uploadMat4f("uProjection", SceneManager.getActiveMainCamera().getProjectionMatrix());

        int[] textureSlots = new int[8];
        for (int i = 0; i < textureSlots.length; i++) {
            textureSlots[i] = i;
        }

        Shader.SPRITE.uploadIntArray("uTextures", textureSlots);
        
    }

    @Override
    public void render(List<Renderable> renderables) {
        // System.out.println("---------------------"); 
        // System.out.println("RENDERING " + renderables.size()+ " sprites"); 
        
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

        // for (Entry<Integer, List<SpriteRenderer>> e : sortedRenderables.entrySet()) {
        //     System.out.printf("[%s - > %s]\n", e.getKey(), e.getValue().size());
        // }

        List<SpriteGroup> batches = new ArrayList<>();

        for(List<SpriteRenderer> bunky : sortedRenderables.values()){
            
            batchSprites:
            for (SpriteRenderer bunk : bunky) {
                for (SpriteGroup batch : batches) {
                    // System.out.printf("spr:%s, tex:%s %s\n", batch.currentSpriteRenderer, batch.currentTexture, batch.canAdd(bunk)); 
                    // if the batch is using the same shader and vao
                    // if the batch is not full
                    if(batch.canAdd(bunk)){
                        
                        // add this to the batch
                        batch.addRenderable(bunk);
                        // System.out.println("adding to existing with text slot: " + bunk.texSlot);//e
                        continue batchSprites;
                    }
                }
                
                // System.out.println("makingn a new batch"); 
                SpriteGroup batch = new SpriteGroup();
                    batches.add(batch);
                    batch.addRenderable(bunk);
            }
        }
        
        // System.out.println("num batches: " + batches.size()); 

        // System.out.println("Number of batches: " + batches.size());
        for (SpriteGroup batch : batches) {
            // System.out.println("Rendering batch with amount: " + batch.spr.length);
            float[] vertices = new float[Renderer.maxBatchSize * ebo.getNumberOfVertices() * vao.vaoSize];

            for (int i = 0; i < batch.tex.length - 1; i++) {
                if(batch.tex[i] == null) continue;
                // System.out.println(i); // TODO: TEST me
                GL20.glActiveTexture(GL20.GL_TEXTURE0 + i);
                batch.tex[i].bind();
            }

            

            for (int i = 0; i < batch.spr.length; i ++){
                if(batch.spr[i] == null) continue;
                // System.out.println(batch.spr[i]);
                batch.spr[i].loadVertexData(vertices, i * ebo.getNumberOfVertices() * vao.vaoSize);
            }

            // for(int i = 0; i < Math.ceil(vertices.length/6); i ++){
            //     System.out.println(Arrays.toString(Arrays.copyOfRange(vertices, i*6, (i + 1)*6))); // TODO: TEST me
            // }

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
