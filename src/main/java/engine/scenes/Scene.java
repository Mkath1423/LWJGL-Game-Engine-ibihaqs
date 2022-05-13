package engine.scenes;

import java.util.List;

import engine.gameobjects.GameObject;
import engine.renderer.Camera;

public abstract class Scene {
    
    protected Camera mainCamera;

    protected String sceneId;
    protected List<GameObject> gameObjects; 

    public void Awake(){
        for (GameObject gameObject : gameObjects) {
            gameObject.Awake();
        }
    }

    public void Start(){
        for (GameObject gameObject : gameObjects) {
            gameObject.Start();
        }
    }

    public void Update(double deltaTime){
        for (GameObject gameObject : gameObjects) {
            gameObject.Update(deltaTime);
        }
    }

    public void End(){
        for (GameObject gameObject : gameObjects) {
            gameObject.End();
        }
    }
}
