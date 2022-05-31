package engine.components;

import engine.renderer.Renderer;
import engine.renderer.Renderer.RenderType;

public abstract class Renderable extends Component{

    protected int layerId;

    public int numberVertices;

    protected boolean dirty;

    protected Renderable(int numberVertices, int layerId){
        this.layerId        = layerId;
        this.numberVertices = numberVertices;
    }

    public void Start(){
        Renderer.addRenderable(layerId, this);
        System.out.println("started sprite");
    }

    public void End(){
        Renderer.removeRenderable(layerId, this);
    }

    public abstract void loadVertexData(float[] buffer, int start);

}
