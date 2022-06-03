package pong;

import java.util.Random;

import engine.components.Component;
import engine.components.Transform;

public class Ball extends Component{
    public Transform transform;
    private Transform paddleOneTransform;
    private Transform paddleTwoTransform;
    private Random randomNum;
    private int xSpeed;
    private int ySpeed;
    
    public Ball(Transform paddleOne, Transform paddleTwo){
        this.paddleOneTransform = paddleOne;
        this.paddleTwoTransform = paddleTwo;
    }

    @Override
    public void Awake() {
        transform = gameObject.getComponent(Transform.class);
        randomNum = new Random();
    };

    @Override
    public void Start() {
        // Generating starting direction for x velocity
        if(randomNum.nextInt(2) == 0){
            xSpeed = 1;
        }
        else{
            xSpeed = -1;
        }

        // Generating starting direction for y velocity
        if(randomNum.nextInt(2) == 0){
            ySpeed = 1;
        }
        else{
            ySpeed = -1;
        }

    };

    @Override
    public void Update(double deltaTime) {
        transform.position.x += xSpeed*1.5;
        transform.position.y += ySpeed*1.5;

        if(transform.position.x <= 0){
            System.out.println("Player 2 scored!");
            resetBall();
        }
        else if(transform.position.x >= 1920){
            System.out.println("Player 1 scored!");
            resetBall();
        }

        if(transform.position.y <= 0){
            ySpeed = -ySpeed;
        }
        else if(transform.position.y >= 1080){
            ySpeed = -ySpeed;
        }

        // Collision detection for paddles
        if((transform.position.x <= paddleOneTransform.position.x+15 && transform.position.x >= paddleOneTransform.position.x-15 && 
           transform.position.y <= paddleOneTransform.position.y + 120 && transform.position.y >= paddleOneTransform.position.y - 120) 
        || transform.position.x <= paddleTwoTransform.position.x+15 && transform.position.x >= paddleTwoTransform.position.x-15 && 
           transform.position.y <= paddleTwoTransform.position.y + 120 && transform.position.y >= paddleTwoTransform.position.y - 120){
            ySpeed = -ySpeed;
            xSpeed = -xSpeed;
        }

    };

    @Override
    public void End() {

    };

    private void resetBall(){
        transform.position.x = 920;
        transform.position.y = 540;
        xSpeed = -xSpeed;
        ySpeed = -ySpeed;
    }
}
