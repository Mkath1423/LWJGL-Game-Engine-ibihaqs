package engine.gameobjects;

import java.util.ArrayList;
import java.util.List;

import engine.components.Component;
import engine.components.Transform;

public class GameObject {


    private boolean isEnabled = true;
    public boolean  getEnabled(               ) { return isEnabled;    }
    public void     setEnabled(boolean enabled) { isEnabled = enabled; }
    
    // from tutorial
    private List<Component> components;
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
    
    public void addComponent(Component instance){
        for (Component component : components) {
            if(instance.getClass() == component.getClass()){
                assert false : String.format("GameObject: GO %s already contains component of type %s", "", instance.getClass().getName());
                return;
            }
        }
        instance.gameObject = this;
        components.add(instance);
    }

    public <T extends Component> void removeComponent(Class<T> componentClass){
        for (Component component : components) {
            if(componentClass.isAssignableFrom(component.getClass())){
                components.remove(component);
            }
        }
    }



    /**
     * Constructor for GameObject class
     * 
     */
    public GameObject(){
        components = new ArrayList<>();
        // addComponent(new SpriteRenderer());
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
     * Called when first frame of scene is displayed
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
     * @param deltaTime     Time elapsed between frames
     */
    public void Update(double deltaTime){
        for (Component component : components) {
            component.Update(deltaTime);
        }
    }

    /**
     * Called when the scene stops being displayed
     * 
     */
    public void End(){
        for (Component component : components) {
            component.End();
        }
    }
}
