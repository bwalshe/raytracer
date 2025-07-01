#ifndef HITTABLE_H
#define HITTABLE_H

#include <malloc.h>
#include <stdbool.h>

#include "ray.h"

typedef struct {
  point3 p;
  vec3 normal;
  void *mat;
  double t;
  bool front_face;
} hit_record;

typedef struct {
  point3 center;
  double radius;
  void *mat;
} sphere;

typedef struct {
  sphere *spheres;
  int sphere_count;
  int max_count;
} world;

void init_world(world *the_world, int world_size);

void free_world(world *the_world);

int add_sphere(world *the_world, double x, double y, double z, double r, void *mat);

bool hit_world(world *the_world, ray *r, double ray_tmin, double ray_tmax,
               hit_record *rec);

void set_face_normal(hit_record *rec, ray *r, vec3 *outward_normal);

bool hit_sphere(sphere *obj, ray *r, double ray_tmin, double ray_tmax,
                hit_record *rec);

#endif // HITTABLE
