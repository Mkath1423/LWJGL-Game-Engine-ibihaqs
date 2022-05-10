package pocketplanets.ships;

import org.joml.Vector2f;

import gameobjects.GameObject;

public abstract class Ship extends GameObject{

    // Information regarding ships fuel sotrage 
    private double fuelCapacity;
    private double fuelHeld;
    private double fuelEfficiency;

    // Speed property of ship
    private double speed;

    // private Stats shipStats;

    // Current desired location of ship path
    private Vector2f destination;

    // Health Property of ship
    private double health;

    // Landed status of ship
    private boolean isLanded;

    //private Planet currentPlanet;

    @Override
    public void Update(double deltaTime){
        goToPosition(destination);
        if(!isLanded){
            if(transform.position.x < destination.x){
                // Constantly move ship towards destination by adding speed property to current position 
                transform.position.x += speed;
            }
            else if(transform.position.x > destination.x){
                transform.position.x -= speed;
            }
            
            

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
    public void goToPosition(Vector2f location){
        // destination = location;
    }

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
