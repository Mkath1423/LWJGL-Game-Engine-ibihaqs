package engine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import engine.renderer.Texture;
import engine.renderer.Texture.Format;


/**
 * Manges assets 
 *  - textures
 * 
 * @link // https://github.com/codingminecraft/MarioYoutube/blob/c115151dcb110eb5a413d1b97a7b2852a472e45f/src/main/java/util/AssetPool.java
 */
public class AssetManager {
    /**
     * All textures that have been loaded
     */
    private static Map<String, Texture> textures = new HashMap<>();;

    /**
     * Gets a texture and loads it if nessessary
     * 
     * @param filepath the filepath
     * @return the texture
     */
    public static Texture getTexture(String filepath){
        File file = new File(filepath);

        /**
         * if the texture has not been loaded, load it
         */
        if(!textures.containsKey(file.getAbsolutePath())){
            textures.put(file.getAbsolutePath(), new Texture(filepath));
        }
        
        return textures.get(file.getAbsolutePath());
    }

    public static Texture getTexture(String filepath, Format format){
        File file = new File(filepath);

        /**
         * if the texture has not been loaded, load it
         */
        if(!textures.containsKey(file.getAbsolutePath())){
            textures.put(file.getAbsolutePath(), new Texture(filepath, format));
        }
        
        return textures.get(file.getAbsolutePath());
    }
}
