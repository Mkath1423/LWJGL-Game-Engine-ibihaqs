package engine.renderer;

import java.util.Arrays;

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

    private int[] indices;
    private int numberVertices;

    private EBOFormat(int[] indices){
        this.indices = indices;

        // https://stackoverflow.com/questions/65637209/counting-unique-values-in-an-array-java
        this.numberVertices = (int)Arrays.stream(indices).distinct().count();
    }

    public int[] getIndices(){
        return indices;
    }

    public int getLength(){
        return indices.length;
    }
    
    public int getNumberOfVertices(){
        return numberVertices;
    }

    public static int[] generateIndices(EBOFormat format, int numElements){
        int[] indices = new int[format.getLength() * numElements];

        for(int i = 0; i < numElements; i++){
            int offsetArrayIndex = format.getLength() * i;
            int offset = format.getNumberOfVertices() * i;
            // System.out.println(offsetArrayIndex);
            // System.out.println(offset);

            int[] eboIndices = format.getIndices();
            for (int j = 0; j < eboIndices.length; j++) {
                indices[offsetArrayIndex + j] = offset + eboIndices[j];
            }
        }

        return indices;
    } 
}