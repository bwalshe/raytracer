#ifndef VEC3_H
#define VEC3_H

#include <stdbool.h>
#include <stdlib.h>

#define UNIT_RAND() (((double)rand())/RAND_MAX)
#define UNIFORM_RAND(MIN, MAX) (MIN + UNIT_RAND()*(MAX-MIN))

typedef struct {
  double x;
  double y;
  double z;
} vec3;


double dot(vec3 *u, vec3 *v);
vec3 add(vec3 *u, vec3 *v);
vec3 sub(vec3 *u, vec3 *v);
vec3 cross(vec3 *u, vec3 *v);
vec3 mul(vec3 *v, double c);
vec3 div_vec(vec3 *v, double c);
double length_sq(vec3 *v);
double length(vec3 *v);
vec3 normal_vec(vec3 *v);
vec3 random_vec(double min, double max);
vec3 random_unit_vec();
vec3 random_on_hemisphere(vec3 *normal);
vec3 reflect(vec3 *v, vec3 *n);
bool near_zero(vec3 *v);
#endif  // VEC3_H
