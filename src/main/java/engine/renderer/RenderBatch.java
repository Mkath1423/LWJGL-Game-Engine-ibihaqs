package engine.renderer;

import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.components.Renderable;
import engine.renderer.VAO.VAOFormat;

public class RenderBatch {
    // Vertex
    // =======
    // pos                  color                       uv
    // float, float, float, float, float, float, float, float, float,

    List<Renderable> renderables;
    private List<Texture>    textures;

    private int quadsUsed;
    private int texturesUsed;

    private VAO vao;
    private VBO vbo;
    private int ebo;

    private Shader shader;
    private VAOFormat vaoFormat;
    private EBOFormat eboFormat;

    public boolean canAddRenderable(Renderable renderable){
        if((renderable.getShader()    != shader   ) ||
           (renderable.getVAOFormat() != vaoFormat) || 
           (renderable.getEBOFormat() != eboFormat)) 
           return false;
        // System.out.println("checking renderable");
        // System.out.println(" same type");
        if(quadsUsed + renderable.numberQuads > Renderer.MAX_BATCH_SIZE) return false;

        // System.out.println(" enough space");
        for (Texture tex : textures) {
            if(tex.getTexId() == renderable.getTexture().getTexId()) return true;            
        }
        // System.out.println(" enough textures?" + (texturesUsed < QuadRenderer.MAX_TEXTURES));

        return texturesUsed < QuadRenderer.MAX_TEXTURES;
    }

    public void addRenderable(Renderable renderable){
        if(!canAddRenderable(renderable)) return;

        boolean allocateTexture = true;

        for (Texture tex : textures) {
            if(tex.getTexId() == renderable.getTexture().getTexId()) allocateTexture = false;            
        }

        renderables.add(renderable);
        quadsUsed += renderable.getNumberOfQuads();

        if(allocateTexture){
            textures.add(renderable.getTexture());
            texturesUsed ++;
        }

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

    public void start(){
        vao = new VAO(vaoFormat);
        vao.bind();

        vbo = new VBO(Renderer.MAX_BATCH_SIZE * EBOFormat.QUAD.getNumberOfVertices() * vaoFormat.getVaoSize() * Float.BYTES);
        System.out.println(Renderer.MAX_BATCH_SIZE * EBOFormat.QUAD.getNumberOfVertices() * vaoFormat.getVaoSize() * Float.BYTES);
        // create indices and upload
        int[] elementArray = EBOFormat.generateIndices(EBOFormat.QUAD, Renderer.MAX_BATCH_SIZE);
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        ebo = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, ebo);
        GL20.glBufferData(GL20.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL20.GL_STATIC_DRAW);

        vao.bindPointers();
        vao.disable();
    }

    public void render(){
        float[] vertices = new float[QuadRenderer.MAX_BATCH_SIZE * EBOFormat.QUAD.getNumberOfVertices() * vaoFormat.getVaoSize()];

        int index = 0;
        for (Renderable r : renderables) {
            r.loadVertexData(vertices, index);
            index += r.numberQuads * eboFormat.getNumberOfVertices() * vaoFormat.getVaoSize();
        }

        vbo.bufferData(vertices);
        for (int i = 0; i < 4; i++) {
            System.out.println(Arrays.toString(Arrays.copyOfRange(vertices, i*11, (i+1) * 11)));
        }
        // System.out.println(vertices.length);
        // System.out.println("----------------");
        // for(int i = 0; i < vertices.length / get().vao.vaoSize; i++){
        //     System.out.println(Arrays.toString(Arrays.copyOfRange(vertices, i, i + get().vao.vaoSize)));
        // }

        for (int i = 0; i < textures.size(); i ++) {
            GL20.glActiveTexture(GL20.GL_TEXTURE0 + i);
            textures.get(i).bind();
        }

        Shader.SPRITE.use();
        Renderer.uploadUniforms(shader);

        vao.enable();

        GL20.glDrawElements(GL30.GL_TRIANGLES, EBOFormat.QUAD.getLength() * Renderer.MAX_BATCH_SIZE, GL30.GL_UNSIGNED_INT, 0);
        
        vao.disable();
        Shader.SPRITE.detach();

        for (int i = 0; i < textures.size(); i ++) {
            GL20.glActiveTexture(GL20.GL_TEXTURE0 + i);
            textures.get(i).unbind();
        }

        
    }
}