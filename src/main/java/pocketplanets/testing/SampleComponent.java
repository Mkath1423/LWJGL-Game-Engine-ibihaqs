package pocketplanets.testing;

import org.joml.Vector3f;

import engine.Inputs.Input;
import engine.Inputs.Input.KeyCode;
import engine.components.Component;
import engine.components.Transform;

public class SampleComponent extends Component{
    
    // declare the other components that you are using
    Transform t;
    // Move m;

    // get the other components you are affecting
    public void Awake(){
        t = gameObject.getComponent(Transform.class);
        // m = gameObject.getComponent(Move.class);
    }

    // re initialize every time the scene restarts
    //   say if you move to a menu scene and then back to this
    public void Start(){
        if(t != null){
            t.rotation = 0;
        }

        // m.initialize(new Vector3f(0, -0.2f, 0), new Vector3f(0, 10, 0));
    }

    // handle inputs and update things in update
    public void Update(double deltaTime){
        if(t == null) return;
        t.rotation += deltaTime * Input.getAxis("rotation") * 30;
        t.position.x += deltaTime * Input.getAxis("horizontal") * 50;
        t.position.y += deltaTime * Input.getAxis("vertical") * 50;
    }
}
