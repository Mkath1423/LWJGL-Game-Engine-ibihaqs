package grappledemo;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Inputs.Input;
import engine.Inputs.Input.KeyCode;
import engine.components.Component;
import engine.components.LineRenderer;
import engine.components.Transform;
import engine.gameobjects.GameObject;
import engine.scenes.SceneManager;

public class Player extends Component{

    private GameObject grapplingLine;
    public Transform transform;
    public LineRenderer lineRenderer;

    private boolean isGrappled;
    private Vector3f grappleForce;
    private Vector2f grapplePosition;
    private Vector2f grappleVector;


    public int playerHealth = 3;


    public Player(GameObject line){
        grapplingLine = line;
    }

    @Override
    public void Awake() {
        grappleForce = new Vector3f();
        grapplePosition = new Vector2f();
        grappleVector = new Vector2f();

        transform = gameObject.getComponent(Transform.class);
        lineRenderer = grapplingLine.getComponent(LineRenderer.class);


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
            lineRenderer.setEndPosition(new Vector3f(grapplePosition, 0));
            lineRenderer.setLineWidth(10);
            
        }
        if(isGrappled){
            lineRenderer.setStartPosition(transform.getPosition());
            // Get vector between Player and Mouse
            grappleVector = new Vector2f(grapplePosition.x - transform.position.x, grapplePosition.y - transform.position.y);
            // Find deltaX for use in hooke's law equation
            Float deltaX = Math.max(grappleVector.length()-100, 0);
            
            // Set the force vector components
            grappleForce.set(new Vector3f(grappleVector.normalize().mul(deltaX * 0.8f ), 0));
            grappleForce.mul((float)deltaTime);
            
                
            transform.position.x += grappleForce.x;
            transform.position.y += grappleForce.y;
            
            moveFromWASD(150, deltaTime);

            // If right click is released 
            if(Input.getMouseButtonReleased(KeyCode.MOUSE_BUTTON_1)){
                isGrappled = false;
                lineRenderer.setLineWidth(0);
    
            }
        }
        else{
            moveFromWASD(80, deltaTime);
        }
          
    };

    @Override
    public void End() {

    };

    private void moveFromWASD(int speed, double deltaTime){
        transform.position.x += deltaTime * Input.getAxis("horizontal") * speed;
        transform.position.y += deltaTime * Input.getAxis("vertical") * speed;
    }
    
}
