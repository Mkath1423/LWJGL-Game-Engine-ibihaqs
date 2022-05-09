public class Ship extends GameObject{

    // Information regarding ships fuel sotrage 
    private double fuelCapacity;
    private double fuelHeld;
    private double fuelEfficiency;

    // Speed property of ship
    private double speed;

    // private Stats shipStats;

    // Current desired location of ship path
    private Vector3f destination;

    // Health Property of ship
    private double health;

    // Landed status of ship
    private boolean isLanded;

    //private Planet currentPlanet;

    @Override
    public void Update(float deltaTime){
        goToPosition(location);
        if(!isLanded){
            if(transform.postion < destination){
                
            }
            
            // Constantly move ship towards destination by adding speed property to current position
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
