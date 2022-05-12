package renderer;

import java.util.List;

import components.Renderable;

public class Layer {
    private List<Renderable> renderables;

    private List<RenderBatch> batches;

    public void addRenderable(Renderable renderable){
        renderables.add(renderable);

        for (RenderBatch batch : batches) {
            // if the batch is using the same shader and vao
            // if the batch is not full

            // add this to the batch

            // otherwise

            // make a new batch with this format 
            // add this to the new batch
        }
    }

    public void removeRenderable(Renderable renderable) {
        renderables.remove(renderable);
    }
}
