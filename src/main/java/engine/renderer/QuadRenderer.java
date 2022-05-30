package engine.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.components.QuadRenderable;
import engine.scenes.SceneManager;

public class QuadRenderer {
    static final int MAX_BATCH_SIZE = 1;
    static final int MAX_TEXTURES = 32;

    // SECTION SINGLETON
    static QuadRenderer instance;

    private static QuadRenderer get(){
        if(QuadRenderer.instance == null){
            QuadRenderer.instance = new QuadRenderer();
        }

        return QuadRenderer.instance;
    }

    public QuadRenderer(){
        renderables = new ArrayList<>();
        init();
    }


    // SECTION RENDERABLES
    private List<QuadRenderable> renderables;


    public static void addRenderable(QuadRenderable renderable){
        get().renderables.add(renderable);
        System.out.println("adding quad renderable");
    }

    public static void removeRenderable(QuadRenderable renderable){
        get().renderables.remove(renderable);
    }

    // SECTION GL RENDERING

    private VAO vao;
    private VBO vbo;

    private void init(){
        vao = new VAO();
            vao.addAttribute("position", 3, 0);
            vao.addAttribute("uvCoords", 2, 0);
            vao.addAttribute("texID", 1, 0);
        vao.bind();

        vbo = new VBO(MAX_BATCH_SIZE * EBO.QUAD.getNumberOfVertices() * vao.vaoSize * Float.BYTES);

        // creat indicies and upload

        int[] elementArray = EBO.generateIndices(EBO.QUAD, MAX_BATCH_SIZE);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        int eboID = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, eboID);
        GL20.glBufferData(GL20.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL20.GL_STATIC_DRAW);

        vao.bindPointers();
        vao.disable();
    }

    private static void uploadUniforms(){
        Shader.SPRITE.uploadMat4f("uProjection", SceneManager.getActiveMainCamera().getProjectionMatrix());
        Shader.SPRITE.uploadMat4f("uView",       SceneManager.getActiveMainCamera().getViewMatrix());
        int[] textureSlots = new int[8];
        for (int i = 0; i < textureSlots.length; i++) {
            textureSlots[i] = i;
        }

        Shader.SPRITE.uploadIntArray("uTextures", textureSlots);
    }

    public static void render(){
        List<QuadRenderBatch> batches = new ArrayList<>();

        // batch the renderables
        renderables:
        for (QuadRenderable renderable : get().renderables) {
            for (QuadRenderBatch batch : batches) {
                if(batch.canAdd(renderable)){
                    batch.add(renderable);
                    break renderables;
                }
            }

            QuadRenderBatch batch = new QuadRenderBatch();
                batch.add(renderable);

            batches.add(batch);
        }
        
        // buffer each batch and render
        for (QuadRenderBatch batch : batches) {
            float[] vertices = new float[QuadRenderer.MAX_BATCH_SIZE * EBO.QUAD.getNumberOfVertices() * get().vao.vaoSize];

            int index = 0;
            for (QuadRenderable qr : get().renderables) {
                qr.loadVertexData(vertices, index);
                index += get().vao.vaoSize * qr.numberVertices;
            }

            System.out.println(Arrays.toString(vertices));

            get().vbo.bufferData(vertices);

            for (int i = 0; i < batch.textures.size(); i ++) {
                GL20.glActiveTexture(GL20.GL_TEXTURE0 + i);
                batch.textures.get(i).bind();
            }

            Shader.SPRITE.use();
            uploadUniforms();

            get().vao.enable();

            GL20.glDrawElements(GL30.GL_TRIANGLES, EBO.QUAD.getLength() * MAX_BATCH_SIZE, GL30.GL_UNSIGNED_INT, 0);

            get().vao.disable();
            Shader.SPRITE.detach();

            for (int i = 0; i < batch.textures.size(); i ++) {
                GL20.glActiveTexture(GL20.GL_TEXTURE0 + i);
                batch.textures.get(i).unbind();
            }
        }
    }
}
