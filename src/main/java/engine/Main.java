package engine;

import engine.scenes.Scene;
import engine.scenes.SceneManager;
import pocketplanets.SampleScene;

public class Main {
    public static void main(String[] args){
        Window window = Window.get(); 
        window.init();

        SceneManager.addScene("sample", new SampleScene());

        SceneManager.swapScene("sample");
        
        window.run();
    }
}
