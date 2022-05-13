package pocketplanets.ships;

import java.text.Normalizer;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.components.Component;
import engine.components.Transform;
import engine.gameobjects.GameObject;
import pocketplanets.Planet;

public abstract class Ship extends Component{

    // Information regarding ships fuel sotrage 
    private double fuelCapacity;
    private double fuelHeld;
    private double fuelEfficiency;

    // Speed property of ship
    private float speed;

    // private Stats shipStats;

    // Current desired location of ship path
    private Vector3f destination;

    private Transform transform;

    // Current velocity of the ship
    private Vector3f velocity;

    // Health Property of ship
    private double health;

    // Landed status of ship
    private boolean isLanded;

    //private Planet currentPlanet;

    public Ship(double fuelCapacity, double fuelHeld, double fuelEfficiency, double speed, double health, boolean isLanded){
        
    }

    @Override
    public void Awake(){
        transform = gameObject.getComponent(Transform.class);
        
    }
    
    @Override
    public void Update(double deltaTime){

        goToPosition(destination);
        if(!isLanded && transform.position.distance(destination) > 30){
            transform.position.fma((float)(speed*deltaTime), velocity);
        } 
    }

    /**
     * Fills ships tank by maximizing current held fuel
     * 
     * @return the current fuel held of the ship post fueling
     */
    private double refuelShip(){
        fuelHeld = fuelCapacity;
        return fuelHeld;
    }

    /**
     * Set destination to coordinate position via transform class
     * 
     * @param location coordinates where we want the ship to move towards
     */
    public void goToPosition(Vector3f destination){
        this.destination = destination;
        velocity = new Vector3f(destination.x - transform.position.x, destination.y - transform.position.y, destination.z - transform.position.z);
    }

    // /*
    //  * Returns the closest planet to ship
    //  * 
    //  * @return  closest planet to ship
    //  */
    // private Planet getNearestPlanet(){
        
    // }

    /**
     * Land the ship on a planet
     * 
     */
    private void landShip(){
        isLanded = true;
    }

    /**
     * Take off from wherever ship is currently landed
     * 
     */
    private void takeOff(){
        isLanded = false;
    }



}
