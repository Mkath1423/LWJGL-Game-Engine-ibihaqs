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

    /**
     * Randomly shifts positions of sprite
     */
    public void Update(double deltaTime) {
        if (transform == null) return;
        transform.position.x += Math.random() * (1 + 1) - 1;
        transform.position.y += Math.random() * (1 + 1) - 1;

    }
}
