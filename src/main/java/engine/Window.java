package engine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

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

import engine.Inputs.Input;
import engine.Inputs.KeyListener;
import engine.Inputs.MouseListener;
import engine.components.SpriteRenderer;
import engine.components.Transform;
import engine.gameobjects.GameObject;
import engine.renderer.DummyBatch;
import engine.renderer.EBOFormat;
import engine.renderer.QuadRenderer;
import engine.renderer.RenderBatch;
import engine.renderer.Renderer;
import engine.renderer.Shader;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.renderer.Texture.Format;
import engine.renderer.VAO.VAOFormat;
import engine.scenes.SceneManager;
import engine.util.Time;

public class Window {
    private int width, height;

    public static Vector2f getSize(){
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);

        GLFW.glfwGetWindowSize(get().glfwWindow, w, h);

        return new Vector2f(w.get(0), h.get(0));
    }

    private String title;
    // Image thumbnail

    private long glfwWindow;

    private static Window window = null;


    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Grapple Demo";
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
        
        GLFW.glfwSetInputMode(glfwWindow, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);

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
    
    private float[] vertexArray = {
        // position               // color
        0f, 0f, 0.0f,         0.0f, 0.0f, 0.0f,
        500f,  0f, 0.0f,      1.0f, 0.0f, 0.0f,
        0f,  500f, 0.0f ,     0.0f, 1.0f, 0.0f, 
        500f, 500f, 0.0f,     1.0f, 1.0f, 0.0f, 
    };

    private float[] vertexArray2 = {
        // position               // color
        300f, 300f, 0.0f,         0.0f, 0.0f, 0.0f,
        800f,  300f, 0.0f,      1.0f, 0.0f, 0.0f,
        300f,  800f, 0.0f ,     0.0f, 1.0f, 0.0f, 
        800f, 800f, 0.0f,     1.0f, 1.0f, 0.0f, 
    };

    // IMPORTANT: Must be in counter-clockwise order
    private int[] elementArray = {
            0, 2, 1,      // Top right triangle tr-tl-br
            1, 2, 3   // bottom left triangle br-tl-bl
    };

    int vaoID, vboID, eboID;
    int vaoID2, vboID2, eboID2;

    public void loop(){

        System.out.println(GL20.glGetInteger(GL20.GL_MAX_TEXTURE_IMAGE_UNITS));
        double beginTime = Time.getTime();
        double endTime = Time.getTime();
        double deltaTime = -1;

        while(!GLFW.glfwWindowShouldClose(glfwWindow)){
            // events
            GLFW.glfwPollEvents();
            Input.Update(0.2f);

            SceneManager.Update(deltaTime);

            // background
            GL11.glClearColor(1f, 0.98f, 0.84f, 0);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            Renderer.draw();

            GLFW.glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;
        }
    }

    public void setWindowTitle(String windowTitle){
        this.title = windowTitle;
    }
    
}