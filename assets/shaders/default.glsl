#type vertex
#version 330 core
layout (location=0) in vec3 aPosition;
layout (location=1) in vec2 aUV;

uniform mat4 uProjection;
uniform mat4 uView;

out vec2 fTexCoords;

void main()
{
    fTexCoords = aUV;
    gl_Position = uProjection * uView * vec4(aPosition, 1.0);
}

#type fragment
#version 330 core

uniform float uTime;
uniform sampler2D TEX_SAMPLER;

in vec2 fTexCoords;

out vec4 color;

void main()
{
    
    color = texture(TEX_SAMPLER, vec2(0.005 + 0.01*floor(fTexCoords.x/0.01), 0.005 + 0.01*floor(fTexCoords.y/0.01)));
    
}