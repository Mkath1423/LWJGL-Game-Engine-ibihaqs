package demo;

import demo.scenes.MultipleBatches;
import demo.scenes.MultipleRenderTypes;
import demo.scenes.SingleScene;
import engine.Window;
import engine.Inputs.Input;
import engine.Inputs.InputAxis;
import engine.scenes.SceneManager;
import grappledemo.GrapplingGameScene;
import pong.PongGameScene;

public class Demo {
    public static void main(String[] args){
        Window window = Window.get(); 
        window.init();
        
        Input.addAxis("rotation", new InputAxis(-1, 1, 20, 20, Input.KeyCode.Q, Input.KeyCode.E));
        Input.addAxis("horizontal", new InputAxis(-1, 1, 20, 20, Input.KeyCode.D, Input.KeyCode.A));
        Input.addAxis("vertical", new InputAxis(-1, 1, 20, 20, Input.KeyCode.W, Input.KeyCode.S));

        SceneManager.addScene("Single", new SingleScene());
        SceneManager.addScene("MultipleBatches", new MultipleBatches());
        SceneManager.addScene("PongGameScene", new PongGameScene());
        SceneManager.addScene("GrappleGameScene", new GrapplingGameScene());
        // SceneManager.addScene("MultipleRenderTypes", new MultipleRenderTypes());

        SceneManager.swapScene("Single");
        
        window.run();
    }
}
