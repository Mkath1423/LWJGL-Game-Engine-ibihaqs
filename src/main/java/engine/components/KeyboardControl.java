package engine.components;

import engine.Inputs.Input;

public class KeyboardControl extends Component{

    private Transform transform;

    private float speed = 20;

    @Override
    public void Awake(){
        transform = gameObject.getComponent(Transform.class); 
    }

    @Override
    public void Update(double deltaTime){
        transform.position.y += speed*Input.getAxis("VERTICAL")*deltaTime;
        transform.position.x += speed*Input.getAxis("HORIZONTAL")*deltaTime;
    }
    
}
