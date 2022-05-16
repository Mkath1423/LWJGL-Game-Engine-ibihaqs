package engine.Inputs;

import org.lwjgl.glfw.GLFW;

/**
 * Listens for and handles key events
 */
public final class KeyListener {
    /**
     * Singleton construction
     */
    private static KeyListener instance;
    
    /**
     * Constructs the key listener  
     */
    private KeyListener(){
    }

    /**
     * Returns the static singleton
     * 
     * Instantiates the singleton is it hasn't already
     * 
     * @return the static singleton
     */
    private static KeyListener get(){
        if(KeyListener.instance == null){
            KeyListener.instance = new KeyListener();
        }

        return KeyListener.instance;
    }

    /**
     * The current value of each possible key
     * 
     * 1 for every key
     * elements correspond to their keycode
     * 
     * @see Input.KeyCode
     */
    private boolean keyPressed[] = new boolean[350];
    
    /**
     * Gets value of key on the keyboard
     * 
     * @param i the key code
     * @return true if the button is prsseed
     * 
     * @see Input.KeyCode
     */
    protected static boolean getKeyPressed(int i){
        if(i < 0 || i >= get().keyPressed.length) return false;
        return get().keyPressed[i];
    } 

    /**
     * The callback for the keyListener
     * 
     * @param window the window
     * @param key the key the key being updated
     * @param scancode unimportant
     * @param action is pressed or released
     * @param mods modifier keys
     */
    public static void keyCallback(long window, int key, int scancode, int action, int mods){
        if(key >= get().keyPressed.length || key < 0) return;

        System.out.println(key);
        /**
         * Update the key value
         */
        if(action == GLFW.GLFW_PRESS){
            get().keyPressed[key] = true;
        }
        else if(action == GLFW.GLFW_RELEASE){
            get().keyPressed[key] = false;
        }
    }
}
