package engine.physics;

import org.joml.Vector3f;

import engine.components.Component;
import engine.components.Transform;

public class Move extends Component {
 
    

    // INITIALIZATION



    Transform n;                            // The transform component of the gameObject we work with
    
    private float dX;                       // X displacement
    private float dY;                       // Y displacement

    private static float MAX = 100;         // The highest allowed magnitude of velocity

    private Vector3f change;                // The new position to be returned
    private Vector3f acceleration;          // The acceleration as a Vector3f
    private Vector3f distance;              // The distance between the two points for parabolic acceleration
    private Vector3f velocity;              // The velocity as a Vector3f
    

    /**
     * Sets the velocity and acceleration. Call to set the object on its course
     * 
     * @param acc   (Vector3f) Acceleration with an x and y component
     * @param vel   (Vector3f) Velocity with an x and y component
     * @param dist  (Vector3f) The distance between the two points. If non-zero, motion is parabolic
     */
    public void initialize(Vector3f acc, Vector3f vel, Vector3f dist) {

        change = new Vector3f(0,0,0);

        acceleration = acc;
        velocity = vel; 
        distance = dist;
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

        // Only run this if there is an active velocity
        // NOTE: WHEN CHANGING VELOCITY, NEVER HAVE IT BE EQUAL TO 0
        if(velocity != new Vector3f(0,0,0)) {

            locomotion();
            n.position = change;
        }
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
     * 
     * @param n (float) The value to be bound-checked
     * 
     * @return  (float) The bound-checked value
     */
    private float boundCheck(float n) {

        // The value is out of bounds
        if(Math.abs(n) > MAX) {

            // Too positive
            if(n > 0) return MAX;

            // Too negative
            if(n < 0) return (MAX * -1); 
        }

        // The value is not out of bounds
        return n;
    }
    
    
    /**
     * Updates dX and dY (X & Y displacement)
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
     * NOTE: WHEN CHANGING VELOCITY, NEVER HAVE IT BE EQUAL TO 0
     */
    public void locomotion() {

        // If a distance was specified, perform parabolic motion instead
        if(distance != new Vector3f(0,0,0)) {
            
            parabolicMotion(3/4);
            return;
        } 

        // Change velocity according to acceleration and set displacement
        accelerate();
        displacement();

        // Change the coordinates according to displacement
        change.x += dX;
        change.y += dY;
    }


    /**
     * Move in such a way that you speed up near the vertex, and slow down afterwards
     * 
     * @param vertex    (float) On a scale from 0-1, where should acceleration peak?
     */
    public void parabolicMotion(float vertex) {

        
    }
    


}