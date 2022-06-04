package demo.scenes;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import demo.components.Ball;
import demo.components.Paddle;
import engine.AssetManager;
import engine.Inputs.Input;
import engine.Inputs.InputAxis;
import engine.Inputs.Input.KeyCode;
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
import engine.scenes.SceneManager;

public class PongGameScene extends Scene {

    public PongGameScene() {
        mainCamera = new GameObject(); // camera will be changed soon
        mainCamera.addComponent(new Transform(
                new Vector3f(0, 0, 0),
                new Vector2f(1920, 1080),
                0f));
        mainCamera.addComponent(new Camera());
        gameObjects = new ArrayList<>();

        // Set up vertical inputs for paddles
        Input.addAxis("playerOneVertical", new InputAxis(-1, 1, 20, 20, Input.KeyCode.W, Input.KeyCode.S));
        Input.addAxis("playerTwoVertical", new InputAxis(-1, 1, 20, 20, Input.KeyCode.UP, Input.KeyCode.DOWN));

        // Get gameObject textures
        Texture paddleTexture = AssetManager.getTexture("assets/textures/pongPaddle.png");

        Texture ballTexture = AssetManager.getTexture("assets/textures/pongBall.png", Format.RGB);

        // Create paddle SpriteMap
        SpriteMap paddleSprite = new SpriteMap(paddleTexture, 1, 1);

        // Create paddle one gameObject
        GameObject paddleOneObject = new GameObject();

        // Create paddle one transform for coordinates, movement + rotation
        Transform paddleOneTransform = new Transform(
                new Vector3f(100, 540, -20),
                new Vector2f(20, 200),
                0);

        // Set middle of sprite to be origin
        paddleOneTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
        paddleOneObject.addComponent(paddleOneTransform);

        // Add sprite to the object
        paddleOneObject.addComponent(new SpriteRenderer(paddleSprite, new Color(new Vector4f(0, 0, 0, 255)), 0));

        // Add paddle object
        paddleOneObject.addComponent(new Paddle("playerOneVertical"));

        // Create paddle two game object 
        GameObject paddleTwoObject = new GameObject();

        // Create paddle two transform for coordinates, movement + rotation
        Transform paddleTwoTransform = new Transform(
                new Vector3f(1820, 540, -20),
                new Vector2f(20, 200),
                0);

        // Set centre of sprite to be origin
        paddleTwoTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
        paddleTwoObject.addComponent(paddleTwoTransform);

        // Add sprite to the object
        paddleTwoObject.addComponent(new SpriteRenderer(paddleSprite, new Color(new Vector4f(0, 0, 0, 255)), 0));

        // Add paddle object
        paddleTwoObject.addComponent(new Paddle("playerTwoVertical"));


        // Create SpriteMap for ball 
        SpriteMap ballSprite = new SpriteMap(ballTexture, 1, 1);

        // Create ball gameObject
        GameObject ballObject = new GameObject();

        // Create ball transform (coordinates, movement + rotation)
        Transform ballTransform = new Transform(
                new Vector3f(920, 540, 0),
                new Vector2f(25, 28),
                0);

        // Set center of sprite to be origin
        ballTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
        ballObject.addComponent(ballTransform);

        // Add sprite to object
        ballObject.addComponent(new SpriteRenderer(ballSprite, new Color(new Vector4f(0, 0, 0, 255)), 0));

        // Add ball object
        ballObject.addComponent(new Ball(paddleOneTransform, paddleTwoTransform));


        // Add all objects to gameObjects list
        gameObjects.add(paddleOneObject);
        gameObjects.add(paddleTwoObject);
        gameObjects.add(ballObject);
    }

    @Override
    public void Awake() {
        System.out.println("Awake");
        super.Awake();
    }

    @Override
    public void Start() {
        System.out.println("Start");
        super.Start();
    }

    @Override
    public void Update(double deltaTime){
        if (Input.getKeyboardButtonPressed(KeyCode.M)) {
            SceneManager.nextScene();
        }
        if (Input.getKeyboardButtonPressed(KeyCode.N)) {
            SceneManager.previousScene();
        }

        super.Update(deltaTime);
    }

    @Override
    public void End() {
        super.End();
        System.out.println("End");
    }
}
