package engine.physics;

import engine.geometry.Quad;
import engine.geometry.Circle;

public class Collision {



    /**
     * Given a circle and coordinates, return true if the coordinates exist within the
     * boundaries of the circle
     * 
     * @param circle    (Circle) The circle in question
     * @param x         (float) The x coordinate of the point
     * @param y         (float) The y coordinate of the point
     * @return          (boolean) Is the point within the circle?
     */
    public static boolean circlePoint(Circle circle, float x, float y) {

        // The coordinates of the center of the circle
        float cX = circle.getX();
        float cY = circle.getY();

        // The radius of the circle
        float radius = circle.getRadius();
    
        // Return true if all of the following conditions are met
        return (
            x >= (cX - radius) &&
            x <= (cX + radius) &&
            y >= (cY - radius) &&
            y <= (cY + radius)
        );
    }


    /**
     * Given a rectangle and a circle, return true if their borders would touch or they would overlap
     * 
     * @param rect      (Rectangle) The rectangle in question
     * @param circle    (Circle) The circle in question
     * @param offset    (float) An optional distance to offset by
     * 
     * @return          (boolean) Do the shapes collide within the given offset range
     */
    public static boolean rectCircle(Quad rect, Circle circle, float offset) {

        // The coordinates of the upper-left corner of the rectangle
        float rX = rect.getX();
        float rY = rect.getY();

        // The height and width of the rectangle
        float rWidth  = rect.getWidth();
        float rHeight = rect.getHeight();

        // The coordinates of the center of the circle
        float cX = circle.getX();
        float cY = circle.getY();

        // The radius of the circle
        float radius = circle.getRadius();

        // Return true if all of the following conditions are met
        return(
            (rX + offset) >= (cX - radius) &&
            (rX + rWidth) <= (cX + radius + offset) &&
            (rY + offset) >= (cY - radius) &&
            (rY + rHeight) <= (cY + radius + offset)
        );

    }


     /**
     * Given a rectangle and coordinates, return true if the coordinates exist within the
     * boundaries of the rectangle
     * 
     * @param rect  (Rectangle) The rectangle in question
     * @param x     (float) The x coordinate of the point
     * @param y     (float) The y coordinate of the point
     * 
     * @return      (boolean) Is the point within the rectangle?
     */
    public static boolean rectPoint(Quad rect, float x, float y) {

        // The coordinates of the upper-left corner of the rectangle
        float rX = rect.getX();
        float rY = rect.getY();

        // The height and width of the rectangle
        float rWidth  = rect.getWidth();
        float rHeight = rect.getHeight();
        
        // Return true if all of the following conditions are met
        return (
            x >= (rX) &&
            x <= (rX + rWidth) &&
            y >= (rY) &&
            y <= (rY + rHeight)
        );
    }


    /**
     * Given two rectangles, return true if their borders would touch or they would overlap
     * 
     * @param rect1     (Rectangle) The first rectangle to be compared
     * @param rect2     (Rectangle) The second rectangle to be compared
     * @param offset    (int) An optional distance to offset by
     * 
     * @return          (boolean) Do the rectangles collide within the given offset range?
     */
    public static boolean rectRect(Quad rect1, Quad rect2, float offset) {

        // The coordinates of the upper-left corner of rectangle 1 & 2
        float rX1 = rect1.getX();
        float rY1 = rect1.getY();

        float rX2 = rect2.getX();
        float rY2 = rect2.getY();

        // The height and width of rectangle 1 & 2
        float rWidth1  = rect1.getWidth();
        float rHeight1 = rect1.getHeight();

        float rWidth2  = rect2.getWidth();
        float rHeight2 = rect2.getHeight();

        // Return true if all of the following conditions are met
        return(
            (rX1 + rWidth1) >= (rX2 - offset) &&
            (rX1 - offset) <= (rX2 + rWidth2) &&
            (rY1 + rHeight1) >= (rY2 - offset) &&
            (rY1 - offset) <= (rY2 + rHeight2)
        );
    }



}