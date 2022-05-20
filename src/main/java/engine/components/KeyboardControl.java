package engine.components;

import engine.Inputs.Input;

public class KeyboardControl extends Component{

    private Transform transform;

    private float speed = 20;

    @Override
    public void Awake(){
        // Getting the camera object's transform 
        transform = gameObject.getComponent(Transform.class); 
    }

    @Override
    public void Update(double deltaTime){
        // Update the position of the camera object based on the speed * the current input axis values 
        transform.position.y += speed*Input.getAxis("VERTICAL")*deltaTime;
        transform.position.x += speed*Input.getAxis("HORIZONTAL")*deltaTime;
    }
    
}
