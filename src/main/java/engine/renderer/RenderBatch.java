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

    private int vaoID, vboID;
    
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

        vaoID = VAO.genBuffer();
        VAO.bind(vaoID);
 
        vboID = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboID);
        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL20.GL_DYNAMIC_DRAW);

        // int eboID = GL20.glGenBuffers();
        int[] indices = new int[ebo.getLength() * maxBatchSize];

        for(int i = 0; i < maxBatchSize; i++){
            int offsetArrayIndex = ebo.getLength() * i;
            int offset = ebo.getNumberOfVertices() * i;
            System.out.println(offsetArrayIndex);
            System.out.println(offset);

            int[] eboIndices = ebo.getIndices();
            for (int j = 0; j < eboIndices.length; j++) {
                indices[offsetArrayIndex + j] = offset + eboIndices[j];
            }
        }

        System.out.println(Arrays.toString(indices));
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(indices.length);
        elementBuffer.put(indices).flip();

        int eboID = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, eboID);
        GL20.glBufferData(GL20.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL20.GL_STATIC_DRAW);

        VAO.bindPointers(vao);

        VAO.disable(vaoID, vao);
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
            if(renderables[i] == null) continue;
            renderables[i].loadVertexData(vertices, i * 5*4);
        }
        // System.out.println(Arrays.toString(vertices));

        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboID);
        GL20.glBufferSubData(GL20.GL_ARRAY_BUFFER, 0, vertices);

        shader.use();
        Renderer.UploadUniforms(shader);

        VAO.enable(vaoID, vao);

        GL20.glDrawElements(GL20.GL_TRIANGLES, 18, GL20.GL_UNSIGNED_INT, 0);

        VAO.disable(vaoID, vao);

        shader.detach();
    }
}
