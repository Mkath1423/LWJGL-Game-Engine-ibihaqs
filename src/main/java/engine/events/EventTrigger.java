package engine.events;

import java.util.function.Supplier;

public class EventTrigger {
    public enum TriggerMode{
        RISING,
        FALLING,
        CHANGE
    }

    private Supplier<Boolean> getTriggerValue;

    private boolean lastValue;

    private boolean isEnabled;

    private TriggerMode triggerMode;

    // public Event<T> eventHandler 

    public EventTrigger(Supplier<Boolean> getter, TriggerMode triggerMode){
        this.getTriggerValue = getter;
        this.triggerMode = triggerMode;

        lastValue = getter.get();
        isEnabled = true;
    }

    public void Update(){
        if(!isEnabled) return;
        boolean newValue = getTriggerValue.get();

        if(triggerMode == TriggerMode.CHANGE && newValue != lastValue){
            // trigger events
        }

        if(triggerMode == TriggerMode.FALLING && newValue == false && lastValue == true){
            // trigger events
        }

        if(triggerMode == TriggerMode.RISING && newValue == true && lastValue == false){
            // trigger events
        }

        lastValue = newValue;
    }
}
