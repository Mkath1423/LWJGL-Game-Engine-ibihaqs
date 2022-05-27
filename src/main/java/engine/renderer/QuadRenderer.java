package engine.renderer;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL20;

import engine.components.QuadRenderable;
import engine.components.Renderable;
import engine.geometry.Quad;
import engine.scenes.SceneManager;

public class QuadRenderer {
    static QuadRenderer instance;

    private static QuadRenderer get(){
        if(QuadRenderer.instance == null){
            QuadRenderer.instance = new QuadRenderer();
        }

        return QuadRenderer.instance;
    }

    private VAO vao;
    public static VAO getVAO(){
        return get().vao;
    }


    private VBO vbo;
    public static VBO getVBO(){
        return get().vbo;
    }

    static final int MAX_BATCH_SIZE = 2;

    int vaoID, vboID, eboID;

    public QuadRenderer(){
        renderables = new ArrayList<>();

        // vao = new VAO();
        //     vao.addAttribute("position", 3, 0);
        //     vao.addAttribute("uvCoords", 2, 0);
        //     vao.addAttribute("texID", 1, 0);

        // vao.bind();

        vaoID = ARBVertexArrayObject.glGenVertexArrays();
        ARBVertexArrayObject.glBindVertexArray(vaoID);

        // generate vbo
        // vbo = new VBO(MAX_BATCH_SIZE * 6 * Float.BYTES);
        vboID = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboID);
        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, 2 * 6 * Float.BYTES, GL20.GL_DYNAMIC_DRAW);

        // Create and upload indices buffer
        int eboID = GL20.glGenBuffers();
        int[] indices = EBO.generateIndices(EBO.QUAD, MAX_BATCH_SIZE);
        GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, eboID);
        GL20.glBufferData(GL20.GL_ELEMENT_ARRAY_BUFFER, indices, GL20.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 3, GL20.GL_FLOAT, false, 6*Float.BYTES, 0*Float.BYTES);
        GL20.glEnableVertexAttribArray(0);

        
        GL20.glVertexAttribPointer(1, 2, GL20.GL_FLOAT, false, 6*Float.BYTES, 3*Float.BYTES);
        GL20.glEnableVertexAttribArray(0);

        
        GL20.glVertexAttribPointer(3, 1, GL20.GL_FLOAT, false, 6*Float.BYTES, 5*Float.BYTES);
        GL20.glEnableVertexAttribArray(0);

        // vao.bindPointers();
        // vao.disable();
        
        ARBVertexArrayObject.glBindVertexArray(0);
    }

    private List<QuadRenderable> renderables;
    public static void addRenderable(QuadRenderable renderable){
        get().renderables.add(renderable);
        System.out.println("adding quad renderable");
    }

    public static void render(){
        // System.out.println("rendering quads");
        Map<Texture, List<QuadRenderable>> sortedRenderables = new HashMap<>();

        for (QuadRenderable r : get().renderables) {
            if(!sortedRenderables.containsKey(r.getTexture())){
                sortedRenderables.put(r.getTexture(), new ArrayList<>());
            }
            sortedRenderables.get(r.getTexture()).add(r);
        }

        for (List<QuadRenderable> lqr : sortedRenderables.values()) {
            // System.out.println("bonkus " + lqr.size());
        }
        
        List<QuadRenderBatch> batches = new ArrayList<>();

        for(List<QuadRenderable> l : sortedRenderables.values()){ 
            for(QuadRenderable qr : l){
                // System.out.println("adding elem to batch");
                for (QuadRenderBatch quadRenderBatch : batches) {
                    if(quadRenderBatch.canAdd(qr))
                    {
                        // System.out.println("I can add to an existing one");
                        quadRenderBatch.add(qr);
                        break;
                    }
                }

                // System.out.println("I am making a new one");
                QuadRenderBatch qrb = new QuadRenderBatch();
                    qrb.add(qr);

                    
                batches.add(qrb);
            }
        }

        // for (QuadRenderBatch quadRenderBatch : batches) {
            // System.out.println("drawing batch of size " + quadRenderBatch.quads.size());
        // }

        for (QuadRenderBatch batch : batches) {
            float[] vertices = new float[MAX_BATCH_SIZE * EBO.QUAD.getNumberOfVertices() * 6];

            int offset = 0;
            for (QuadRenderable qr : batch.quads) {
                qr.loadVertexData(vertices, offset);
                offset += qr.numberVertices * 6;
            }

            GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, get().vboID);

            FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
            verticesBuffer.put(vertices).flip();
            
            GL20.glBufferSubData(GL20.GL_ARRAY_BUFFER, 0, verticesBuffer);

            // System.out.println(Arrays.toString(vertices));

            for (int i = 0; i < batch.textures.size(); i++) {
                if(batch.textures.get(i) == null) continue;
                GL20.glActiveTexture(GL20.GL_TEXTURE0 + i);
                batch.textures.get(i).bind();
            }

            Shader.SPRITE.use();
            Shader.SPRITE.uploadMat4f("uProjection", SceneManager.getActiveMainCamera().getProjectionMatrix());
            Shader.SPRITE.uploadMat4f("uView",       SceneManager.getActiveMainCamera().getViewMatrix());
            int[] textureSlots = new int[8];
            for (int i = 0; i < textureSlots.length; i++) {
                textureSlots[i] = i;
            }

            Shader.SPRITE.uploadIntArray("uTextures", textureSlots);

            // getVAO().bind();
            // getVAO().enable();

            ARBVertexArrayObject.glBindVertexArray(get().vaoID);

            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);

            GL20.glDrawElements(GL20.GL_TRIANGLES, EBO.QUAD.getLength() * MAX_BATCH_SIZE, GL20.GL_UNSIGNED_INT, 0);

            GL20.glEnableVertexAttribArray(2);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(0);

            ARBVertexArrayObject.glBindVertexArray(0);
            // getVAO().disable();
            // getVAO().unbind();

            Shader.SPRITE.detach();

            for (int i = 0; i < batch.textures.size(); i++) {
                if(batch.textures.get(i) == null) continue;
                GL20.glActiveTexture(GL20.GL_TEXTURE0 + i);
                batch.textures.get(i).unbind();
            }

        }

    }
}
