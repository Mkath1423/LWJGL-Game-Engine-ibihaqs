package engine.gameobjects;

import java.util.ArrayList;
import java.util.List;

import engine.components.Component;

/**
 * An object that exists within the game
 * 
 * behavior of that object is defined by its components
 */
public class GameObject {

    /**
     * Enabled gameobjects will receive callbacks (TODO: this)
     */
    private boolean isEnabled = true;
    public boolean  getEnabled(               ) { return isEnabled;    }
    public void     setEnabled(boolean enabled) { isEnabled = enabled; }
    
    /**
     * Components that define the gameobject's functionality 
     */
    private List<Component> components;

    /**
     * Gets and returns a component 
     *
     * Sample:
     *    gameObject.getComponent(Transform.class)
     * 
     * @param componentClass the class of the desired component 
     * @return the component or null
     */
    public <T extends Component> T getComponent(Class<T> componentClass){
        for (Component component : components) {
            if(componentClass.isAssignableFrom(component.getClass())){
                try {
                    return componentClass.cast(component);
                } catch (ClassCastException e) {
                    assert false : String.format("Error: Component casting failed");
                }
            }
        }

        return null;
    }
    /**
     * Adds a component to the gameobject
     *  
     * will cause an assertion error if you try to add a component type that is already added
     * 
     * @param instance the component to add
     */
    public void addComponent(Component instance){
        /**
         * Return if component of same type is already added
         */
        for (Component component : components) {
            if(instance.getClass() == component.getClass()){
                assert false : String.format("GameObject: GO %s already contains component of type %s", "", instance.getClass().getName());
                return;
            }
        }
        /**
         * Give the component its gameobject
         */
        instance.gameObject = this;
        components.add(instance);
    }

    /**
     * Removes a component from this gameobject
     * 
     * @param componentClass the class of the component to remove
     */
    public <T extends Component> void removeComponent(Class<T> componentClass){
        for (Component component : components) {
            if(componentClass.isAssignableFrom(component.getClass())){
                
                component.End(); // calls the end callback
                components.remove(component);
            }
        }
    }

    /**
     * Constructor for GameObject class
     * 
     */
    public GameObject(){
        components = new ArrayList<>();;
    }

    /**
     * Called when GameObject is added to a scene
     * 
     */
    public void Awake(){
        for (Component component : components) {
            component.Awake();
        }
        
    }

    /**
     * Called when swapping to this scene
     * 
     */
    public void Start(){
        for (Component component : components) {
            component.Start();
        }
    }

    /**
     * Called every frame that the scene is displayed and this game object is enabled
     * 
     * @param deltaTime time elapsed between frames
     */
    public void Update(double deltaTime){
        for (Component component : components) {
            component.Update(deltaTime);
        }
    }

    /**
     * Called when swapping off this scene
     * 
     */
    public void End(){
        for (Component component : components) {
            component.End();
        }
    }
}
