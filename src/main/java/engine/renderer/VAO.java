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
    
    public enum VAOFormat{
        SPRITE(new String[] {"aPosition", "aUV", "aTexID"}, new int[] {3, 2, 1});
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

        private List<Attribute> attributes;

        public List<Attribute> getAttributes(){ return attributes; }
        
        private int vaoSize = 0;

        public int getVaoSize() { return vaoSize; }

        private VAOFormat(String[] names, int[] sizes){
            attributes = new ArrayList<>();
            vaoSize = 0;

            for (int i = 0; i < names.length; i++) {
                attributes.add(new Attribute(names[i], sizes[i], vaoSize, 0));
                vaoSize += sizes[i];
            }
        }
    }

    public int ID = -1;
    public int getID(){ return ID; }
    
    private VAOFormat format;

    public VAOFormat getFormat() { return format; }

    public VAO(VAOFormat vaoFormat){
        this.ID = genBuffer();
        this.format = vaoFormat;
    }

    public static int genBuffer(){
        return ARBVertexArrayObject.glGenVertexArrays();
    }

    public void bind(){
        ARBVertexArrayObject.glBindVertexArray(ID);
    }

    public void unbind(){
        ARBVertexArrayObject.glBindVertexArray(0);
    }

    public void bindPointers(){
        List<Attribute> attributes = format.getAttributes();

        for (int i = 0; i < attributes.size(); i++) {
            GL20.glVertexAttribPointer(i, attributes.get(i).size, GL20.GL_FLOAT, false, format.getVaoSize()*Float.BYTES, attributes.get(i).offset*Float.BYTES);
            // GL33.glVertexAttribDivisor(i, attributes.get(i).divisor);
            GL20.glEnableVertexAttribArray(i);
        }
    }

    public void enable(){
        List<Attribute> attributes = format.getAttributes();

        bind();
        for (int i = 0; i < attributes.size(); i++) {
            GL20.glEnableVertexAttribArray(i);
        }
    }

    public void disable(){
        List<Attribute> attributes = format.getAttributes();

        for (int i = 0; i < attributes.size(); i++) {
            GL20.glDisableVertexAttribArray(i);
        }
        unbind();
    }
}