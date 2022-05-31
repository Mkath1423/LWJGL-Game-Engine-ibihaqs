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

    public static Transform transform;
    public Move move;

    private Vector3f inputForce;
    private boolean isGrappled;
    private Vector3f grappleForce;
    private Vector2f grapplePosition;
    private Vector2f grappleVector;

    public int playerHealth = 3;

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



        // Gets vector angle between mouse and player and sets current sprite rotation to match
        transform.rotation = (float)Math.atan2(mouseWorldCoordinates.y - transform.position.y, mouseWorldCoordinates.x - transform.position.x);

        // If the right mouse button is pressed
        if(Input.getMouseButtonPressed(KeyCode.MOUSE_BUTTON_1)){
            isGrappled = true;
            grapplePosition = mouseWorldCoordinates;
            
        }
        if(isGrappled){
            // Get vector between Player and Mouse
            grappleVector = new Vector2f(grapplePosition.x - transform.position.x, grapplePosition.y - transform.position.y);

            // Find deltaX for use in hooke's law equation
            Float deltaX = Math.max(grappleVector.length() - 300, 0);



            // if(deltaX <= 30){
            //     grappleForce.x = grappleVector.y;
            //     grappleForce.y = -grappleVector.x;
            //     grappleForce.mul((float)deltaTime);
                     
            // }
            // else{
                // Set the force vector components
                grappleForce.set(new Vector3f(grappleVector.normalize().mul(deltaX * 0.8f ), 0));
                grappleForce.mul((float)deltaTime);
            // }
                
            transform.position.x += grappleForce.x;
            transform.position.y += grappleForce.y;
            
            transform.position.x += deltaTime * Input.getAxis("horizontal") * 150;
            transform.position.y += deltaTime * Input.getAxis("vertical") * 150;

            // move.addMass(10000);
            // move.addForce(grappleForce);

            // If right click is released 
            if(Input.getMouseButtonReleased(KeyCode.MOUSE_BUTTON_1)){
                isGrappled = false;
            }
        }
        else{
            transform.position.x += deltaTime * Input.getAxis("horizontal") * 80;
            transform.position.y += deltaTime * Input.getAxis("vertical") * 80;
        }
        

          
    };

    @Override
    public void End() {

    };
    
}
