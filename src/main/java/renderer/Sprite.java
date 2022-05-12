package renderer;

import geometry.Quad;

public class Sprite {
    public Quad uvCoordinates;
    public Texture texture;

    public Sprite(Texture texture, Quad uvCoordinates){
        this.texture = texture;
        this.uvCoordinates = uvCoordinates;
    }
}
