package engine.renderer;

import java.util.Arrays;

/**
 * Holds the values for an EBO
 * 
 * @author Lex Stapleton
 */
public enum EBOFormat {
    QUAD(new int[] {0, 2, 1,      // Top right triangle tr-tl-br
                    1, 2, 3 }),   // bottom left triangle br-tl-bl
    TRIANGLE(new int[] {0, 1, 2}),
    PANEL(new int[]
    {
        5,  2,  1,  5,  6,  2,
        6,  3,  2,  6,  7,  3,
        7,  4,  3,  7,  8,  4,
        9,  6,  5,  9,  10, 6,
        10, 7,  6,  10, 11, 7, 
        11, 8,  7,  11, 12, 8,
        13, 10, 9,  3,  14, 10, 
        14, 11, 10, 14, 15, 11, 
        15, 12, 11, 15, 16, 12
    });

    /**
     * The values of 1 repitition of the EBO
     */
    private int[] indices;

    /**
     * The amount of verticies one repition of the EBO expects
     */
    private int numberVertices;

    private EBOFormat(int[] indices){
        this.indices = indices;

        // https://stackoverflow.com/questions/65637209/counting-unique-values-in-an-array-java
        this.numberVertices = (int)Arrays.stream(indices).distinct().count();
    }

    /**
     * Gets the indicies of the EBO
     * 
     * @return the indicies
     */
    public int[] getIndices(){
        return indices;
    }

    /**
     * Gets the amount of values in one repitition of the EBO
     * 
     * @return the mount of values
     */
    public int getLength(){
        return indices.length;
    }
    
    /**
     * Gets the amount of verticies in the EBO
     * 
     * @return the amount of verticies
     */
    public int getNumberOfVertices(){
        return numberVertices;
    }

    /**
     * Generates the indicies of an EBO
     * 
     * @param format the format/pattern to follow
     * @param numElements the amount of times to repeat the pattern
     * @return the full array of indicies
     */
    public static int[] generateIndices(EBOFormat format, int numElements){
        int[] indices = new int[format.getLength() * numElements];

        for(int i = 0; i < numElements; i++){
            // calculate the offset from the start of the buffer
            int positionOffset = format.getLength() * i;

            // calculate the offset of each value in this repitition
            int valueOffset = format.getNumberOfVertices() * i;
            
            // add the values for this iteration of the pattern
            int[] eboIndices = format.getIndices();
            for (int j = 0; j < eboIndices.length; j++) {
                indices[positionOffset + j] = valueOffset + eboIndices[j];
            }
        }

        return indices;
    } 
}
