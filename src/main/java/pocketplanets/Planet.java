package pocketplanets;

import gameobjects.GameObject;

public class Planet extends GameObject{


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
