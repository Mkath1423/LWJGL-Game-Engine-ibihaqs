package engine.renderer;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.components.Renderable;
import engine.renderer.VAO.VAOFormat;

/**
 * A batch of renderables that can be rendered together
 * 
 * Adapted from tutorial by GamesWithGabe
 * 
 * @author Lex Stapleton
 */
public class RenderBatch {

    /**
     * The renderables and textures to use
     */
    private List<Renderable> renderables;
    private List<Texture>    textures;

    /**
     * The amount of space in the batch that has been used up
     */
    private int quadsUsed;
    private int texturesUsed;

    /**
     * The buffers this batch will use
     */
    private VAO vao;
    private VBO vbo;
    private int ebo;

    /**
     * The configuration of the renderering method
     */
    private Shader shader;
    private VAOFormat vaoFormat;
    private EBOFormat eboFormat;

    /**
     * Checks if a renderable can be added to this batch
     * 
     * @param renderable the renderable to check
     * @return true if it can be safely added
     */
    public boolean canAddRenderable(Renderable renderable){
        // return false if that renderable uses a different rendering configuration
        if((renderable.getShader()    != shader   ) ||
           (renderable.getVAOFormat() != vaoFormat) || 
           (renderable.getEBOFormat() != eboFormat)) 
           return false;
           
        // return false if there is not enough space for the renderable
        if(quadsUsed + renderable.numberQuads > Renderer.MAX_BATCH_SIZE) return false;

        // return true if you don't need to allocate any more textures
        for (Texture tex : textures) {
            if(tex.getTexId() == renderable.getTexture().getTexId()) return true;            
        }
        
        // return true if there is enough space for another texture
        return texturesUsed < Renderer.MAX_TEXTURES;
    }

    /**
     * Adds a renderable to this batch
     * 
     * @param renderable the renderable to add
     */
    public void addRenderable(Renderable renderable){
        // make sure it can be safely added
        if(!canAddRenderable(renderable)) return;

        // add the renderable and update the amount of space used
        renderables.add(renderable);
        quadsUsed += renderable.getNumberOfQuads();

        // check if this renderables needs to use a texture
        if(renderable.getTexture() == null) return;

        boolean allocateTexture = true;

        // check if you can use a preallocated texture
        for (Texture tex : textures) {
            if(tex.getTexId() == renderable.getTexture().getTexId()) allocateTexture = false;            
        }

        // allocate the new texture if needed
        if(allocateTexture){
            textures.add(renderable.getTexture());
            texturesUsed ++;
        }

        // set the renderables texSlot to the slot of the texture it will be using
        renderable.texSlot = textures.indexOf(renderable.getTexture());

    }

    public RenderBatch(Shader shader, VAOFormat vaoFormat, EBOFormat eboFormat){
        this.shader    = shader;
        this.vaoFormat = vaoFormat;
        this.eboFormat = eboFormat;

        this.renderables = new ArrayList<>();
        this.textures    = new ArrayList<>();
        
        this.quadsUsed    = 0;
        this.texturesUsed = 0;
    }

    /**
     * Initialize the buffers of the render batch
     */
    public void start(){
        // bind the vao
        vao = new VAO(vaoFormat);
        vao.bind();

        // bind the vbo
        vbo = new VBO(Renderer.MAX_BATCH_SIZE * EBOFormat.QUAD.getNumberOfVertices() * vaoFormat.getVaoSize() * Float.BYTES);

        // bind and upload the ebo
        int[] elementArray = EBOFormat.generateIndices(EBOFormat.QUAD, Renderer.MAX_BATCH_SIZE);
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        ebo = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, ebo);
        GL20.glBufferData(GL20.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL20.GL_STATIC_DRAW);

        // bind the pointers and disable
        vao.bindPointers();
        vao.disable();
    }

    /**
     * Renders all of this batches renderables
     */
    public void render(){
        float[] vertices = new float[Renderer.MAX_BATCH_SIZE * EBOFormat.QUAD.getNumberOfVertices() * vaoFormat.getVaoSize()];

        // add each renderable's data to the vertices buffer
        int index = 0;
        for (Renderable r : renderables) {
            // skip disabled gameobjects
            if(!r.gameObject.getEnabled()) continue;

            // load the data of the renderable and move forward in the buffer
            r.loadVertexData(vertices, index);
            index += r.numberQuads * eboFormat.getNumberOfVertices() * vaoFormat.getVaoSize();
        }

        // upload the vertex data
        vbo.bufferData(vertices);

        // bind the textures
        for (int i = 0; i < textures.size(); i ++) {
            GL20.glActiveTexture(GL20.GL_TEXTURE0 + i);
            textures.get(i).bind();
        }

        // start the shader and upload uniforms
        Shader.SPRITE.use();
        Renderer.uploadUniforms(shader);

        vao.enable();

        // draw everythin=g in the buffer
        GL20.glDrawElements(GL30.GL_TRIANGLES, EBOFormat.QUAD.getLength() * Renderer.MAX_BATCH_SIZE, GL30.GL_UNSIGNED_INT, 0);
        
        // disable the vao and shader
        vao.disable();
        Shader.SPRITE.detach();

        // unbind the textures
        for (int i = 0; i < textures.size(); i ++) {
            GL20.glActiveTexture(GL20.GL_TEXTURE0 + i);
            textures.get(i).unbind();
        }
    }
}