package components;

import renderer.VAO;
import renderer.VAO.Attribute;;

public abstract class Renderable extends Component{
    
    public int shaderProgram;

    public Renderable(int shaderProgram){
        this.shaderProgram = shaderProgram;
    }

    public abstract void loadData(float[] buffer);

    public void draw(){
        
    }
}
