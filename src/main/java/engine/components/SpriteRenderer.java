package engine.components;
import org.joml.Vector3f;

import engine.renderer.EBO;
import engine.renderer.Shader;
import engine.renderer.SpriteMap;
import engine.renderer.VAO;

public class SpriteRenderer extends Renderable{
    /** NOTE
     *  each implementation of renderable will only use a specific shader/vao
     * 
     * suport for shader switching will come if and only if you keep the same vao structure
     */
private float[] vertexArray = {
        // position               // color
         50.5f, 0f, 0.0f,          1, 1,// Bottom right 0
         0f,  50f, 0.0f,           0, 0,// Top left     1
         50.5f,  50f, 0.0f ,       1, 0,// Top right    2
        -0.5f, -0.5f, 0.0f,        0, 1 // Bottom left  3
    };

    // IMPORTANT: Must be in counter-clockwise order
    private int[] elementArray = {
            2, 1, 0, // Top right triangle
            0, 1, 3 // bottom left triangle
    };


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

        for(int i = 0; i < 4; i ++){
            System.out.printf("(%s, %s) ", vertices[i].x, vertices[i].y);
            buffer[start + vao.vaoSize * i + 0] = vertices[i].x;
            buffer[start + vao.vaoSize * i + 1] = vertices[i].y;
            buffer[start + vao.vaoSize * i + 2] = vertices[i].z;

            buffer[start + vao.vaoSize * i + 3] = uvVertices[i].x; 
            buffer[start + vao.vaoSize * i + 4] = uvVertices[i].y; 
        }
        System.out.println();
    }
    
}
