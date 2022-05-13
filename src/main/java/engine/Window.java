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

        // // set up callbacks
        // GLFW.glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        // GLFW.glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        // GLFW.glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        // GLFW.glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);


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

        for (VAO vao : VAO.values()) {
            vao.init();
        }
        
        
    }

    public void loop(){
        Texture t = new Texture("assets/textures/testImage.png");

        SpriteMap sp = new SpriteMap(t, 1, 1);
    
        GameObject go = new GameObject();
            go.addComponent(new Transform(
                new Vector3f(0, 0, 0),
                new Vector2f(100, 100),
                0
            ));
            go.addComponent(new SpriteRenderer(sp));

        go.Awake();

        GameObject go2 = new GameObject();
            go2.addComponent(new Transform(
                new Vector3f(0, 300, 0),
                new Vector2f(100, 100),
                0
            ));
            go2.addComponent(new SpriteRenderer(sp));

        go2.Awake();

        // RenderBatch batch = new RenderBatch(1, Shader.SPRITE_RGB, VAO.SPRITE, EBO.QUAD);
        //     batch.addRenderable(go.getComponent(SpriteRenderer.class));
            

        // // creat float buffer verticies

        // FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        // vertexBuffer.put(vertexArray).flip();

        // VBO and upload vertex butffer

        // vboID = GL20.glGenBuffers();
        // System.out.println("testing vboID " + vboID);
        // GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboID);
        // GL20.glBufferData(GL20.GL_ARRAY_BUFFER, 48, GL20.GL_DYNAMIC_DRAW);

        // creat indicies and upload
        // IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        // elementBuffer.put(elementArray).flip();

        // eboID = GL20.glGenBuffers();
        // GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, eboID);
        // GL20.glBufferData(GL20.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL20.GL_STATIC_DRAW);
    
        // batch.start();
        

        while(!GLFW.glfwWindowShouldClose(glfwWindow)){
            // events
            GLFW.glfwPollEvents();

            // background
            GL11.glClearColor(1, 1, 1, 0);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            // float[] nextVertexArray = new float[20];
            // go.getComponent(SpriteRenderer.class).loadVertexData(nextVertexArray, 0);

            // FloatBuffer nextvertexBuffer = BufferUtils.createFloatBuffer(nextVertexArray.length);
            // nextvertexBuffer.put(nextVertexArray).flip();

            // GL20.glBufferData(GL20.GL_ARRAY_BUFFER, nextvertexBuffer, GL20.GL_DYNAMIC_DRAW);

            // Shader.SPRITE_RGB.use();

            // Renderer.UploadUniforms(Shader.SPRITE_RGB);
            


            // VAO.SPRITE.enable();

            // GL20.glDrawElements(GL30.GL_TRIANGLES, elementArray.length, GL30.GL_UNSIGNED_INT, 0);

            // VAO.SPRITE.disable(); 

            GL20.glActiveTexture(GL20.GL_TEXTURE0);
            t.bind();
            Renderer.draw();
            t.unbind();


            go.getComponent(Transform.class).position.x += 0.5;
            
            GLFW.glfwSwapBuffers(glfwWindow);

        }
    }
}