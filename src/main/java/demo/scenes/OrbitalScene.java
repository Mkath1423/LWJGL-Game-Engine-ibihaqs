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
import engine.components.Transform.PositionMode;
import engine.gameobjects.GameObject;
import engine.renderer.Camera;
import engine.renderer.Color;
import engine.renderer.SpriteMap;
import engine.renderer.Texture;
import engine.renderer.Texture.Format;
import engine.scenes.Scene;
import engine.scenes.SceneManager;

/**
 * A demo for parenting objects to one another
 */
public class OrbitalScene extends Scene{
    public OrbitalScene(){
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

        // get textures
        Texture tex_planet = AssetManager.getTexture("assets/textures/whitePlanet.png", Format.RGBA);
        Texture tex_sun = AssetManager.getTexture("assets/textures/smiley.png", Format.RGBA);
        
        // generate sprite maps
        SpriteMap spr_planet = new SpriteMap(tex_planet, 1, 1);
        SpriteMap spr_sun = new SpriteMap(tex_sun, 1, 1);

        // generate colors
        Color sunColor = Color.HSV(59, 1f, 1f);
        Color planetColor = Color.HSV(158f, 1f, 1f);

        // add the sun object
        GameObject sun = new GameObject();
            sun.addComponent(new Transform(
                new Vector3f(100, 500 , 0),
                new Vector2f(150, 150),
                0
            ));
                sun.getComponent(Transform.class).positionOrigin = PositionMode.CENTER_MIDDLE;
            sun.addComponent(new SpriteRenderer(spr_sun, sunColor, 1));
                sun.getComponent(SpriteRenderer.class).setUseColor(false);
                sun.getComponent(SpriteRenderer.class).setIsUI(false);
                sun.getComponent(SpriteRenderer.class).setMasking(true); // make it a colored mask
            sun.addComponent(new ConstantSpin(0.1f));

        // add the planet object
        GameObject planet = new GameObject();
            planet.addComponent(new Transform(
                new Vector3f(300, 0, 0),
                new Vector2f(100, 100),
                0
            ));
                planet.getComponent(Transform.class).positionOrigin = PositionMode.CENTER_MIDDLE;
            planet.addComponent(new SpriteRenderer(spr_planet, planetColor, 1));
                planet.getComponent(SpriteRenderer.class).setUseColor(false);
                planet.getComponent(SpriteRenderer.class).setIsUI(false);
                planet.getComponent(SpriteRenderer.class).setMasking(true); // make it a colored mask
            planet.addComponent(new ConstantSpin(0.2f));
            planet.setParent(sun); // make it follow the sun
        
        // add the moon object
        GameObject moon = new GameObject();
            moon.addComponent(new Transform(
                new Vector3f(100, 0, 0),
                new Vector2f(30, 30),
                0
            ));
                moon.getComponent(Transform.class).positionOrigin = PositionMode.CENTER_MIDDLE;
            moon.addComponent(new SpriteRenderer(spr_planet, new Color(), 1));
                moon.getComponent(SpriteRenderer.class).setUseColor(false);
                moon.getComponent(SpriteRenderer.class).setIsUI(false);
                moon.getComponent(SpriteRenderer.class).setMasking(false); // make it a normal sprite
            moon.addComponent(new ConstantSpin(1));
            moon.setParent(planet);

        gameObjects.add(planet);
        gameObjects.add(moon);
        gameObjects.add(sun);
    }

    @Override
    public void Update(double deltaTime){
        // swap scenes
        if (Input.getKeyboardButtonPressed(KeyCode.M)) {
            SceneManager.nextScene();
        }
        if (Input.getKeyboardButtonPressed(KeyCode.N)) {
            SceneManager.previousScene();
        }

        super.Update(deltaTime);
    }
}
