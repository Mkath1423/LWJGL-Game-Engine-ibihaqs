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
     * Position 
     */
    private Vector3f startPosition;
    private Vector3f endPosition;

    public void setStartPosition(Vector3f newStartPosition){
        startPosition = new Vector3f(newStartPosition);
    }

    public void setEndPosition(Vector3f newEndPosition){
        endPosition = new Vector3f(newEndPosition);
    }

    public Vector3f getStartPosition(){
        return new Vector3f(startPosition);
    }

    public Vector3f getEndPosition(){
        return new Vector3f(endPosition);
    }

    /**
     * Color
     */
    private Color startColor;
    private Color endColor;

    public void setStartColor(Color newStartColor){
        startColor = new Color(newStartColor);
    }

    public void setEndColor(Color newEndColor){
        endColor = new Color(newEndColor);
    }

    public Color getStartColor(){
        return new Color(startColor);
    }

    public Color getEndColor(){
        return new Color(endColor);
    }

    /**
     * Line Width
     */
    private float lineWidth;

    public void setLineWidth(float newLineWidth){
        lineWidth = newLineWidth;
    }

    public float getLineWidth(){
        return lineWidth;
    }

    public LineRenderer(Vector3f startPosition, Vector3f endPosition,
                        float lineWidth,
                        Color startColor,       Color endColor){
        super(Shader.SPRITE, VAOFormat.SPRITE, EBOFormat.QUAD, 1, 0);
                            
        this.startPosition = startPosition;
        this.endPosition   = endPosition;

        this.lineWidth     = lineWidth;

        this.startColor    = startColor;
        this.endColor      = endColor;
    }

    @Override
    public void loadVertexData(float[] buffer, int start) {
        Vector3f length = new Vector3f();
            endPosition.sub(startPosition, length);
            
        Vector3f width = new Vector3f(-length.y/length.x, 1, 0);
            width.normalize();
            width.mul((float)lineWidth/2);

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

        Quad line = new Quad(topLeft, topRight, bottomLeft, bottomRight);
        Vector3f[] vertices = line.getVertices();

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

    @Override
    public Texture getTexture() {
        return null;
    }
    
}