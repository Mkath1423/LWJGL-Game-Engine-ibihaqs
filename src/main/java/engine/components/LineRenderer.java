package engine.components;

import java.util.Arrays;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.geometry.Quad;
import engine.renderer.Color;
import engine.renderer.EBOFormat;
import engine.renderer.Shader;
import engine.renderer.Texture;
import engine.renderer.VAO;
import engine.renderer.VAO.VAOFormat;

public class LineRenderer extends Renderable {

    /**
     * Start and end coordinates of the line
     */
    private Vector3f startPosition;
    private Vector3f endPosition;

    /**
     * Sets the start position to a new vector with the same values
     * 
     * @param newStartPosition the new start position
     */
    public void setStartPosition(Vector3f newStartPosition){
        startPosition = new Vector3f(newStartPosition);
    }

    
    /**
     * Sets the end position to a new vector with the same values
     * 
     * @param newEndPosition the new end position
     */
    public void setEndPosition(Vector3f newEndPosition){
        endPosition = new Vector3f(newEndPosition);
    }

    
    /** 
     * Gets the start position of the line
     * 
     * @return (Vector3f) a vertor with the same values as the start position
     */
    public Vector3f getStartPosition(){
        return new Vector3f(startPosition);
    }

    
    /** 
     * Gets the end position of the line
     * 
     * @return (Vector3f) a vertor with the same values as the end position
     */
    public Vector3f getEndPosition(){
        return new Vector3f(endPosition);
    }

    /**
     * Color of the line
     * 
     * set them to different values to interpolate between them
     */
    private Color startColor;
    private Color endColor;
    
    /** 
     * Sets the start color to a new Color with the same values
     * 
     * @param newStartPosition the new start color
     */
    public void setStartColor(Color newStartColor){
        startColor = new Color(newStartColor);
    }

    
    /** 
     * Sets the end color to a new Color with the same values
     * 
     * @param newEndColor the new end color
     */
    public void setEndColor(Color newEndColor){
        endColor = new Color(newEndColor);
    }

    
    /** 
     * Gets the start color of the line
     * 
     * @return (Color) a vertor with the same values as the start color
     */
    public Color getStartColor(){
        return new Color(startColor);
    }

    
    /** 
     * Gets the end color of the line
     * 
     * @return (Color) a vertor with the same values as the end color
     */
    public Color getEndColor(){
        return new Color(endColor);
    }

    /**
     * width of the line
     */
    private float lineWidth;

    
    /** 
     * @param newLineWidth
     */
    public void setLineWidth(float newLineWidth){
        lineWidth = newLineWidth;
    }

    
    /** 
     * @return float
     */
    public float getLineWidth(){
        return lineWidth;
    }

    public LineRenderer(Vector3f startPosition, Vector3f endPosition,
                        float lineWidth,
                        Color startColor,       Color endColor, int layerID){
        super(Shader.SPRITE, VAOFormat.SPRITE, EBOFormat.QUAD, 1, layerID);
                            
        this.startPosition = startPosition;
        this.endPosition   = endPosition;

        this.lineWidth     = lineWidth;

        this.startColor    = startColor;
        this.endColor      = endColor;
    }

    @Override
    public void loadVertexData(float[] buffer, int start) {

        // the vector between the start and end
        Vector3f length = new Vector3f();
            endPosition.sub(startPosition, length);
        
        // the vector between the start and edge of the line
        Vector3f width;
        if(length.x != 0) width = new Vector3f(-length.y/length.x, 1, 0);
        else width = new Vector3f(0, 1, 0);

            width.normalize();
            width.mul((float)lineWidth/2);
        /**
         * Find the four points that bound the line
         */
        Vector3f topLeft = new Vector3f();
            topLeft.add(startPosition);
            topLeft.add(length);
            topLeft.add(width);

        Vector3f topRight = new Vector3f();
            topRight.add(startPosition);
            topRight.add(length);
            topRight.sub(width);

        Vector3f bottomLeft = new Vector3f();
            bottomLeft.add(startPosition);
            bottomLeft.add(width);

        Vector3f bottomRight = new Vector3f();
            bottomRight.add(startPosition);
            bottomRight.sub(width);

        Vector3f[] vertices = new Vector3f[]{topLeft, topRight, bottomLeft, bottomRight};

        // add the verticies to the VBO
        for (int i = 0; i < 4; i++) {
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 0] = vertices[i].x;
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 1] = vertices[i].y;
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 2] = vertices[i].z;

            if(i < 2){
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 3] = startColor.getRed();
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 4] = startColor.getGreen();
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 5] = startColor.getBlue();
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 6] = startColor.getAlpha();
            }
            else{
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 3] = endColor.getRed();
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 4] = endColor.getGreen();
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 5] = endColor.getBlue();
                buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 6] = endColor.getAlpha();
            }

            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 7] = 0; 
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 8] = 0;
            
            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 9] = 0;

            buffer[start + VAOFormat.SPRITE.getVaoSize() * i + 10] = (true ? 1 : 0) + (true ? 2 : 0);
        }
    }

    
    /** 
     * No texture is accosiated with the line
     * 
     * @return Texture
     */
    @Override
    public Texture getTexture() {
        return null;
    }
    
}