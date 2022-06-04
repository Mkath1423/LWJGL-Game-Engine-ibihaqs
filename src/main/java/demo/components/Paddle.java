package demo.components;

import engine.Inputs.Input;
import engine.components.Component;
import engine.components.Transform;

public class Paddle extends Component {

    // Storing transform
    public Transform transform;

    // Defining which paddle the player will control
    private String playerAxis;

    public Paddle(String playerControlledAxis) {
        playerAxis = playerControlledAxis;
    }

    /**
     * Get transform component
     */
    @Override
    public void Awake() {
        transform = gameObject.getComponent(Transform.class);
    };

    /**
     * Shifts paddle via keyboard inputs up or down and passes it through top/bottom
     * of screen once crossing edge
     */
    @Override
    public void Update(double deltaTime) {
        if (transform == null) return;

        // Depending on player axis definition will respond to either arrow keys or WASD for input in y axis
        transform.position.y += deltaTime * Input.getAxis(playerAxis) * 200;
       
        if (transform.position.y < 0) {
            transform.position.y = 1080;
        } else if (transform.position.y > 1080) {
            transform.position.y = 0;
        }

    };

}
