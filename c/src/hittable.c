#include <math.h>
#include "hittable.h"
#include "vec3.h"
#include "ray.h"

void init_world(world *the_world, int world_size) {
  the_world->spheres = malloc(world_size * sizeof(sphere));
  the_world->max_count = world_size;
  the_world->sphere_count = 0;
}

void free_world(world *the_world) { free(the_world->spheres); }

int add_sphere(world *the_world, double x, double y, double z, double r) {
  if (the_world->sphere_count < the_world->max_count) {
    int i = the_world->sphere_count;
    the_world->spheres[i].center.x = x;
    the_world->spheres[i].center.y = y;
    the_world->spheres[i].center.z = z;
    the_world->spheres[i].radius = r;
    the_world->sphere_count += 1;
    return the_world->sphere_count;
  }
  return -1;
}

bool hit_world(world *the_world, ray *r, double ray_tmin, double ray_tmax,
               hit_record *rec) {
  hit_record temp_rec;
  bool hit_anything = false;
  double closest = ray_tmax;

  for (int i = 0; i < the_world->sphere_count; ++i) {
    if (hit_sphere(the_world->spheres + i, r, ray_tmin, closest, &temp_rec)) {
      hit_anything = true;
      closest = temp_rec.t;
      *rec = temp_rec;
    }
  }

  return hit_anything;
}

void set_face_normal(hit_record *rec, ray *r, vec3 *outward_normal) {
  rec->front_face = dot(&r->direction, outward_normal) < 0;
  rec->normal = rec->front_face ? *outward_normal : mul(outward_normal, -1);
}

bool hit_sphere(sphere *obj, ray *r, double ray_tmin, double ray_tmax,
                hit_record *rec) {
  vec3 oc = sub(&obj->center, &r->origin);
  double a = length_sq(&r->direction);
  double h = dot(&r->direction, &oc);
  double c = length_sq(&oc) - obj->radius * obj->radius;

  double discriminant = h * h - a * c;
  if (discriminant < 0)
    return false;

  double sqrtd = sqrt(discriminant);
  double root = (h - sqrtd) / a;
  if (root <= ray_tmin || ray_tmin <= root) {
    root = (h + sqrtd) / a;
    if (root <= ray_tmin || ray_tmax <= root)
      return false;
  }

  rec->t = root;
  rec->p = point_at(r, rec->t);
  vec3 outward_normal = sub(&rec->p, &obj->center);
  outward_normal = div_vec(&outward_normal, obj->radius);
  set_face_normal(rec, r, &outward_normal);

  return true;
}
