package engine.components;

import org.joml.Vector3f;

import engine.renderer.Color;
import engine.renderer.EBOFormat;
import engine.renderer.Shader;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.renderer.VAO.VAOFormat;

public class SpriteRenderer extends Renderable{

    /**
     * The sprite map and sprite index to be rendered
     */
    private SpriteMap spriteMap;
    private int currentSprite;

    /**
     * Sets the sprite map
     * @param spriteMap the new sprite map
     */
    public void setSpriteMap(SpriteMap spriteMap){
        this.spriteMap = spriteMap;
    }

    /**
     * Sets the index of the current sprite
     * 
     * @param spriteIndex the index in the sprite map
     */
    public void setSprite(int spriteIndex){
        this.currentSprite = spriteIndex;
    }

    /**
     * The color to be rendered
     */
    private Color color;

    /**
     * Sets the color to be rendered
     * 
     * @param color the color to be rendered
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * The bounding box of this GO
     */
    private Transform transform;

    /**
     * If true the solid color will be rendered instead of the sprite
     */
    private boolean useColor;
    public void setIsUI(boolean isUI){ this.isUI = isUI;}

    /**
     * if true the renderable will be rendered is a static position on the screen
     */
    private boolean isUI;
    public void setUseColor(boolean useColor){ this.useColor = useColor;}

    /**
     * if true the renderable will be rendered as a solid color mask of the sprite
     */
    private boolean doMasking;
    public void setMasking(boolean doMasking){ this.doMasking = doMasking;}

    public SpriteRenderer(SpriteMap spriteMap, Color color, int layerID){
        super(Shader.SPRITE, VAOFormat.SPRITE, EBOFormat.QUAD, 1, layerID);

        this.spriteMap = spriteMap;
        this.color = color;
        
        this.useColor = false;
        this.isUI = false;
        this.doMasking = false;
    }

    @Override
    public void Awake(){
        transform = gameObject.getComponent(Transform.class);
        super.Awake();
    }

    @Override
    public void loadVertexData(float[] buffer, int start) {
        // get the vertices and tex coordinates
        Vector3f[] vertices = transform.getQuad().getVertices();

        Vector3f[] uvVertices = null;
        if(spriteMap != null){
            uvVertices = spriteMap.getSprite(currentSprite).uvCoordinates.getVertices();
        }
        
        // write vertex attributes to the buffer
        for(int i = 0; i < 4; i ++){
            // write position data
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 0] = vertices[i].x;
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 1] = vertices[i].y;
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 2] = vertices[i].z;

            if(color != null){
                // write color data
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 3] = color.getRed();
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 4] = color.getGreen();
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 5] = color.getBlue();
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 6] = color.getAlpha();
            }

            if(uvVertices != null){
                // write texture data
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 7] = uvVertices[i].x; 
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 8] = uvVertices[i].y;

                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 9] = texSlot;
            }

            // write extra params encoded as a float
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 10] = (useColor ? 1 : 0) + (!isUI ? 2 : 0) + (doMasking ? 4 : 0);
        }
    }

    @Override
    public Texture getTexture() {
        if(useColor) return null;
        if(spriteMap == null) return null;

        return spriteMap.texture;
    }
}
