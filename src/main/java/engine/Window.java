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

        // set up alpha blending


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

        GL20.glEnable(GL20.GL_BLEND);
        GL20.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);


        for (Shader s : Shader.values()) {
            s.compile();
        }

        for (VAO vao : VAO.values()) {
            vao.init();
        }
        
        
    }

    public void loop(){
        Texture t = new Texture("assets/textures/testImage.png");


        Input.addAxis("horizontal", new InputAxis(-1, 1, 0.1f, 0.1f, false, 0f, Input.KeyCode.D, Input.KeyCode.LEFT, Input.KeyCode.A, Input.KeyCode.RIGHT));
        Input.addAxis("vertical", new InputAxis(-1, 1, 0.2f, 0.2f, true, 0, Input.KeyCode.W, Input.KeyCode.UP, Input.KeyCode.DOWN, Input.KeyCode.S));

        SpriteMap sp = new SpriteMap(t, 1, 1);
    
        List<GameObject> gos = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                GameObject go = new GameObject();

                go.addComponent(new Transform(
                    new Vector3f(5*(i+1), 5*(j+1), 0),
                    new Vector2f(5, 5),
                    0
                ));
                go.addComponent(new SpriteRenderer(sp));

                go.Awake();
                gos.add(go);
            }
        }
        GameObject go = new GameObject();

                go.addComponent(new Transform(
                    new Vector3f(9, 9, 0),
                    new Vector2f(50, 50),
                    0
                ));
                go.addComponent(new SpriteRenderer(sp));

                go.Awake();

        while(!GLFW.glfwWindowShouldClose(glfwWindow)){
            double beginTime = Time.getTime();
            double endTime = Time.getTime();
            double deltaTime = -1;

            // events
            GLFW.glfwPollEvents();
            Input.Update(0.2f);

            SceneManager.Update(0.01);

            // background
            GL11.glClearColor(1, 1, 1, 0);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            GL20.glActiveTexture(GL20.GL_TEXTURE0);
            t.bind();
            Renderer.draw();
            t.unbind();


            GLFW.glfwSwapBuffers(glfwWindow)
            ;
            endTime = Time.getTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;

            System.out.println(1/deltaTime + " fps");

        }
    }
}