package nebula;

import org.joml.Vector2f;


import engine.Inputs.Input;
import engine.components.Component;
import engine.components.Transform;
import engine.physics.Move;
import engine.scenes.SceneManager;

public class Weapon extends Component{
    public Transform transform;
    public Move move;
    
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

        transform.position.x = (float) ((Math.cos(Player.transform.rotation) * 150) + Player.transform.position.x);
        transform.position.y = (float) ((Math.sin(Player.transform.rotation) * 150) + Player.transform.position.y);
    };

    @Override
    public void End() {

    };
}
