package engine.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.html.parser.Entity;

import engine.components.Renderable;

public class Layer {
    private List<RenderBatch> batches;
    private Map<String, List<Renderable>> renderables;


    public Layer(){
        batches = new ArrayList<RenderBatch>();
        renderables = new HashMap<String, List<Renderable>>();
    }

    protected void addRenderable(Renderable r){
        for(Entry<String, List<Renderable>> e : renderables.entrySet()){
            if(e.getKey().equals(r.getClass().toString())){
                e.getValue().add(r);
                return;
            }
        }

        List<Renderable> toAdd = new ArrayList<>();
            toAdd.add(r);

        renderables.put(r.getClass().toString(), toAdd);
        Renderer.initializeBuffers(r);

        for (RenderBatch batch : batches) {
            // if the batch is using the same shader and vao
            // if the batch is not full
            if(batch.canAddRenderable(r) && batch.hasRoom()){
                
                // add this to the batch
                batch.addRenderable(r);
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
        for(Entry<String, List<Renderable>> e : renderables.entrySet()){
            if(e.getKey().equals(r.getClass().toString())){
                if(e.getValue().contains(r)){
                    e.getValue().remove(r);
                    return;
                }
            }
        }

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
        for(Entry<String, List<Renderable>> e : renderables.entrySet()){
            if(e.getValue().size() <= 0) continue;

            Renderer.enable( e.getValue().get(0));
            
            e.getValue().get(0).uploadUniforms();
            e.getValue().get(0).render(e.getValue());

            Renderer.disable( e.getValue().get(0));
        }

        for (RenderBatch renderBatch : batches) {
            renderBatch.render();
        }
    }
}
