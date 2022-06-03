package engine.serializer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.joml.Vector3f;


// References: 
//  https://stackabuse.com/reading-and-writing-files-in-java/
//  https://youtu.be/BbI8FdQOKNs
//  https://www.geeksforgeeks.org/different-ways-reading-text-file-java/

public class SaveStates {



    // INITIALIZATION


    
    private static FileReader r;               // Class for reading from save file
    
    private static BufferedWriter writer;      // Class for writing to save file
                   
    private static String file;                // The file we are reading and writing to
    private static String result;              // The result of the reading



    // EXTERNAL METHODS



    /**
     * Load and return gameObject's position from a given slot
     * 
     * @param slot  (int) The save slot to read
     * @return      (Vector3f) The position vector of the gameObject
     */
    public static Vector3f loadPosition(int slot) {

        // Get the text from the save slot in the result variable
        read(slot);

        // Variables that will be useful later
        int count = 0;
        int border = 0;

        // Coordinate variables
        float x = 0;
        float y = 0;
        float z = 0;

        // Convert from a string into floats
        for(int i = 0; i < result.length(); i++) {

            switch(count) {

                case 0:

                    if(result.charAt(i) == ',') {

                        x = Float.parseFloat(result.substring(border, i));
                        count++;
                        border = i+1;
                    }

                    break;
                
                case 1:

                    if(result.charAt(i) == ',') {

                        y = Float.parseFloat(result.substring(border, i));
                        count++;
                        border = i+1;
                    }

                    break;

                case 2:

                    if(result.charAt(i) == ',') {

                        z = Float.parseFloat(result.substring(border, i));
                        count++;
                    }

                    break;
                
                default:

                    break;

            }
        }

        return new Vector3f(x, y, z);
    }
    
    
    /**
     * Saves the current gameObject's position
     * 
     * @param slot  (int) The save slot to save to
     * @param pos   (Vector3f) The position of the object
     */
    public static void savePosition(int slot, Vector3f pos) {

        // The vector pos to store as a String
        String codedPos =   
        
        Float.toString(pos.x) + "," + 
        Float.toString(pos.y) + "," + 
        Float.toString(pos.z) + ",";

        write(slot, codedPos);
    }



    // INTERNAL METHODS



    /**
     * Selects the save slot file and sets the file path accordingly
     * 
     * @param slot  (int) The slot number we are writing to
     */
    private static void selectFile(int slot) {

        String filepath = 
        "src\\main\\java\\engine\\serializer\\"
        + "Slot" + Integer.toString(slot) + ".txt";

        File fp = new File(filepath);

        file = fp.getAbsolutePath();
    }

    
    /**
     * Read the information from a save slot, storing it in result
     * 
     * @param   slot (int) The slot which we are reading from
     */ 
    private static void read(int slot) {

        selectFile(slot);

        try {

            // Deriving a BufferedReader from the FileReader r
            r = new FileReader(file);
            BufferedReader reader = new BufferedReader(r);
            String st;
            
            // As long as there is something, read
            while((st = reader.readLine()) != null) {

                result = st;
            }

        // Error catching necessary in using these classes
        } catch (IOException e) {

            e.printStackTrace();

        }
    }
    
    
    /**
     * Writes to a save slot
     * 
     * @param slot  (int) The slot which we are writing to
     * @param text  (String) The text to be written
     */
    private static void write(int slot, String text) {

        selectFile(slot);

        // Create the file writer
        try {
            
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(text);
            writer.close();
        
        // Error catching necessary in using these classes
        } catch (IOException e) {
            
            e.printStackTrace();
        
        }
    }



    // "MAIN" METHOD



    public static void main(String[] args) {

        // savePosition(1, new Vector3f(0, 0, 0));
        // System.out.println(loadPosition(1));
    }
    


}
