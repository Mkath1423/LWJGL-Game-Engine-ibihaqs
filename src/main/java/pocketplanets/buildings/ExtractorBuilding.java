package pocketplanets.buildings;

import pocketplanets.Resource;

public class ExtractorBuilding extends Building{



    // Type of resource generated by extractor 
    public Resource resource;
    
    private int productionRate;
    private int varianceRange;

    public ExtractorBuilding(int size, Resource resource, int productionRate, int varianceRange) {
        super(size);
        //TODO Auto-generated constructor stub
    }

    public void generateResource(){
        
    }
}
