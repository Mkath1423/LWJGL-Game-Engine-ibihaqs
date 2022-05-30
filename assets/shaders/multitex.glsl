#type vertex
#version 330 core
layout (location=0) in vec3 aPosition;
layout (location=1) in vec2 aUV;
layout (location=2) in float aTexID;

uniform mat4 uProjection;
uniform mat4 uView;

out vec2 fTexCoords;
out float fTexID;

void main()
{
    fTexCoords = aUV;
    fTexID = aTexID;
    gl_Position = uProjection * uView * vec4(aPosition, 1);
    
}

#type fragment
#version 330 core

uniform float uTime;
uniform sampler2D[8] uTextures;

in vec2 fTexCoords;
in float fTexID;

out vec4 color;

void main()
{
    int id = int(fTexID);
    color = texture(uTextures[id], fTexCoords);
    
}