package engine.components;

import org.joml.Vector3f;

import engine.renderer.QuadRenderer;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;

public class SpriteRenderable extends QuadRenderable{
    private SpriteMap spriteMap;
    private int currentSprite;

    private Transform transform;

    private int texSlot = 0;


    public SpriteRenderable(SpriteMap spriteMap){

        this.spriteMap = spriteMap;

        this.layerID = 0;
        this.numberVertices = 4;
    }

    @Override
    public void Awake(){
        transform = gameObject.getComponent(Transform.class);
        System.out.println("Sprite Renderer: Transform is... " + transform);
        super.Awake();
    }

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
            buffer[start + 6 * i + 0] = vertices[i].x;
            buffer[start + 6 * i + 1] = vertices[i].y;
            buffer[start + 6 * i + 2] = vertices[i].z;

            buffer[start + 6 * i + 3] = uvVertices[i].x; 
            buffer[start + 6 * i + 4] = uvVertices[i].y;
            
            buffer[start + 6 * i + 5] = texSlot; 

            // System.out.printf("(%s, %s) -> (%s, %s)\n", buffer[start + vao.vaoSize * i + 0], buffer[start + vao.vaoSize * i + 1], 
            // buffer[start + vao.vaoSize * i + 3], buffer[start + vao.vaoSize * i + 4]);
        }
        // System.out.println("-----------------");
        // System.out.println();
    }

    @Override
    public Texture getTexture() {
        if(spriteMap == null) return null;
        if(spriteMap.texture == null) return null;

        return spriteMap.texture;
    }
}
