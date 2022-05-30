package engine.components;

import engine.renderer.QuadRenderer;
import engine.renderer.Texture;

public abstract class QuadRenderable extends Component{
    public static final int MAX_BATCH_SIZE = 0;

    public int layerID;

    public int numberVertices;

    public int texSlot;

    protected QuadRenderable(int numberVertices, int layerID){
        this.layerID        = layerID;
        this.numberVertices = numberVertices;
    }

    public void Start(){
        QuadRenderer.addRenderable(this);
    }

    public void End(){
        QuadRenderer.removeRenderable(this);
    }

    public abstract void loadVertexData(float[] buffer, int start);

    public abstract Texture getTexture();
}
