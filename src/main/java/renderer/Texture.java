package renderer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.stb.STBImage;

public class Texture {
    private String filepath;
    private int texId;

    public Texture(String filepath){
        this.filepath = filepath;

        texId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        // repeat in both directions
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        // pixelate when scaling
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    
        // 
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = STBImage.stbi_load(filepath, width, height, channels, 0);
    
        if(image != null){
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width.get(0), height.get(0), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
        }
        else{
            assert false : "Texture " + filepath + " could not be loaded";
        }

        STBImage.stbi_image_free(image);
    }

    public void bind(){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
    }

    public void unbind(){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    @Override
    public boolean equals(Object o){
        if(this.getClass() != o.getClass()) return false;

        Texture that = (Texture)o;

        return this.filepath.equals(that.filepath);
    }


}
