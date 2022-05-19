package physics;

import org.joml.Vector3f;

import engine.components.Component;
import engine.components.Transform;

public class Move extends Component {
 
    

    // INITIALIZATION



    Transform n;                            // The transform component of the gameObject we work with
    
    private float dX;                       // X displacement
    private float dY;                       // Y displacement

    private static float max = 100;         // The highest allowed magnitude of velocity

    private Vector3f change;                // The new position to be returned
    private Vector3f acceleration;          // The acceleration as a Vector3f
    private Vector3f velocity;              // The velocity as a Vector3f
    

    /**
     * Sets the velocity and acceleration. Call to set the object on its course
     * 
     * @param acc   (Vector3f) Acceleration with an x and y component
     * @param vel   (Vector3f) Velocity with an x and y component
     */
    public void initialize(Vector3f acc, Vector3f vel) {
        change = new Vector3f();
        acceleration = acc;
        velocity = vel; 
    }



    // EXTERNAL METHODS



    /**
     * Called once, to get the transform component of the gameObject that calls Move
     */
    public void Awake() {

        n = gameObject.getComponent(Transform.class);
    }


    /**
     * Constantly updates the position of a given gameObject
     */
    public void Update(double time) {

        locomotion();
        n.position = change;

    }



    // INTERNAL METHODS



    /**
     * Changes the velocity according to acceleration
     */
    private void accelerate() {
        velocity.add(acceleration);
    }
    
    
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
    private void displacement() {

        // There is an x velocity
        if(velocity.x != 0) {

            dX = velocity.x;
            // dX = boundCheck(dX);
        }

        // There is a y velocity
        if(velocity.y != 0) {

            dY = velocity.y;
            // dY = boundCheck(dY);
        }
    }
    
    
    /**
     * Move the gameObject according to a velocity, factoring acceleraiton
     * 
     * @param velocity  (Vector3f) The x and y components of the velocity
     */
    public void locomotion() {

        // Change velocity according to acceleration and set displacement
        accelerate();
        displacement();

        // Change the coordinates according to displacement
        change.x += dX;
        change.y += dY;
    }
    


}