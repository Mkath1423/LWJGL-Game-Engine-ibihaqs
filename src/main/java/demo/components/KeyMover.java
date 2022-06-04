package demo.components;

import engine.Inputs.Input;
import engine.components.Component;
import engine.components.Transform;

public class KeyMover extends Component {

    private float moveSpeed;
    private float turnSpeed;

    // declare the other components that you are using
    Transform t;

    public KeyMover(float moveSpeed, float turnSpeed) {
        this.moveSpeed = moveSpeed;
        this.turnSpeed = turnSpeed;
    }

    // get the other components you are affecting
    public void Awake() {
        t = gameObject.getComponent(Transform.class);
        // m = gameObject.getComponent(Move.class);
    }

    // re initialize every time the scene restarts
    // say if you move to a menu scene and then back to this
    public void Start() {
        if (t != null) {
            t.rotation = 0;
        }

        // m.initialize(new Vector3f(0, -0.2f, 0), new Vector3f(0, 10, 0));
    }

    // handle inputs and update things in update
    public void Update(double deltaTime) {
        if (t == null)
            return;
        t.rotation += deltaTime * Input.getAxis("rotation") * turnSpeed;
        t.position.x += deltaTime * Input.getAxis("horizontal") * moveSpeed;
        t.position.y += deltaTime * Input.getAxis("vertical") * moveSpeed;
    }
}
