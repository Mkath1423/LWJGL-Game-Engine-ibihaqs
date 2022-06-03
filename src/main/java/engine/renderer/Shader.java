package engine.renderer;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

/**
 * Represent a shader
 * 
 * from tutorial: https://github.com/codingminecraft/MarioYoutube/tree/master
 * 
 * @author GamesWithGabe
 */
public enum Shader {
    SPRITE_RGB("assets/shaders/default.glsl"),
    SPRITE("assets/shaders/multitex.glsl"),
    PANEL("assets/shaders/panel.glsl");
    // SPRITE_RGBA("xxx.glsl"),
    // COLOR("xxx.glsl"),
    // SPRITE_TINT("xxx.glsl");
    
    private int shaderProgramID;
    public int getShaderId(){return shaderProgramID;}
    
    private boolean beingUsed;

    private String vertexSource;
    private String fragmentSource;

    private String filepath;

    private Shader(String path /* add VAO definition and uniform suppliers */){
        this.filepath = path;

        try{
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = source.split("(#type)( )+([a-xA-Z]+)");

            // find first pattern
            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\r\n", index);
            String firstPattern = source.substring(index, eol).trim();

            // find second patter
            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index, eol).trim();

            System.out.println(Arrays.toString(splitString));

            if(firstPattern.equals("vertex")){
                System.out.println("vertex is first");
                vertexSource = splitString[1];
            }
            else if(firstPattern.equals("fragment")){
                System.out.println("fragment is first");
                fragmentSource = splitString[1];
            }
            else{
                throw new IOException("Invalid type: " + firstPattern + " ");
            }

            if(secondPattern.equals("vertex")){
                System.out.println("vertex is second");
                vertexSource = splitString[2];
            }
            else if(secondPattern.equals("fragment")){
                System.out.println("fragment is second");
                fragmentSource = splitString[2];
            }
            else{
                throw new IOException("Invalid type: " + secondPattern + " ");
            }
        }catch(IOException e){
            e.printStackTrace();
            assert false: "error: could not open shader file" + filepath;
        }

        System.out.println("vertex source");
        System.out.println(vertexSource);

        
        System.out.println("fragment source");
        System.out.println(fragmentSource);

    }

    /**
     * Compiles and uploads the shader to the gpu
     */
    public void compile(){
        int vertexID;
        int fragmentID;

        // load and compile vertex shader
        vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);

        // pass shader to gpu
        GL20.glShaderSource(vertexID, vertexSource);
        GL20.glCompileShader(vertexID);

        // check for errors
        int success = GL20.glGetShaderi(vertexID, GL20.GL_COMPILE_STATUS);
        if(success == GL20.GL_FALSE){
            int len = GL20.glGetShaderi(vertexID, GL20.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR WITH VERTEX SHADER " + filepath + " COMPILATION");
            System.out.println(GL20.glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        // load and compile fragment shader
        fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        // pass shader to gpu
        GL20.glShaderSource(fragmentID, fragmentSource);
        GL20.glCompileShader(fragmentID);

        // check for errors
        success = GL20.glGetShaderi(fragmentID, GL20.GL_COMPILE_STATUS);
        if(success == GL20.GL_FALSE){
            int len = GL20.glGetShaderi(fragmentID, GL20.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR WITH FRAGMENT SHADER " + filepath + "COMPILATION");
            System.out.println(GL20.glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }
        
        shaderProgramID = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgramID, vertexID);
        GL20.glAttachShader(shaderProgramID, fragmentID);

        GL20.glLinkProgram(shaderProgramID);

        success = GL20.glGetProgrami(shaderProgramID, GL20.GL_LINK_STATUS);
        if(success == GL20.GL_FALSE){
            int len = GL20.glGetProgrami(shaderProgramID, GL20.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR IN SHADER " + filepath + " LINKING");
            System.out.println(GL20.glGetProgramInfoLog(shaderProgramID, len));
            assert false : "";
        }
    }

    /**
     * Tells the gpu to use this shader if not already
     */
    public void use(){
        if(!beingUsed){
            GL20.glUseProgram(shaderProgramID);
            beingUsed = true;
        }
    }

    /**
     * Tells the gpu to stop using this shader
     */
    public void detach(){
        GL20.glUseProgram(0);
        beingUsed = false;
    }

    // SECTION: UNIFORM UPLOADS

    /**
     * Uploads a Matrix4f
     * 
     * @param varName the name of the uniform
     * @param mat4 the data to pass in
     */
    public void uploadMat4f(String varName, Matrix4f mat4){
        int varLocation = GL20.glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);

        GL20.glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    
    /**
     * Uploads a Matrix3f
     * 
     * @param varName the name of the uniform
     * @param mat3 the data to pass in
     */
    public void uploadMat3f(String varName, Matrix3f mat3){
        int varLocation = GL20.glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
        mat3.get(matBuffer);

        GL20.glUniformMatrix3fv(varLocation, false, matBuffer);
    }

    /**
     * Uploads a Vector4f
     * 
     * @param varName the name of the uniform
     * @param vec4f the data to pass in
     */
    public void uploadVec4f(String varName, Vector4f vec4f){
        int varLocation = GL20.glGetUniformLocation(shaderProgramID, varName);
        use();
        GL20.glUniform4f(varLocation, vec4f.x, vec4f.y, vec4f.z, vec4f.w);
    }

    /**
     * Uploads a Vector2f
     * 
     * @param varName the name of the uniform
     * @param vec2f the data to pass in
     */
    public void uploadVec2f(String varName, Vector2f vec2f){
        int varLocation = GL20.glGetUniformLocation(shaderProgramID, varName);
        use();
        GL20.glUniform2f(varLocation, vec2f.x, vec2f.y);
    }

    /**
     * Uploads a float
     * 
     * @param varName the name of the uniform
     * @param val the data to pass in
     */
    public void uploadFloat(String varName, Float val){
        int varLocation = GL20.glGetUniformLocation(shaderProgramID, varName);
        use();
        GL20.glUniform1f(varLocation, val);
    }

    /**
     * Uploads an int
     * 
     * @param varName the name of the uniform
     * @param val the data to pass in
     */
    public void uploadInt(String varName, int val){
        int varLocation = GL20.glGetUniformLocation(shaderProgramID, varName);
        use();
        GL20.glUniform1f(varLocation, val);
    }

    /**
     * Uploads an array of ints
     * 
     * @param varName the name of the uniform
     * @param array the data to pass in
     */
    public void uploadIntArray(String varName, int[] array) {
        int varLocation = GL20.glGetUniformLocation(shaderProgramID, varName);
        use();
        GL20.glUniform1iv(varLocation, array);
    }
}
