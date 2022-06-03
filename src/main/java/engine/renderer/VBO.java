package engine.renderer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

/**
 * Represents a VBO
 * 
 * @author Lex Stapleton
 */
public class VBO {
    /**
     * The ID of the buffer
     */
    private int ID;
    public int getID(){return ID;}

    /**
     * Generates the buffer for the vbo
     * 
     * @return the buffer id
     */
    private int genBuffer(){
        return GL20.glGenBuffers();
    }

    public VBO(int sizeBytes){
        ID = genBuffer();
        bind();
        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, sizeBytes, GL20.GL_DYNAMIC_DRAW);

    }

    /**
     * Binds the vbo
     */
    public void bind(){
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, ID);
    }

    /**
     * Unbinds the vbo 
     */
    public void unbind(){
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
    }

    /**
     * Sends data to the gpu over the vbo
     * 
     * @param data the data to send
     */
    public void bufferData(float[] data){
        bind();

        // add data to a buffer
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(data.length);
        verticesBuffer.put(data).flip();
        
        // send the data
        GL20.glBufferSubData(GL20.GL_ARRAY_BUFFER, 0, verticesBuffer);
        
        unbind();
    }


}
