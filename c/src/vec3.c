#include <math.h>
#include "vec3.h"

double dot(vec3 *u, vec3 *v) { 
  return u->x * v->x + 
         u->y * v->y + 
         u->z * v->z;
}

vec3 add(vec3 *u, vec3 *v) {
  vec3 new_vec = {u->x + v->x, u->y + v->y, u->z + v->z};
  return new_vec;
}

vec3 sub(vec3 *u, vec3 *v) {
  return (vec3) {u->x - v->x, u->y - v->y, u->z - v->z};
}

vec3 mul(vec3 *v, double c) {
  vec3 new_vec = {v->x * c, v->y * c, v->z * c};
  return new_vec;
}

vec3 div_vec(vec3 *v, double c) {
  vec3 new_vec = {v->x / c, v->y / c, v->z / c};
  return new_vec;
}

vec3 cross(vec3 *u, vec3 *v) {
  vec3 new_vec;
  new_vec.x = u->y * v->z - u->z * v->y;
  new_vec.y = u->x * v->z - u->z * v->x;
  new_vec.z = u->x * v->y - u->y * v->x;
  return new_vec;
}

double length_sq(vec3 *v) {
  return v->x * v->x +
         v->y * v->y +
         v->z * v->z;
}

double length(vec3 *v) {
  return sqrt(length_sq(v));
}

vec3 normal_vec(vec3 *v) {
  return div_vec(v, length(v));
}

vec3 random_vec(double min, double max) {
  return (vec3) {UNIFORM_RAND(min, max), UNIFORM_RAND(min, max), UNIFORM_RAND(min, max)};
}

vec3 random_unit_vec() {
  for(;;) {
    vec3 v = random_vec(-1,1);
    double lensq = length_sq(&v);
    if(1e-160 < lensq && lensq <= 1)
      return div_vec(&v, sqrt(lensq));
  }
}


vec3 random_on_hemisphere(vec3 *normal) {
    vec3 on_unit_sphere = random_unit_vec();
    if (dot(&on_unit_sphere, normal) > 0.0)
        return on_unit_sphere;
    else
        return mul(&on_unit_sphere, -1.0);
}

vec3 reflect(vec3 *v, vec3 *n) {
  vec3 delta = mul(n, 2 * dot(v, n));
  return add(v, &delta);
}

bool near_zero(vec3 *v) {
  double epsilon = 1-8;
  return fabs(v->x) < epsilon && 
         fabs(v->y) < epsilon &&
         fabs(v->z) < epsilon;
}
