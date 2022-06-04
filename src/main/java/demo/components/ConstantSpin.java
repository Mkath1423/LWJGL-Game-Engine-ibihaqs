package demo.components;

import engine.components.Component;
import engine.components.Transform;

public class ConstantSpin extends Component {
    
    private Transform t;

    private float turnRate;

    public ConstantSpin(float turnRate){
        this.turnRate = turnRate;
    }

    @Override
    public void Awake(){
        t = gameObject.getComponent(Transform.class);
    }

    @Override
    public void Update(double deltaTime){
        t.rotation += turnRate * deltaTime;
    }
}
