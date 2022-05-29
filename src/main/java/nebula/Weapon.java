package nebula;

import org.joml.Vector2f;


import engine.Inputs.Input;
import engine.Inputs.Input.KeyCode;
import engine.components.Component;
import engine.components.Transform;
import engine.physics.Move;
import engine.scenes.SceneManager;

public class Weapon extends Component{
    public Transform transform;
    public Move move;
    private boolean isAttacking = false;
    private float weaponDisplacement;
    private boolean extendingAnimation = true;
    private float weaponRange = 100;

    @Override
    public void Awake() {
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

        // Gets vector angle between mouse and sword and sets current sprite rotation to match
        transform.rotation = Player.transform.rotation;



        if(Input.getMouseButtonPressed(KeyCode.MOUSE_BUTTON_0)){
            isAttacking = true;
        }

        if(isAttacking){
            transform.position.x = (float) ((Math.cos(Player.transform.rotation) * (150+weaponDisplacement) + Player.transform.position.x));
            transform.position.y = (float) ((Math.sin(Player.transform.rotation) * (150+weaponDisplacement) + Player.transform.position.y));
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
            transform.position.x = (float) ((Math.cos(Player.transform.rotation) * 150) + Player.transform.position.x);
            transform.position.y = (float) ((Math.sin(Player.transform.rotation) * 150) + Player.transform.position.y);
        }
    
    };

    @Override
    public void End() {

    };
}
