package engine.renderer;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    private List<Integer> p = new ArrayList<>();

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
            int offset = ebo.getNumberOfVertices() * i;
            // System.out.println(offsetArrayIndex);
            // System.out.println(offset);

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
        System.out.println(numSprites);
        // Map<Texture, Renderable> sortedRenderables = new LinkedHashMap<>();

        // for (int i = 0; i < renderables.length; i++) {
        //     if(renderables[i] == null) continue;
        //     if(renderables[i].getTexture() == null) continue;
        //     sortedRenderables.put(renderables[i].getTexture(), renderables[i]);
        // }

        for (int i = 0; i < renderables.length; i++) {
            if(renderables[i] == null) continue;
            renderables[i].loadVertexData(vertices, i * vao.vaoSize * ebo.getNumberOfVertices());
        }

        // for (int i = 0; i < Math.ceil(sortedRenderables.size()/16); i++) {
        //     for(int j = 0; j < 16 && i*16 + j < sortedRenderables.size(); j ++){
                
        //     }
        // }

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, verticesBuffer, GL20.GL_DYNAMIC_DRAW);

        shader.use();
        Renderer.UploadUniforms(shader);

        vao.enable();

        GL20.glDrawElements(GL20.GL_TRIANGLES, maxBatchSize * ebo.getLength(), GL20.GL_UNSIGNED_INT, 0);

        vao.disable();

        shader.detach();
    }
}
