package engine.renderer;

import java.util.ArrayList;
import java.util.List;

import engine.components.Renderable;

public class Layer {
    private List<RenderBatch> batches;


    public Layer(){
        batches = new ArrayList<RenderBatch>();
    }

    protected void addRenderable(Renderable r){
        System.out.println("Layer: Adding renderable");
        for (RenderBatch batch : batches) {
            // if the batch is using the same shader and vao
            // if the batch is not full
            if(batch.canAddRenderable(r) && batch.hasRoom()){
                
                // add this to the batch
                batch.addRenderable(r);
                System.out.println("Layer: Adding renderable to existing batch");
                return;
            }
        }
        
        System.out.println("Layer: Adding renderable to new batch");
        RenderBatch rb = new RenderBatch(Renderer.maxBatchSize, r.getShader(), r.getVAO(), r.getEBO());
            rb.addRenderable(r);
            batches.add(rb);
            
            rb.start();
    }

    protected void removeRenderable(Renderable r) {
        // for (RenderBatch batch : batches) {
        //     // if the batch is using the same shader and vao
        //     // if the batch is not full
        //     batch.removeRenderable(renderable);
        //     if(batch.canAddRenderable(r)){
        //         // add this to the batch
        //         batch.addRenderable(r);
        //         return;
        //     }
        // }
    }

    public void draw() {
        for (RenderBatch renderBatch : batches) {
            renderBatch.render();
        }
    }
}
