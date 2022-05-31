package engine.components;

import org.joml.Vector3f;

import engine.renderer.EBOFormat;
import engine.renderer.Shader;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.renderer.VAO.VAOFormat;

public class SpriteRenderer extends Renderable{

    private SpriteMap spriteMap;
    private int currentSprite;

    private Transform transform;

    public SpriteRenderer(SpriteMap spriteMap, int layer){
        super(Shader.SPRITE, VAOFormat.SPRITE, EBOFormat.QUAD, 1, layer);

        this.spriteMap = spriteMap;
    }

    @Override
    public void Awake(){
        transform = gameObject.getComponent(Transform.class);
        super.Awake();
    }

    @Override
    public void loadVertexData(float[] buffer, int start) {
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
    }

    @Override
    public Texture getTexture() {
        return spriteMap.texture;
    }
}
