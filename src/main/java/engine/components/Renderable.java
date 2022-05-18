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

    public String renderableType;

    // store the shader vao and ebo
    public Shader shader;
    public VAO    vao;
    public EBO    ebo;

    private static int vboID;
    private static int vaoID;

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
    public abstract void uploadUniforms();
    public abstract void render(List<Renderable> renderables);
}
