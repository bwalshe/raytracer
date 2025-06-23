#include "ray.h"


point3 point_at(ray *r, double t) {
   vec3 scaled_dir = mul(&r->direction, t);
   return add(&r->origin, &scaled_dir);
}
