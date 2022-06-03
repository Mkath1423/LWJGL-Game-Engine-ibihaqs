package engine;

import org.lwjgl.opengl.GL20;

import engine.scenes.Scene;
import engine.scenes.SceneManager;
import grappledemo.GrapplingGameScene;
import pocketplanets.testing.BatchingTestScene;
import pong.PongGameScene;


public class Main {
    public static void main(String[] args){

        Window window = Window.get(); 
        window.init();


        SceneManager.addScene("GrapplingGameScene", new PongGameScene());

        SceneManager.swapScene("GrapplingGameScene");
        
        window.run();
    }
}
