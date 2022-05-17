package pocketplanets;

import engine.components.Component;
import engine.gameobjects.GameObject;

public class Planet extends Component{


    private boolean isDiscovered;
    private String planetName;
    private int planetId;
    private int buildingCapacity;

    public Planet(String name, int id, int capacity){
        isDiscovered = false;
        planetName = name;
        planetId = id;
        buildingCapacity = capacity;

    }

    public void planetDiscovered(){
        isDiscovered = true;
    }
    

}
