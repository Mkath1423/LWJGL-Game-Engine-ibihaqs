package engine.renderer;

import engine.geometry.Quad;

/**
 * A Sprite 
 * 
 * @author Lex Stapleton
 */
public class Sprite {
    /**
     * The uv coordinates of the texture
     */
    public Quad uvCoordinates;
    
    /**
     * The texture to use
     */
    public Texture texture;

    /**
     * Constructs a new Sprite
     * 
     * @param texture The texture to use
     * @param uvCoordinates The uv coordinates of the texture
     */
    public Sprite(Texture texture, Quad uvCoordinates){
        this.texture = texture;
        this.uvCoordinates = uvCoordinates;
    }
}
