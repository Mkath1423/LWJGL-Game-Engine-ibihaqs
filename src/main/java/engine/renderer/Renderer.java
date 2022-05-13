package engine.renderer;

import java.util.HashMap;
import java.util.Map;

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
        Refresh();
    }

    private Map<Integer, Layer> layers;

    public static int maxBatchSize = 1000;

    public static void addRenderable(int layerID, Renderable renderable){
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

    protected static void UploadUniforms(Shader shader){
        switch(shader){
            case SPRITE_RGB:
                Shader.SPRITE_RGB.uploadMat4f("uProjection", SceneManager.getActiveMainCamera().getProjectionMatrix());
                Shader.SPRITE_RGB.uploadMat4f("uView",       SceneManager.getActiveMainCamera().getViewMatrix());
                Shader.SPRITE_RGB.uploadFloat("uTime",       (float)Time.getTime());  
                break;
        }
    }


}
