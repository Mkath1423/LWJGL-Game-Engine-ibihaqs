package engine.renderer;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.components.QuadRenderable;
import engine.components.Renderable;
import engine.scenes.SceneManager;

public class QuadRenderBatch {
    List<QuadRenderable> renderables = new ArrayList<>();

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
        return amountTextures < QuadRenderer.MAX_TEXTURES;
    }

    public void add(QuadRenderable renderable){
        if(!canAdd(renderable)) return;
        // System.out.println("adding qr to batch");

        boolean allocateTexture = true;

        for (Texture tex : textures) {
            if(tex.getTexId() == renderable.getTexture().getTexId()) allocateTexture = false;            
        }

        renderables.add(renderable);
        amountVertices += renderable.numberVertices;

        if(allocateTexture){
            textures.add(renderable.getTexture());
            amountTextures ++;
        }

        renderable.texSlot = textures.indexOf(renderable.getTexture());
    }
}
