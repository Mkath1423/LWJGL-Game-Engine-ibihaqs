package demo.components;

import java.util.Random;

import engine.components.Component;
import engine.components.Transform;

public class Ball extends Component{

    // Storing transforms for this sprite and both paddles 
    public Transform transform;                 
    private Transform paddleOneTransform;
    private Transform paddleTwoTransform;


    private Random randomNum;
    private int xDirection;
    private int yDirection;
    
    public Ball(Transform paddleOne, Transform paddleTwo){
        // Passing transforms in through constructor 
        this.paddleOneTransform = paddleOne;
        this.paddleTwoTransform = paddleTwo;
    }

    @Override
    /** 
     * Grabs transform object for ball and constructs Random class for number generation
     */
    public void Awake() {
        transform = gameObject.getComponent(Transform.class);
        randomNum = new Random();
    };

    @Override
    /**
     * Generate random directions for 
     */
    public void Start() {
        // Generating starting direction for x velocity
        if(randomNum.nextInt(2) == 0){
            xDirection = 1;
        }
        else{
            xDirection = -1;
        }

        // Generating starting direction for y velocity
        if(randomNum.nextInt(2) == 0){
            yDirection = 1;
        }
        else{
            yDirection = -1;
        }

        resetBall();

    };

    @Override
    public void Update(double deltaTime) {
        transform.position.x += xDirection*250*deltaTime;
        transform.position.y += yDirection*250*deltaTime;

        if(transform.position.x <= 0){
            System.out.println("Player 2 scored!");
            resetBall();
        }
        else if(transform.position.x >= 1920){
            System.out.println("Player 1 scored!");
            resetBall();
        }

        if(transform.position.y <= 0){
            yDirection = -yDirection;
        }
        else if(transform.position.y >= 1080){
            yDirection = -yDirection;
        }

        // Collision detection for paddles
        if((transform.position.x <= paddleOneTransform.position.x+15 && transform.position.x >= paddleOneTransform.position.x-15 && 
           transform.position.y <= paddleOneTransform.position.y + 120 && transform.position.y >= paddleOneTransform.position.y - 120) 
        || transform.position.x <= paddleTwoTransform.position.x+15 && transform.position.x >= paddleTwoTransform.position.x-15 && 
           transform.position.y <= paddleTwoTransform.position.y + 120 && transform.position.y >= paddleTwoTransform.position.y - 120){
            yDirection = -yDirection;
            xDirection = -xDirection;
        }

    };

    @Override
    public void End() {

    };

    private void resetBall(){
        transform.position.x = 920;
        transform.position.y = 540;
        xDirection = -xDirection;
        yDirection = -yDirection;
    }
}
