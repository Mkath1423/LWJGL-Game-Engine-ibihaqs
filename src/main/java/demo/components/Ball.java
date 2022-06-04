package demo.components;

import java.util.Random;

import org.joml.Vector3f;

import engine.components.Component;
import engine.components.Transform;

public class Ball extends Component {

    // Storing transforms for this sprite and both paddles
    public Transform transform;
    private Transform paddleOneTransform;
    private Transform paddleTwoTransform;

    // Used for Random() method
    private Random randomNum;

    // Control magnitude + direction of ball in motion
    private int xDirection;
    private int yDirection;
    private float ySpeed;

    // Vector between ball and both paddles
    private Vector3f ballToPaddleOne;
    private Vector3f ballToPaddleTwo;

    public Ball(Transform paddleOne, Transform paddleTwo) {
        // Passing transforms in through constructor
        this.paddleOneTransform = paddleOne;
        this.paddleTwoTransform = paddleTwo;
    }

    @Override
    /**
     * Grabs transform object for ball and constructs Random class for number
     * generation
     */
    public void Awake() {
        transform = gameObject.getComponent(Transform.class);
        randomNum = new Random();
        ballToPaddleOne = new Vector3f(0, 0, 0);
        ballToPaddleTwo = new Vector3f(0, 0, 0);
    };

    @Override
    /**
     * Generate random directions for velocity + reset's the balls position
     */
    public void Start() {
        // Generating starting direction for x velocity
        if (randomNum.nextInt(2) == 0) {
            xDirection = 1;
        } else {
            xDirection = -1;
        }

        // Generating starting direction for y velocity
        if (randomNum.nextInt(2) == 0) {
            yDirection = 1;
        } else {
            yDirection = -1;
        }
        resetBall();

    };

    @Override
    /**
     * Handles movement logic as well as detection for edges + paddles
     */
    public void Update(double deltaTime) {

        if (transform.position.x <= 0) {
            System.out.println("Player 2 scored!");
            resetBall();
        } else if (transform.position.x >= 1920) {
            System.out.println("Player 1 scored!");
            resetBall();
        }

        if (transform.position.y <= 0) {
            yDirection = -yDirection;
        } else if (transform.position.y >= 1080) {
            yDirection = -yDirection;
        }

        ballToPaddleOne.set(paddleOneTransform.position.x - transform.position.x,
                paddleOneTransform.position.y - transform.position.y, 0);
        ballToPaddleTwo.set(paddleTwoTransform.position.x - transform.position.x,
                paddleTwoTransform.position.y - transform.position.y, 0);

        // Collision detection for paddles
        if ((transform.position.x <= paddleOneTransform.position.x + 15
                && transform.position.x >= paddleOneTransform.position.x - 15 &&
                transform.position.y <= paddleOneTransform.position.y + 120
                && transform.position.y >= paddleOneTransform.position.y - 120)) {
            xDirection = -xDirection;
            ySpeed = ballToPaddleOne.length();

        }
        if ((transform.position.x <= paddleTwoTransform.position.x + 15
                && transform.position.x >= paddleTwoTransform.position.x - 15 &&
                transform.position.y <= paddleTwoTransform.position.y + 120
                && transform.position.y >= paddleTwoTransform.position.y - 120)) {
            xDirection = -xDirection;
            ySpeed = ballToPaddleTwo.length();
        }

        // Move x position of ball at a speed of 250 with reference to current time and
        // direction
        transform.position.x += xDirection * 250 * deltaTime;

        // Move y position of ball at a magnitude relative to height when it made
        // contact with paddle
        transform.position.y += yDirection * ySpeed * 5 * deltaTime;

    };

    /**
     * Sets the ball's position back to the centre of the stage as well as flips
     * it's previous velocities
     */
    private void resetBall() {
        transform.position.x = 920;
        transform.position.y = 540;
        xDirection = -xDirection;
        yDirection = -yDirection;
        ySpeed = 1;
    }
}
