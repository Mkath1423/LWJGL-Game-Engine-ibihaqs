package pocketplanets.buildings;

public class FuelStationBuilding extends Building{

    private double maxFuel;
    private double currentFuel;
    private double generationRate;

    public FuelStationBuilding(int size, double max, double rate) {
        super(size);
        maxFuel = max;
        currentFuel = maxFuel;
        generationRate = rate;
    }

    @Override
    public void Update(double deltaTime){
        if(currentFuel < maxFuel){
            generateFuel();
        }
    }

    private void generateFuel(){
        currentFuel += generationRate;
    }
}
