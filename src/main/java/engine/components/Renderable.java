package engine.components;

import engine.renderer.Renderer;

import engine.renderer.Texture;

import engine.renderer.Shader;
import engine.renderer.VAO.VAOFormat;
import engine.renderer.EBOFormat;

public abstract class Renderable extends Component{

    /**
     * The layer this renderable should be on
     */
    private int layerID;
    public int getLayerID(){return layerID;}

    /**
     * The amount of quads this renderable will display
     */
    public int numberQuads;
    public int getNumberOfQuads(){return numberQuads;}

    /**
     * The shader this renderable uses
     */
    private Shader shader;
    public  Shader getShader() { return shader; }

    /**
     * The VAO format for this renderable
     */
    private VAOFormat vaoFormat; 
    public  VAOFormat getVAOFormat() { return vaoFormat; }

    /**
     * The EBO format for this renderable
     */
    private EBOFormat eboFormat;
    public  EBOFormat getEBOFormat() { return eboFormat; }

    /**
     * The texture slot this renderable will use
     * 
     * This gets set when the object is added to the renderbatch
     */
    public int texSlot;

    /**
     * if this renderable should be displayed
     */
    private boolean isHidden;
    public boolean getHidden() {return isHidden;}
    public void setHidden(boolean hidden) { isHidden = hidden;}

    protected Renderable(Shader shader, VAOFormat vaoFormat, EBOFormat eboFormat,
                         int numberQuads, int layerID){
        this.layerID        = layerID;
        this.numberQuads = numberQuads;

        this.shader    = shader;
        this.vaoFormat = vaoFormat;
        this.eboFormat = eboFormat;
    }

    @Override
    public void Start(){
        Renderer.addRenderable(layerID, this);
    }

    @Override
    public void End(){
        Renderer.removeRenderable(layerID, this);
    }

    /**
     * Load the verted data of this renderable into the VBO
     * 
     * @param buffer the buffer to be sent to the VBO
     * @param start the position to start writing from
     */
    public abstract void    loadVertexData(float[] buffer, int start);

    /**
     * Gets the texture this renderable uses
     * 
     * null if it uses no texture
     * 
     * @return the texture this renderable uses
     */
    public abstract Texture getTexture();
}
