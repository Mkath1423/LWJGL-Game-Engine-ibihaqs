package gameobjects;


import org.joml.Vector3f;

import components.Transform;

public abstract class GameObject {
    Transform transform;
    // renderer (?)

    private boolean isEnabled = true;
    public boolean  getEnabled(               ) { return isEnabled;    }
    public void     setEnabled(boolean enabled) { isEnabled = enabled; }
    
    public GameObject(){
        transform = new Transform();
    }

    public void Awake(){
        
    }

    public void Start(){

    }

    public void Update(double deltaTime){

    }

    public void End(){

    }
}
