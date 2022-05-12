package pocketplanets;

import components.Component;
import gameobjects.GameObject;

public class Planet extends Component{


    private boolean isDiscovered;
    private int radius;
    private String name;
    private int planetId;

    public Planet(int radius, String name, int planetId){
        isDiscovered = false;
        this.radius = radius;
        this.name = name;
        this.planetId = planetId;

    }


    

}
