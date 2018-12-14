#version 110

uniform mat3 transform;

void main(void) {
  gl_Position = vec4(transform * gl_Vertex.xyz, 1.0);
}
