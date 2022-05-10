package pocketplanets;

import java.util.Random;

public class Resource {
    private String resourceName;
    private int resourceId;
    private int amount;
    private double resourceValue;


    public void generateResource(String name, int id, double value, int maxmimumAmount){
        resourceName = name;
        resourceId = id;
        resourceValue = value;

        Random rand = new Random();
        setAmount(rand.nextInt(maxmimumAmount));
    }

    private void setAmount(int count){
        amount = count;
    }

    public int getAmount(){
        return amount;
    }
}
