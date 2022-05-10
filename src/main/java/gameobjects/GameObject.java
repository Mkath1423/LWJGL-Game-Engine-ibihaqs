package gameobjects;


import org.joml.Vector3f;

import components.Transform;

public abstract class GameObject {
    protected Transform transform;
    // renderer (?)

    private boolean isEnabled = true;
    public boolean  getEnabled(               ) { return isEnabled;    }
    public void     setEnabled(boolean enabled) { isEnabled = enabled; }
    
    /**
     * Constructor for GameObject class
     * 
     */
    public GameObject(){
        transform = new Transform();
    }

    /**
     * Called when GameObject is added to a scene
     * 
     */
    public void Awake(){
        
    }

    /**
     * Called when first frame of scene is displayed
     * 
     */
    public void Start(){

    }

    /**
     * Called every frame that the scene is displayed and this game object is enabled
     * 
     * @param deltaTime     Time elapsed between frames
     */
    public void Update(double deltaTime){

    }

    /**
     * Called when the scene stops being displayed
     * 
     */
    public void End(){

    }
}
