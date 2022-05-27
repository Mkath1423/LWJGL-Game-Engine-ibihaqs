package engine.components;

import java.util.List;

import engine.renderer.EBO;
import engine.renderer.Renderer;
import engine.renderer.Shader;
import engine.renderer.VAO;

public abstract class Renderable extends Component{

    protected int layerId;

    protected boolean dirty;

    protected Renderable(Shader shader, VAO vao, EBO ebo){
        this.shader = shader;
        this.vao = vao;
        this.ebo = ebo;
    }

    public void Start(){
        Renderer.addRenderable(layerId, this);
        System.out.println("started sprite");
    }

    public void End(){
        Renderer.removeRenderable(layerId, this);
    }

    public abstract void loadVertexData(float[] buffer, int start);

    // protected boolean CheckBufferSize(int[] buffer){
    //     return buffer.length == vao.vaoSize;
    // }

    // public boolean isBatchable(Renderable that){
    //     return isBatchable(that.shader, that.vao, that.ebo);
    // }

    // public boolean isBatchable(Shader s, VAO a, EBO e){
    //     return this.shader == s && this.vao  == a && this.ebo == e;
    // }

    // public abstract void UploadUniforms();

    // public abstract void render(List<Renderable> value);
}
