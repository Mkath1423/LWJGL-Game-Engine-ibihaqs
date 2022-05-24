package pocketplanets.testing;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.AssetManager;
import engine.Inputs.Input;
import engine.Inputs.InputAxis;
import engine.components.SpriteRenderer;
import engine.components.Transform;
import engine.gameobjects.GameObject;
import engine.renderer.Camera;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.renderer.Texture.Format;
import engine.scenes.Scene;
import engine.scenes.SceneManager;

public class BatchingTestScene extends Scene{


    public BatchingTestScene(){
        mainCamera = new Camera(new Vector2f(0, 0)); // camera will be changed soon
        gameObjects = new ArrayList<>();


        // get textures (and other assets later)
        Texture t = AssetManager.getTexture("assets/textures/testImage.png");
        Texture t2 = AssetManager.getTexture("assets/textures/opacityTest.png", Format.RGB);
        

        // // create gameobject 
        SpriteMap sp = new SpriteMap(t, 1, 1);
        SpriteMap sp2 = new SpriteMap(t2, 1, 1);


        for(int x = 0; x < 20; x ++){
            for (int y = 0; y < 50; y++) {
                GameObject go = new GameObject();
                    go.addComponent(new Transform(
                        new Vector3f(500 - x*10, 500 - y*10, 0),
                        new Vector2f(10, 10),
                        0
                    ));
                    go.addComponent(new SpriteRenderer(sp));

                gameObjects.add(go);
                GameObject go2 = new GameObject();
                go2.addComponent(new Transform(
                    new Vector3f(1000- x*10, 500 - y*10, 0),
                    new Vector2f(10, 10),
                    0
                ));
                go2.addComponent(new SpriteRenderer(sp2));

                gameObjects.add(go2);
        
            }
        }

        GameObject funky = new GameObject();
            funky.addComponent(new Transform(
                new Vector3f(100, 100, 0),
                new Vector2f(100, 200),
                0
            ));
            funky.addComponent(new SpriteRenderer(sp2));

        gameObjects.add(funky);
    }

    @Override
    public void Update(double deltaTime){
        System.out.println("fps: " + 1/deltaTime);
        super.Update(deltaTime);
    }
}
