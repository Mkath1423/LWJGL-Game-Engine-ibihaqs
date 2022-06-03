package robinhood;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

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

public class RobinhoodGameScene extends Scene{

    public RobinhoodGameScene(){
        mainCamera = new GameObject(); // camera will be changed soon
            mainCamera.addComponent(new Transform(
                new Vector3f(0, 0, 0),
                new Vector2f(1920, 1080),
                0f
            ));
            mainCamera.addComponent(new Camera());
        gameObjects = new ArrayList<>();

        // setup inputs
        Input.addAxis("horizontal", new InputAxis(-1, 1, 20, 20, Input.KeyCode.D, Input.KeyCode.A));
        Input.addAxis("vertical", new InputAxis(-1, 1, 20, 20, Input.KeyCode.W, Input.KeyCode.S));

        // get textures (and other assets later)
        Texture bowTexture = AssetManager.getTexture("assets/textures/bow.png");

        Texture arrowTexture = AssetManager.getTexture("assets/textures/arrow.png", Format.RGB);

        // // create gameobject 
        SpriteMap bowSprite = new SpriteMap(bowTexture, 1, 1);        

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
        if(Input.getKeyboardButtonPressed(KeyCode.M)){
            SceneManager.swapScene("MultipleBatches");
        }
        if(Input.getKeyboardButtonPressed(KeyCode.N)){
            SceneManager.swapScene("GrappleGameScene");
        }   
    }

    @Override
    public void End(){
        super.End();
        System.out.println("End");
    }
}
