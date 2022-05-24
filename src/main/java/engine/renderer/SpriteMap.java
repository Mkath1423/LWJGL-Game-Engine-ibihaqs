package engine.renderer;

import org.joml.Vector3f;

import engine.geometry.Quad;

/**
 * A Bunch of sprites stored in one texture
 * 
 * @author Lex Stapleton
 */
public class SpriteMap {

    /**
     * The amount of cells in the sprite map
     */
    private int numCol;
    private int numRow;

    /**
     * The texture of this sprite map
     */
    public Texture texture;

    /**
     * Constructor for a sprite map
     * 
     * @param texture the texture object
     * @param numCol the amount of cells horizontally
     * @param numRow the amount of cells vertically
     */
    public SpriteMap(Texture texture, int numCol, int numRow){
        this.numCol = Math.max(1, numCol);
        this.numRow = Math.max(1, numRow);

        this.texture = texture;
    }

    /**
     * Creates a new sprite with the correct UV coords
     * 
     * @param index the position of the sprite in the map
     * @return the Sprite
     * 
     * @see Sprite
     */
    public Sprite getSprite(int index){
        if(index >= numCol * numRow || index < 0) return null;
        
        /**
         * calculate the position
         */
        int x = index % numCol;
        int y = index / numCol;

        return new Sprite(
            texture,
            new Quad( // TODO: fix the order here
                new Vector3f( x    * 1/numCol,  y    * 1/numCol, 0),
                new Vector3f((x+1) * 1/numCol,  y    * 1/numCol, 0),
                new Vector3f( x    * 1/numCol, (y+1) * 1/numCol, 0),
                new Vector3f((x+1) * 1/numCol, (y+1) * 1/numCol, 0)
            )
        );
    }
}
