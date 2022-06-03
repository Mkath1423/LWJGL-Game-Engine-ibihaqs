package pong;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.AssetManager;
import engine.Inputs.Input;
import engine.Inputs.InputAxis;
import engine.components.SpriteRenderer;
import engine.components.Transform;
import engine.components.Transform.PositionMode;
import engine.gameobjects.GameObject;
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

        Texture ballTexture = AssetManager.getTexture("assets/textures/pongBall.png", Format.RGB);

        // // create gameobject 
        SpriteMap paddleSprite = new SpriteMap(paddleTexture, 1, 1);        

        GameObject paddleOneObject = new GameObject();
            Transform paddleOneTransform = new Transform(
                new Vector3f(100, 540, -20),
                new Vector2f(20, 200),
                0
                );

            paddleOneTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
            paddleOneObject.addComponent(paddleOneTransform);

            paddleOneObject.addComponent(new SpriteRenderer(paddleSprite, new Color(new Vector4f(0, 0, 0, 255)), 0));

            paddleOneObject.addComponent(new Paddle(1));

        GameObject paddleTwoObject = new GameObject();
            Transform paddleTwoTransform = new Transform(
                new Vector3f(1820, 540, -20),
                new Vector2f(20, 200),
                0
                );

            paddleTwoTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
            paddleTwoObject.addComponent(paddleTwoTransform);

            paddleTwoObject.addComponent(new SpriteRenderer(paddleSprite, new Color(new Vector4f(0, 0, 0, 255)), 0));

            paddleTwoObject.addComponent(new Paddle(2));
        
        SpriteMap ballSprite = new SpriteMap(ballTexture, 1, 1);        

        GameObject ballObject = new GameObject();
            Transform ballTransform = new Transform(
                new Vector3f(920, 540, 0),
                new Vector2f(25, 28),
                0
            );

            ballTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
            ballObject.addComponent(ballTransform);

            ballObject.addComponent(new SpriteRenderer(ballSprite, new Color(new Vector4f(0, 0, 0, 255)), 0));

            ballObject.addComponent(new Ball(paddleOneTransform, paddleTwoTransform));

        gameObjects.add(paddleOneObject);
        gameObjects.add(paddleTwoObject);
        gameObjects.add(ballObject);
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
