package engine.util;

import org.lwjgl.glfw.GLFW;

/**
 * Tracks the time
 */
public class Time {
    /**
     * the time the program was started
     */
    private static double timeStarted = GLFW.glfwGetTime();

    /**
     * Gets the current time
     * 
     * @return the time since the start of the program
     */
    public static double getTime() {return (System.nanoTime() - timeStarted) * 1E-9; }
}
