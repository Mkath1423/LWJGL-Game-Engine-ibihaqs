package engine.components;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;

import engine.Window;
import engine.renderer.EBO;
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


    public SpriteRenderer(SpriteMap spriteMap){
        super(Shader.SPRITE_RGB, VAO.SPRITE, EBO.QUAD);

        this.spriteMap = spriteMap;

        this.layerId = 0;
    }

    @Override
    public void Awake(){
        transform = gameObject.getComponent(Transform.class);
        // System.out.println("Sprite Renderer: Transform is... " + transform);
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

        // System.out.println(Arrays.toString(Arrays.copyOfRange(buffer, start, start +6*4)));
        // System.out.println("-----------------");
        // System.out.println();
    }

    @Override
    public Texture getTexture(){
        return spriteMap.texture;
    }

    @Override
    public void uploadUniforms() {
        Shader.SPRITE_RGB.uploadMat4f("uProjection", Window.get().camera.getProjectionMatrix());
        Shader.SPRITE_RGB.uploadMat4f("uView",       Window.get().camera.getViewMatrix());
        Shader.SPRITE_RGB.uploadFloat("uTime",       (float)Time.getTime());  
        Shader.SPRITE_RGB.uploadInt("texSampler", 0);
    }

    @Override
    public void render(List<Renderable> renderables) {
        // sort the renderables by texture
        Map<Texture, Renderable> sortedRenderables = new HashMap<>();

        for (int i = 0; i < renderables.size(); i++) {
            if(renderables.get(i) == null) continue;
            if(renderables.get(i).getTexture() == null) continue;
            sortedRenderables.put(renderables.get(i).getTexture(), renderables.get(i));
        }

        // batching loop
        for(int i = 0; i < sortedRenderables.entrySet().size(); i += 16){
            



        }


        // for(int i = 0; i < Math.ceil((float)l.size()/5); i ++){
        //     System.out.println(l.subList(i*5, Math.min(l.size(), i*5 + 5)));
        // }
        
    }
    
}
