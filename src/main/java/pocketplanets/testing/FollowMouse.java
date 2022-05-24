package pocketplanets.testing;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Inputs.Input;
import engine.Inputs.Input.KeyCode;
import engine.components.Component;
import engine.components.Transform;
import engine.scenes.Scene;
import engine.scenes.SceneManager;

public class FollowMouse extends Component{
    
    // declare the other components that you are using
    Transform t;
    // Move m;

    // get the other components you are affecting
    public void Awake(){
        t = gameObject.getComponent(Transform.class);
        // m = gameObject.getComponent(Move.class);
    }

    // handle inputs and update things in update
    public void Update(double deltaTime){
        if(t == null) return;
    
        Vector2f mousePos = Input.getMousePosition();
        Vector2f newPosition = SceneManager.getActiveMainCamera().screenToWorldCoordinate(mousePos);
        t.position.x = newPosition.x;
        t.position.y = newPosition.y;
    }
}
