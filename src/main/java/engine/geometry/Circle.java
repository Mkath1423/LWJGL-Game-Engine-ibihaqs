package engine.geometry;

import org.joml.Vector3f;

public class Circle {



    // INITIALIZATION



    public Vector3f origin;
    public float radius;

    
    /**
     * Constructor for circle
     * 
     * @param o    (Vector3f) The x, y, and z coordinates of the center point
     * @param r    (float) The radius
     */
    public Circle(Vector3f o, float r){

        origin = o;
        radius = r;

        // Defining a circle as a square tangent to the diameter, in the order:
        //  topLeft
        //  topRight
        //  bottomLeft
        //  bottomRight
        new Quad(
            new Vector3f(origin.x - radius, origin.y - radius, origin.z), 
            new Vector3f(origin.x + radius, origin.y - radius, origin.z), 
            new Vector3f(origin.x - radius, origin.y + radius, origin.z), 
            new Vector3f(origin.x + radius, origin.y + radius, origin.z)
        );
    }



    // METHODS



    /**
     * Return the radius of the circle
     * 
     * @return  (float) The radius of the circle
     */
    public float getRadius() {

        return radius;
    }
    
    
    /**
     * Return the x coordinate of the origin point
     * 
     * @return  (float) The x coordinate of the center of the circle
     */
    public float getX() {

        return this.origin.x;
    }


    /**
     * Return the y coordinate of the origin point
     * 
     * @return  (float) The y coordinate of the center of the circle
     */
    public float getY() {

        return this.origin.y;
    }
    
    

}
