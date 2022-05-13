package engine.Inputs;


import engine.Inputs.Input.KeyCode;

public class InputAxis {
    private float value;
    public float getValue(){
        return value;
    }

    private float max, min;
    private float decRate, incRate;
    
    private float settlePoint;
    private boolean doSettling;

    private KeyCode pos, altPos;
    private KeyCode neg, altNeg;


    public InputAxis(float min, float  max, 
                     float decRate, float incRate, 
                     KeyCode pos, KeyCode neg){
        this(
            min, max,
            decRate, incRate,
            true, (min + max )/ 2,
            pos,
            pos,
            neg,
            neg
        );
    }

    public InputAxis(float min, float  max, 
                     float decRate, float incRate, 
                     boolean doSettling, float settlePoint, 
                     KeyCode pos, KeyCode altPos, 
                     KeyCode neg, KeyCode altNeg){
        this.min = min;
        this.max = max;
        this.decRate = decRate;
        this.incRate = incRate;
        this.settlePoint = settlePoint;
        this.doSettling = doSettling;
        this.pos = pos;
        this.neg = neg;
        this.altPos = altPos;
        this.altNeg = altNeg;

        value = settlePoint;
    }

    public void Update(double dt){
        int inputDir = (KeyListener.getKeyPressed(pos.getValue()) || KeyListener.getKeyPressed(altPos.getValue()) ?  1 : 0) +
                       (KeyListener.getKeyPressed(neg.getValue()) || KeyListener.getKeyPressed(altNeg.getValue()) ? -1 : 0);
        
        System.out.println("updateing " + inputDir);
        // Decay the value
        if(inputDir == 0){
            if(Math.abs(value) < 0.0001){
                value = 0;
                return;
            }

            float decay = (float)dt * decRate * value/Math.abs(value);
            if(Math.abs(decay) > Math.abs(value)){
                value = 0;
            }
            else{
                value -= decay;
            }
        }
        // Incrment the value
        else{
            value += incRate * (float)dt * inputDir;
            value = Math.max(min, Math.min(max, value));
        }


    }

}
