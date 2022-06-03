package engine;

import java.nio.IntBuffer;

import org.joml.Vector2f;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.Callbacks;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import org.lwjgl.system.MemoryUtil;

import engine.Inputs.Input;
import engine.Inputs.KeyListener;
import engine.Inputs.MouseListener;

import engine.renderer.Renderer;
import engine.renderer.Shader;

import engine.scenes.SceneManager;

import engine.util.Time;

/**
 * The window itself
 */
public class Window {



    // Image thumbnail


    /**
     * Singleton instance
     */
    private static Window window = null;
    private long glfwWindow;

    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Skrunk Game Engine";
    }

    /**
     * window properties
     */
    private boolean isResizable;
    private boolean isMaximized;
    private boolean showCursor;
    private boolean doMultiSampling;
    
    /**
     * the tile of the window
     */
    private String title;

        /**
     * The size of the window
     */
    private int width, height;

    /**
     * Gets the size of the widow
     * 
     * @return the width and height
     */
    public static Vector2f getSize(){
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);

        GLFW.glfwGetWindowSize(get().glfwWindow, w, h);

        return new Vector2f(w.get(0), h.get(0));
    }

    public static Window get(){
        if(Window.window == null){
            window = new Window();
        }

        return Window.window;
    }

    /**
     * runs the loop and frees memory when done
     */
    public void run(){
        System.out.println(Version.getVersion());

        loop();

        // free the memory

        Callbacks.glfwFreeCallbacks(glfwWindow);
        GLFW.glfwDestroyWindow(glfwWindow);

        // Terminate things
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    /**
     * Initializes the window
     */
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
        
        GLFW.glfwSetInputMode(glfwWindow, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);

        // compinle all shaders
        for (Shader s : Shader.values()) {
            s.compile();
        }

        // enable alpha blending
        GL20.glEnable(GL20.GL_BLEND);
        GL20.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        // enable antialiasing 
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 16);
        GL20.glEnable(GL20.GL_MULTISAMPLE); 

    }
    
    /**
     * Handle the main game loop
     */
    public void loop(){

        // track the time between frames
        double beginTime = Time.getTime();
        double endTime = Time.getTime();
        double deltaTime = -1;

        while(!GLFW.glfwWindowShouldClose(glfwWindow)){
            // events
            GLFW.glfwPollEvents();
            Input.Update(0.2f);

            // update the gameobjects
            SceneManager.Update(deltaTime);

            // clear the background
            GL11.glClearColor(1f, 1f, 1f, 0);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            // render the renderables
            Renderer.draw();

            GLFW.glfwSwapBuffers(glfwWindow);

            // update thetime between frames
            endTime = Time.getTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;
        }
    }

    public void setWindowTitle(String windowTitle){
        this.title = windowTitle;
    }
    
}