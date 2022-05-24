package engine.physics;

import engine.geometry.Quad;
import engine.geometry.Circle;

public class Collision {



    // EXTERNAL METHODS



    /**
     * Given a circle and coordinates, return true if the coordinates exist within the
     * boundaries of the circle
     * 
     * @param circle    (Circle) The circle in question
     * @param point     (Vector3f) The coordinates of the point
     * @return          (boolean) Is the point within the circle?
     */
    public static boolean circlePoint(Circle circle, Vector3f point) {

        // The coordinates of the center of the circle
        float cX = circle.getX();
        float cY = circle.getY();

        // The radius of the circle
        float radius = circle.getRadius();

        // The coordinates of the point
        float x = point.x;
        float y = point.y;

        // The distance between the point and the circle's origin
        float dX = Math.abs(cX - x);
        float dY = Math.abs(cY - y);

        // The "border" coordinates of the circle we are checking
        if(dX > radius) {

            return false;

        } else {

            float bX = dX;
            float bY = Math.sqrt((radius*radius) - (dX*dX));

        } 
    
        // Return true if all of the following conditions are met
        return (
            x >= (cX - bX) &&
            x <= (cX + bX) &&
            y >= (cY - bY) &&
            y <= (cY + bY)
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

        // The "border" x coordinate of the circle we are checking
        // Case 1: The rectangle is on the left
        if(rX < cX) {

            dX = Math.abs(rX + rWidth + offset - cX);

        // Case 2: The rectangle is on the right
        }   else {

            dX = Math.abs(rX + offset - cX);

        }

        // The "border" y coordinate of the circle we are checking
        // Case 1: The rectangle is up
        if(rY < cY) {

            dY = Math.abs(rY + rHeight + offset - cY);

        // Case 2: The rectangle is down
        } else {

            dY = Math.abs(rY + offset - cY);

        }

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
     * @param point (Vector3f) The coordinates of the point
     * 
     * @return      (boolean) Is the point within the rectangle?
     */
    public static boolean rectPoint(Quad rect, Vector3f point) {

        // The coordinates of the upper-left corner of the rectangle
        float rX = rect.getX();
        float rY = rect.getY();

        // The height and width of the rectangle
        float rWidth  = rect.getWidth();
        float rHeight = rect.getHeight();

        // The coordinates of the point
        float x = point.x;
        float y = point.y;
        
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

        // LOGIC: Create one border rectangle whose dimensions are the sum of the dimensions of
        // both rectangles, center it on one rectangle, and check if the top left corner
        // of the other rectangle resides within that area

        // The coordinates of the upper-left corner of rectangle 1 & 2
        float rX1 = rect1.getX();
        float rY1 = rect1.getY();

        float rX2 = rect2.getX();
        float rY2 = rect2.getY();

        // The dimensions of rectangle 1 & 2
        float rWidth1  = rect1.getWidth();
        float rHeight1 = rect1.getHeight();

        float rWidth2  = rect2.getWidth();
        float rHeight2 = rect2.getHeight();

        // The x and y coordinates of the top left corner of the border rectangle, centered on rX2
        float bX = rX2 - rWidth1 - offset;
        float bY = ry2 - rHeight1 - offset;

        // The dimensions of the border rectangle
        float bHeight = rHeight1 + rHeight2 + offset;
        float bWidth  = rWidth1 + rWidth2 + offset;


        // Return true if all of the following conditions are met
        return(
            (rX1 + rWidth1) >= (bX) &&
            (rX1) <= (bX + bWidth) &&
            (rY1 + rHeight1) >= (bY) &&
            (rY1) <= (bY + bHeight)
        );
    }



}