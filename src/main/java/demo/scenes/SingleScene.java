package demo.scenes;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

import demo.components.ConstantSpin;
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

public class SingleScene extends Scene {
    public SingleScene() {
        mainCamera = new GameObject(); // camera will be changed soon
        mainCamera.addComponent(new Transform(
                new Vector3f(0, 0, 0),
                new Vector2f(1920, 1080),
                0f));
        mainCamera.addComponent(new Camera());

        gameObjects = new ArrayList<>();

        Texture texture = AssetManager.getTexture("assets/textures/smiley.png", Format.RGBA);
        Texture texture2 = AssetManager.getTexture("assets/textures/whitePlanet.png", Format.RGBA);
        SpriteMap spriteMap = new SpriteMap(texture, 1, 1);
        SpriteMap spriteMap2 = new SpriteMap(texture2, 1, 1);

        GameObject doubleObject = new GameObject();
        doubleObject.addComponent(new Transform(
                new Vector3f(100, 0, 0),
                new Vector2f(100, 100),
                0f));
        doubleObject.addComponent(new SpriteRenderer(spriteMap2, new Color(), 1));
        doubleObject.addComponent(new KeyMover(0, 20));

        gameObjects.add(doubleObject);

        GameObject singlGameObject = new GameObject();
        singlGameObject.addComponent(new Transform(
                new Vector3f(100, 100, 0),
                new Vector2f(100, 100),
                0f));
        singlGameObject.addComponent(new SpriteRenderer(spriteMap, new Color(), 2));
        singlGameObject.addComponent(new KeyMover(100, 2));
        doubleObject.setParent(singlGameObject);
        gameObjects.add(singlGameObject);
    }

    @Override
    public void Update(double deltaTime) {
        if (Input.getKeyboardButtonPressed(KeyCode.M)) {
            SceneManager.swapScene("GrappleGameScene");
        }
        if (Input.getKeyboardButtonPressed(KeyCode.N)) {
            SceneManager.swapScene("MultipleBatches");
        }
    }
}
