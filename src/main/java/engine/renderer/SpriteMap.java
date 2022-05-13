package engine.renderer;

import org.joml.Vector3f;

import engine.geometry.Quad;

public class SpriteMap {

    private int numCol;
    private int numRow;

    private Texture texture;

    public SpriteMap(Texture texture, int numCol, int numRow){
        this.numCol = numCol;
        this.numRow = numRow;

        this.texture = texture;
    }

    public Sprite getSprite(int index){
        if(index >= numCol * numRow) return null;
        
        int x = index % numCol;
        int y = index / numCol;

        return new Sprite(
            texture,
            new Quad(
                new Vector3f( x    * 1/numCol,  y    * 1/numCol, 0), 
                new Vector3f((x+1) * 1/numCol,  y    * 1/numCol, 0), 
                new Vector3f((x+1) * 1/numCol, (y+1) * 1/numCol, 0), 
                new Vector3f( x    * 1/numCol, (y+1) * 1/numCol, 0)
            )
        );
    }
}
