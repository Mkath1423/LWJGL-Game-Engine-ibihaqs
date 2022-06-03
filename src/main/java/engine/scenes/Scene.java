package engine.scenes;

import java.util.List;

import engine.gameobjects.GameObject;

public abstract class Scene {
    
    /**
     * The camera that will view this scene
     */
    protected GameObject mainCamera;

    /**
     * the id of this scene
     */
    protected String sceneId;

    /**
     * The gameObjects in this scene
     */
    protected List<GameObject> gameObjects; 

    /**
     * Callback triggered when scene is added to SceneManager
     * 
     * Gives Awake callback to all gameobject
     */
    public void Awake(){
        mainCamera.Awake();
        for (GameObject gameObject : gameObjects) {
            gameObject.Awake();
        }
    }

    /**
     * Callback triggered when scene is swapped to
     * 
     * Gives Start callback to all gameobject
     */
    public void Start(){
        mainCamera.Start();
        for (GameObject gameObject : gameObjects) {
            gameObject.Start();
        }
    }

    /**
     * Callback triggered before gameobjets are updated
     * 
     * Gives EarlyUpdate call back to all gameobject
     * 
     * @param deltaTime the time between frames
     */
    public void EarlyUpdate(double deltaTime){
        mainCamera.EarlyUpdate(deltaTime);
        for (GameObject gameObject : gameObjects) {
            gameObject.Update(deltaTime);
        }
    }

    /**
     * Callback triggers every frame that the scene is active
     * 
     * Gives Update callbakc to all gameobjects
     * 
     * @param deltaTime the time between frames
     */
    public void Update(double deltaTime){
        mainCamera.Update(deltaTime);
        for (GameObject gameObject : gameObjects) {
            gameObject.Update(deltaTime);
        }
    }

    /**
     * Callback triggered after gameobjets are updated
     * 
     * Gives LateUpdate call back to all gameobject
     *
     * @param deltaTime the time between frames
     */
    public void LateUpdate(double deltaTime){
        mainCamera.LateUpdate(deltaTime);
        for (GameObject gameObject : gameObjects) {
            gameObject.LateUpdate(deltaTime);
        }
    }

    /**
     * Callback triggered when scene is swapped off
     * 
     * Gives End callback to all gameobject
     */
    public void End(){
        mainCamera.End();
        for (GameObject gameObject : gameObjects) {
            gameObject.End();
        }
    }
}
