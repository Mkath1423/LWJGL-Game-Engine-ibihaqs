package engine.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;

import engine.renderer.VAO.VAOFormat.Attribute;

/**
 * Defines the structure of a vertex
 */
public class VAO {
    
    /**
     * The format of a VBO
     * 
     * Defines the attributes and their sizes 
     */
    public enum VAOFormat{
        SPRITE(new String[] {"aPosition", "aColor", "aUV", "aTexID", "aSettings"}, new int[] {3, 4, 2, 1, 1});
        // TINTED_SPRITE(new String[] {"aPosition", "aColor", "aUV"}, new int[] {3, 4, 2}),
        // COLOR(new String[] {"aPosition", "aColor"}, new int[] {3, 4});
        protected class Attribute{
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

        /**
         * the attributes of the VAO
         */
        private List<Attribute> attributes;
        public List<Attribute> getAttributes(){ return attributes; }
        
        /**
         * the size of the VAO in floats
         */
        private int vaoSize = 0;
        public int getVaoSize() { return vaoSize; }

        private VAOFormat(String[] names, int[] sizes){
            attributes = new ArrayList<>();
            vaoSize = 0;

            // add each attribute while caounting the total size
            for (int i = 0; i < names.length; i++) {
                attributes.add(new Attribute(names[i], sizes[i], vaoSize, 0));
                vaoSize += sizes[i];
            }
        }
    }

    /**
     * The id of the attribute buffer
     */
    public int ID = -1;
    public int getID(){ return ID; }
    
    /**
     * The format of this VAO
     */
    private VAOFormat format;
    public VAOFormat getFormat() { return format; }

    public VAO(VAOFormat vaoFormat){
        this.ID = genBuffer();
        this.format = vaoFormat;
    }

    /**
     * Generates the buffer for a VAO
     * 
     * @return the buffer ID
     */
    public static int genBuffer(){
        return ARBVertexArrayObject.glGenVertexArrays();
    }

    /**
     * Binds this VAO
     */
    public void bind(){
        ARBVertexArrayObject.glBindVertexArray(ID);
    }

    /**
     * Unbinds this VAO
     */
    public void unbind(){
        ARBVertexArrayObject.glBindVertexArray(0);
    }

    /**
     * Binds the pointers for all attributes
     * 
     * TODO: add divisor for instancing
     */
    public void bindPointers(){
        List<Attribute> attributes = format.getAttributes();

        // bind each attribute
        for (int i = 0; i < attributes.size(); i++) {
            GL20.glVertexAttribPointer(i, attributes.get(i).size, GL20.GL_FLOAT, false, format.getVaoSize()*Float.BYTES, attributes.get(i).offset*Float.BYTES);
            // GL33.glVertexAttribDivisor(i, attributes.get(i).divisor);
            GL20.glEnableVertexAttribArray(i);
        }
    }

    /**
     * Bind vao and enable its attributes
     */
    public void enable(){
        List<Attribute> attributes = format.getAttributes();

        bind();
        for (int i = 0; i < attributes.size(); i++) {
            GL20.glEnableVertexAttribArray(i);
        }
    }

    /**
     * Unbind the vao and disable its attributes
     */
    public void disable(){
        List<Attribute> attributes = format.getAttributes();

        for (int i = 0; i < attributes.size(); i++) {
            GL20.glDisableVertexAttribArray(i);
        }
        unbind();
    }
}