package physics;

import javafx.scene.shape.Rectangle;

public class Collision {



    /**
     * Given a circle and coordinates, return true if the coordinates exist within the
     * boundaries of the circle
     * 
     * @param circle    (Circle) The circle in question
     * @param x         (int) The x coordinate of the point
     * @param y         (int) The y coordinate of the point
     * @return          (boolean) Is the point within the circle?
     */
    public static boolean circlePoint(Circle circle, int x, int y) {

        // The coordinates of the center of the circle
        int cX = circle.getX();
        int cY = circle.getY();

        // The radius of the circle
        int radius = circle.getRadius();
    
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
     * @param offset    (int) An optional distance to offset by
     * 
     * @return          (boolean) Do the shapes collide within the given offset range
     */
    public static boolean rectCircle(Rectangle rect, Circle circle, int offset) {

        // The coordinates of the upper-left corner of the rectangle
        int rX = rect.getX();
        int rY = rect.getY();

        // The height and width of the rectangle
        int rWidth  = rect.getWidth();
        int rHeight = rect.getHeight();

        // The coordinates of the center of the circle
        int cX = circle.getX();
        int cY = circle.getY();

        // The radius of the circle
        int radius = circle.getRadius();

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
     * @param x     (int) The x coordinate of the point
     * @param y     (int) The y coordinate of the point
     * 
     * @return      (boolean) Is the point within the rectangle?
     */
    public static boolean rectPoint(Rectangle rect, int x, int y) {

        // The coordinates of the upper-left corner of the rectangle
        int rX = rect.getX();
        int rY = rect.getY();

        // The height and width of the rectangle
        int rWidth  = rect.getWidth();
        int rHeight = rect.getHeight();
        
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
    public static boolean rectRect(Rectangle rect1, Rectangle rect2, int offset) {

        // The coordinates of the upper-left corner of rectangle 1 & 2
        int rX1 = rect1.getX();
        int rY1 = rect1.getY();

        int rX2 = rect2.getX();
        int rY2 = rect2.getY();

        // The height and width of rectangle 1 & 2
        int rWidth1  = rect1.getWidth();
        int rHeight1 = rect1.getHeight();

        int rWidth2  = rect2.getWidth();
        int rHeight2 = rect2.getHeight();

        // Return true if all of the following conditions are met
        return(
            (rX1 + rWidth1) >= (rX2 - offset) &&
            (rX1 - offset) <= (rX2 + rWidth2) &&
            (rY1 + rHeight1) >= (rY2 - offset) &&
            (rY1 - offset) <= (rY2 + rHeight2)
        );
    }



}
