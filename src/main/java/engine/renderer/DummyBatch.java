package engine.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.AssetManager;
import engine.renderer.VAO.VAOFormat;
import engine.scenes.SceneManager;

public class DummyBatch {

    private float[] vertexArray;
    private int[] elementArray;

    private int vaoID, vboID, eboID;

    private VAO vao;
    
    private VBO vbo;

    private Texture t;

    public DummyBatch(float[] vertexArray, int[] elementArray){
        this.vertexArray = vertexArray;
        this.elementArray = elementArray;
        
        t = AssetManager.getTexture("assets/textures/testImage.png");

        start();
    }

    public void start(){
        vao = new VAO(VAOFormat.SPRITE);
        vao.bind();
        // creat float buffer verticies

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // VBO and upload vertex butffer

        vbo = new VBO(vertexArray.length * Float.BYTES);

        // creat indicies and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, eboID);
        GL20.glBufferData(GL20.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL20.GL_STATIC_DRAW);
    
        vao.bindPointers();
        vao.unbind();
    }

    public void render(){
        Shader.SPRITE.use();

        vbo.bind();
        vbo.bufferData(vertexArray);
        

        GL20.glActiveTexture(GL20.GL_TEXTURE0);
        t.bind();

        Shader.SPRITE.uploadMat4f("uProjection", SceneManager.getActiveMainCamera().getProjectionMatrix());
        Shader.SPRITE.uploadMat4f("uView",       SceneManager.getActiveMainCamera().getViewMatrix());
        int[] textureSlots = new int[8];
        for (int i = 0; i < textureSlots.length; i++) {
            textureSlots[i] = i;
        }

        Shader.SPRITE.uploadIntArray("uTextures", textureSlots);

        vao.enable();

        GL20.glDrawElements(GL30.GL_TRIANGLES, elementArray.length, GL30.GL_UNSIGNED_INT, 0);

        vao.disable();
        t.unbind();
        Shader.SPRITE.detach();
    }
}
