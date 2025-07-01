#ifndef MATERIAL_H
#define MATERIAL_H

#include <stdbool.h>

#include "color.h"
#include "hittable.h"
#include "ray.h"

enum material_type { LAMBERTIAN, METAL };

typedef struct {
  enum material_type type;
  color albedo;
} material;

bool scatter(material *the_material, ray *r_in, hit_record *rec, color *attenuation, ray *scattered);


#endif // MATERIAL_H
