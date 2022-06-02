package engine.serializer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.SelectableChannel;
import java.util.List;

// References: 
//  https://stackabuse.com/reading-and-writing-files-in-java/
//  https://youtu.be/BbI8FdQOKNs

public class SaveStates {



    // INITIALIZATION


    
    private static List<Character> result;          // Array of chars for reading file
    
    private static FileReader reader;               // Class for reading from save file
    
    private static BufferedWriter writer;           // Class for writing to save file
                   
    private static String file;                     // The file we are reading and writing to



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
     * Read the information from the selected file
     * 
     * @param   slot (int) The slot which we are reading from
     */ 
    private static List<Character> read(int slot) {

        selectFile(slot);

        try {

            reader = new FileReader(file);
            int ch = reader.read();
            
            while(ch != -1) {

                System.out.print((char)ch);
                result.add((char)ch);
                System.out.println("done reading");
                reader.close();
            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        System.out.println(result);
        return(result);

    }
    
    
    private static void write(int slot) {

        selectFile(slot);

        // Create the file writer
        try {
            
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("test lmao");
            writer.close();
        
        } catch (IOException e) {
            
            e.printStackTrace();
        
        }
    }



    // "MAIN" METHOD



    public static void main(String[] args) {

        write(1);      
        read(1);  
    }
    


}
