package engine.util;

import org.lwjgl.glfw.GLFW;

public class Time {
    private static double timeStarted = GLFW.glfwGetTime();
    public static double getTime() {return (System.nanoTime() - timeStarted) * 1E-9; }
}
