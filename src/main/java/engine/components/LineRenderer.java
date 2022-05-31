package engine.components;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.geometry.Quad;
import engine.renderer.Texture;

public class LineRenderer extends QuadRenderable {

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
    private Vector4f startColor;
    private Vector4f endColor;

    public void setStartColor(Vector4f newStartColor){
        startColor = new Vector4f(newStartColor);
    }

    public void setEndColor(Vector4f newEndColor){
        endColor = new Vector4f(newEndColor);
    }

    public Vector4f getStartColor(){
        return new Vector4f(startColor);
    }

    public Vector4f getEndColor(){
        return new Vector4f(endColor);
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
                        Vector4f startColor,    Vector4f endColor){
        super(4, 0);
                            
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
        
        Vector3f width = new Vector3f(-length.x/length.y, 1, 0);
            width.normalize();
            width.mul((float)lineWidth/2);

        Vector3f topLeft = new Vector3f();
            topLeft.add(startPosition);
            topLeft.add(length);
            topLeft.add(width);

        Vector3f topRight = new Vector3f();
            topRight.add(startPosition);
            topLeft.add(length);
            topRight.sub(width);

        Vector3f bottomLeft = new Vector3f();
            bottomLeft.add(startPosition);
            bottomLeft.add(width);

        Vector3f bottomRight = new Vector3f();
            bottomRight.add(startPosition);
            bottomRight.sub(width);
        
        Quad line = new Quad(topLeft, topRight, bottomLeft, bottomRight);

        for (int i = 0; i < numberVertices; i++) {
            // load buffer
        }
    }

    @Override
    public Texture getTexture() {
        return null;
    }
    
}
