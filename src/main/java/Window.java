import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector2f;
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

import Inputs.KeyListener;
import Inputs.MouseListener;
import Inputs.Input;
import Inputs.InputAxis;
import Inputs.KeyListener;
import Inputs.MouseListener;
import Inputs.Input.KeyCode;
import renderer.Shader;
import renderer.Texture;
import util.Time;

public class Window {
    private int width, height;
    private String title;
    // Image thumbnail

    private long glfwWindow;

    private static Window window = null;

    public Camera camera;

    private float[] vertexArray = {
        // position               // color
         50.5f, 0f, 0.0f,         1.0f, 0.0f, 0.0f, 1.0f, 1, 1,// Bottom right 0
        0f,  50f, 0.0f,           0.0f, 1.0f, 0.0f, 1.0f, 0, 0,// Top left     1
         50.5f,  50f, 0.0f ,      0.0f, 0.0f, 1.0f, 1.0f, 1, 0,// Top right    2
        -0.5f, -0.5f, 0.0f,       1.0f, 1.0f, 0.0f, 1.0f, 0, 1 // Bottom left  3
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
        Shader s = new Shader("assets/shaders/default.glsl");
        Texture t = new Texture("assets/textures/testImage.png");

        s.compile();

        Input.addAxis("test", new InputAxis(-1, 1, 2, 2, KeyCode.W, KeyCode.S));
        
        double beginTime = Time.getTime();
        double endTime = Time.getTime();
        double deltaTime = -1;


        vaoID = ARBVertexArrayObject.glGenVertexArrays();
        ARBVertexArrayObject.glBindVertexArray(vaoID);

        // creat float buffer verticies

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // VBO and upload vertex butffer

        vboID = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboID);
        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, vertexBuffer, GL20.GL_STATIC_DRAW);

        // creat indicies and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, eboID);
        GL20.glBufferData(GL20.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL20.GL_STATIC_DRAW);
    
        // taiohkjqthjr3wqioupfhnbersadhjklfbvdahjklfhwenfbewlsfnbdsalfh2   rfhdjnsdk;azvbsdjiapfhw
        int positionSize = 3;
        int colorSize = 4;
        int uvSize = 2;

        int vertexSizeBytes = (positionSize + colorSize + uvSize) * Float.BYTES;

        GL20.glVertexAttribPointer(0, positionSize, GL20.GL_FLOAT, false, vertexSizeBytes, 0);
        GL20.glEnableVertexAttribArray(0);

        GL20.glVertexAttribPointer(1, colorSize, GL20.GL_FLOAT, false, vertexSizeBytes, positionSize*Float.BYTES);
        GL20.glEnableVertexAttribArray(1);

        GL20.glVertexAttribPointer(2, uvSize, GL20.GL_FLOAT, false, vertexSizeBytes, (positionSize + colorSize)*Float.BYTES);
        GL20.glEnableVertexAttribArray(2);

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

            s.use();

            s.uploadTexture("texSampler", 0);
            GL20.glActiveTexture(GL20.GL_TEXTURE0);
            t.bind();

            s.uploadMat4f("uProjection", camera.getProjectionMatrix());
            s.uploadMat4f("uView",       camera.getViewMatrix());
            s.uploadFloat("uTime",       (float)Time.getTime());

            camera.position.x -= 50 *deltaTime;

            GL30.glBindVertexArray(vaoID);

            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);

            GL20.glDrawElements(GL30.GL_TRIANGLES, elementArray.length, GL30.GL_UNSIGNED_INT, 0);

            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);

            GL30.glBindVertexArray(0);
            t.unbind();
            s.detach();
            
            GLFW.glfwSwapBuffers(glfwWindow);
            
            endTime = Time.getTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
