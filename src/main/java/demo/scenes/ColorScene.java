package demo.scenes;

import engine.scenes.Scene;
import engine.scenes.SceneManager;
import engine.util.Time;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

import demo.components.KeyMover;
import engine.Inputs.Input;
import engine.Inputs.Input.KeyCode;
import engine.components.SpriteRenderer;
import engine.components.Transform;
import engine.gameobjects.GameObject;
import engine.renderer.Camera;
import engine.renderer.Color;

/**
 * A scene for demonstrating the colors and performance 
 */
public class ColorScene extends Scene{
    
    /**
     * The renderables for the colored objects
     */
    private SpriteRenderer[][] swatches = new SpriteRenderer[100][360];

    public ColorScene(){
        // add the camera
        mainCamera = new GameObject();
            mainCamera.addComponent(new Transform(
                new Vector3f(0, 0, 0),
                new Vector2f(1920, 1080),
                0f
            ));
            mainCamera.addComponent(new Camera());
            mainCamera.addComponent(new KeyMover(500, 1));

        gameObjects = new ArrayList<>();

        // add the colored rectangle game objects
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
        // swap scenes when buttons are pressed
        if (Input.getKeyboardButtonPressed(KeyCode.M)) {
            SceneManager.nextScene();
        }
        if (Input.getKeyboardButtonPressed(KeyCode.N)) {
            SceneManager.previousScene();
        }

        // update the color of the sprite renderers
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 360; x++) {
                swatches[y][x].setColor(Color.HSV((x + (float)Time.getTime() * 50) % 360, (float)(Math.cos(Time.getTime())+1)/2, 1 - (float)y/100f));
            } 
        }

        super.Update(deltaTime);
    }
}
