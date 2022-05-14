package engine.Inputs;

import org.lwjgl.glfw.GLFW;

/**
 * Listens for an handles mouse events
 */
public class MouseListener {
    /**
     * Singleton construction
     */
    private static MouseListener instance;

    /**
     * Constructs with default values
     */
    private MouseListener(){
        this.scrollX = 0;
        this.scrollY = 0;
        this.xPos = 0;
        this.yPos = 0;
        this.lastX = 0;
        this.lastY = 0;
    }

    /**
     * Returns the static singleton
     * 
     * Instantiates the singleton is it hasn't already
     * 
     * @return the static singleton
     */
    public static MouseListener get(){
        if(MouseListener.instance == null){
            MouseListener.instance = new MouseListener();
        }

        return MouseListener.instance;
    }

    /**
     * position of the scroll wheel
     */
    private double scrollX, scrollY;

    /**
     * The position of the mouse
     */
    private double xPos, yPos, lastX, lastY;

    /**
     * True if the mouse is drgging
     */
    private boolean isDragging;

    /**
     * Value of mouse buttons
     */
    private boolean mouseButtonPressed[] = new boolean[11];
    

    /**
     * getters for the previous values
     */
    public static double getMouseX() { return get().xPos; }
    public static double getMouseY() { return get().yPos; }

    public static double getLastX() { return get().lastX; }
    public static double getLastY() { return get().lastY; }
    
    public static double getDeltaX() { return get().xPos - get().lastX; }
    public static double getDeltaY() { return get().yPos - get().lastY; }

    public static double getScrollX() { return get().scrollX; }
    public static double getScrollY() { return get().scrollY; }
    
    public static boolean getDragging() {return get().isDragging; }

    public static boolean getMouseButtonDown(int button){
        if(button >= get().mouseButtonPressed.length) return false;
        return get().mouseButtonPressed[button];
    }

    /**
     * The callback for the mouse position listener
     * 
     * @param window the window
     * @param x the new x position
     * @param y the new y position
     */
    public static void mousePosCallback(long window, double x, double y){
        get().lastX = get().xPos;
        get().lastY = get().yPos;

        get().xPos = x;
        get().yPos = y;

        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1]; 
    }

    /**
     * The callback for the mouse button listener
     * 
     * @param window the window
     * @param button the mouse button being changed
     * @param action pressed/released
     * @param mods modifier keys
     */
    public static void mouseButtonCallback(long window, int button, int action, int mods){
        if(button >= get().mouseButtonPressed.length) return;

        if(action == GLFW.GLFW_PRESS){
            get().mouseButtonPressed[button] = true;
        }
        if(action == GLFW.GLFW_RELEASE){
            get().mouseButtonPressed[button] = false;
            get().isDragging = false;
        }
    }

    /**
     * The callback for the scroll position listener
     * 
     * @param window the window
     * @param x the new x position
     * @param y the new y position
     */
    public static void mouseScrollCallback(long window, double x, double y){
        get().scrollX = x;
        get().scrollY = y;
    }

    // i don't remember why this exists
    // and if you think it was probably a good reason...
    // you are wrong
    // public static void endFrame(){
    //     get().scrollX = 0;
    //     get().scrollY = 0;

    //     get().lastX = get().xPos;
    //     get().lastY = get().yPos;
    // }
}
