package engine.renderer;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import engine.Window;
import engine.components.Renderable;
import engine.util.Time;

public class Renderer {

    private static Renderer instance;
    public static Renderer get(){
        if(Renderer.instance == null){
            Renderer.instance = new Renderer();
        }
        return Renderer.instance;
    }

    private Renderer(){
        layers = new HashMap<>();
        buffers = new HashMap<>();
    }

    private Map<Integer, Layer> layers;

    public static int maxBatchSize = 1000;

    public static void addRenderable(int layerID, Renderable renderable){
        if(renderable == null) return;

        if(!get().layers.containsKey(layerID)){
            get().layers.put(layerID, new Layer());
            // System.out.println("Renderer: making new layer " + layerID);
        }

        get().layers.get(layerID).addRenderable(renderable);

    }

    public static void removeRenderable(int layerID, Renderable renderable){
        if(!get().layers.containsKey(layerID)){
            return;
        }

        get().layers.get(layerID).removeRenderable(renderable);
    }

    public static void Refresh(){
        get().layers = new HashMap<>();
    }

    public static void UploadUniforms(Shader shader){
        switch(shader){
            case SPRITE_RGB:
                // Shader.SPRITE_RGB.uploadMat4f("uProjection", SceneManager.getActiveMainCamera().getProjectionMatrix());
                // Shader.SPRITE_RGB.uploadMat4f("uView",       SceneManager.getActiveMainCamera().getViewMatrix());
                Shader.SPRITE_RGB.uploadMat4f("uProjection", Window.get().camera.getProjectionMatrix());
                Shader.SPRITE_RGB.uploadMat4f("uView",       Window.get().camera.getViewMatrix());
                Shader.SPRITE_RGB.uploadFloat("uTime",       (float)Time.getTime());  
                Shader.SPRITE_RGB.uploadInt("texSampler", 0);
                break;
        }
    }

    private Map<String, BufferIDs> buffers;

    public static void initializeBuffers(Renderable r){
        if(get().buffers.containsKey(r.renderableType)) return;

        float[] vertices = new float[Renderer.maxBatchSize * r.ebo.getNumberOfVertices() * r.vao.vaoSize];

        int vboID = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboID);
        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL20.GL_DYNAMIC_DRAW);

        int[] indices = new int[r.ebo.getLength() * Renderer.maxBatchSize];

        for(int i = 0; i < Renderer.maxBatchSize; i++){
            int offsetArrayIndex = r.ebo.getLength() * i;
            int offset = r.ebo.getNumberOfVertices() * i;
            // System.out.println(offsetArrayIndex);
            // System.out.println(offset);

            int[] eboIndices = r.ebo.getIndices();
            for (int j = 0; j < eboIndices.length; j++) {
                indices[offsetArrayIndex + j] = offset + eboIndices[j];
            }

        }

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(indices.length);
        elementBuffer.put(indices).flip();

        int eboID = GL20.glGenBuffers();
        GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, eboID);
        GL20.glBufferData(GL20.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL20.GL_STATIC_DRAW);

        int vaoID = r.vao.init();
        r.vao.bindPointers(vaoID);

        get().buffers.put(r.renderableType, new BufferIDs(vaoID, vboID, eboID));
    }

    public static void enable(Renderable r){
        if(!get().buffers.containsKey(r.renderableType)) return;
        BufferIDs ids = get().buffers.get(r.renderableType);
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, ids.vboID); // TODO: check this
        // GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, ids.eboID); // TODO: check this

        r.shader.use();
        r.UploadUniforms();
        r.vao.enable(ids.vaoID);
    }
    public static void bufferVertices(Renderable renderable, float[] vertices) {
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        GL20.glBufferSubData(GL20.GL_ARRAY_BUFFER, 0, verticesBuffer);
    }

    public static void drawVertices(Renderable r){
        
        GL20.glDrawElements(GL20.GL_TRIANGLES, r.ebo.getLength() * maxBatchSize, GL20.GL_UNSIGNED_INT, 0);
    }

    public static void disable(Renderable r){
        if(!get().buffers.containsKey(r.renderableType)) return;

        r.shader.detach();
        r.vao.disable();
    }

    public static void draw(){
        // System.out.println("---------");
        // for (Entry<String, Integer[]> e : get().buffers.entrySet()) {
        //     System.out.printf("  %s: %s\n", e.getKey(), Arrays.toString(e.getValue()));
        // }

        for (Layer layer : get().layers.values()) {
            layer.draw();
        }
    }

    

}