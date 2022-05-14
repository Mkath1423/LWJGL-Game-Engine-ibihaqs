package engine.Inputs;

import org.lwjgl.glfw.GLFW;

/**
 * Listens for and handles key events
 */
public class KeyListener {
    /**
     * Singleton construction
     */
    private static KeyListener instance;
    
    /**
     * Constructs the key listener  
     */
    private KeyListener(){
        for (int i = 0; i < keyPressed.length; i++) {
            keyStates[i] = new BoolState(false);
        }
    }

    /**
     * Returns the static singleton
     * 
     * Instantiates the singleton is it hasn't already
     * 
     * @return the static singleton
     */
    public static KeyListener get(){
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
    public  static boolean getKeyPressed(int i){
        if(i < 0 || i >= get().keyPressed.length) return false;
        return get().keyPressed[i];
    }

    /**
     * Gets state of key on the keyboard
     * 
     * @param i the key code
     * @return the state of the button
     * 
     * @see Input.KeyCode
     */
    private BoolState keyStates[] = new BoolState[350];
    public  static BoolState getKeyStates(int i){
        if(i < 0 || i >= get().keyStates.length) return null;
        return get().keyStates[i];
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
        if(key >= get().keyPressed.length) return;

        /**
         * Update the key value
         */
        if(action == GLFW.GLFW_PRESS){
            get().keyPressed[key] = true;
        }
        else if(action == GLFW.GLFW_RELEASE){
            get().keyPressed[key] = false;
        }

        /**
         * Update the key
         */
        for (int i = 0; i < get().keyStates.length; i++) {
            get().keyStates[i].Refresh(getKeyPressed(i));
        }
    }
}
