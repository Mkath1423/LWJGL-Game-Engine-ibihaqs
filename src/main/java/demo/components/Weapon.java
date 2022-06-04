package demo.components;

import org.joml.Vector3f;

import engine.Inputs.Input;
import engine.Inputs.Input.KeyCode;
import engine.components.Component;
import engine.components.Transform;
import engine.physics.Collision;
import engine.physics.Move;

public class Weapon extends Component{
    public Transform transform;
    public Transform playerTransform;
    public Collision collision;
    public Collision planetCollision;
    public Move move;
    private boolean isAttacking = false;
    private float weaponDisplacement;
    private boolean extendingAnimation = true;
    private float weaponRange = 100;


    public Weapon(Transform player, Collision planet){
        playerTransform = player;
        planetCollision = planet;
    }

    @Override
    public void Awake() {
        transform = gameObject.getComponent(Transform.class);
        move = gameObject.getComponent(Move.class);
        collision = gameObject.getComponent(Collision.class);
    };

    @Override
    public void Start() {

    };

    @Override
    public void Update(double deltaTime) {
        if(transform == null) return;

        // Sets position to constantly update to players position
        transform.position = new Vector3f(playerTransform.position.x, playerTransform.position.y, 0);

        // Gets vector angle between mouse and sword and sets current sprite rotation to match
        transform.rotation = playerTransform.getRotation();


        if(collision.isColliding(planetCollision)){
            System.out.println("WOOOOOO");
        }
        System.out.println("Awww");
        
        if(Input.getMouseButtonPressed(KeyCode.MOUSE_BUTTON_0)){
            isAttacking = true;
        }

        if(isAttacking){
            transform.position.x = (float) ((Math.cos(transform.rotation) * (150+weaponDisplacement) + transform.position.x));
            transform.position.y = (float) ((Math.sin(transform.rotation) * (150+weaponDisplacement) + transform.position.y));
            if(weaponDisplacement <= weaponRange && extendingAnimation){
                weaponDisplacement += 2;
            }
            else if(!extendingAnimation && weaponDisplacement >= 0){
                weaponDisplacement -= 2;
            }
            else if(weaponDisplacement >= weaponRange){
                extendingAnimation = false;
            }
            else if(weaponDisplacement <= 0){
                extendingAnimation = true;
            }

            if(Input.getMouseButtonReleased(KeyCode.MOUSE_BUTTON_0)){
                isAttacking = false;
                extendingAnimation = true;
                weaponDisplacement = 0;
            }
        }
        else{
            transform.position.x = (float) ((Math.cos(transform.rotation) * 150) + transform.position.x);
            transform.position.y = (float) ((Math.sin(transform.rotation) * 150) + transform.position.y);
        }
    
    };

    @Override
    public void End() {

    };
}
