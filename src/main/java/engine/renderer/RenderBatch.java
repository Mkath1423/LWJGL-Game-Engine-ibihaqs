package engine.renderer;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import engine.components.Renderable;

public class RenderBatch {
    // Vertex
    // =======
    // pos                  color                       uv
    // float, float, float, float, float, float, float, float, float,

    private Renderable[] renderables;
    private int numSprites;
    private int maxBatchSize;
    private boolean hasRoom;
    private float[] vertices;

    private int vboID;
    
    private Shader shader;
    private VAO vao;
    private EBO ebo;

    public boolean canAddRenderable(Renderable r){
        return r.isBatchable(shader, vao, ebo);
    }

    public boolean hasRoom(){
        return hasRoom;
    }

    public RenderBatch(int maxBatchSize, Shader shader, VAO vao, EBO ebo){
        this.shader = shader;
        this.vao = vao;
        this.ebo = ebo;

        this.maxBatchSize = maxBatchSize;
        this.renderables = new Renderable[maxBatchSize];
        
        vertices = new float[maxBatchSize * ebo.getNumberOfVertices() * vao.vaoSize];

        numSprites = 0;
        hasRoom = true;
    }

    public void start(){
        vboID = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboID);
        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL20.GL_DYNAMIC_DRAW);

        // int eboID = GL20.glGenBuffers();
        int[] indices = new int[ebo.getLength() * maxBatchSize];

        for(int i = 0; i < maxBatchSize; i++){
            int offsetArrayIndex = ebo.getLength() * i;
            int offset = vao.vaoSize * i;

            int[] eboIndices = ebo.getIndices();
            for (int j = 0; j < eboIndices.length; j++) {
                indices[offsetArrayIndex + j] = offset + eboIndices[j];
            }
        }

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(indices.length);
        elementBuffer.put(indices).flip();

        int eboID = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, eboID);
        GL20.glBufferData(GL20.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL20.GL_STATIC_DRAW);

        System.out.printf("%s | %s", indices.length, vertices.length);
        vao.bindPointers();
    }


    public void addRenderable(Renderable renderable){
        int index = numSprites;
        this.renderables[index] = renderable;
        this.numSprites ++;

        /**
         * give the renderable this (so it can remove)
         * 
         */

        renderable.loadVertexData(vertices, index);

        if(numSprites >= maxBatchSize){
            hasRoom = false;
        }
    }

    public void render(){

        for (int i = 0; i < renderables.length; i++) {
            renderables[i].loadVertexData(vertices, i * 5*4);
        }
        // System.out.println(Arrays.toString(vertices));

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, verticesBuffer, GL20.GL_DYNAMIC_DRAW);

        shader.use();
        Renderer.UploadUniforms(shader);

        vao.enable();

        GL20.glDrawElements(GL20.GL_TRIANGLES, 12, GL20.GL_UNSIGNED_INT, 0);

        vao.disable();

        shader.detach();
    }
}