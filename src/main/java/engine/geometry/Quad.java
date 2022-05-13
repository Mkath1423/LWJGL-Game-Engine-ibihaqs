package engine.geometry;

import org.joml.Vector3f;

public class Quad {



    // INITIALIZATION



    public Vector3f topLeft;
    public Vector3f topRight;
    public Vector3f bottomLeft;
    public Vector3f bottomRight;

    public Vector3f[] getVertices(){return new Vector3f[] {topLeft, topRight, bottomLeft, bottomRight};}

    public Quad(Vector3f topLeft, Vector3f topRight, Vector3f bottomLeft, Vector3f bottomRight){
        
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    public static Quad Rect(Vector3f topLeft, float width, float height){
        
        return new Quad(
            new Vector3f(topLeft), 
            new Vector3f(topLeft.x + width, topLeft.y,          topLeft.z), 
            new Vector3f(topLeft.x,         topLeft.y + height, topLeft.z),  
            new Vector3f(topLeft.x + width, topLeft.y + height, topLeft.z)
        );
    }

    public static Quad Square(Vector3f topLeft, float length){
        return Rect(topLeft, length, length);
    }



    // METHODS



    /**
     * Assuming a Rectangular shape, return the y distance between the two left corners
     * 
     * @return  (float) The height of the shape
     */
    public float getHeight() {

        return Math.abs(this.topLeft.y - this.bottomLeft.y);
    }
    
    
    /**
     * Assuming a Rectangular shape, return the x distance between the two top corners
     * 
     * @return (float) The width of the shape
     */
    public float getWidth() {

        return Math.abs(this.topLeft.x - this.topRight.x);
    }


    /**
     * Get the x coordinate of the top left corner of a shape
     * 
     * @return  (float) The x coordinate
     */
    public float getX() {
        
        return this.topLeft.x;
    }


    /**
     * Get the y coordinate of the top left corner of a shape
     * 
     * @return  (float) The x coordinate
     */
    public float getY() {
        
        return this.topLeft.y;
    }


    
}
