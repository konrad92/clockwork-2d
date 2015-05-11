#ifdef GL_ES
#define LOWP lowp
    precision mediump float;
#else
    #define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float u_ticks;
uniform float u_strength;

void main()
{
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords + vec2(
		u_strength * sin(u_ticks + v_texCoords.s * 100.f),
		u_strength * cos(u_ticks + v_texCoords.t * -150.f)
	));
}