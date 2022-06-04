package demo.scenes;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import demo.components.Player;
import demo.components.Weapon;
import engine.AssetManager;
import engine.Inputs.Input;
import engine.Inputs.Input.KeyCode;
import engine.Inputs.InputAxis;
import engine.components.FollowMouse;
import engine.components.LineRenderer;
import engine.components.SpriteRenderer;
import engine.components.Transform;
import engine.components.Transform.PositionMode;
import engine.gameobjects.GameObject;
import engine.geometry.Circle;
import engine.geometry.Quad;
import engine.physics.Collision;
import engine.physics.Move;
import engine.renderer.Camera;
import engine.renderer.Color;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.renderer.Texture.Format;
import engine.scenes.Scene;
import engine.scenes.SceneManager;

public class GrapplingGameScene extends Scene {

    public GrapplingGameScene() {
        // Construct camera
        mainCamera = new GameObject();
        mainCamera.addComponent(new Transform(
                new Vector3f(0, 0, 0),
                new Vector2f(1920, 1080),
                0f));
        mainCamera.addComponent(new Camera());
        gameObjects = new ArrayList<>();

        // Setup input axis (each incremented/decremented by specified keys)
        Input.addAxis("rotation", new InputAxis(-1, 1, 20, 20, Input.KeyCode.Q, Input.KeyCode.E));
        Input.addAxis("horizontal", new InputAxis(-1, 1, 20, 20, Input.KeyCode.D, Input.KeyCode.A));
        Input.addAxis("vertical", new InputAxis(-1, 1, 20, 20, Input.KeyCode.W, Input.KeyCode.S));

        // Get textures being used for game
        Texture playerTexture = AssetManager.getTexture("assets/textures/smiley.png");

        Texture reticleTexture = AssetManager.getTexture("assets/textures/reticle.png");

        Texture planetTexture = AssetManager.getTexture("assets/textures/whitePlanet.png");

        Texture backgroundTexture = AssetManager.getTexture("assets/textures/background.png", Format.RGB);

        Texture swordTexture = AssetManager.getTexture("assets/textures/sword.png");

        // Define spritemap for background
        SpriteMap backgroundSprite = new SpriteMap(backgroundTexture, 1, 1);

        // Construct background GameObject
        GameObject backgroundObject = new GameObject();
        // Construct transform for background for coordinate system and
        // positioning/rotation
        Transform backgroundTransform = new Transform(
                new Vector3f(0, 0, -20),
                new Vector2f(1920, 1080),
                0);

        // Set origin point of sprite to be bottom left
        backgroundTransform.positionOrigin = PositionMode.BOTTOM_LEFT;
        backgroundObject.addComponent(backgroundTransform);

        backgroundObject.addComponent(new SpriteRenderer(backgroundSprite, new Color(new Vector4f(0, 0, 0, 1)), 0));
        backgroundObject.getComponent(SpriteRenderer.class).setIsUI(true);

        // Construct grappling line object
        GameObject grapplingLine = new GameObject();

        // Utilize the LineRenderer to define line's starting specifications
        grapplingLine.addComponent(new LineRenderer(
                new Vector3f(0, 0, 0),
                new Vector3f(0, 0, 0),
                0,
                new Color(255, 0, 0, 255),
                new Color(255, 0, 0, 255),
                0));

        

        // Reticle SpriteMap construction
        SpriteMap reticleSprite = new SpriteMap(reticleTexture, 1, 1);

        // Create reticle GameObject
        GameObject reticleObject = new GameObject();
        // Construct transform for reticle positioning
        Transform reticleTransform = new Transform(
                new Vector3f(100, 100, -1),
                new Vector2f(140, 100),
                0);

        reticleTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
        reticleObject.addComponent(reticleTransform);
        reticleObject.addComponent(new SpriteRenderer(reticleSprite, new Color(new Vector4f(255, 0, 0, 1)), 0));

        // Add the FollowMouse component to execute code to set transform to mouse
        // coordinates
        reticleObject.addComponent(new FollowMouse());

        SpriteMap planetSprite = new SpriteMap(planetTexture, 1, 1);

        GameObject planetOneObject = new GameObject();
        Transform planetOneTransform = new Transform(
                new Vector3f(450, 400, -10),
                new Vector2f(200, 200),
                0);

        planetOneTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
        planetOneObject.addComponent(planetOneTransform);

        planetOneObject.addComponent(new SpriteRenderer(planetSprite, new Color(new Vector4f(255, 0, 0, 1)), 0));
        Circle planetOneCircle = new Circle(planetOneTransform.getPosition(), 100);
        planetOneObject.addComponent(new Collision(planetOneCircle));

        GameObject planetTwoObject = new GameObject();
        Transform planetTwoTransform = new Transform(
                new Vector3f(1400, 800, -10),
                new Vector2f(200, 200),
                20);

        planetTwoTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
        planetTwoObject.addComponent(planetTwoTransform);

        planetTwoObject.addComponent(new SpriteRenderer(planetSprite, new Color(new Vector4f(255, 0, 0, 1)), 0));
        Circle planetTwoCircle = new Circle(planetTwoTransform.getPosition(), 100);
        planetTwoObject.addComponent(new Collision(planetTwoCircle));

        // Define spriteMap for player
        SpriteMap playerSprite = new SpriteMap(playerTexture, 1, 1);

        // Player game object
        GameObject playerObject = new GameObject();
        // Construct transform for player for coordinate system and positioning/rotation
        Transform playerTransform = new Transform(
                new Vector3f(300, 100, 0),
                new Vector2f(100, 100),
                0);

        // Set origin of player to be center
        playerTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
        playerObject.addComponent(playerTransform);
        playerObject.addComponent(new SpriteRenderer(playerSprite, new Color(new Vector4f(255, 0, 0, 1)), 0));
        playerObject.addComponent(new Player(grapplingLine, planetOneCircle, planetTwoCircle));
        playerObject.addComponent(new Move());

        // Set up SpriteMap for sword 
        SpriteMap swordSprite = new SpriteMap(swordTexture, 1, 1);

        // Create gameObject for sword
        GameObject swordObject = new GameObject();

        // Create sword transform
        Transform swordTransform = new Transform(
                new Vector3f(200, 375, -11),
                new Vector2f(300, 100),
                0);

        // Set sprite origin to be center
        swordTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
        swordObject.addComponent(swordTransform);

        swordObject.addComponent(new SpriteRenderer(swordSprite, new Color(new Vector4f(255, 0, 0, 1)), 0));
        swordObject.addComponent(new Weapon(playerTransform));
        Collision swordCollision = new Collision(Quad.Rect(
                new Vector3f(swordTransform.position.x - swordTransform.scale.x / 2,
                        swordTransform.position.y - swordTransform.scale.y / 2, 0),
                300,
                100));
        swordObject.addComponent(swordCollision);


        // Add all objects to gameObjects list
        gameObjects.add(backgroundObject);
        gameObjects.add(planetOneObject);
        gameObjects.add(planetTwoObject);
        gameObjects.add(grapplingLine);
        gameObjects.add(playerObject);
        gameObjects.add(swordObject);
        gameObjects.add(reticleObject);

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
