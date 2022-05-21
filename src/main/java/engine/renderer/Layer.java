package engine.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import engine.components.Renderable;

public class Layer {
    private Map<String, List<Renderable>> renderables;

    public Layer(){
        renderables = new HashMap<String, List<Renderable>>();
    }

    protected void addRenderable(Renderable r){
        System.out.println("attempting to add new " + r.renderableType+" to layer");
        for(Entry<String, List<Renderable>> e : renderables.entrySet()){
            if(e.getKey().equals(r.renderableType)){
                e.getValue().add(r);
                return;
            }
        }
        
        System.out.println("detected new type, making new catagory");
        List<Renderable> toAdd = new ArrayList<>();
            toAdd.add(r);

        renderables.put(r.renderableType, toAdd);
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
    }

    public void draw() {
        for(Entry<String, List<Renderable>> e : renderables.entrySet()){
            if(e.getValue().size() <= 0) continue;

            Renderer.enable( e.getValue().get(0));
            
            e.getValue().get(0).UploadUniforms();
            e.getValue().get(0).render(e.getValue());

            Renderer.disable( e.getValue().get(0));
        }
    }
}
