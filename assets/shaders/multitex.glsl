#type vertex
#version 330 core
layout (location=0) in vec3 aPosition;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aUV;
layout (location=3) in float aTexID;
layout (location=4) in float aSettings;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out vec2 fTexCoords;
out float fTexID;
out float fSettings;

void main()
{
    fColor = aColor;
    fTexCoords = aUV;
    fTexID = aTexID;
    fSettings = aSettings;

    bool useView = mod(aSettings, 4) >= 2;

    if(useView){
        gl_Position = uProjection * uView * vec4(aPosition, 1);
    }
    else{
        gl_Position = uProjection * vec4(aPosition, 1);
    }
    
}

#type fragment
#version 330 core

uniform float uTime;
uniform sampler2D[8] uTextures;

in vec4 fColor;
in vec2 fTexCoords;
in float fTexID;
in float fSettings;

out vec4 color;

void main()
{
    bool useColor = mod(fSettings, 2) == 1;
    bool doMasking = mod(fSettings, 8) >= 4;

    if(!useColor && doMasking){
        int id = int(fTexID);
        color.w = texture(uTextures[id], fTexCoords).w;
        color.xyz = fColor.xyz;
        return;
    } 

    if(useColor){
        color = fColor;
        return;
    }
    
    int id = int(fTexID);
    color = texture(uTextures[id], fTexCoords);
    
}