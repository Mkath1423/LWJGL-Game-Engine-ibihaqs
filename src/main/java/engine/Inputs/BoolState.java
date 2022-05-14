package engine.Inputs;

/**
 * Represents the state of a keyboard button
 * 
 * @author Lex Stapleton 
 */
public class BoolState {
    /**
     * The current value of the boolean being tracked
     */
    private boolean value;

    /**
     * How the buttons state is changing in the last frame
     *  rising : from false to true (pressed)
     *  falling: form true to false (released)
     *  changed: is not the same    (either)
     */
    private boolean changed;
    private boolean rising;
    private boolean falling;

    /**
     * Getters for the previous values 
     * 
     * @return
     */
    public boolean getValue   () { return value;   }

    public boolean getChanged () { return changed; }

    public boolean getRising  () { return rising;  }
    public boolean getPressed () { return rising;  }

    public boolean getFalling () { return falling; }
    public boolean getReleased() { return falling; }


    /**
     * Constructs a new BoolState
     * 
     * @param startValue initial value of the button
     */
    public BoolState(boolean startValue){

        this.value = startValue;

        changed = false; // TODO: ask about this
        rising  = false;
        falling = false;
    }
    /**
     * Constructs a new BoolState
     * 
     * Uses initial value of false
     */
    public BoolState(){
        this(false);
    }

    /**
     * Updates the boolean state variables
     * 
     * @param newValue
     */
    public void Refresh(boolean newValue){
        changed = value != newValue;
        rising = !value && newValue;
        falling = value && !newValue;
        
        value = newValue;
    }
}
