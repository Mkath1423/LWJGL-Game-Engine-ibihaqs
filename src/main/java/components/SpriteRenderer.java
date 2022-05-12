package components;

import java.lang.Thread.UncaughtExceptionHandler;

import org.joml.Vector2f;
import org.joml.Vector3f;

import geometry.Quad;
import renderer.Sprite;
import renderer.SpriteMap;
import renderer.VAO;

public class SpriteRenderer extends Renderable{
    /** NOTE
     *  each implementation of renderable will only use a specific shader/vao
     * 
     * suport for shader switching will come if and only if you keep the same vao structure
     */



    private SpriteMap spriteMap;
    private int currentSprite;

    private Transform transform;
    private VAO vao;

    

    public SpriteRenderer(SpriteMap spriteMap){
        this.spriteMap = spriteMap;
    }

    public void Awake(){
        transform = gameObject.getComponent(Transform.class);
    }

    @Override
    public void loadVertexData(float[] buffer) {
        /**
         * pos       uv    texId
         * 0, 0, 0   0, 0, 0
         * 
         */
        if(buffer.length != (3 + 2 + 1)*4) return;

        Vector3f[] vertices = transform.getQuad().getVertices();
        Vector3f[] uvVertices = spriteMap.getSprite(currentSprite).uvCoordinates.getVertices();
        
        int texId = spriteMap.getSprite(currentSprite).texture.getTexId();

        for(int i = 0; i < 4; i ++){
            buffer[0 + vao.vaoSize * i] = vertices[i].x;
            buffer[1 + vao.vaoSize * i] = vertices[i].y;
            buffer[2 + vao.vaoSize * i] = vertices[i].z;

            buffer[3 + vao.vaoSize * i] = uvVertices[i].x; 
            buffer[4 + vao.vaoSize * i] = uvVertices[i].y; 

            buffer[5 + vao.vaoSize * i] = texId;
        }
    }
    
}
