package engine.renderer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

public class VBO {
    private int ID;

    private int sizeBytes;

    public int getID(){
        return ID;
    }

    private int genBuffer(){
        return GL20.glGenBuffers();
    }

    public VBO(int sizeBytes){
        this.sizeBytes = sizeBytes;

        ID = genBuffer();
        bind();
        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, sizeBytes, GL20.GL_DYNAMIC_DRAW);

    }

    public void bind(){
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, ID);
    }
    public void unbind(){
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
    }

    public void bufferData(float[] data){
        bind();

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(data.length);
        verticesBuffer.put(data).flip();
        
        GL20.glBufferSubData(GL20.GL_ARRAY_BUFFER, 0, verticesBuffer);
        
        unbind();
    }


}
