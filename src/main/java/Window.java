import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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

import javafx.scene.input.KeyCode;
import util.Time;

public class Window {
    private int width, height;
    private String title;
    // Image thumbnail

    private long glfwWindow;

    private static Window window = null;

    private String vertexShaderSource = "#version 330 core\n" +
                                        "layout (location=0) in vec3 aPos;\n" +
                                        "layout (location=1) in vec4 aColor;\n" +
                                        "\n" +
                                        "out vec4 fColor;\n" +
                                        "\n" +
                                        "void main()\n" +
                                        "{\n" +
                                        "    fColor = aColor;\n" +
                                        "    gl_Position = vec4(aPos, 1.0);\n" +
                                        "}\n";

    private String fragmentShaderSource = "#version 330 core\n"+
                                          "\n"+
                                          "in vec4 fColor;\n"+
                                          "\n"+
                                          "out vec4 color;\n"+
                                          "\n"+
                                          "void main()\n"+
                                          "{\n"+
                                          "    color = fColor;\n"+
                                          "}\n";


    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray = {
        // position               // color
         0.5f, -0.5f, 0.0f,       1.0f, 0.0f, 0.0f, 1.0f, // Bottom right 0
        -0.5f,  0.5f, 0.0f,       0.0f, 1.0f, 0.0f, 1.0f, // Top left     1
         0.5f,  0.5f, 0.0f ,      0.0f, 0.0f, 1.0f, 1.0f, // Top right    2
        -0.5f, -0.5f, 0.0f,       1.0f, 1.0f, 0.0f, 1.0f, // Bottom left  3
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

        // temp
    
        // load and compile vertex shader
        vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);

        // pass shader to gpu
        GL20.glShaderSource(vertexID, vertexShaderSource);
        GL20.glCompileShader(vertexID);

        // check for errors
        int success = GL20.glGetShaderi(vertexID, GL20.GL_COMPILE_STATUS);
        if(success == GL20.GL_FALSE){
            int len = GL20.glGetShaderi(vertexID, GL20.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR WITH VERTEX SHADER COMPILATION");
            System.out.println(GL20.glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        // load and compile fragment shader
        fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        // pass shader to gpu
        GL20.glShaderSource(fragmentID, fragmentShaderSource);
        GL20.glCompileShader(fragmentID);

        // check for errors
        success = GL20.glGetShaderi(fragmentID, GL20.GL_COMPILE_STATUS);
        if(success == GL20.GL_FALSE){
            int len = GL20.glGetShaderi(fragmentID, GL20.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR WITH FRAGMENT SHADER COMPILATION");
            System.out.println(GL20.glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }
        
        shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vertexID);
        GL20.glAttachShader(shaderProgram, fragmentID);

        GL20.glLinkProgram(shaderProgram);

        success = GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS);
        if(success == GL20.GL_FALSE){
            int len = GL20.glGetProgrami(shaderProgram, GL20.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR IN SHADER LINKING");
            System.out.println(GL20.glGetProgramInfoLog(shaderProgram, len));
            assert false : "";
        }

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

        int floatByteSize = 4;
        int vertexSizeBytes = (positionSize + colorSize) * floatByteSize;

        GL20.glVertexAttribPointer(0, positionSize, GL20.GL_FLOAT, false, vertexSizeBytes, 0);
        GL20.glEnableVertexAttribArray(0);

        GL20.glVertexAttribPointer(1, colorSize, GL20.GL_FLOAT, false, vertexSizeBytes, positionSize*floatByteSize);
        GL20.glEnableVertexAttribArray(1);

        while(!GLFW.glfwWindowShouldClose(glfwWindow)){
            // events
            GLFW.glfwPollEvents();

            // background
            GL11.glClearColor(0, 0, 0, 0);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);


            GL20.glUseProgram(shaderProgram);
            GL30.glBindVertexArray(vaoID);

            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);

            GL20.glDrawElements(GL30.GL_TRIANGLES, elementArray.length, GL30.GL_UNSIGNED_INT, 0);

            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);

            GL30.glBindVertexArray(0);
            GL20.glUseProgram(0);

            
            GLFW.glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
