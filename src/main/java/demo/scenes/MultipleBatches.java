package demo.scenes;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

import demo.components.KeyMover;
import engine.AssetManager;
import engine.Inputs.Input;
import engine.Inputs.Input.KeyCode;
import engine.components.SpriteRenderer;
import engine.components.Transform;
import engine.gameobjects.GameObject;
import engine.renderer.Camera;
import engine.renderer.Color;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.renderer.Texture.Format;
import engine.scenes.Scene;
import engine.scenes.SceneManager;

public class MultipleBatches extends Scene{
    public MultipleBatches(){
        mainCamera = new GameObject(); // camera will be changed soon
            mainCamera.addComponent(new Transform(
                new Vector3f(0, 0, 0),
                new Vector2f(1920, 1080),
                0f
            ));
            mainCamera.addComponent(new Camera());

        gameObjects = new ArrayList<>();

        Texture tex_smile = AssetManager.getTexture("assets/textures/smiley.png", Format.RGBA);
        Texture tex_planet = AssetManager.getTexture("assets/textures/whitePlanet.png", Format.RGBA);

        SpriteMap sprmap_smile = new SpriteMap(tex_smile, 1, 1);
        SpriteMap sprmap_planet = new SpriteMap(tex_planet, 1, 1);


        for(int x = 0; x < 60; x ++){
            for (int y = 0; y < 100; y++) {
                GameObject go = new GameObject();
                    go.addComponent(new Transform(
                        new Vector3f(x*10, y*10, -10),
                        new Vector2f(15, 15),
                        0
                    ));
                    go.addComponent(new SpriteRenderer(sprmap_smile, new Color(), 0));

                gameObjects.add(go);
                GameObject go2 = new GameObject();
                go2.addComponent(new Transform(
                    new Vector3f(1920/2 + x*10,  y*10, -10),
                    new Vector2f(15, 15),
                    0
                ));
                go2.addComponent(new SpriteRenderer(sprmap_planet, new Color(), 0));

                gameObjects.add(go2);

        
            }
        }



    }

    @ Override
    public void Update(double deltaTime){
        if(Input.getKeyboardButtonPressed(KeyCode.N)){
            SceneManager.swapScene("Single");
        }
    }
}
