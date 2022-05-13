package engine.scenes;

import java.util.HashMap;
import java.util.Map;

import engine.renderer.Camera;
import engine.renderer.Renderer;

public class SceneManager {
    private static SceneManager instance;
    private static SceneManager get(){
        if(instance == null){
            instance = new SceneManager();
        }

        return instance;
    }


    private SceneManager(){
        scenes = new HashMap<String,Scene>();
        activeScene = "";
    }
    private Map<String, Scene> scenes;

    private String activeScene;

    public static Camera getActiveMainCamera(){
        return get().scenes.get(get().activeScene).mainCamera;
    }

    public static void addScene(String id, Scene scene){
        if(!get().scenes.containsKey(id)){
            get().scenes.put(id, scene);
            get().scenes.get(id).Awake();
        }

        assert false : "Scene Manager: scene with id" + id + "already exists";
    }

    public static void swapScene(String nextScene){
        if(get().scenes.containsKey(nextScene)){
            get().scenes.get(get().activeScene).End();

            Renderer.Refresh();

            get().activeScene = nextScene;
            get().scenes.get(get().activeScene).Start();
        }

        assert false : "Scene Manager: no scene with id " + nextScene;
    }

    public static void Update(double deltaTime){
        if(get().scenes.containsKey(get().activeScene)){
            get().scenes.get(get().activeScene).Update(deltaTime);
        }
    }
}
