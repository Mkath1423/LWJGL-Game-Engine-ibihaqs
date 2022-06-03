package engine.scenes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.gameobjects.GameObject;
import engine.renderer.Camera;
import engine.renderer.Renderer;

/**
 * Singleton class from managing scenes
 * 
 * @author Lex Stapleton
 */
public class SceneManager {
    /**
     * Singleton construction
     */
    private static SceneManager instance;
    private static SceneManager get(){
        if(instance == null){
            instance = new SceneManager();
        }

        return instance;
    }

    private SceneManager(){
        scenes = new HashMap<String,Scene>();
        activeScene = null;
    }

    /**
     * The scenes that can be used
     */
    private Map<String, Scene> scenes;

    /**
     * The scene that is currently active
     */
    private String activeSceneName = "";
    private Scene activeScene = null;

    /**
     * Gets the main camera from the active scene
     * 
     * @return the camera from the acrive scene
     */
    public static Camera getActiveMainCamera(){
        return get().activeScene.mainCamera.getComponent(Camera.class);
    }

    /**
     * Adds a scene to the scene manager
     * 
     * @param id the id of the scene
     * @param scene the scene object
     */
    public static void addScene(String id, Scene scene){
        // add and awaken the scene 
        if(!get().scenes.containsKey(id)){
            get().scenes.put(id, scene);
            get().scenes.get(id).Awake();
        }

        // raise assertion error if scene has already been added
        assert false : "Scene Manager: scene with id" + id + "already exists";
    }

    /**
     * Changes the active scene
     * 
     * @param nextScene the id of the next scene
     */
    public static void swapScene(String nextScene){
        if(get().scenes.containsKey(nextScene)){
            // End the current scene
            if(get().activeScene != null)
                get().activeScene.End();

            // Clear the renderer
            Renderer.Refresh();

            // swap the scene 
            get().activeScene = get().scenes.get(nextScene);
            get().activeSceneName = nextScene;

            // start the new scene
            get().activeScene.Start();
        }

        assert false : "Scene Manager: no scene with id " + nextScene;
    }
    /**
     * Updates the active scene
     * 
     * calles EarlyUpadte Update and LateUpdate in that order
     * 
     * @param deltaTime the time between frames
     */
    public static void Update(double deltaTime){
        if(get().activeScene != null){
            get().activeScene.EarlyUpdate(deltaTime);
            get().activeScene.Update(deltaTime);
            get().activeScene.LateUpdate(deltaTime);
        }
    }

    /**
     * Getter for game objects
     * 
     * @return A list of all the gameobjects in the activve scene
     */
    public static List<GameObject> getGameObjects() {

        return get().activeScene.gameObjects;
    }
}
