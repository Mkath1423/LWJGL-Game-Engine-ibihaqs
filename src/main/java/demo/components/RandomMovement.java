package demo.components;

import java.util.Random;

import engine.components.Component;
import engine.components.Transform;

public class RandomMovement extends Component {
    private Transform transform;
    private Random randNum;

    // Get the transform of the object being moved randomly
    public void Awake() {
        transform = gameObject.getComponent(Transform.class);
        randNum = new Random();

    }

    // re initialize every time the scene restarts
    // say if you move to a menu scene and then back to this
    public void Start() {
        if (transform != null) {

        }

        // m.initialize(new Vector3f(0, -0.2f, 0), new Vector3f(0, 10, 0));
    }

    // handle inputs and update things in update
    public void Update(double deltaTime) {
        if (transform == null)
            return;
        transform.position.x += Math.random() * (1 + 1) - 1;
        transform.position.y += Math.random() * (1 + 1) - 1;

    }
}
