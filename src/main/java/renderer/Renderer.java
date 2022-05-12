package renderer;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import components.Renderable;

public class Renderer {

    private static Renderer instance;
    public static Renderer get(){
        if(Renderer.instance == null){
            Renderer.instance = new Renderer();
        }
        return Renderer.instance;
    }

    public enum VAOFormats{
        COLORED,
        TEXTURED
    }

    private EnumMap<VAOFormats, Integer> vaoIDs;
    private Map<String, Integer> shaderIDs;

    private Map<Integer, Layer> layers;

    private Map<String, Shader> shaders;
    public static void loadShader(String filePath){
        if(!get().shaders.containsKey(filePath)){
            get().shaders.put(filePath, new Shader(filePath));
        }
    }
    public static int getShader(String filePath){
        if(!get().shaders.containsKey(filePath)){
            return 0;
        }
        return get().shaders.get(filePath).getShaderId();
    }

    private Map<Integer, VAO> vaos;
    public static void loadVAO(VAO filePath){
        if(!get().shaders.containsKey(filePath)){
            get().shaders.put(filePath, new Shader(filePath));
        }
    }
    public static int getShader(String filePath){
        if(!get().shaders.containsKey(filePath)){
            return 0;
        }
        return get().shaders.get(filePath).getShaderId();
    }

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

    public static void draw(){

    }


}
