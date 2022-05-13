package engine.renderer;

import java.util.HashMap;
import java.util.Map;

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
    }

    private Map<Integer, Layer> layers;

    public static int maxBatchSize = 2;

    public static void addRenderable(int layerID, Renderable renderable){
        if(renderable == null) return;

        if(!get().layers.containsKey(layerID)){
            get().layers.put(layerID, new Layer());
            System.out.println("Renderer: making new layer " + layerID);
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

    public static void draw(){
        for (Layer layer : get().layers.values()) {
            layer.draw();
        }
    }

}
