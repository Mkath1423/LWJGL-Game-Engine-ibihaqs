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
    static final int MAX_BATCH_SIZE = 1000;
    static final int MAX_TEXTURES = 8;

    public enum RenderType{
        QUAD,
        TRIANGLE,
        LINE;
    }

    private static Renderer instance;
    public static Renderer get(){
        if(Renderer.instance == null){
            Renderer.instance = new Renderer();
        }
        return Renderer.instance;
    }

    private Renderer(){
        layers = new HashMap<>();
    }

    private Map<Integer, Layer> layers;

    public static void addRenderable(int layerID, Renderable renderable){
        if(renderable == null) return;

        if(!get().layers.containsKey(layerID)){
            get().layers.put(layerID, new Layer());
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

    public static void draw(){
        for (Layer layer : get().layers.values()) {
            layer.draw();
        }
    }

    public static void uploadUniforms(Shader shader) {
        switch(shader){
            case SPRITE:
                Shader.SPRITE.uploadMat4f("uProjection", SceneManager.getActiveMainCamera().getProjectionMatrix());
                Shader.SPRITE.uploadMat4f("uView",       SceneManager.getActiveMainCamera().getViewMatrix());
                
                int[] textureSlots = new int[MAX_TEXTURES];
                for (int i = 0; i < textureSlots.length; i++) {
                    textureSlots[i] = i;
                }

                Shader.SPRITE.uploadIntArray("uTextures", textureSlots);
                break;

            default:
                break;
        }
    }


}
