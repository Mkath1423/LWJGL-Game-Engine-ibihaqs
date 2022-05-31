package engine.renderer;

import java.util.ArrayList;
import java.util.List;

import engine.components.Renderable;

public class Layer {

    private List<Renderable> renderables;

    protected void addRenderable(Renderable r){
        renderables.add(r);
    }

    protected void removeRenderable(Renderable r) {
        renderables.remove(r);
    }

    public Layer(){
        renderables = new ArrayList<Renderable>();
    }

    public void draw() {
        System.out.println("number of renderables: " + renderables.size());
        List<RenderBatch> batches = new ArrayList<>();

        batchingLoop:
        for (Renderable renderable : renderables) {
            for (RenderBatch batch : batches) {
                if(batch.addRenderable(renderable)) continue batchingLoop;
            }
            
            RenderBatch rb = new RenderBatch(renderable.getShader(), renderable.getVAOFormat(), renderable.getEBOFormat());
                rb.addRenderable(renderable);
                batches.add(rb);
                
                rb.start();
        }
        System.out.println("number of batches: " + batches.size());
        for (RenderBatch renderBatch : batches) {
            renderBatch.render();
        }
    }
}