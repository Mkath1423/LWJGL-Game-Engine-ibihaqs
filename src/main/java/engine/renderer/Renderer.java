package engine.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import engine.Window;
import engine.components.Renderable;
import engine.scenes.SceneManager;
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
    public static int maxTextures = 32;

    public static void addRenderable(int layerID, Renderable renderable){
        if(renderable == null) return;

        if(!get().layers.containsKey(layerID)){
            get().layers.put(layerID, new Layer());
        }

        get().layers.get(layerID).addRenderable(renderable);
        initializeBuffers(renderable);

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

    private Map<String, BufferIDs> buffers;
    private static int getVAO(Renderable r){
        return get().buffers.getOrDefault(r.renderableType, new BufferIDs(0, 0, 0)).vaoID;
    }

    private static int getVBO(Renderable r){
        return get().buffers.getOrDefault(r.renderableType, new BufferIDs(0, 0, 0)).vboID;
    }

    private static int getEBO(Renderable r){
        return get().buffers.getOrDefault(r.renderableType, new BufferIDs(0, 0, 0)).eboID;
    }

    public static void initializeBuffers(Renderable r){
        System.out.println("initializing buffer for " + r.renderableType);
        if(get().buffers.containsKey(r.renderableType)) return;

        int vaoID = VAO.genBuffer();
        VAO.bind(vaoID);

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

        VAO.bindPointers(r.vao);
        VAO.disable(vaoID, r.vao);

        get().buffers.put(r.renderableType, new BufferIDs(vaoID, vboID, eboID));
        System.out.println("made buffers: " + get().buffers.get(r.renderableType));
    }

    public static void enable(Renderable r){
        if(!get().buffers.containsKey(r.renderableType)) return;
        
        r.shader.use();
        r.UploadUniforms();
        VAO.enable(getVAO(r), r.vao);
    }

    public static void bufferVertices(Renderable renderable, float[] vertices) {
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();
        
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, getVBO(renderable));
        GL20.glBufferSubData(GL20.GL_ARRAY_BUFFER, 0, vertices);
    }

    public static void drawVertices(Renderable r){
        GL20.glDrawElements(GL20.GL_TRIANGLES, r.ebo.getLength() * maxBatchSize, GL20.GL_UNSIGNED_INT, 0);
    }

    public static void disable(Renderable r){
        if(!get().buffers.containsKey(r.renderableType)) return;

        r.shader.detach();
        VAO.disable(getVAO(r), r.vao);
    }

    public static void draw(){
        for (Layer layer : get().layers.values()) {
            layer.draw();
        }
    }


}
