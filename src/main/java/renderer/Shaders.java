package renderer;

public enum Shaders {
    SPRITE_RGB("default.glsl");
    // SPRITE_RGBA,
    // COLOR,
    // SPRITE_TINT;

    private Shader shader;

    private Shaders(String filePath){
        shader = new Shader(filePath);
    }

    public int getShaderID(){
        return shader.getShaderId(); 
    }
}
