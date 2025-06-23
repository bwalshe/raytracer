#ifndef RAY_H
#define RAY_H

#include "vec3.h"

typedef vec3 point3;

typedef struct {
   point3 origin;
   vec3 direction;
} ray;

point3 point_at(ray *r, double t);

#endif  // RAY_H
