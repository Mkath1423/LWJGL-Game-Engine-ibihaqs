import org.lwjgl.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import javafx.scene.input.KeyCode;
import util.Time;

public class Window {
    private int width, height;
    private String title;
    // Image thumbnail

    private long glfwWindow;

    private static Window window = null;

    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Pocket Planets";
    }

    public static Window get(){
        if(Window.window == null){
            window = new Window();
        }

        return Window.window;
    }

    public void run(){
        System.out.println(Version.getVersion());

        init();
        loop();

        // free the memory

        Callbacks.glfwFreeCallbacks(glfwWindow);
        GLFW.glfwDestroyWindow(glfwWindow);

        // Terminate things
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    public void init(){
        // Setup error callback
        GLFWErrorCallback.createPrint(System.err).set();
        
        if(!GLFW.glfwInit()){
            throw new IllegalStateException("Unable to init GLFW");
        }

        // configure window
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE,   GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);

        // create window
        glfwWindow = GLFW.glfwCreateWindow(this.width, this.height, this.title, MemoryUtil.NULL, MemoryUtil.NULL);

        if(glfwWindow == MemoryUtil.NULL){
            throw new IllegalStateException("Failed to create window");
        }

        // set up callbacks
        GLFW.glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        GLFW.glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        GLFW.glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        GLFW.glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);


        // make the openGL context
        GLFW.glfwMakeContextCurrent(glfwWindow);

        GLFW.glfwSwapInterval(1);

        // show window
        GLFW.glfwShowWindow(glfwWindow);

        // init caps
        GL.createCapabilities();
    }

    public void loop(){
        double beginTime = Time.getTime();
        double endTime = Time.getTime();
        double deltaTime = -1;

        while(!GLFW.glfwWindowShouldClose(glfwWindow)){
            // events
            GLFW.glfwPollEvents();

            // background
            GL11.glClearColor(1, 0, 0, 0);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            GLFW.glfwSwapBuffers(glfwWindow);

            if(KeyListener.isKeyPressed(GLFW.GLFW_KEY_SPACE)){
                System.out.println(1/deltaTime);
            }

            endTime = Time.getTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
