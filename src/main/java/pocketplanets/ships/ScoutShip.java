package pocketplanets.ships;

public class ScoutShip extends Ship{
    
    // Radius of sonar scans from search
    private double sonarRadius;

    
    public ScoutShip(double fuelCapacity, double fuelHeld, double fuelEfficiency, double speed, double health, boolean isLanded, double sonar){
        super(fuelCapacity, fuelHeld, fuelEfficiency, speed, health, isLanded);
    }

    /**
     * Directs you to distant planets within sonar radius
     * 
     */
    private void search(){

    }
}
