package engine.components;

import engine.renderer.Renderer;

import engine.renderer.Texture;

import engine.renderer.Shader;
import engine.renderer.VAO.VAOFormat;
import engine.renderer.EBOFormat;

public abstract class Renderable extends Component{

    private int layerID;

    public int getLayerID(){
        return layerID;
    }

    public int numberQuads;

    public int getNumberOfQuads(){
        return numberQuads;
    }

    private Shader shader;
    public  Shader getShader() { return shader; }

    private VAOFormat vaoFormat; 
    public  VAOFormat getVAOFormat() { return vaoFormat; }

    private EBOFormat eboFormat;
    public  EBOFormat getEBOFormat() { return eboFormat; }

    public int texSlot;

    protected Renderable(Shader shader, VAOFormat vaoFormat, EBOFormat eboFormat,
                         int numberQuads, int layerID){
        this.layerID        = layerID;
        this.numberQuads = numberQuads;

        this.shader    = shader;
        this.vaoFormat = vaoFormat;
        this.eboFormat = eboFormat;
    }

    public void Start(){
        Renderer.addRenderable(layerID, this);
    }

    public void End(){
        Renderer.removeRenderable(layerID, this);
    }

    public abstract void    loadVertexData(float[] buffer, int start);
    public abstract Texture getTexture();
}
