package engine.components;

import java.nio.IntBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import engine.renderer.EBO;
import engine.renderer.Renderer;
import engine.renderer.Shader;
import engine.renderer.Texture;
import engine.renderer.VAO;
import engine.renderer.VBO;

public abstract class Renderable extends Component{
    
    protected int layerId;

    // store the shader vao and ebo
    protected Shader shader;
    protected VAO    vao;
    protected EBO    ebo;

    protected boolean dirty;
    public boolean getDirty(){
        return true/**dirty */;
    }

    public Texture getTexture() {
        return null;
    }

    public Shader getShader(){
        return shader;
    }

    public VAO getVAO(){
        return vao;
    }

    public EBO getEBO(){
        return ebo;
    }

    protected Renderable(Shader shader, VAO vao, EBO ebo){
        this.shader = shader;
        this.vao = vao;
        this.ebo = ebo;
    }

    public void Awake(){
        Renderer.addRenderable(layerId, this);
    }

    public void End(){
        Renderer.removeRenderable(layerId, this);
    }

    public abstract void loadVertexData(float[] buffer, int start);

    protected boolean CheckBufferSize(int[] buffer){
        return buffer.length == vao.vaoSize;
    }

    public boolean isBatchable(Renderable that){
        return isBatchable(that.shader, that.vao, that.ebo);
    }

    public boolean isBatchable(Shader s, VAO a, EBO e){
        return this.shader == s && this.vao  == a && this.ebo == e;
    }
    // SECTION how to render this renderable


    public static final VBO initializeBuffers(VAO vao, EBO ebo){
        VBO vbo = new VBO();
        
        float[] vertices = new float[Renderer.maxBatchSize * ebo.getNumberOfVertices() * vao.vaoSize];

        int vboID = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboID);
        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL20.GL_DYNAMIC_DRAW);

        int[] indices = new int[ebo.getLength() * Renderer.maxBatchSize];

        for(int i = 0; i < Renderer.maxBatchSize; i++){
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

        vao.bindPointers();// TODO: find a better place for this 
    }

    public final void enable(Shader shader, VAO vao, int vboID, int eboID){
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboID);
        GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, eboID);

        shader.use();
        vao.enable();
    }

    public final void disable(Shader shader, VAO vao, int vboID, int eboID){
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboID);
        GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, eboID);

        shader.detach();
        vao.disable();
    }

    public void uploadUniforms(){

    }

    protected void render(List<Renderable> renderables){
        List<>
    }
}
