package engine.renderer;

import java.util.Arrays;

import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL20;

/**
 * Defines the structure of a vertex
 */
public enum VAO {
    SPRITE(new String[] {"aPosition", "aUV"}, new int[] {3, 2});
    // TINTED_SPRITE(new String[] {"aPosition", "aColor", "aUV"}, new int[] {3, 4, 2}),
    // COLOR(new String[] {"aPosition", "aColor"}, new int[] {3, 4});

    public class Attribute{
        public String name;
        public int size;
        public int offset;
    }

    public Attribute[] attributes;
    public Attribute[] getEmptyAttributes(){ return Arrays.copyOf(attributes, attributes.length); }

    public int vaoSize = 0;

    private int ID = -1;
    public int getID(){ return ID; }

    private VAO(String[] propertyNames, int[] propertySizes){
        assert propertyNames.length == propertySizes.length : "VAO: property names and sizes do not match up";

        attributes = new Attribute[propertySizes.length];

        vaoSize = 0;

        for (int i = 0; i < propertyNames.length; i++) {
            attributes[i] = new Attribute();

            attributes[i].name   = propertyNames[i];
            attributes[i].size   = propertySizes[i];
            attributes[i].offset = vaoSize;

            vaoSize += propertySizes[i];
        }

    }

    public int init(){
        ID = ARBVertexArrayObject.glGenVertexArrays();
        ARBVertexArrayObject.glBindVertexArray(ID);
    
        return ID;
    }

    public void bindPointers(int ID){
        ARBVertexArrayObject.glBindVertexArray(ID);
        for (int i = 0; i < attributes.length; i++) {
            // System.out.println(attributes[i].name + " " + attributes[i].size + " " + attributes[i].offset);
            GL20.glVertexAttribPointer(i, attributes[i].size, GL20.GL_FLOAT, false, vaoSize*Float.BYTES, attributes[i].offset*Float.BYTES);
            GL20.glEnableVertexAttribArray(i);
        }
    }

    public void enable(int ID){
        ARBVertexArrayObject.glBindVertexArray(ID);
        for (int i = 0; i < attributes.length; i++) {
            GL20.glEnableVertexAttribArray(i);
        }
    }

    public void disable(){
        for (int i = 0; i < attributes.length; i++) {
            GL20.glDisableVertexAttribArray(i);
        }
        ARBVertexArrayObject.glBindVertexArray(0);
    }
}
