package engine.components;

import java.util.Arrays;

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

    private Transform transform;

    private boolean useColor;
    private boolean isUI;

    public void setUseColor(boolean useColor){
        this.useColor = useColor;
    }

    public void setIsUI(boolean isUI){
        this.isUI = isUI;
    }

    public SpriteRenderer(SpriteMap spriteMap, Color color, int layer){
        super(Shader.SPRITE, VAOFormat.SPRITE, EBOFormat.QUAD, 1, layer);

        this.spriteMap = spriteMap;
        this.color = color;
        this.useColor = false;
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
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 0] = vertices[i].x;
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 1] = vertices[i].y;
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 2] = vertices[i].z;

            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 3] = color.getRed();
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 4] = color.getGreen();
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 5] = color.getBlue();
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 6] = color.getAlpha();

            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 7] = uvVertices[i].x; 
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 8] = uvVertices[i].y;
            
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 9] = texSlot;

            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 10] = (useColor ? 1 : 0) + (isUI ? 2 : 0);

            // System.out.printf("(%s, %s) -> (%s, %s)\n", buffer[start + vao.vaoSize * i + 0], buffer[start + vao.vaoSize * i + 1], 
            // buffer[start + vao.vaoSize * i + 3], buffer[start + vao.vaoSize * i + 4]);
        }
    }

    @Override
    public Texture getTexture() {
        if(useColor) return null;
        return spriteMap.texture;
    }
}
