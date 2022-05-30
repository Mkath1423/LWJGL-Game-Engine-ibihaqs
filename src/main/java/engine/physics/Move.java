package engine.physics;

import org.joml.Vector3f;

import engine.components.Component;
import engine.components.Transform;

public class Move extends Component {
 
    

    // INITIALIZATION



    Transform n;                            // The transform component of the gameObject we work with
    
    private static float FRICTION = 1/2;   // The coefficient of friction
    
    public float mass = 0;                  // The mass, in kg, of the object

    private Vector3f change =               // The new position to be returned
        new Vector3f(0, 0, 0);                
    private Vector3f acceleration =         // The acceleration as a Vector3f
        new Vector3f(0, 0, 0);          
    private Vector3f force =                // The force applied to the object
        new Vector3f(0, 0, 0);                
    private Vector3f velocity =             // The velocity as a Vector3f
        new Vector3f(0, 0, 0);              
    private Vector3f zero =                 // A Vector3f quantity of 0
        new Vector3f(0, 0, 0);



    // EXTERNAL METHODS



    /**
     * Sets the mass of the current object
     * 
     * @param m     (float) The mass of the component, in kg
     */
    public void addMass(float m) {

        mass = Math.abs(m);
    }


    /**
     * Applies a force to the object as a vector, in Newtons
     * 
     * @param f     (Vector3f) The force to be applied as a vector
     */
    public void addForce(Vector3f f) {

        force = f;
    }
    
    
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

        // Only run this if the object has mass
        if(mass > 0) {

            locomotion();
            n.position.add(change);
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
     * Prevents the velocity from switching direction naturally
     * 
     * @return  (Vector3f) The value to set velocity to
     */
    private Vector3f boundCheck() {

        return zero;
    }


    /**
     * Given the force and mass, calculate acceleration and velocity
     */
    private void calculate() {

        if(force != zero) {

            acceleration.add(acceleration.mul(FRICTION * -1));
            acceleration.add(force.div(mass));
            force = zero;

        } else {

            acceleration.add(acceleration.mul(FRICTION * -1));

        }
    }
    
    
    /**
     * Move the gameObject according to a velocity, factoring acceleraiton
     */
    public void locomotion() {

        // Extract acceleration and apply it to velocity
        calculate();
        accelerate();

        // Change the coordinates according to velocity
        change.add(velocity);
    }
    


}