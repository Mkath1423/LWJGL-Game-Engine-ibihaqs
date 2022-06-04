package demo.scenes;

import engine.scenes.Scene;
import engine.util.Time;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

import demo.components.ConstantSpin;
import demo.components.KeyMover;
import engine.AssetManager;
import engine.Window;
import engine.Inputs.Input;
import engine.Inputs.Input.KeyCode;
import engine.components.SpriteRenderer;
import engine.components.Transform;
import engine.components.Transform.Parameters;
import engine.components.Transform.PositionMode;
import engine.gameobjects.GameObject;
import engine.renderer.Camera;
import engine.renderer.Color;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.renderer.Texture.Format;
import engine.scenes.Scene;

public class ColorScene extends Scene{
    
    private SpriteRenderer[][] swatches = new SpriteRenderer[100][360];

    public ColorScene(){
        mainCamera = new GameObject(); // camera will be changed soon
            mainCamera.addComponent(new Transform(
                new Vector3f(0, 0, 0),
                new Vector2f(1920, 1080),
                0f
            ));
            mainCamera.addComponent(new Camera());
            mainCamera.addComponent(new KeyMover(500, 1));

        gameObjects = new ArrayList<>();

        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 360; x++) {
                GameObject swatch = new GameObject();
                    swatch.addComponent(new Transform(
                        new Vector3f(5*x, 1080 - 10*y , 0),
                        new Vector2f(5, 10),
                        0
                    ));
                    swatch.addComponent(new SpriteRenderer(null, new Color(), 1));
                        swatch.getComponent(SpriteRenderer.class).setUseColor(true);
                        swatch.getComponent(SpriteRenderer.class).setIsUI(false);
                        swatch.getComponent(SpriteRenderer.class).setMasking(false);

                swatches[y][x] = swatch.getComponent(SpriteRenderer.class);
                gameObjects.add(swatch);
            } 
        }
    }

    @Override
    public void Update(double deltaTime){
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 360; x++) {
                swatches[y][x].setColor(Color.HSV((x + (float)Time.getTime() * 50) % 360, (float)(Math.cos(Time.getTime())+1)/2, 1 - (float)y/100f));
            } 
        }

        System.out.println(1/deltaTime);
        super.Update(deltaTime);
    }
}
