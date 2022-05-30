package engine.components;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import engine.geometry.Quad;

public class Transform extends Component{

    public enum PositionMode{
        TOP_LEFT     (0f,   -0f),
        TOP_MIDDLE   (0.5f, -0f),
        TOP_RIGHT    (1f,   -0f),
        CENTER_LEFT  (0f,   -0.5f),
        CENTER_MIDDLE(0.5f, -0.5f),
        CENTER_RIGHT (1f,   -0.5f),
        BOTTOM_LEFT  (0f,   -1f),
        BOTTOM_MIDDLE(0.5f, -1f),
        BOTTOM_RIGHT (1f,   -1f);

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

    public Vector3f getTopLeft(){
        Vector3f topLeft = new Vector3f(position.x, position.y, position.z);
            topLeft.x = topLeft.x - scale.x * positionOrigin.getXShift();
            topLeft.y = topLeft.y - scale.y * positionOrigin.getYShift();

        return topLeft;
    }

    public Vector3f getCenterOfRotation(){
        Vector3f topLeft = getTopLeft();

        Vector3f centerOfRotation = new Vector3f();
            centerOfRotation.add(topLeft);
            centerOfRotation.add(new Vector3f(scale.x * rotationOrigin.getXShift(), scale.y * rotationOrigin.getYShift(), 0));

        return centerOfRotation;
    }

    public float getRotationRadians(){
        return rotation;
    }

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
        Vector3f topLeft = getTopLeft();

        // get bounding box vectors in world space
        Quad boundingBox = Quad.Rect(topLeft, scale.x, scale.y);

        // get center of rotation in world space
        Vector3f centerOfRotation = getCenterOfRotation();
    
        // create the rotation matrix
        Matrix3f rotationMatrix = new Matrix3f();
            rotationMatrix.rotate(getRotationRadians(), new Vector3f(0, 0, 1));

        // rotate each of the bb vectors about the CoR
        for(Vector3f v : boundingBox.getVertices()){
            v.sub(centerOfRotation);
            v.mul(rotationMatrix);
            v.add(centerOfRotation);
        }

        // Transform into parent vector space
        if(gameObject.getParent() != null){

            Transform pt  = gameObject.getParent().getComponent(Transform.class);
            if(pt != null){
                // get the parent's CoR
                Vector3f parentCenterOfRotation = pt.getCenterOfRotation();

                // get the parent's rotation matrix
                Matrix3f parentMatrix = new Matrix3f();
                    parentMatrix.rotate((pt.getRotationRadians()), new Vector3f(0, 0, 1));

                // transform the parent
                for(Vector3f v : boundingBox.getVertices()){
                    // rotate the bb about the parent's CoR
                    v.mul(parentMatrix);

                    // translate the bb by the parent's CoR
                    v.add(new Vector3f(parentCenterOfRotation.x, parentCenterOfRotation.y, 0));
                }
            }
        }

        return boundingBox;
    }
}
