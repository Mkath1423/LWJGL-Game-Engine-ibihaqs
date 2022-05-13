package engine.renderer;

import java.util.Arrays;

public enum EBO {
    QUAD(new int[] {1, 0, 2,      // Top right triangle tr-tl-br
                    3, 1, 2 }),   // bottom left triangle br-tl-bl
    TRIANGLE(new int[] {0, 1, 2}) 
    ;

    private int[] indices;
    private int numberVertices;

    private EBO(int[] indices){
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
}
