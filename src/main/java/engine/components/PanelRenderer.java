// package engine.components;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.stream.BaseStream;

// import org.joml.Vector2f;
// import org.joml.Vector3f;
// import org.lwjgl.opengl.GL20;

// import engine.AssetManager;
// import engine.renderer.EBOFormat;
// import engine.renderer.Renderer;
// import engine.renderer.Shader;
// import engine.renderer.SpriteMap;
// import engine.renderer.Texture;
// import engine.renderer.VAO;
// import engine.renderer.Renderer.RenderType;
// import engine.renderer.VAO.VAOFormat;
// import engine.scenes.SceneManager;

// public class PanelRenderer extends Renderable{

//     public int leftBoarder;
//     public int rightBoarder;
//     public int topBoarder;
//     public int bottomBoarder;

//     SpriteMap spriteMap;

//     Transform t;

//     float[] xOffsets, yOffsets;

//     public PanelRenderer(SpriteMap spriteMap, int leftBoarder, int rightBoarder, int topBoarder, int bottomBoarder){
//         super(Shader.SPRITE, VAOFormat.SPRITE, EBOFormat.QUAD, 9, 1);

//         this.spriteMap = spriteMap;

//         this.leftBoarder = leftBoarder;
//         this.rightBoarder = rightBoarder;
//         this.topBoarder = topBoarder;
//         this.bottomBoarder = bottomBoarder;
//     }

//     public void Awake(){
//         t = gameObject.getComponent(Transform.class);

//         if(t == null) return;

//         xOffsets = new float[]{
//             0, leftBoarder, rightBoarder, t.getQuad().getWidth()
//         };

//         yOffsets = new float[]{
//             0, leftBoarder, rightBoarder, t.getQuad().getWidth()
//         };
//     }

//     @Override
//     public void loadVertexData(float[] buffer, int start) {
        
//         Vector3f topLeft = t.getQuad().topLeft;

//         for(int x = 0; x < 4; x ++){
//             for(int y = 0; y < 4; y ++){
//                 buffer[start + 7 * (x + y*4) + 0] = topLeft.x + xOffsets[x];
//                 buffer[start + 7 * (x + y*4) + 1] = topLeft.y + yOffsets[y];
//                 buffer[start + 7 * (x + y*4) + 2] = topLeft.z;

//                 buffer[start + 7 * (x + y*4) + 3] = spriteMap.texture.toNDCWidth(xOffsets[x]); 
//                 buffer[start + 7 * (x + y*4) + 4] = spriteMap.texture.toNDCHeight(yOffsets[y]);

                
//                 buffer[start + 7 * (x + y*4) + 3] = texSlot; 
//                 buffer[start + 7 * (x + y*4) + 4] = 0;
//             }
//         }
//     }

//     @Override
//     public Texture getTexture() {
//         return spriteMap.texture;
//     }
    
// }
