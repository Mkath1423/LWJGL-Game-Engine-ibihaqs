package engine.components;

import engine.renderer.QuadRenderer;
import engine.renderer.Texture;

public abstract class QuadRenderable extends Component{

    public int layerID;
    public int numberVertices;

    @Override
    public void Awake(){
        QuadRenderer.addRenderable(this);
        super.Awake();
    }

    public abstract void loadVertexData(float[] buffer, int start);
    public abstract Texture getTexture();
}
