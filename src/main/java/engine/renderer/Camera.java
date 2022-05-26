package engine.renderer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Window;
import engine.components.Component;
import engine.components.Transform;

public class Camera extends Component{
    private Matrix4f projectionMatrix, viewMatrix;
    private Matrix4f invProjectionMatrix, invViewMatrix;
    
    public Matrix4f getViewMatrix(){
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix(){
        return projectionMatrix;
    }

    private Transform t;

    public Camera(){
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.invProjectionMatrix = new Matrix4f();
        this.invViewMatrix = new Matrix4f();
    }

    @Override
    public void Awake(){
        t = gameObject.getComponent(Transform.class);
    }

    @Override
    public void LateUpdate(double deltaTime){
        if(t == null) return;

        // calculate the 
        Vector3f cameraFront = new Vector3f(0, 0, -1);
        Vector3f cameraUp = new Vector3f(0, 1, 0);
        
        viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(t.position.x, t.position.y, 20f), 
                                            cameraFront.add(t.position.x, t.position.y, 0),
                                            cameraUp);
        viewMatrix.invert(invViewMatrix);

        projectionMatrix.identity();

        projectionMatrix.ortho(0f, t.scale.x /*32f*40f*/, 0f, t.scale.y  /*32f*21f*/, 0f, 100f);
        projectionMatrix.invert(invProjectionMatrix);

    }


    public Vector2f screenToWorldCoordinate(Vector2f screenPosition){
        Vector2f windowSize = Window.getSize();

        Vector4f resultant = new Vector4f(screenPosition.x / windowSize.x * 2.0f - 1.0f, 
                                          screenPosition.y/ windowSize.y * -2.0f + 1.0f, 
                                          1f, 1f);

        // System.out.printf("(%s, %s)\n", screenPosition.x, screenPosition.y);
        resultant.mul(invViewMatrix).mul(invProjectionMatrix);
        
        return new Vector2f(resultant.x, resultant.y);
    }
}
