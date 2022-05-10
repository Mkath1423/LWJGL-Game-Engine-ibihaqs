package Inputs;

import java.security.Key;

import org.lwjgl.glfw.GLFW;

public class KeyListener {
    private static KeyListener instance;

    private boolean keyPressed[] = new boolean[350];
    public  static boolean getKeyPressed(int i){
        if(i < 0 || i >= get().keyPressed.length) return false;
        return get().keyPressed[i];
    }

    private BoolState keyStates[] = new BoolState[350];
    public  static BoolState getKeyStates(int i){
        if(i < 0 || i >= get().keyPressed.length) return null;
        return get().keyStates[i];
    }   

    private KeyListener(){
        for (int i = 0; i < keyPressed.length; i++) {
            keyStates[i] = new BoolState(getKeyPressed(i));
        }
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

        for (int i = 0; i < get().keyStates.length; i++) {
            get().keyStates[i].Refresh(get().getKeyPressed(i));
        }
    }

    public static boolean isKeyPressed(int key){
        if(key >= get().keyPressed.length) return false;

        return get().keyPressed[key];
    }
}
