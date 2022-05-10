package Inputs;

import org.lwjgl.glfw.GLFW;

public class MouseListener {
    private static MouseListener instance;

    private double scrollX, scrollY;

    private double xPos, yPos, lastX, lastY;

    private boolean mouseButtonPressed[] = new boolean[11];
    private boolean isDragging;

    private MouseListener(){
        this.scrollX = 0;
        this.scrollY = 0;
        this.xPos = 0;
        this.yPos = 0;
        this.lastX = 0;
        this.lastY = 0;
    }

    public static MouseListener get(){
        if(MouseListener.instance == null){
            MouseListener.instance = new MouseListener();
        }

        return MouseListener.instance;
    }

    public static void mousePosCallback(long window, double x, double y){
        get().lastX = get().xPos;
        get().lastY = get().yPos;

        get().xPos = x;
        get().yPos = y;

        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1]; 
    }

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

    public static void mouseScrollCallback(long window, double x, double y){
        get().scrollX = x;
        get().scrollY = y;
    }

    public static void endFrame(){
        get().scrollX = 0;
        get().scrollY = 0;

        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static double getMouseX() { return get().xPos; }
    public static double getMouseY() { return get().yPos; }

    public static double getLastX() { return get().lastX; }
    public static double getLastY() { return get().lastY; }
    
    public static double getDeltaX() { return get().xPos - get().lastX; }
    public static double getDeltaY() { return get().yPos - get().lastY; }

    public static double getScrollX() { return get().scrollX; }
    public static double getScrollY() { return get().scrollY; }

    public static boolean getMouseButtonDown(int button){
        if(button >= get().mouseButtonPressed.length) return false;
        return get().mouseButtonPressed[button];
    }
}
