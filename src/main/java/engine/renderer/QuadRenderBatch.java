package engine.renderer;

import java.util.ArrayList;
import java.util.List;

import engine.components.QuadRenderable;

public class QuadRenderBatch {
    List<QuadRenderable> quads = new ArrayList<>();

    List<Texture> textures = new ArrayList<>();

    int amountVertices = 0;
    int amountTextures = 0;

    public boolean canAdd(QuadRenderable qr){
        // System.out.println("check if i can add this");
        // System.out.println("    checking v amount");
        if(amountVertices + qr.numberVertices > QuadRenderer.MAX_BATCH_SIZE  * 4) return false;

        // System.out.println("    checking t matches");
        for (Texture tex : textures) {
            if(tex.getTexId() == qr.getTexture().getTexId()) return true;            
        }

        // System.out.println("    checking t amount");
        return amountTextures < 8;
    }

    public void add(QuadRenderable qr){
        if(!canAdd(qr)) return;
        // System.out.println("adding qr to batch");

        boolean allocateTexture = true;

        for (Texture tex : textures) {
            if(tex.getTexId() == qr.getTexture().getTexId()) allocateTexture = false;            
        }

        quads.add(qr);
        amountVertices += qr.numberVertices;

        if(allocateTexture){
            textures.add(qr.getTexture());
            amountTextures ++;
        }
    }
}
