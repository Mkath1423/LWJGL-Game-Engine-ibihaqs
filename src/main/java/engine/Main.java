package engine;

import org.lwjgl.opengl.GL20;

import engine.scenes.Scene;
import engine.scenes.SceneManager;
import pocketplanets.testing.BatchingTestScene;


public class Main {
    public static void main(String[] args){

        Window window = Window.get(); 
        window.init();

        SceneManager.addScene("sample", new BatchingTestScene());

        SceneManager.swapScene("sample");
        
        window.run();
    }
}
