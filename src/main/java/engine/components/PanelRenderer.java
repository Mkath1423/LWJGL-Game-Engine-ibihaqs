package engine.components;

import java.util.List;
import java.util.stream.BaseStream;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.AssetManager;
import engine.renderer.EBO;
import engine.renderer.Shader;
import engine.renderer.Texture;
import engine.renderer.VAO;

public class PanelRenderer extends Renderable{

    public int leftBoarder;
    public int rightBoarder;
    public int topBoarder;
    public int bottomBoarder;

    Texture baseTexture = AssetManager.getTexture("filepath");

    Transform t;

    float[] xOffsets, yOffsets;

    public PanelRenderer(Texture baseTexture, int leftBoarder, int rightBoarder, int topBoarder, int bottomBoarder){
        // TODO: changes these
        super(Shader.SPRITE_RGB, VAO.SPRITE, EBO.PANEL);

        this.leftBoarder = leftBoarder;
        this.rightBoarder = rightBoarder;
        this.topBoarder = topBoarder;
        this.bottomBoarder = bottomBoarder;

        
    }

    public void Awake(){
        t = gameObject.getComponent(Transform.class);

        if(t == null) return;

        xOffsets = new float[]{
            0, leftBoarder, rightBoarder, t.getQuad().getWidth()
        };

        yOffsets = new float[]{
            0, leftBoarder, rightBoarder, t.getQuad().getWidth()
        };
    }

    @Override
    public void loadVertexData(float[] buffer, int start) {
        try {
            Vector3f topLeft = t.getQuad().topLeft;

            for(int x = 0; x < 4; x ++){
                for(int y = 0; y < 4; y ++){
                    buffer[start + vao.vaoSize * (x + y*4) + 0] = topLeft.x + xOffsets[x];
                    buffer[start + vao.vaoSize * (x + y*4) + 1] = topLeft.y + yOffsets[y];
                    buffer[start + vao.vaoSize * (x + y*4) + 2] = topLeft.z;

                    buffer[start + vao.vaoSize * (x + y*4) + 3] = baseTexture.toNDCWidth(xOffsets[x]); 
                    buffer[start + vao.vaoSize * (x + y*4) + 4] = baseTexture.toNDCHeight(yOffsets[y]);
                }
            }


            
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void UploadUniforms() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(List<Renderable> value) {
        // TODO Auto-generated method stub
        
    }
    
}
