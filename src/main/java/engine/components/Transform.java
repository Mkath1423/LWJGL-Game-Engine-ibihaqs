package engine.components;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import engine.geometry.Quad;

public class Transform extends Component{

    /**
     * Where the transform's origin is
     */
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

    /**
     * Extra parameters for locking certain attributes
     */
    public enum Parameters{
        LOCK_ROTATION,
        LOCK_X_SCALE,
        LOCK_Y_SCALE,
        LOCK_X_POSITION,
        LOCK_Y_POSITION,
        LOCK_Z_POSITION; 
    }

    /** 
     * Inforamtion about the object's transformation
     *  - position relative to parent
     *  - scale in pixels
     *  - rotation relative to parent
    */
    public Vector3f position;
    public Vector2f scale;
    public float rotation;

    /**
     * Where the origin of position and rotation are
     */
    public PositionMode positionOrigin;
    public PositionMode rotationOrigin;

    /**
     * Stop the position components from being changed after construction
     */
    private boolean lockXPosition;
    private boolean lockYPosition;
    private boolean lockZPosition;

    /**
     * Stop the rotation from being changed after construction
     */
    private boolean lockRotation;

    /**
     * Stop the scale components from being changed after construction
     */
    private boolean lockXScale;
    private boolean lockYScale;

    /**
     * Gets the position as a new vector3f
     * 
     * @return (Vector3f) the transform's position
     */
    public Vector3f getPosition(){
        return new Vector3f(position);
    }

    /**
     * Sets each component if it is not locked
     * 
     * @param newPosition the new position
     */
    public void setPosition(Vector3f newPosition){
        if(!lockXPosition) position.x = newPosition.x;
        if(!lockYPosition) position.y = newPosition.y;
        if(!lockZPosition) position.z = newPosition.z;
    }

    /**
     * Adds to each component if it is not locked
     * 
     * @param newPosition the amount to change by
     */
    public void addPosition(Vector3f newPosition){
        if(!lockXPosition) position.x += newPosition.x;
        if(!lockYPosition) position.y += newPosition.y;
        if(!lockZPosition) position.z += newPosition.z;
    }

    /**
     * Subs from each component if it is not locked
     * 
     * @param newPosition the amount to change by
     */
    public void subPosition(Vector3f newPosition){
        if(!lockXPosition) position.x -= newPosition.x;
        if(!lockYPosition) position.y -= newPosition.y;
        if(!lockZPosition) position.z -= newPosition.z;
    }

    /**
     * Gets the scale as a new Vector2f
     * 
     * @return (Vector2f) the transform's scale
     */
    public Vector2f getScale(){
        return new Vector2f(scale);
    }

    /**
     * Sets the scale by component
     * 
     * @param newScale the new value for the scale
     */
    public void setScale(Vector2f newScale){
        if(!lockXScale) scale.x = newScale.x;
        if(!lockYScale) scale.y = newScale.y;
    }

    /**
     * Gets the rotation
     * 
     * @return (float) the transform's rotation
     */
    public float getRotation(){
        return rotation;
    }

    /**
     * Sets the value of the rotation
     * 
     * @param newRotation the new rotation
     */
    public void setRotation(float newRotation){
        if(!lockRotation) rotation = newRotation;
    }

    /**
     * Gets the top left position of the transform
     * 
     * @return the coordinates of the top left relative to the position origin
     */
    public Vector3f getTopLeft(){
        Vector3f topLeft = new Vector3f(position.x, position.y, position.z);
            topLeft.x = topLeft.x - scale.x * positionOrigin.getXShift();
            topLeft.y = topLeft.y - scale.y * positionOrigin.getYShift();

        return topLeft;
    }

    /**
     * Gets the position of the center of rotation
     * 
     * @return the position of the center of rotation
     */
    private Vector3f getCenterOfRotation(){
        Vector3f topLeft = getTopLeft();

        Vector3f centerOfRotation = new Vector3f();
            centerOfRotation.add(topLeft);
            centerOfRotation.add(new Vector3f(scale.x * rotationOrigin.getXShift(), scale.y * rotationOrigin.getYShift(), 0));

        return centerOfRotation;
    }

    /**
     * Gets the transform's position is world coordinates
     * 
     * @return the transform's position in world coordinates
     */
    public Vector3f getWorldPosition(){
        if(gameObject.getParent() == null ||
          gameObject.getParent().getComponent(Transform.class) == null
        ){
            return getPosition();
        } 
        else{
            Transform parentTransform = gameObject.getParent().getComponent(Transform.class);

            Vector3f parentCoR = parentTransform.getCenterOfRotation();

            Matrix3f rotationMatrix = new Matrix3f();
                rotationMatrix.rotate(parentTransform.getRotation(), new Vector3f(0, 0, 1));

            Vector3f out = getPosition();
                out.mul(rotationMatrix);
                out.add(new Vector3f(parentCoR.x, parentCoR.y, 0));

            return out; 
        } 
    }


    public Transform(){
        this(new Vector3f(), new Vector2f(), 0f);
    }

    public Transform(Vector3f position, Vector2f scale, float rotation, Parameters... params){
        this.position = new Vector3f(position.x, position.y, position.z);
        this.scale    = new Vector2f(scale.x,    scale.y);
        this.rotation = rotation;

        this.positionOrigin = PositionMode.TOP_LEFT;
        this.rotationOrigin = PositionMode.CENTER_MIDDLE;

        /**
         * Lock attributes based on parameters
         */
        for (Parameters param : params) {
            switch(param){
                case LOCK_ROTATION:
                    lockRotation = true;
                    break;

                case LOCK_X_POSITION:
                    lockXPosition = true;
                    break;

                case LOCK_Y_POSITION:
                    lockYPosition = true;
                    break;

                case LOCK_Z_POSITION:
                    lockZPosition = true;
                    break;

                case LOCK_X_SCALE:
                    lockXScale = true;
                    break;

                case LOCK_Y_SCALE:
                    lockYScale = true;
                    break;
            }
        }
    }

    /**
     * Gets the bounding bot of the transform
     * 
     * @return A rectangle with top left position of this tranform and side lengths corosponding to the scale
     */
    public Quad getQuad(){
        // get top left vector in world space
        Vector3f topLeft = getTopLeft();

        // get bounding box vectors in world space
        Quad boundingBox = Quad.Rect(topLeft, getScale().x, getScale().y);

        // get center of rotation in world space
        Vector3f centerOfRotation = getCenterOfRotation();
    
        // create the rotation matrix
        Matrix3f rotationMatrix = new Matrix3f();
            rotationMatrix.rotate(getRotation(), new Vector3f(0, 0, 1));

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
                    parentMatrix.rotate((pt.getRotation()), new Vector3f(0, 0, 1));

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
