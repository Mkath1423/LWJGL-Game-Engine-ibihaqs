package engine.renderer;

public class BufferIDs {

    public int vaoID;
    public int vboID;
    public int eboID;

    public BufferIDs(int vaoID, int vboID, int eboID){
        this.vaoID = vaoID;
        this.vboID = vboID;
        this.eboID = eboID;
    }

    public String toString(){
        return String.format("BufferIDs(vao:%s, vbo:%s, ebo:%s)", vaoID, vboID, eboID);
    }
}