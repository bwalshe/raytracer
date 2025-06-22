#ifndef VEC3_H
#define VEC3_H

typedef struct {
  double x;
  double y;
  double z;
} vec3;

double dot(vec3 *u, vec3 *v);
vec3 add(vec3 *u, vec3 *v);
vec3 cross(vec3 *u, vec3 *v);
vec3 mul(vec3 *v, double c);
double length_sq(vec3 *v);
double length(vec3 *v);
#endif  // VEC3_H
