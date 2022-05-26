package engine.scenes;

import java.util.List;

import engine.gameobjects.GameObject;
import engine.renderer.Camera;

public abstract class Scene {
    
    protected GameObject mainCamera;

    protected String sceneId;
    protected List<GameObject> gameObjects; 

    public void Awake(){
        mainCamera.Awake();
        for (GameObject gameObject : gameObjects) {
            gameObject.Awake();
        }
    }

    public void Start(){
        mainCamera.Start();
        for (GameObject gameObject : gameObjects) {
            gameObject.Start();
        }
    }

    public void EarlyUpdate(double deltaTime){
        mainCamera.EarlyUpdate(deltaTime);
        for (GameObject gameObject : gameObjects) {
            gameObject.Update(deltaTime);
        }
    }

    public void Update(double deltaTime){
        mainCamera.Update(deltaTime);
        for (GameObject gameObject : gameObjects) {
            gameObject.Update(deltaTime);
        }
    }

    public void LateUpdate(double deltaTime){
        mainCamera.LateUpdate(deltaTime);
        for (GameObject gameObject : gameObjects) {
            gameObject.LateUpdate(deltaTime);
        }
    }

    public void End(){
        mainCamera.End();
        for (GameObject gameObject : gameObjects) {
            gameObject.End();
        }
    }
}
