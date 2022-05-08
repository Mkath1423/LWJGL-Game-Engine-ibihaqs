import java.security.Key;

import org.lwjgl.glfw.GLFW;

public class KeyListener {
    private static KeyListener instance;

    private boolean keyPressed[] = new boolean[350];

    private KeyListener(){

    }

    public static KeyListener get(){
        if(KeyListener.instance == null){
            KeyListener.instance = new KeyListener();
        }

        return KeyListener.instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods){
        if(key >= get().keyPressed.length) return;

        if(action == GLFW.GLFW_PRESS){
            get().keyPressed[key] = true;
        }
        else if(action == GLFW.GLFW_RELEASE){
            get().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int key){
        if(key >= get().keyPressed.length) return false;

        return get().keyPressed[key];
    }
}
