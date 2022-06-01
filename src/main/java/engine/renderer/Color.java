package engine.renderer;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Color {
    private Vector4f color;

    public float getRed(){
        return color.x / 255;
    }

    public float getGreen(){
        return color.y / 255;
    }

    public float getBlue(){
        return color.z / 255;
    }

    public float getAlpha(){
        return color.w / 255;
    }

    public Color(Vector4f color){
        this.color = color;
    }

    public Color(float red, float green, float blue, float alpha){
        this(new Vector4f(red, green, blue, alpha));
    }

    public Color(){
        this(new Vector4f());
    }

    public Color(Color copy){
        this(new Vector4f(copy.color));
    }

    public Color CreateHSVColor(float hue, float saturation, float value){
        hue        = Math.max(0, Math.min(hue, 360));
        saturation = Math.max(0, Math.min(saturation, 1));
        value      = Math.max(0, Math.min(value, 1));


        float C = value * saturation;

        float X = C * (1 - Math.abs((hue/60) % 2) - 1);

        float m = value - C;

        Vector3f prime = new Vector3f();
        
        if      (  0 <= hue && hue < 60)  prime = new Vector3f(C, X, 0);
        else if ( 60 <= hue && hue < 120) prime = new Vector3f(X, C, 0);
        else if (120 <= hue && hue < 180) prime = new Vector3f(0, C, X);
        else if (180 <= hue && hue < 240) prime = new Vector3f(0, X, C);
        else if (240 <= hue && hue < 300) prime = new Vector3f(X, 0, C);
        else if (300 <= hue && hue < 360) prime = new Vector3f(C, 0, X);

        return new Color(new Vector4f(
            (prime.x + m) * 255,
            (prime.y + m) * 255,
            (prime.z + m) * 255,
            1
        ));
    }

}
