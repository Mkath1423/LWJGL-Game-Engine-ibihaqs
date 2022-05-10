package Inputs;

import org.lwjgl.system.windows.INPUT;

import Inputs.Input.KeyCode;

public class InputAxis {
    private float value;

    private float max, min;
    private float decRate, incRate;
    
    private float settlePoint;

    private KeyCode pos, altPos;
    private KeyCode neg, altNeg;

    public InputAxis(float min, float  max, 
                     float decRate, float incRate, 
                     KeyCode pos, KeyCode neg){
        this(
            min, max,
            decRate, incRate,
            (min + max )/ 2,
            pos,
            KeyCode.UNKNOWN,
            neg,
            KeyCode.UNKNOWN
        );
    }

    public InputAxis(float min, float  max, 
                     float decRate, float incRate, 
                     float settlePoint, 
                     KeyCode pos, KeyCode altPos, 
                     KeyCode neg, KeyCode altNeg){
        this.min = min;
        this.max = max;
        this.decRate = decRate;
        this.incRate = incRate;
        this.settlePoint = settlePoint;
        this.pos = pos;
        this.pos = pos;
        this.altPos = altPos;
        this.altNeg = altNeg;

        value = settlePoint;
    }

    public void Update(double dt){
        int direction = (KeyListener.getKeyPressed(pos.getValue()) || KeyListener.getKeyPressed(altPos.getValue()) ?  1 : 0) +
                        (KeyListener.getKeyPressed(neg.getValue()) || KeyListener.getKeyPressed(altNeg.getValue()) ? -1 : 0);;

        float adjustment = decRate * (float)dt * direction;
    }

}
