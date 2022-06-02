package engine.renderer;

import java.util.ArrayList;
import java.util.List;

import engine.components.Renderable;

public class Layer {

    private List<RenderBatch> batches;

    protected void addRenderable(Renderable renderable){
        for (RenderBatch batch : batches) {
            if(batch.canAddRenderable(renderable)){
                batch.addRenderable(renderable);
                return;
            }
        }
        
        RenderBatch rb = new RenderBatch(renderable.getShader(), renderable.getVAOFormat(), renderable.getEBOFormat());
            rb.start();

            rb.addRenderable(renderable);
            batches.add(rb);
            
    }

    protected void removeRenderable(Renderable r) {
        // renderables.remove(r);
    }

    public Layer(){
        batches = new ArrayList<RenderBatch>();
    }

    public void draw() {
        // System.out.println("number of renderables: " + renderables.size());
        // List<RenderBatch> batches = new ArrayList<>();

        // batchingLoop:
        // for (Renderable renderable : renderables) {
        //     for (RenderBatch batch : batches) {
        //         if(batch.addRenderable(renderable)) continue batchingLoop;
        //     }
            
        //     RenderBatch rb = new RenderBatch(renderable.getShader(), renderable.getVAOFormat(), renderable.getEBOFormat());
        //         rb.addRenderable(renderable);
        //         batches.add(rb);
                
        //         rb.start();
        // }
        for (RenderBatch renderBatch : batches) {
            renderBatch.render();
        }
    }
}