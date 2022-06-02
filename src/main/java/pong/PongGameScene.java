package grappledemo;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.AssetManager;
import engine.Inputs.Input;
import engine.Inputs.InputAxis;
import engine.components.LineRenderer;
import engine.components.SpriteRenderer;
import engine.components.Transform;
import engine.components.Transform.PositionMode;
import engine.gameobjects.GameObject;
import engine.physics.Move;
import engine.renderer.Camera;
import engine.renderer.Color;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.renderer.Texture.Format;
import engine.scenes.Scene;

public class PongGameScene extends Scene{

    public PongGameScene(){
        mainCamera = new GameObject(); // camera will be changed soon
            mainCamera.addComponent(new Transform(
                new Vector3f(0, 0, 0),
                new Vector2f(1920, 1080),
                0f
            ));
            mainCamera.addComponent(new Camera());
        gameObjects = new ArrayList<>();

        // setup inputs
        Input.addAxis("playerOneHorizontal", new InputAxis(-1, 1, 20, 20, Input.KeyCode.D, Input.KeyCode.A));
        Input.addAxis("playerTwoHorizontal", new InputAxis(-1, 1, 20, 20, Input.KeyCode.RIGHT, Input.KeyCode.LEFT));
        Input.addAxis("playerOneVertical", new InputAxis(-1, 1, 20, 20, Input.KeyCode.W, Input.KeyCode.S));
        Input.addAxis("playerTwoVertical", new InputAxis(-1, 1, 20, 20, Input.KeyCode.UP, Input.KeyCode.DOWN));

        // get textures (and other assets later)
        Texture paddleTexture = AssetManager.getTexture("assets/textures/pongPaddle.png");

        // // create gameobject 
        SpriteMap playerSprite = new SpriteMap(paddleTexture, 1, 1);        
     
        
    }


    @Override
    public void Awake(){
        System.out.println("Awake");
        super.Awake();
    }

    @Override
    public void Start(){
        System.out.println("Start");
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
