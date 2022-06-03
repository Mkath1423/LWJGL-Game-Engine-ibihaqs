package engine.renderer;

import java.util.ArrayList;
import java.util.List;

import engine.components.Renderable;

/**
 * A layer of renderables
 * 
 * @author Lex Stapleton
 */
public class Layer {

    private List<RenderBatch> batches;

    /**
     * Adds a renderable to the batches
     * 
     * @param renderable the renderable to add
     */
    protected void addRenderable(Renderable renderable){
        // if any batch can add this renderable then add it
        for (RenderBatch batch : batches) {
            if(batch.canAddRenderable(renderable)){
                batch.addRenderable(renderable);
                return;
            }
        }
        
        // otherwise, make a new batch and add the renderable to it
        RenderBatch rb = new RenderBatch(renderable.getShader(), renderable.getVAOFormat(), renderable.getEBOFormat());
            rb.start();
            rb.addRenderable(renderable);
            batches.add(rb);
            
    }

    public Layer(){
        batches = new ArrayList<RenderBatch>();
    }

    /**
     * Draws all the renderables in its batches
     */
    public void draw() {
        for (RenderBatch renderBatch : batches) {
            renderBatch.render();
        }
    }
}