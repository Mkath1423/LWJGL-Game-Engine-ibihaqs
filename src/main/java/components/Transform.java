package components;

import org.joml.Vector2f;
import org.joml.Vector3f;

import geometry.Quad;

public class Transform extends Component{

    public enum PositionMode{
        TOP_LEFT     (0f,   0f),
        TOP_MIDDLE   (0.5f, 0f),
        TOP_RIGHT    (1f,   0f),
        CENTER_LEFT  (0f,   0.5f),
        CENTER_MIDDLE(0.5f, 0.5f),
        CENTER_RIGHT (1f,   0.5f),
        BOTTON_LEFT  (0f,   1f),
        BOTTOM_MIDDLE(0.5f, 1f),
        BOTTOM_RIGHT (1f,   1f);

        float xShift;
        public float getXShift(){ return xShift; };

        float yShift;
        public float getYShift(){ return yShift; };

        private PositionMode(float x, float y){
            this.xShift = x;
            this.xShift = y;
        }
    }

    public Vector3f position;
    public Vector2f scale;
    public float roation;

    public PositionMode positionOrigin;
    public PositionMode rotationOrigin;

    public Transform(){
        this(new Vector3f(), new Vector2f(), 0f);
    }

    public Transform(Vector3f position, Vector2f scale, float roation){
        this.position = new Vector3f(position.x, position.y, position.z);
        this.scale    = new Vector2f(scale.x,    scale.y);
        this.roation  = roation;
    }

    public Quad getQuad(){
        Vector3f topLeft = new Vector3f(position.x, position.y, position.z);
            topLeft.x = topLeft.x - scale.x * positionOrigin.getXShift();
            topLeft.y = topLeft.y - scale.y * positionOrigin.getYShift();

        return Quad.Rect(topLeft, scale.x, scale.y);
    }
}
