package engine.renderer;

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

        int eboID = GL20.glGenBuffers();
        int[] indices = new int[ebo.getLength() * maxBatchSize];

        for(int i = 0; i < maxBatchSize; i++){
            int offsetArrayIndex = ebo.getLength() * i;
            int offset = vao.vaoSize * i;

            int[] eboIndices = ebo.getIndices();
            for (int j = 0; j < indices.length; j++) {
                indices[offsetArrayIndex + j] = offset + eboIndices[j];
            }
        }

        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, eboID);
        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, indices, GL20.GL_STATIC_DRAW);
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

    public void renderer(){

        for (int i = 0; i < renderables.length; i++) {
            renderables[i].loadVertexData(vertices, i * vao.vaoSize * ebo.getNumberOfVertices());
        }

        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboID);
        GL20.glBufferSubData(GL20.GL_ARRAY_BUFFER, 0, vertices);

        shader.use();
        Renderer.UploadUniforms(shader);

        vao.enable();

        GL20.glDrawElements(GL20.GL_TRIANGLES, numSprites * 6, GL20.GL_UNSIGNED_INT, 0);

        vao.disable();

        shader.detach();
    }
}
