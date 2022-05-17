package pocketplanets;

import engine.Inputs.Input;
import engine.components.Component;
import engine.components.Transform;

public class SampleComponent extends Component{
    
    // declare the other components that you are using
    Transform t;

    // get the other components you are affecting
    public void Awake(){
        t = gameObject.getComponent(Transform.class);
    }

    // re initialize every time the scene restarts
    //   say if you move to a menu scene and then back to this
    public void Start(){
        if(t != null){
            t.rotation = 0;
        }
    }

    // handle inputs and update things in update
    public void Update(double deltaTime){
        t.rotation -= 0.01 * Input.getAxis("rotation");
        t.position.x -= 0.01 * Input.getAxis("horizontal");
        t.position.y -= 0.01 * Input.getAxis("vertical");
    }
}
