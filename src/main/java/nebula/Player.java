package nebula;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Inputs.Input;
import engine.Inputs.Input.KeyCode;
import engine.components.Component;
import engine.components.Transform;
import engine.physics.Move;
import engine.scenes.SceneManager;

public class Player extends Component{

    public Transform transform;
    public Move move;

    private Vector3f inputForce;
    private boolean isGrappled;
    private Vector3f grappleForce;
    private Vector2f grapplePosition;
    private Vector2f grappleVector;

    @Override
    public void Awake() {
        inputForce = new Vector3f();
        grappleForce = new Vector3f();
        grapplePosition = new Vector2f();
        grappleVector = new Vector2f();
        transform = gameObject.getComponent(Transform.class);
        move = gameObject.getComponent(Move.class);
    };

    @Override
    public void Start() {

    };

    @Override
    public void Update(double deltaTime) {
        if(transform == null) return;
        
        // Convert mouse coordinates to world coordinates
        Vector2f mouseWorldCoordinates = SceneManager.getActiveMainCamera().screenToWorldCoordinate(Input.getMousePosition());

        // Setting up input force to influence grapple movement 
        inputForce.set(Input.getAxis("horizontal"), Input.getAxis("vertical"), 0);
        inputForce.normalize();

        // Gets vector angle between mouse and player and sets current sprite rotation to match
        transform.rotation = (float)Math.atan2(mouseWorldCoordinates.y - transform.position.y, mouseWorldCoordinates.x - transform.position.x);

        // If the right mouse button is pressed
        if(Input.getMouseButtonPressed(KeyCode.MOUSE_BUTTON_1)){
            isGrappled = true;
            grapplePosition = mouseWorldCoordinates;
        }
        if(isGrappled){
            // Get vector between Player and Mouse
            grappleVector = new Vector2f(grapplePosition.y - transform.position.y, grapplePosition.x - transform.position.x);

            // Find deltaX for use in hooke's law equation
            Float deltaX = Math.max(grappleVector.length() - 10, 0);
            
            // Set the force vector components
            grappleForce.set(new Vector3f(grappleVector.normalize().add(grappleVector.x * deltaX * 5, grappleVector.y * deltaX * 5), 0));
            
            //move.addMass(10);
            //move.addForce(new Vector3f(10, 0, 0));
      
            // If right click is released 
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
