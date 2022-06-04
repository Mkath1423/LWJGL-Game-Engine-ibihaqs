package demo.components;

import org.joml.Vector3f;

import engine.Inputs.Input;
import engine.Inputs.Input.KeyCode;
import engine.components.Component;
import engine.components.Transform;
import engine.physics.Collision;
import engine.physics.Move;

public class Weapon extends Component {
    public Transform transform;
    public Transform playerTransform;
    public Collision collision;

    public Move move;
    private boolean isAttacking = false;
    private float weaponDisplacement;
    private boolean extendingAnimation = true;
    private float weaponRange = 100;

    public Weapon(Transform player) {
        playerTransform = player;
    }

    @Override
    public void Awake() {
        transform = gameObject.getComponent(Transform.class);
        move = gameObject.getComponent(Move.class);
        
    };

    @Override
    public void Update(double deltaTime) {
        if (transform == null)
            return;

        // Sets position to constantly update to players position
        transform.position = new Vector3f(playerTransform.position.x, playerTransform.position.y, 0);

        // Gets vector angle between mouse and sword and sets current sprite rotation to match
        transform.rotation = playerTransform.getRotation();

        if (Input.getMouseButtonPressed(KeyCode.MOUSE_BUTTON_0)) {
            isAttacking = true;
        }

        // When left click is held
        if (isAttacking) {
            transform.position.x = (float) ((Math.cos(transform.rotation) * (150 + weaponDisplacement)
                    + transform.position.x));
            transform.position.y = (float) ((Math.sin(transform.rotation) * (150 + weaponDisplacement)
                    + transform.position.y));

            // Animation cycle to handle thrusting in and out of sword

            // If weapon is currently extending and hasn't reached it's max range push it out
            if (weaponDisplacement <= weaponRange && extendingAnimation) {
                weaponDisplacement += 2;
            // If weapon is not extending and it's displacement is greater than 0 pull it back
            } else if (!extendingAnimation && weaponDisplacement >= 0) {
                weaponDisplacement -= 2;
            // If the displacement is greater than the range stop extending
            } else if (weaponDisplacement >= weaponRange) {
                extendingAnimation = false;
            // If the displacement is less than 0 begin extending
            } else if (weaponDisplacement <= 0) {
                extendingAnimation = true;
            }

            // If left click is released
            if (Input.getMouseButtonReleased(KeyCode.MOUSE_BUTTON_0)) {
                isAttacking = false;
                extendingAnimation = true;
                weaponDisplacement = 0;
            }
            // Standard code to move sword without animation
        } else {
            transform.position.x = (float) ((Math.cos(transform.rotation) * 150) + transform.position.x);
            transform.position.y = (float) ((Math.sin(transform.rotation) * 150) + transform.position.y);
        }

    };
}
