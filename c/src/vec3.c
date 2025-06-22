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

vec3 unit_vec(vec3 *v) {
  return div_vec(v, length(v));
}
