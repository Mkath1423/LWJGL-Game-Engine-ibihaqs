package nebula;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.AssetManager;
import engine.Inputs.Input;
import engine.Inputs.InputAxis;
import engine.components.SpriteRenderer;
import engine.components.Transform;
import engine.gameobjects.GameObject;
import engine.physics.Move;
import engine.renderer.Camera;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.scenes.Scene;
import engine.scenes.SceneManager;
//import physics.Move;
import pocketplanets.ships.BuilderShip;
import pocketplanets.testing.FollowMouse;
import pocketplanets.testing.SampleComponent;

public class MainGameScene extends Scene{


    public MainGameScene(){
        mainCamera = new Camera(new Vector2f(0, 0)); // camera will be changed soon
        gameObjects = new ArrayList<>();

        // setup inputs
        Input.addAxis("rotation", new InputAxis(-1, 1, 20, 20, Input.KeyCode.Q, Input.KeyCode.E));
        Input.addAxis("horizontal", new InputAxis(-1, 1, 20, 20, Input.KeyCode.D, Input.KeyCode.A));
        Input.addAxis("vertical", new InputAxis(-1, 1, 20, 20, Input.KeyCode.W, Input.KeyCode.S));

        // get textures (and other assets later)
        Texture t = AssetManager.getTexture("assets/textures/testImage.png");
        Texture t2 = AssetManager.getTexture("assets/textures/luigi.png");
        Texture t3 = AssetManager.getTexture("assets/textures/smiley.png");


        // // create gameobject 
        SpriteMap sp = new SpriteMap(t, 1, 1);

        // mario
        GameObject go1 = new GameObject();
            go1.addComponent(new Transform(
                new Vector3f(300, 100, 0),
                new Vector2f(100, 100),
                0
            ));
            go1.addComponent(new SpriteRenderer(sp));
            go1.addComponent(new SampleComponent());
            // go.addComponent(new Move());

        SpriteMap sp2 = new SpriteMap(t2, 1, 1);
        // GameObject go2 = new GameObject();
        //     go2.addComponent(new Transform(
        //         new Vector3f(0, 0, 0),
        //         new Vector2f(30, 150),
        //         0
        //     ));
        //     go2.addComponent(new SpriteRenderer(sp2));
        //     go2.addComponent(new SampleSwinging());
        //     go2.setParent(go1);
        //     // go.addComponent(new Move());
        

        

        GameObject go3 = new GameObject();
            go3.addComponent(new Transform(
                new Vector3f(100, 100, 0),
                new Vector2f(50, 50),
                0
            ));
            go3.addComponent(new SpriteRenderer(sp2));
            go3.addComponent(new FollowMouse());
            // go.addComponent(new Move());

        gameObjects.add(go1);
        // gameObjects.add(go2);
        gameObjects.add(go3);
        SpriteMap sp3 = new SpriteMap(t3, 1, 1);
        MakeShip(sp3);
        
    }

    

    public void MakeShip(SpriteMap sprite){
        GameObject newShip = new GameObject();
            newShip.addComponent(new Transform(
                new Vector3f(200, 200, 0), 
                new Vector2f(50, 50), 
                0
            ));
            newShip.addComponent(new SpriteRenderer(sprite));
            //newShip.addComponent(new FollowMouse());
            //newShip.addComponent(new Move());

            newShip.addComponent(new BuilderShip(100, 100, 3, 20, 10, false));

        gameObjects.add(newShip);
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
