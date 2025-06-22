#ifndef RAY_H
#define RAY_H

#include "vec3.h"

typedef vec3 point3;

typedef struct {
   point3 origin;
   vec3 direction;
} ray;

point3 point_at(ray *r, double t) {
   vec3 scaled_dir = mul(&r->direction, t);
   return add(&r->origin, &scaled_dir);
}

#endif  // RAY_H
