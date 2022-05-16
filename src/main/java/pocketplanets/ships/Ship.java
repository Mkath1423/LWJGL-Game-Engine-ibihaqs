package pocketplanets.ships;

import java.text.Normalizer;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.components.Component;
import engine.components.Transform;
import engine.gameobjects.GameObject;
import physics.Move;
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
    private Move move;

    // Current velocity of the ship
    private Vector3f velocity;

    private Vector3f acceleration;

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
        move = gameObject.getComponent(Move.class);
        
    }
    
    @Override
    public void Update(double deltaTime){
        if(!isLanded && transform.position.distance(destination) < 30){
            acceleration.set(0, 0, 0);
            velocity.set(0, 0, 0);
            move.initialize(acceleration, velocity);
            
            
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
    public void goToPosition(Vector3f destination, float accelMagnitude){
        this.destination = destination;
        velocity = new Vector3f((destination.x - transform.position.x), (destination.y - transform.position.y), (destination.z - transform.position.z));
        acceleration = new Vector3f(accelMagnitude, accelMagnitude, accelMagnitude);
        velocity = velocity.normalize().add(speed, speed, speed);
        move.initialize(acceleration, velocity);
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
