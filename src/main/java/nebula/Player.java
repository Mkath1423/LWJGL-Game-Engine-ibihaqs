package nebula;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Inputs.Input;
import engine.Inputs.Input.KeyCode;
import engine.components.Component;
import engine.components.Transform;
import engine.scenes.SceneManager;

public class Player extends Component{

    public Transform transform;
    private Vector3f inputForce;
    private boolean isGrappled;
    private float grappleLength;
    private Vector3f grappleHookForce;
    private Vector2f grapplePosition;
    private Vector2f grappleVector;

    @Override
    public void Awake() {
        inputForce = new Vector3f();
        grappleHookForce = new Vector3f();
        grapplePosition = new Vector2f();
        grappleVector = new Vector2f();
        transform = gameObject.getComponent(Transform.class);
    };

    @Override
    public void Start() {

    };

    @Override
    public void Update(double deltaTime) {
        if(transform == null) return;
        
        Vector2f mouseWorldCoordinates = SceneManager.getActiveMainCamera().screenToWorldCoordinate(Input.getMousePosition());

        inputForce.set(Input.getAxis("horizontal"), Input.getAxis("vertical"), 0);
        inputForce.normalize();
        // Gets vector angle between mouse and player and sets current sprite rotation to match
        transform.rotation = (float)(Math.atan2(mouseWorldCoordinates.y - transform.position.y, mouseWorldCoordinates.x - transform.position.x));
        if(Input.getMouseButtonPressed(KeyCode.MOUSE_BUTTON_1)){
            isGrappled = true;
            grapplePosition = mouseWorldCoordinates;
            System.out.println("Pressed right click");

        }
        if(isGrappled){
            grappleVector = grapplePosition.sub(new Vector2f(transform.position.x, transform.position.y));
            
            System.out.println(grappleVector.length());
            if(Input.getMouseButtonReleased(KeyCode.MOUSE_BUTTON_1)){
                isGrappled = false;
            }
        }
        else{
            transform.position.x += deltaTime * Input.getAxis("horizontal") * 40;
            transform.position.y += deltaTime * Input.getAxis("vertical") * 40;
        }
        

          
    };

    @Override
    public void End() {

    };
}
