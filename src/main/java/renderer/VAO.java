package renderer;

import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL20;

public class VAO {
    private class Attribute{
        String name;
        int size;
        int offset;
    }

    private Attribute[] attributes;
    public Attribute[] getAttributes(){ return attributes; }

    public int vaoSize = 0;

    private int ID = -1;

    public VAO(String[] propertyNames, int[] propertySizes){
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

    public void init(){
        ID = ARBVertexArrayObject.glGenVertexArrays();
        ARBVertexArrayObject.glBindVertexArray(ID);

        for (int i = 0; i < attributes.length; i++) {
            GL20.glVertexAttribPointer(i, attributes[i].size, GL20.GL_FLOAT, false, vaoSize*Float.BYTES, attributes[i].offset);
            GL20.glEnableVertexAttribArray(i);
        }

    }

    public void enable(){
        ARBVertexArrayObject.glBindVertexArray(ID);
        for (int i = 0; i < attributes.length; i++) {
            GL20.glEnableVertexAttribArray(i);
        }
    }

    public void disable(int vaoID){
        for (int i = 0; i < attributes.length; i++) {
            GL20.glDisableVertexAttribArray(i);
        }
        ARBVertexArrayObject.glBindVertexArray(0);
    }
}
