package engine.renderer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Window;
import engine.components.Component;

public class Camera extends Component{
    private Matrix4f projectionMatrix, viewMatrix;
    private Matrix4f invProjectionMatrix, invViewMatrix;
    public Vector2f position;

    public Camera(Vector2f position){
        this.position = position;

        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.invProjectionMatrix = new Matrix4f();
        this.invViewMatrix = new Matrix4f();

        adjustProjection();
    }

    public void adjustProjection(){
        projectionMatrix.identity();

        projectionMatrix.ortho(0f, 32f*40f, 0f, 32f*21f, 0f, 100f);
        projectionMatrix.invert(invProjectionMatrix);
    }

    public Matrix4f getViewMatrix(){
        Vector3f cameraFront = new Vector3f(0, 0, -1);
        Vector3f cameraUp = new Vector3f(0, 1, 0);
        
        viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(position.x, position.y, 20f), 
                                            cameraFront.add(position.x, position.y, 0),
                                            cameraUp);
        viewMatrix.invert(invViewMatrix);
        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix(){
        return projectionMatrix;
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
