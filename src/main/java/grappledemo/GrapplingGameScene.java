package grappledemo;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.AssetManager;
import engine.Inputs.Input;
import engine.Inputs.InputAxis;
import engine.components.FollowMouse;
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

public class GrapplingGameScene extends Scene{

    public GrapplingGameScene(){
        mainCamera = new GameObject(); // camera will be changed soon
            mainCamera.addComponent(new Transform(
                new Vector3f(0, 0, 0),
                new Vector2f(1920, 1080),
                0f
            ));
            mainCamera.addComponent(new Camera());
        gameObjects = new ArrayList<>();

        // setup inputs
        Input.addAxis("rotation", new InputAxis(-1, 1, 20, 20, Input.KeyCode.Q, Input.KeyCode.E));
        Input.addAxis("horizontal", new InputAxis(-1, 1, 20, 20, Input.KeyCode.D, Input.KeyCode.A));
        Input.addAxis("vertical", new InputAxis(-1, 1, 20, 20, Input.KeyCode.W, Input.KeyCode.S));

        

        // get textures (and other assets later)
        Texture playerTexture = AssetManager.getTexture("assets/textures/smiley.png");
        Texture reticleTexture = AssetManager.getTexture("assets/textures/reticle.png");
        Texture planetTexture = AssetManager.getTexture("assets/textures/whitePlanet.png");
        Texture backgroundTexture = AssetManager.getTexture("assets/textures/background.png", Format.RGB);

        // Player tool textures 
        Texture swordTexture = AssetManager.getTexture("assets/textures/sword.png");


        // // create gameobject 
        SpriteMap playerSprite = new SpriteMap(playerTexture, 1, 1);

        SpriteMap backgroundSprite = new SpriteMap(backgroundTexture, 1, 1);
        
        GameObject backgroundObject = new GameObject();
            Transform backgroundTransform = new Transform(
                new Vector3f(0, 0, -20),
                new Vector2f(1920, 1080),
                0
                );
    
            backgroundTransform.positionOrigin = PositionMode.BOTTOM_LEFT;
            backgroundObject.addComponent(backgroundTransform);

            backgroundObject.addComponent(new SpriteRenderer(backgroundSprite, new Color(new Vector4f(0, 0, 0, 1)), 0));
            backgroundObject.getComponent(SpriteRenderer.class).setIsUI(true);

        GameObject grapplingLine = new GameObject();
            
            grapplingLine.addComponent(new LineRenderer(
                new Vector3f(0,0,0), 
                new Vector3f(0,0,0), 
                0,
                new Color(255, 0, 0, 255), 
                new Color(255, 0, 0, 255)
            ));

        // Player game object
        GameObject playerObject = new GameObject();
            Transform playerTransform = new Transform(
                new Vector3f(300, 100, 0),
                new Vector2f(100, 100),
                0
            );

            playerTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
            playerObject.addComponent(playerTransform);
            playerObject.addComponent(new SpriteRenderer(playerSprite, new Color(new Vector4f(255, 0, 0, 1)), 0));
            playerObject.addComponent(new Player(grapplingLine));
            playerObject.addComponent(new Move());
            

        SpriteMap reticleSprite = new SpriteMap(reticleTexture, 1, 1);

        GameObject reticleObject = new GameObject();
            Transform reticleTransform = new Transform(
                new Vector3f(100, 100, -1),
                new Vector2f(140, 100),
                0
            );

            reticleTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
            reticleObject.addComponent(reticleTransform);
            reticleObject.addComponent(new SpriteRenderer(reticleSprite, new Color(new Vector4f(255, 0, 0, 1)), 0));
            reticleObject.addComponent(new FollowMouse());

        SpriteMap planetSprite = new SpriteMap(planetTexture, 1, 1);

        GameObject planetObject = new GameObject();
            planetObject.addComponent(new Transform(
                new Vector3f(600, 400, -10),
                new Vector2f(200, 200),
                0
            ));

            planetObject.addComponent(new SpriteRenderer(planetSprite, new Color(new Vector4f(255, 0, 0, 1)), 0));
  

        SpriteMap swordSprite = new SpriteMap(swordTexture, 1, 1);
        
        GameObject swordObject = new GameObject();
            Transform swordTransform = new Transform(
                new Vector3f(200, 375, -11),
                new Vector2f(300, 100),
                0
                );

            swordTransform.positionOrigin = PositionMode.CENTER_MIDDLE;
            swordObject.addComponent(swordTransform);

            swordObject.addComponent(new SpriteRenderer(swordSprite, new Color(new Vector4f(255, 0, 0, 1)), 0));
            swordObject.addComponent(new Weapon(playerTransform));
        
        
        
    
        gameObjects.add(backgroundObject);
        gameObjects.add(planetObject);
        
        
        gameObjects.add(grapplingLine);
        gameObjects.add(playerObject);
        gameObjects.add(swordObject);
        gameObjects.add(reticleObject);
        
        
        
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
