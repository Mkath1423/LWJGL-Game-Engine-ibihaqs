package engine.physics;

import engine.geometry.Quad;
import engine.geometry.Circle;

import org.joml.Vector3f;

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
        double cX = circle.getX();
        double cY = circle.getY();

        // The radius of the circle
        double radius = circle.getRadius();

        // The coordinates of the point
        double x = point.x;
        double y = point.y;

        // The distance between the point and the circle's origin
        double dX = Math.abs(cX - x);
        double dY = Math.abs(cY - y);

        // The "border" coordinates of the circle we are checking
        double bX;
        double bY;
        
        // Checking if it is out of bounds right away
        if(dX > radius) {

            return false;

        // Set it if its within bounds, and return the assessment with the y coordinate
        } else {

            bX = dX;
            bY = Math.sqrt((radius*radius) - (bX*bX));
            return (dY <= bY);

        } 
    }


    /**
     * Given a rectangle and a circle, return true if their borders would touch or they would overlap
     * 
     * @param rect      (Rectangle) The rectangle in question
     * @param circle    (Circle) The circle in question
     * @param offset    (double) An optional distance to offset by
     * 
     * @return          (boolean) Do the shapes collide within the given offset range
     */
    public static boolean rectCircle(Quad rect, Circle circle, double offset) {

        // The coordinates of the upper-left corner of the rectangle
        double rX = rect.getX();
        double rY = rect.getY();

        // The height and width of the rectangle
        double rWidth  = rect.getWidth();
        double rHeight = rect.getHeight();

        // The coordinates of the center of the circle
        double cX = circle.getX();
        double cY = circle.getY();

        // The radius of the circle
        double radius = circle.getRadius();


        // RESOLVING DISTANCE BETWEEN TOP-LEFT RECT CORNER AND CENTER OF CIRCLE


        // The x and y distance from the top-left rectangle corner to the center of the circle
        double dX;
        double dY;

        // Case 1: The rectangle is on the left
        if((rX + rWidth + offset) < cX) {

            dX = Math.abs(rX + rWidth + offset - cX);

        // Case 2: The rectangle is on the right
        } else {

            dX = Math.abs(rX + offset - cX);

        }

        // Case 1: The rectangle is up
        if((rY + rHeight + offset) < cY) {

            dY = Math.abs(rY + rHeight + offset - cY);

        // Case 2: The rectangle is down
        } else {

            dY = Math.abs(rY + offset - cY);

        }


        // RESOLVING "BORDER" COORDINATES


        double bX;
        double bY;

        // Checking if its out of bounds right away
        if(dX > radius) {

            return false;

        // Set it if its within bounds, and return the assessment with the y coordinate
        } else {

            bX = dX;
            bY = Math.sqrt((radius*radius) - (bX*bX));
            return(dY <= bY);

        }
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
        double rX = rect.getX();
        double rY = rect.getY();

        // The height and width of the rectangle
        double rWidth  = rect.getWidth();
        double rHeight = rect.getHeight();

        // The coordinates of the point
        double x = point.x;
        double y = point.y;
        
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
     * @param offset    (double) An optional distance to offset by
     * 
     * @return          (boolean) Do the rectangles collide within the given offset range?
     */
    public static boolean rectRect(Quad rect1, Quad rect2, double offset) {

        // LOGIC: Create one border rectangle whose dimensions are the sum of the dimensions of
        // both rectangles, center it on one rectangle, and check if the top left corner
        // of the other rectangle resides within that area

        // The coordinates of the upper-left corner of rectangle 1 & 2
        double rX1 = rect1.getX();
        double rY1 = rect1.getY();

        double rX2 = rect2.getX();
        double rY2 = rect2.getY();

        // The dimensions of rectangle 1 & 2
        double rWidth1  = rect1.getWidth();
        double rHeight1 = rect1.getHeight();

        double rWidth2  = rect2.getWidth();
        double rHeight2 = rect2.getHeight();

        // The x and y coordinates of the top left corner of the border rectangle, centered on rX2
        double bX = rX2 - rWidth1 - offset;
        double bY = rY2 - rHeight1 - offset;

        // The dimensions of the border rectangle
        double bHeight = rHeight1 + rHeight2 + offset;
        double bWidth  = rWidth1 + rWidth2 + offset;


        // Return true if all of the following conditions are met
        return(
            (rX1 + rWidth1) >= (bX) &&
            (rX1) <= (bX + bWidth) &&
            (rY1 + rHeight1) >= (bY) &&
            (rY1) <= (bY + bHeight)
        );
    }



}