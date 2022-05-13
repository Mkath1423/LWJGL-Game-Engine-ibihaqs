package physics;

import org.joml.Vector3f;

import engine.components.Component;
import engine.components.Transform;

public class Move extends Component {
 
    

    // INITIALIZATION

    Transform n;



    public static float drag = 1;       // What magnitude of decceleration is applied?
    
    private static float dX;            // X displacement
    private static float dY;            // Y displacement
    private static float max = 100;     // The highest allowed magnitude of velocity

    private static Vector3f change;     // The new position to be returned



    // EXTERNAL METHODS



    public void Awake() {

        n = gameObject.getComponent(Transform.class);

    }


    /**
     * Constantly updates the position of a given gameObject
     */
    public void Update(double time) {

        n.position = change;

    }



    // INTERNAL METHODS



    /**
     * Check to see if the given displacement is out of bounds
     * @param n
     * @return
     */
    private static float boundCheck(float n) {

        // The value is out of bounds
        if(Math.abs(n) > max) {

            // Too positive
            if(n > 0) return max;

            // Too negative
            if(n < 0) return (max * -1); 
        }

        // The value is not out of bounds
        return n;
    }
    
    
    /**
     * Updates dX and dY (X & Y displacement)
     * 
     * @param velocity  (Vector3f) The velocity to apply displacement accordingly to
     */
    private static void displacement(Vector3f velocity) {

        // There is an x velocity
        if(velocity.x != 0) {

            dX = velocity.x;
            dX = boundCheck(dX);
        }

        // There is a y velocity
        if(velocity.y != 0) {

            dY = velocity.y;
            dY = boundCheck(dY);
        }
    }
    
    
    /**
     * Move the gameObject at a constant velocity
     * 
     * @param velocity  (Vector3f) The x and y components of the velocity
     */
    public static void glide(Vector3f velocity) {

        // Update dX and dY according to the given velocity
        displacement(velocity);

        // Change the coordinates according to displacement
        change.x += dX;
        change.y += dY;
    }
    


}