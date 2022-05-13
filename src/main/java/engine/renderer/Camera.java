package engine.renderer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.components.Component;

public class Camera extends Component{
    private Matrix4f projectionMatrix, viewMatrix;
    public Vector2f position;

    public Camera(Vector2f position){
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();

        adjustProjection();
    }

    public void adjustProjection(){
        projectionMatrix.identity();

        projectionMatrix.ortho(0f, 32f*40f, 0f, 32f*21f, 0f, 100f);
    }

    public Matrix4f getViewMatrix(){
        Vector3f cameraFront = new Vector3f(0, 0, -1);
        Vector3f cameraUp = new Vector3f(0, 1, 0);
        
        viewMatrix.identity();
        this.viewMatrix = viewMatrix.lookAt(new Vector3f(position.x, position.y, 20f), 
                                            cameraFront.add(position.x, position.y, 0),
                                            cameraUp);
        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix(){
        return projectionMatrix;
    }
}
