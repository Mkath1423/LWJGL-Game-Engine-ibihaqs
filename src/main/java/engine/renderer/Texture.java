package engine.renderer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.stb.STBImage;

/**
 * A Texture for openGL
 * 
 * Slightly adapted from tutorial by GamesWithGabe
 */
public class Texture {

    /**
     * The format of the Texture
     * 
     * Either RGB or RGBA
     */
    public enum Format{
        RGB(GL11.GL_RGB),
        RGBA(GL11.GL_RGBA);

        int format;

        private Format(int format){
            this.format = format;
        }

        /**
         * Gets the OpenGL constant for the texture format
         * 
         * @return the GL format
         */
        public int get(){
            return format;
        }

    }

    /**
     * The file this texture was loaded from
     */
    private String filepath;

    /**
     * The openGL texture id
     */
    private int texId;
    public int getTexId(){ return texId; }

    /**
     * The height and width of the texture
     */
    private int texWidth;
    private int texHeight;

    public int getWidth(){return texWidth;}
    public int getHeight(){return texHeight;}

    /**
     * Normalizes a position in the texture
     * 
     * @param pos the position in the texture
     * @return the normalized position
     */
    public Vector2f toNDC(Vector2f pos){
        return new Vector2f(pos.x / getWidth(), pos.y/getWidth());
    }

    /**
     * Normalizes the y value in the texture
     * 
     * @param pos the y pos in the texture
     * @return the normalized position
     */
    public float toNDCHeight(float pos){
        return pos / getHeight();
    }

    /**
     * Normalizes the x value in the texture
     * 
     * @param pos the x pos in the texture
     * @return the normalized position
     */
    public float toNDCWidth(float pos){
        return pos / getWidth();
    }

    public Texture(String filepath, Format format){
        this.filepath = filepath;

        /**
         * generate and bind the texture
         */
        texId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        /**
         * make texture repeat in both directions
         */
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        /**
         * pixelate the texture when scaling 
         */ 
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    
        /**
         * load the image into the buffer
         */
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = STBImage.stbi_load(filepath, width, height, channels, 0);
    
        /**
         * Send the texture buffer to openGL
         */
        if(image != null){
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, format.get(), width.get(0), height.get(0), 0, format.get(), GL11.GL_UNSIGNED_BYTE, image);
        }
        else{
            assert false : "Texture " + filepath + " could not be loaded";
        }

        /**
         * no memory leaks :)
         */
        STBImage.stbi_image_free(image);

        /**
         * get the texture size and width
         */
        texWidth = GL20.glGetTexLevelParameteri(texId, 0, GL20.GL_TEXTURE_WIDTH);
        texHeight = GL20.glGetTexLevelParameteri(texId, 0, GL20.GL_TEXTURE_HEIGHT);
    }

    public Texture(String filepath){
        this(filepath, Format.RGBA);
    }

    /**
     * Binds this texture to the active texture
     */
    public void bind(){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
    }

    /**
     * Binds 0 to the active texture
     */
    public void unbind(){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    /**
     * Checks if the textures have the same file path
     * 
     * @return true if textures have the same file path 
     */
    @Override
    public boolean equals(Object o){
        if(this.getClass() != o.getClass()) return false;

        Texture that = (Texture)o;

        return this.filepath.equals(that.filepath);
    }


}
