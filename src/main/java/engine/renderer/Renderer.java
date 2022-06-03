package engine.renderer;

import java.util.HashMap;
import java.util.Map;

import engine.components.Renderable;
import engine.scenes.SceneManager;

/**
 * Singleton class from handling rendering
 */
public class Renderer {
    /**
     * Maximum elements and texures per batch
     */
    static final int MAX_BATCH_SIZE = 1000;
    static final int MAX_TEXTURES = 8;

    /**
     * Singleton construction
     */
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

    /**
     * The layers
     */
    private Map<Integer, Layer> layers;

    /**
     * Adds a renderable to the right layer
     * 
     * @param layerID the target layer
     * @param renderable the renderable to add
     */
    public static void addRenderable(int layerID, Renderable renderable){
        if(renderable == null) return;

        // if the layer does not exist, make a new one
        if(!get().layers.containsKey(layerID)){
            get().layers.put(layerID, new Layer());
        }

        // add this renderable to that layer
        get().layers.get(layerID).addRenderable(renderable);
    }

    /**
     * Removes a renderable from a layer
     * 
     * @param layerID the target layer
     * @param renderable the renderable to remvove
     * 
     * @warning currently does nothing becuse of issue with batching
     */
    public static void removeRenderable(int layerID, Renderable renderable){
        if(!get().layers.containsKey(layerID)) return;

        // get().layers.get(layerID).removeRenderable(renderable);
    }

    /**
     * Refreshes the renderer when changing scene
     * 
     * Clears all the layers and batches
     */
    public static void Refresh(){
        get().layers = new HashMap<>();
    }

    /**
     * Draws all the layers in order
     * 
     * Lowest number get drawn on the bottom
     */
    public static void draw(){

        // sort the layers 
        Object[] layerIDs = get().layers.keySet().toArray(); 

        // draw each layer
        for (Object object : layerIDs) {
            try {
                Integer key = (Integer)object;
                get().layers.get(key).draw();
            } catch (ClassCastException e) {
                assert false : "key could not cast. layer was not rendered.";
            }
        }
    }

    /**
     * Uploads the uniform data from a particular shader
     * 
     * @param shader the shader bing used
     */
    public static void uploadUniforms(Shader shader) {
        switch(shader){
            case SPRITE:
                // upload camera matrix
                Shader.SPRITE.uploadMat4f("uProjection", SceneManager.getActiveMainCamera().getProjectionMatrix());
                Shader.SPRITE.uploadMat4f("uView",       SceneManager.getActiveMainCamera().getViewMatrix());
                
                // upload texture slots
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
