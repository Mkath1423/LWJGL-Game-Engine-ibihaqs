package engine.components;

import org.joml.Vector3f;

import engine.renderer.Color;
import engine.renderer.EBOFormat;
import engine.renderer.Shader;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.renderer.VAO.VAOFormat;

public class SpriteRenderer extends Renderable{

    private SpriteMap spriteMap;
    private int currentSprite;

    private Color color;

    private boolean useColor = false;

    private Transform transform;

    public SpriteRenderer(SpriteMap spriteMap, Color color, int layer){
        super(Shader.SPRITE, VAOFormat.SPRITE, EBOFormat.QUAD, 1, layer);
        
        this.spriteMap = spriteMap;
        this.color = color;
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
            buffer[start + 11 * i + 0] = vertices[i].x;
            buffer[start + 11 * i + 1] = vertices[i].y;
            buffer[start + 11 * i + 2] = vertices[i].z;

            buffer[start + 11 * i + 3] = color.getRed();
            buffer[start + 11 * i + 4] = color.getGreen();
            buffer[start + 11 * i + 5] = color.getBlue();
            buffer[start + 11 * i + 6] = color.getAlpha();

            buffer[start + 11 * i + 7] = uvVertices[i].x; 
            buffer[start + 11 * i + 8] = uvVertices[i].y;
            
            buffer[start + 11 * i + 9] = texSlot;

            buffer[start + 11 * i + 10] = useColor ? 0 : 1;

            // System.out.printf("(%s, %s) -> (%s, %s)\n", buffer[start + vao.vaoSize * i + 0], buffer[start + vao.vaoSize * i + 1], 
            // buffer[start + vao.vaoSize * i + 3], buffer[start + vao.vaoSize * i + 4]);
        }
    }

    @Override
    public Texture getTexture() {
        return spriteMap.texture;
    }
}
