package engine.components;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;

import engine.geometry.Quad;

public class Transform extends Component{

    public enum PositionMode{
        TOP_LEFT     (0f,   0f),
        TOP_MIDDLE   (0.5f, 0f),
        TOP_RIGHT    (1f,   0f),
        CENTER_LEFT  (0f,   0.5f),
        CENTER_MIDDLE(0.5f, 0.5f),
        CENTER_RIGHT (1f,   0.5f),
        BOTTOM_LEFT  (0f,   1f),
        BOTTOM_MIDDLE(0.5f, 1f),
        BOTTOM_RIGHT (1f,   1f);

        float xShift;
        public float getXShift(){ return xShift; };

        float yShift;
        public float getYShift(){ return yShift; };

        private PositionMode(float x, float y){
            this.xShift = x;
            this.yShift = y;
        }
    }

    public Vector3f position;
    public Vector2f scale;
    public float rotation;

    public PositionMode positionOrigin;
    public PositionMode rotationOrigin;

    public Transform(){
        this(new Vector3f(), new Vector2f(), 0f);
    }

    public Transform(Vector3f position, Vector2f scale, float rotation){
        this.position = new Vector3f(position.x, position.y, position.z);
        this.scale    = new Vector2f(scale.x,    scale.y);
        this.rotation = rotation;

        this.positionOrigin = PositionMode.TOP_LEFT;
        this.rotationOrigin = PositionMode.CENTER_MIDDLE;
    }

    public Quad getQuad(){
        // get top left vector in world space
        Vector3f topLeft = new Vector3f(position.x, position.y, position.z);
            topLeft.x = topLeft.x - scale.x * positionOrigin.getXShift();
            topLeft.y = topLeft.y - scale.y * positionOrigin.getYShift();

        // get bounding box vectors in world space
        Quad boundingBox = Quad.Rect(topLeft, scale.x, scale.y);


        // TODO: FIX Y ORIGIN BEING INVERTED
        // get center of rotation in world space
        Vector3f centerOfRotation = new Vector3f();
            centerOfRotation.add(topLeft);
            centerOfRotation.add(new Vector3f(scale.x * rotationOrigin.getXShift(), scale.y * rotationOrigin.getYShift(), 0));
        
        // make bounding box in CoR space
        boundingBox.topLeft.sub(centerOfRotation);
        boundingBox.topRight.sub(centerOfRotation);
        boundingBox.bottomLeft.sub(centerOfRotation);
        boundingBox.bottomRight.sub(centerOfRotation);
        
        // create the rotation matrix
        Matrix3f rotationMatrix = new Matrix3f(
            new Vector3f((float)Math.cos(rotation), (float)Math.sin(rotation), 0), 
            new Vector3f(-(float)Math.sin(rotation),  (float)Math.cos(rotation), 0),
            new Vector3f( 0,                         0,                         1));

        // rotate the bb vectors about the center of rotation
        boundingBox.topLeft.mul(rotationMatrix);
        boundingBox.topRight.mul(rotationMatrix);
        boundingBox.bottomLeft.mul(rotationMatrix);
        boundingBox.bottomRight.mul(rotationMatrix);
        
        // make the bb vectors in world space
        boundingBox.topLeft.add(centerOfRotation);
        boundingBox.topRight.add(centerOfRotation);
        boundingBox.bottomLeft.add(centerOfRotation);
        boundingBox.bottomRight.add(centerOfRotation);
        
        return boundingBox;
    }
}
