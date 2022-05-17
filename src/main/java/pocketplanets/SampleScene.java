package pocketplanets;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Inputs.Input;
import engine.Inputs.InputAxis;
import engine.components.SpriteRenderer;
import engine.components.Transform;
import engine.gameobjects.GameObject;
import engine.renderer.Camera;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.scenes.Scene;
import engine.scenes.SceneManager;

public class SampleScene extends Scene{


    public SampleScene(){
        mainCamera = new Camera(new Vector2f(0, 0)); // camera will be changed soon
        gameObjects = new ArrayList<>();

        // setup inputs
        Input.addAxis("rotation", new InputAxis(-1, 1, 20, 20, Input.KeyCode.Q, Input.KeyCode.E));
        Input.addAxis("horizontal", new InputAxis(-1, 1, 20, 20, Input.KeyCode.D, Input.KeyCode.A));
        Input.addAxis("vertical", new InputAxis(-1, 1, 20, 20, Input.KeyCode.W, Input.KeyCode.S));

        // get textures (and other assets later)
        Texture t = new Texture("assets/textures/testImage.png");

        // create gameobject 
        SpriteMap sp = new SpriteMap(t, 1, 1);
    
        GameObject go = new GameObject();
            go.addComponent(new Transform(
                new Vector3f(0, 100, 0),
                new Vector2f(100, 100),
                0
            ));
            go.addComponent(new SpriteRenderer(sp));
            go.addComponent(new SampleComponent());
        
        gameObjects.add(go);
    }

    @Override
    public void Awake(){
        System.out.println("Awake");
        super.Awake();
    }

    @Override
    public void Start(){
        System.out.println("Start");
        super.Start();
    }

    @Override
    public void Update(double deltaTime){
        super.Update(deltaTime);
    }

    @Override
    public void End(){
        super.End();
        System.out.println("End");
    }
}
