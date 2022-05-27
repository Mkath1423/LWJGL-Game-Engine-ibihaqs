package engine.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;

/**
 * Defines the structure of a vertex
 */
public class VAO {
    // SPRITE(new String[] {"aPosition", "aUV", "aTexID"}, new int[] {3, 2, 1});
    // // TINTED_SPRITE(new String[] {"aPosition", "aColor", "aUV"}, new int[] {3, 4, 2}),
    // // COLOR(new String[] {"aPosition", "aColor"}, new int[] {3, 4});

    private class Attribute{
        public String name;
        public int size;
        public int offset;
        public int divisor;

        public Attribute(String name, int size, int offset, int divisor){
            this.name = name;
            this.size = size;
            this.offset = offset;
            this.divisor = divisor;
        }
    }

    public List<Attribute> attributes;

    public int vaoSize = 0;

    private int ID = -1;
    public int getID(){ return ID; }
    public static int genBuffer(){
        return ARBVertexArrayObject.glGenVertexArrays();
    }

    public VAO(){
        ID = genBuffer();
        attributes = new ArrayList<>();
        vaoSize = 0;
    }

    public void addAttribute(String name, int sizeFloats, int divisor){
        attributes.add(
            new Attribute(
                name,
                sizeFloats,
                vaoSize,
                divisor
            )
        );

        vaoSize += sizeFloats;
    }
    
    public void bind(){
        ARBVertexArrayObject.glBindVertexArray(ID);
    }

    public void unbind(){
        ARBVertexArrayObject.glBindVertexArray(0);
    }

    public void bindPointers(){
        for (int i = 0; i < attributes.size(); i++) {
            System.out.println(attributes.get(i).name + " " + attributes.get(i).size + " " + attributes.get(i).offset);
            GL20.glVertexAttribPointer(i, attributes.get(i).size, GL20.GL_FLOAT, false, vaoSize*Float.BYTES, attributes.get(i).offset*Float.BYTES);
            // GL33.glVertexAttribDivisor(i, attributes.get(i).divisor);
            GL20.glEnableVertexAttribArray(i);
        }
    }

    public void enable(){
        for (int i = 0; i < attributes.size(); i++) {
            GL20.glEnableVertexAttribArray(i);
        }
    }

    public void disable(){
        for (int i = 0; i < attributes.size(); i++) {
            GL20.glDisableVertexAttribArray(i);
        }
        unbind();
    }
}
