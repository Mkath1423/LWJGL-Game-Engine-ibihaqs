package engine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.windows.INPUT;

import engine.Inputs.Input;
import engine.Inputs.InputAxis;
import engine.Inputs.KeyListener;
import engine.Inputs.MouseListener;
import engine.components.SpriteRenderer;
import engine.components.Transform;
import engine.gameobjects.GameObject;
import engine.renderer.Camera;
import engine.renderer.EBO;
import engine.renderer.RenderBatch;
import engine.renderer.Renderer;
import engine.renderer.Shader;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.renderer.VAO;
import engine.scenes.SceneManager;
import engine.util.Time;

public class Window {
    private int width, height;
    private String title;
    // Image thumbnail

    private long glfwWindow;

    private static Window window = null;

    public Camera camera;

    private float[] vertexArray = {
        // position               // color
         50.5f, 0f, 0.0f,          1, 1,// Bottom right 0
         0f,  50f, 0.0f,           0, 0,// Top left     1
         50.5f,  50f, 0.0f ,       1, 0,// Top right    2
        -0.5f, -0.5f, 0.0f,        0, 1 // Bottom left  3
    };

    // IMPORTANT: Must be in counter-clockwise order
    private int[] elementArray = {
            2, 1, 0, // Top right triangle
            0, 1, 3 // bottom left triangle
    };

    private int vaoID, vboID, eboID;

    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Pocket Planets";

        camera = new Camera(new Vector2f());
    }

    public static Window get(){
        if(Window.window == null){
            window = new Window();
        }

        return Window.window;
    }

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

        for (Shader s : Shader.values()) {
            s.compile();
        }

        // enable alpha blending
        GL20.glEnable(GL20.GL_BLEND);
        GL20.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        // enable antialiasing 
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);
        GL20.glEnable(GL20.GL_MULTISAMPLE); 
    }

    public void loop(){

        System.out.println(GL20.glGetInteger(GL20.GL_MAX_TEXTURE_IMAGE_UNITS));

        while(!GLFW.glfwWindowShouldClose(glfwWindow)){
            // events
            GLFW.glfwPollEvents();
            Input.Update(0.2f);

            SceneManager.Update(0.01);

            // background
            GL11.glClearColor(1f, 0.98f, 0.84f, 0);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            Renderer.draw();

            GLFW.glfwSwapBuffers(glfwWindow);
        }
    }
}