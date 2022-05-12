package scenes;

import java.util.List;

import components.Renderable;
import gameobjects.GameObject;

public abstract class Scene {
    
    private long sceneId;
    private List<GameObject> gameObjects; 

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
            Renderable r = gameObject.getComponent(Renderable.class);
            if(r != null){
                r.draw();
            }
        }


    }

    public void End(){
        for (GameObject gameObject : gameObjects) {
            gameObject.End();
        }
    }
}
