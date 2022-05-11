package components;

import gameobjects.GameObject;

public abstract class Component {
    
    public GameObject gameObject;

    public void Awake() {};
    public void Start() {};
    public void Update(double deltaTime) {};
    public void End() {};


}
