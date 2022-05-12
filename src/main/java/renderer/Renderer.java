package renderer;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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


    private List<RenderBatch> batches;

    private Renderer(){
    }

    public static void addRenderable(){
        
    }

    public static void draw(){

    }


}
