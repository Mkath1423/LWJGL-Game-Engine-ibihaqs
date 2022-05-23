package pocketplanets;

import javax.print.attribute.SupportedValuesAttribute;

import engine.Inputs.Input;
import engine.Inputs.Input.KeyCode;
import engine.components.Component;
import engine.components.Transform;
import engine.scenes.SceneManager;

public class SampleSwinging extends Component{
    
    // declare the other components that you are using
    Transform t;
    // Move m;

    float sumTime = 0;

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
        if(Input.getMouseButtonPressed(KeyCode.MOUSE_BUTTON_0)){
            System.out.println("-----------------");
            System.out.println(Input.getMousePosition());
            System.out.println(SceneManager.getActiveMainCamera().screenToWorldCoordinate(Input.getMousePosition()));

        }

        if(t == null) return;
        sumTime += deltaTime;
        t.rotation = (float)Math.toDegrees(Math.cos(sumTime));
    }
    
}
