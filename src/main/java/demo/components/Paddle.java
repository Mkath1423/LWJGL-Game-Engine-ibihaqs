package demo.components;

import engine.Inputs.Input;
import engine.components.Component;
import engine.components.Transform;

public class Paddle extends Component{

    public Transform transform;
    private int playerNumber;

    public Paddle(int playerSlot){
        playerNumber = playerSlot;
    }

    @Override
    public void Awake() {
        transform = gameObject.getComponent(Transform.class);
    };

    @Override
    public void Start() {

    };

    @Override
    public void Update(double deltaTime) {
        if(transform == null) return;
        moveSpriteKeyboard(deltaTime);
        if(transform.position.y < 0){
            transform.position.y = 1080;
        }
        else if(transform.position.y > 1080){
            transform.position.y = 0;
        }

    };

    @Override
    public void End() {

    };

    private void moveSpriteKeyboard(double deltaTime){
        if(playerNumber == 1){
            transform.position.y += deltaTime * Input.getAxis("playerOneVertical") * 200;
        }
        else{
            transform.position.y += deltaTime * Input.getAxis("playerTwoVertical") * 200;
        }
    }
}
