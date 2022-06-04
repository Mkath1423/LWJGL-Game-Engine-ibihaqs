package demo;

import demo.scenes.ColorScene;
import demo.scenes.GrapplingGameScene;
import demo.scenes.MultipleBatches;
import demo.scenes.OrbitalScene;
import demo.scenes.PongGameScene;
import engine.Window;
import engine.Inputs.Input;
import engine.Inputs.InputAxis;
import engine.renderer.Color;
import engine.scenes.SceneManager;

/**
 * Demo program for show casing engine capabilities 
 */
public class Demo {
    public static void main(String[] args) {
        // get the window
        Window window = Window.get();

            // set window properties
            Window.setColor(new Color(255, 255, 255, 255));
            Window.setTitle("Final Project Tech Demonstration");
            Window.setResizable(false);
            Window.setShowCursor(false);

        // start open gl context
        window.init();

        // init input axes
        Input.addAxis("rotation", new InputAxis(-1, 1, 20, 20, Input.KeyCode.Q, Input.KeyCode.E));
        Input.addAxis("horizontal", new InputAxis(-1, 1, 20, 20, Input.KeyCode.D, Input.KeyCode.A));
        Input.addAxis("vertical", new InputAxis(-1, 1, 20, 20, Input.KeyCode.W, Input.KeyCode.S));

        // add scenes to scene manager
        SceneManager.addScene("PongGameScene", new PongGameScene());
        SceneManager.addScene("GrappleGameScene", new GrapplingGameScene());
        SceneManager.addScene("MultipleBatches", new MultipleBatches());
        SceneManager.addScene("OrbitalScene", new OrbitalScene());
        SceneManager.addScene("ColorScene", new ColorScene());

        // choose a starting scene
        SceneManager.swapScene("PongGameScene");

        // run the window
        window.run();
    }
}
