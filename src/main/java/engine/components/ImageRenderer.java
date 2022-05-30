package engine.components;

import org.joml.Vector3f;

import engine.renderer.SpriteMap;
import engine.renderer.Texture;

public class ImageRenderer extends QuadRenderable{

    private SpriteMap spriteMap;
    private int currentSprite;

    private Transform transform;

    private int texSlot = 0;


    public ImageRenderer(SpriteMap spriteMap){
        super(4, 0);

        this.spriteMap = spriteMap;
    }

    @Override
    public void Awake(){
        transform = gameObject.getComponent(Transform.class);
        System.out.println("Sprite Renderer: Transform is... " + transform);
        super.Awake();
    }

    @Override
    public void loadVertexData(float[] buffer, int start) {

        Vector3f[] vertices = transform.getQuad().getVertices();
        Vector3f[] uvVertices = spriteMap.getSprite(currentSprite).uvCoordinates.getVertices();
        
        for(int i = 0; i < 4; i ++){
            buffer[start + 7 * i + 0] = vertices[i].x;
            buffer[start + 7 * i + 1] = vertices[i].y;
            buffer[start + 7 * i + 2] = vertices[i].z;

            buffer[start + 7 * i + 3] = uvVertices[i].x; 
            buffer[start + 7 * i + 4] = uvVertices[i].y;
            
            buffer[start + 7 * i + 5] = texSlot;
            buffer[start + 7 * i + 6] = 0;  
        }
    }

    @Override
    public Texture getTexture() {
        return spriteMap.texture;
    }
}
