package engine.Inputs;

import java.util.function.Function;

public class BoolState {
    boolean value;

    boolean changed;
    boolean rising;
    boolean falling;

    int index;
    Function<Integer, Boolean> getter;

    public BoolState(boolean startValue){

        this.value = startValue;

        changed = false; // TODO: ask about this
        rising  = false;
        falling = false;
    }

    public BoolState(){

        this.value = false;

        changed = false; // TODO: ask about this
        rising  = false;
        falling = false;
    }

    public void Refresh(boolean newValue){
        changed = value != newValue;
        rising = !value && newValue;
        falling = value && !newValue;
        
        value = newValue;
    }
}
