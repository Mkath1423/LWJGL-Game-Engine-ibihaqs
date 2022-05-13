package engine;

import org.joml.Vector2f;
import org.lwjgl.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import engine.Inputs.KeyListener;
import engine.Inputs.MouseListener;
import engine.Inputs.Input;
import engine.renderer.Camera;
import engine.renderer.Shader;
import engine.renderer.Texture;
import engine.renderer.VAO;
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

        for(VAO vao : VAO.values()){
            vao.init();
        }

        for(Shader shader : Shader.values()){
            shader.compile();
        }
    }

    public void loop(){
        Texture t = new Texture("assets/textures/testImage.png");

        // Input.addAxis("test", new InputAxis(-1, 1, 2, 2, KeyCode.W, KeyCode.S));
        
        double beginTime = Time.getTime();
        double endTime = Time.getTime();
        double deltaTime = -1;


        while(!GLFW.glfwWindowShouldClose(glfwWindow)){
            // events
            GLFW.glfwPollEvents();
            Input.Update((float)deltaTime);
            System.out.println(Input.getAxis("test"));
            // System.out.print(KeyListener.getKeyPressed(KeyCode.W.getValue()));
            // System.out.println(KeyListener.getKeyPressed(KeyCode.S.getValue()));

            // background
            GL11.glClearColor(1, 1, 1, 0);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            GL20.glActiveTexture(GL20.GL_TEXTURE0);
            t.bind();


            camera.position.x -= 50 *deltaTime;

            // draw
            t.unbind();
            
            GLFW.glfwSwapBuffers(glfwWindow);
            
            endTime = Time.getTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
