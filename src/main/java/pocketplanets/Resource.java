package pocketplanets;

import java.util.Random;

public class Resource {
    private String resourceName;
    private int resourceId;
    private int resourceAmount;


    public Resource(String name, int id, int amount){
        resourceName = name;
        resourceId = id;
        if(amount > 99){
            amount = 99;
        }
        setAmount(amount);
    }

    public void setAmount(int amount){
        if(amount > 99){
            amount = 99;
        }
        resourceAmount = amount;
    }

    public int getAmount(){
        return resourceAmount;
    }

    public String getName(){
        return resourceName;
    }

    public int getId(){
        return resourceId;
    }
}
