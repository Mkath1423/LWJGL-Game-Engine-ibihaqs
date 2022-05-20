package engine.components;
import java.util.List;

import org.joml.Vector3f;

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

    @Override
    public void render(List<Renderable> renderables) {
        System.out.println(renderableType + " drawing " + renderables.size() + "sprites");

        for(int batchNumber = 0; batchNumber < Math.ceil((float)renderables.size()/Renderer.maxBatchSize); batchNumber++){
            float[] vertices = new float[Renderer.maxBatchSize * ebo.getNumberOfVertices() * vao.vaoSize];

            for (int i = batchNumber*Renderer.maxBatchSize; 
                 i < renderables.size() && i < (batchNumber+1)*Renderer.maxBatchSize; 
                 i++) {
                if(renderables.get(i) == null) continue;
                renderables.get(i).loadVertexData(vertices, batchNumber * ebo.getNumberOfVertices() * vao.vaoSize);
            }

            Renderer.bufferVertices(this, vertices);
            Renderer.drawVertices(this);
        }
        
    }
    
}
