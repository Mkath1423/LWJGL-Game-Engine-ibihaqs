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
        activeScene = null;
    }
    private Map<String, Scene> scenes;

    private Scene activeScene = null;

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
            if(get().activeScene != null)
                get().activeScene.End();

            Renderer.Refresh();

            get().activeScene = get().scenes.get(nextScene);
            get().activeScene.Start();
        }

        assert false : "Scene Manager: no scene with id " + nextScene;
    }

    public static void Update(double deltaTime){
        if(get().activeScene != null){
            get().activeScene.Update(deltaTime);
        }
    }
}
