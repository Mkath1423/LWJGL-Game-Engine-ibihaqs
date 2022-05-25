package nebula;

import org.joml.Vector2f;

import engine.Inputs.Input;
import engine.components.Component;
import engine.components.Transform;
import engine.scenes.SceneManager;

public class Player extends Component{

    public Transform transform;

    @Override
    public void Awake() {
        transform = gameObject.getComponent(Transform.class);
    };

    @Override
    public void Start() {

    };

    @Override
    public void Update(double deltaTime) {
        if(transform == null) return;
        
        Vector2f mouseWorldCoordinates = SceneManager.getActiveMainCamera().screenToWorldCoordinate(Input.getMousePosition());

        // Gets vector angle between mouse and player and sets current sprite rotation to match
        transform.rotation = (float)(Math.atan2(mouseWorldCoordinates.y - transform.position.y, mouseWorldCoordinates.x - transform.position.x));
        System.out.println(transform.rotation);
        transform.position.x += deltaTime * Input.getAxis("horizontal") * 40;
        transform.position.y += deltaTime * Input.getAxis("vertical") * 40;
    };

    @Override
    public void End() {

    };
}
