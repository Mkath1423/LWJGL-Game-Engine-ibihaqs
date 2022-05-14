package engine.Inputs;


import engine.Inputs.Input.KeyCode;


/**
 * An axis that is moved by keys
 * 
 *  min <---------------- settle point -----------------> max
 * 
 *  Positive buttons increase value
 *  Negative buttons decrease value
 * 
 *  System will return to settle point when no input is given
 */
public class InputAxis {

    /**
     * The current value of the input axis
     */
    private float value;
    public float getValue(){
        return value;
    }

    /**
     * The min and max value of the axis
     */
    private float max, min;

    /**
     * The point to return to when no input is given
     */
    private float settlePoint;

    /**
     * How quickly the axis
     */
    private float decRate, incRate;
    
    /**
     * if true the input value will instantly change to the min max or settle point
     */
    private boolean enableSnapping;
    
    /**
     * keys that can change the axis
     */
    private KeyCode pos, altPos;
    private KeyCode neg, altNeg;

    /**
     * Constructor for the input axis
     * @param min the max input value
     * @param max the min input value
     * @param decRate how fast to increase the value
     * @param incRate how fast to decay    the value
     * @param enableSnapping snapping or interpolation
     * @param settlePoint the value when no input is given
     * @param pos the positive input
     * @param altPos the alternate positive input
     * @param neg the negative input
     * @param altNe the alternate negative input
     */
    public InputAxis(float min, float  max, 
                     float decRate, float incRate, 
                     boolean enableSnapping, float settlePoint, 
                     KeyCode pos, KeyCode altPos, 
                     KeyCode neg, KeyCode altNeg){
        this.min = min;
        this.max = max;
        this.decRate = decRate;
        this.incRate = incRate;
        this.settlePoint = settlePoint;
        this.enableSnapping = enableSnapping;
        this.pos = pos;
        this.neg = neg;
        this.altPos = altPos;
        this.altNeg = altNeg;

        value = settlePoint;
    }

    /**
     * Shortened constructor for the input axis
     * 
     * settle point is 0
     * snapping is disabled
     * the alternate inputs are the same as the positive inputs
     * 
     * @param min the max input value
     * @param max the min input value
     * @param decRate how fast to increase the value
     * @param incRate how fast to decay    the value
     * @param pos the positive input
     * @param neg the negative input
     */
    public InputAxis(float min, float  max, 
                     float decRate, float incRate, 
                     KeyCode pos, KeyCode neg){
        this(
            min, max,
            decRate, incRate,
            false, 0,
            pos,
            pos,
            neg,
            neg
        );
    }

    /**
     * Updates the value of the input axis
     * 
     * @param dt time that has elapsed between frames
     */
    public void Update(double dt){
        /**
         * the direction of the input
         * 
         *  1 if pos or alt pos is pressed
         * -1 if neg or alt neg is pressed
         * 
         *  values cancel out if pos and neg are pressed
         */
        int inputDir = (KeyListener.getKeyPressed(pos.getValue()) || KeyListener.getKeyPressed(altPos.getValue()) ?  1 : 0) +
                       (KeyListener.getKeyPressed(neg.getValue()) || KeyListener.getKeyPressed(altNeg.getValue()) ? -1 : 0);
        
        /**
         *  If snapping is enabled set the value to max, settlePoint or min
         */
        if(enableSnapping){
            switch(inputDir){
                case 1:
                    value = max;
                    return;
                case 0:
                    value = settlePoint;
                    return; 
                case -1:
                    value = min;
                    return; 
            }
            return; 
        }

        /**
         * Otherwise increase/decay the value
         * 
         * If no input is given decay the value
         */
        if(inputDir == 0){
            /**
             * if the value is close to 0 make it 0
             *      (prevents drift)
             */
            if(Math.abs(value) < 0.0001){
                value = 0;
                return;
            }

            /**
             * decay the value by the decRate until the settle point
             */
            float decay = (float)dt * decRate * value/Math.abs(value);
            if(Math.abs(decay) > Math.abs(Math.abs(value) - Math.abs(settlePoint))){
                value = settlePoint;
            }
            else{
                value -= decay;
            }
        }
        /**
         * Increase the value by the rate in the inputted direction
         */
        else{
            value += incRate * (float)dt * inputDir;
            value = Math.max(min, Math.min(max, value));
        }
    }
}
