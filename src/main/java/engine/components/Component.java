package engine.components;

import engine.gameobjects.GameObject;

public abstract class Component {
    
    /**
     * the gameobject that stores this component
     */
    public GameObject gameObject;

    /**
     * Callback triggers when the scene is added to scene manager
     * 
     * Use for 1 time initalization
     */
    public void Awake() {}

    /**
     * Callback triggers each time the scene starts
     * 
     * Use for resetting a scene
     */
    public void Start() {}

    /**
     * Callback trigger once per frame for the active scene
     * 
     * @param deltaTime the time between the last frame and this one
     */
    public void Update(double deltaTime) {}

    /** 
     * Callback trigger once per frame for the active scene
     * 
     * Called at the start of the update cycle (before Update)
     * 
     * @param deltaTime the time between the last frame and this one
     */
    public void EarlyUpdate(double deltaTime) {}
    
    /** 
     * Callback trigger once per frame for the active scene
     * 
     * Called at the end of the update cycle (after Update)
     * 
     * Use for physics / rendering, stuff that needs to happen after the game logic is applied
     * 
     * @param deltaTime the time between the last frame and this one
     */
    public void LateUpdate(double deltaTime) {}

    /**
     * Callback for when the scene ends
     * 
     * Use for finaliztion
     */
    public void End() {}


}
