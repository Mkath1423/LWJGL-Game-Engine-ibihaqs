package geometry;

import org.joml.Vector3f;

public class Quad {
    private Vector3f topLeft;
    private Vector3f topRight;
    private Vector3f bottomLeft;
    private Vector3f bottomRight;

    public Quad(Vector3f topLeft, Vector3f topRight, Vector3f bottomLeft, Vector3f bottomRight){
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    public Quad Rect(Vector3f topLeft, float width, float height){
        return new Quad(
            new Vector3f(topLeft), 
            new Vector3f(topLeft.x + width, topLeft.y,          topLeft.z), 
            new Vector3f(topLeft.x,         topLeft.y + height, topLeft.z),  
            new Vector3f(topLeft.x + width, topLeft.y + height, topLeft.z));
    }

    public Quad Square(Vector3f topLeft, float length){
        return Rect(topLeft, length, length);
    }

    public void shift(){

    }

}
