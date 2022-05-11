package renderer;

import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL20;

public class RenderBatch {
    // Vertex
    // =======
    // pos                  color                       uv
    // float, float, float, float, float, float, float, float, float,

    private final int POS_SIZE = 2;
    private final int COLOR_SIZE = 4;
    private final int UV_SIZE = 2;

    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE*Float.BYTES;
    private final int UV_OFFSET = COLOR_OFFSET + COLOR_OFFSET*Float.BYTES;

    private final int VERTEX_SIZE = POS_SIZE + COLOR_SIZE + UV_SIZE;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    private SpriteRenderer[] sprites;
    private int numSprites;
    private int maxBatchSize
    private boolean hasRoom;
    private float[] vertices;

    private int vaoID, vboID;
    private Shader shader;

    public RenderBatch(int maxBatchSize){
        shader = new Shader("assets/shaders/default.glsl");
        shader.compile();

        this.sprites = new SpriteRenderer[maxBatchSize];
        this.maxBatchSize = maxBatchSize;
        
        vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];

        numSprites = 0;
        hasRoom = true;
    }

    public void start(){
        vaoID = ARBVertexArrayObject.glGenVertexArrays();
        ARBVertexArrayObject.glBindVertexArray(vaoID);

        vboID = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboID);
        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL20.GL_DYNAMIC_DRAW);

        int eboID = GL20.glGenBuffers();
        int[] indices = generateIndices();
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, eboID);
        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, indices, GL20.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, POS_SIZE, GL20.GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
        GL20.glEnableVertexAttribArray(0);

        GL20.glVertexAttribPointer(1, COLOR_SIZE, GL20.GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        GL20.glEnableVertexAttribArray(1);

        GL20.glVertexAttribPointer(2, UV_SIZE, GL20.GL_FLOAT, false, VERTEX_SIZE_BYTES, UV_OFFSET);
        GL20.glEnableVertexAttribArray(2);
    }


    private int[] generateIndices(){
        int[] elements = new int[6 * maxBatchSize];

        for(int i = 0; i < maxBatchSize; i++){
            loadElementIndices(elements, i);
        }

        return elements;
    }

    private void loadElementIndices(int[] elements, int index){
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;

        elements[offsetArrayIndex + 0] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset + 0;

        elements[offsetArrayIndex + 3] = offset + 0;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }

    public void addSprite(SpriteRenderer spr){
        int index = numSprites;
        this.sprites[index] = spr;
        this.numSprites ++;

        loadVertexProperties(index);

        if(numSprites)
    }

    public void renderer(){
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vaoID);
        GL20.glBufferSubData(GL20.GL_ARRAY_BUFFER, 0, vertices);

        shader.use();
        shader.uploadMat4f("uProjection", camera matrix);
        shader.uploadMat4f(varName, "uView", camrea view);

        ARBVertexArrayObject.glBindVertexArray(vaoID);

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        GL20.glDrawElements(GL20.GL_TRIANGLES, numSprites * 6, GL20.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        ARBVertexArrayObject.glBindVertexArray(0);

        shader.detach();
    }
}
