package pocketplanets.ships;

import pocketplanets.Resource;

public class CargoShip extends Ship{

    // List of currently held cargo
    private Resource[] cargo;

    // Maxmimum held resources (sum of all resource amounts)
    private int cargoCapacity;

    // Current amoung of cargo slots taken up   
    private int spaceUsed; 

    @Override
    public void Awake(){
        cargo = new Resource[cargoCapacity];
    }

    /**
     * Used to extract resource objects from planets
     * 
     * @return  # of objects extracted
     */
    private int extract(){

        return 0;
    }

    /**
     * Used to deposit resource objects to cities
     * 
     */
    private void deposit(){
        // Filler
    
    }
}
