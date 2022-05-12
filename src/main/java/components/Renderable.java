package components;

import renderer.Shader;
import renderer.VAO;
import renderer.VAO.Attribute;;

public abstract class Renderable extends Component{
    
    // store the shader vao and ebo as array

    private Shader shader;
    private VAO vao;
    private int[] ebo;

    public abstract void loadVertexData(float[] buffer);

    public boolean isBatchable(Renderable that){
        return this.shader.getShaderId() == that.shader.getShaderId() &&
               this.vao.getID()          == that.vao.getID();
    }
}
