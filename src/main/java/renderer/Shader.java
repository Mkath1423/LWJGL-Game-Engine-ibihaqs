package renderer;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Shader {
    private int shaderProgramID;
    
    private boolean beingUsed;

    private String vertexSource;
    private String fragmentSource;

    private String filepath;

    public Shader(String path){
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

    public void use(){
        if(!beingUsed){
            GL20.glUseProgram(shaderProgramID);
            beingUsed = true;
        }
    }

    public void detach(){
        GL20.glUseProgram(0);
        beingUsed = false;
    }

    public void uploadMat4f(String varName, Matrix4f mat4){
        int varLocation = GL20.glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);

        GL20.glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    public void uploadMat4f(String varName, Matrix3f mat3){
        int varLocation = GL20.glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
        mat3.get(matBuffer);

        GL20.glUniformMatrix3fv(varLocation, false, matBuffer);
    }

    public void uploadVec4f(String varName, Vector4f vec4f){
        int varLocation = GL20.glGetUniformLocation(shaderProgramID, varName);
        use();
        GL20.glUniform4f(varLocation, vec4f.x, vec4f.y, vec4f.z, vec4f.w);
    }

    public void uploadVec2f(String varName, Vector4f vec2f){
        int varLocation = GL20.glGetUniformLocation(shaderProgramID, varName);
        use();
        GL20.glUniform2f(varLocation, vec2f.x, vec2f.y);
    }

    public void uploadFloat(String varName, Float val){
        int varLocation = GL20.glGetUniformLocation(shaderProgramID, varName);
        use();
        GL20.glUniform1f(varLocation, val);
    }

    public void uploadInt(String varName, int val){
        int varLocation = GL20.glGetUniformLocation(shaderProgramID, varName);
        use();
        GL20.glUniform1f(varLocation, val);
    }
}
