package pocketplanets.testing;

import java.util.ArrayList;

import javax.swing.text.Position;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.AssetManager;
import engine.Inputs.Input;
import engine.Inputs.InputAxis;
import engine.components.LineRenderer;
import engine.components.SpriteRenderer;
import engine.components.Transform;
import engine.gameobjects.GameObject;
import engine.renderer.Camera;
import engine.renderer.Color;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.renderer.Texture.Format;
import engine.scenes.Scene;

public class BatchingTestScene extends Scene{


    public BatchingTestScene(){

        Input.addAxis("rotation", new InputAxis(-1, 1, 20, 20, Input.KeyCode.Q, Input.KeyCode.E));
        Input.addAxis("horizontal", new InputAxis(-1, 1, 20, 20, Input.KeyCode.D, Input.KeyCode.A));
        Input.addAxis("vertical", new InputAxis(-1, 1, 20, 20, Input.KeyCode.W, Input.KeyCode.S));

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
        Texture t = AssetManager.getTexture("assets/textures/testImage.png");
        Texture t2 = AssetManager.getTexture("assets/textures/opacityTest.png", Format.RGB);
        

        // // create gameobject 
        SpriteMap sp = new SpriteMap(t, 1, 1);
        SpriteMap sp2 = new SpriteMap(t2, 1, 1);

        // for(int x = 0; x < 10; x ++){
        //     for (int y = 0; y < 50; y++) {
        //         GameObject go = new GameObject();
        //             go.addComponent(new Transform(
        //                 new Vector3f(500 - x*10, 500 - y*10, -10),
        //                 new Vector2f(10, 10),
        //                 0
        //             ));
        //             go.addComponent(new SpriteRenderer(sp, 0));

        //         gameObjects.add(go);
        //         GameObject go2 = new GameObject();
        //         go2.addComponent(new Transform(
        //             new Vector3f(1000- x*10, 500 - y*10, -10),
        //             new Vector2f(10, 10),
        //             0
        //         ));
        //         go2.addComponent(new SpriteRenderer(sp2, 0));

        //         gameObjects.add(go2);

        
        //     }
        // }

        GameObject funky = new GameObject();
            funky.addComponent(new Transform(
                new Vector3f(100, 300, -9),
                new Vector2f(100, 200),
                0
            ));

            funky.addComponent(new SpriteRenderer(sp, new Color(255, 255, 90, 255), 0));
            funky.addComponent(new SampleComponent());

        gameObjects.add(funky);
    
        GameObject line = new GameObject();
            line.addComponent(
                new LineRenderer(
                    new Vector3f(100, 100, 0), 
                    new Vector3f(400, 400, 0), 
                    20, 
                    new Color(0f, 125, 0f, 255), 
                    new Color(255f, 125, 255f, 255)
                )
            );

        gameObjects.add(line);

    }

    @Override
    public void Update(double deltaTime){
        System.out.println(1/deltaTime);
        super.Update(deltaTime);
    }
}
