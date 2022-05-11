package pocketplanets;

import java.util.Random;

public class Resource {
    private String resourceName;
    private int resourceId;
    private int amount;


    public Resource(String name, int id, int maxmimumAmount, int minimumAmount){
        generateResource(name, id, maxmimumAmount, minimumAmount);
    }

    private void generateResource(String name, int id, int max, int min){
        resourceName = name;
        resourceId = id;

        Random rand = new Random();
        setAmount((max - min + 1) + min);
    }

    private void setAmount(int count){
        amount = count;
    }

    public int getAmount(){
        return amount;
    }
}
