#include <stdio.h>

#include "material.h"


bool scatter(material *the_material, ray *r_in, hit_record *rec, color *attenuation, ray *scattered) {
  if(the_material->type == LAMBERTIAN) {
    vec3 delta = random_unit_vec();
    vec3 scatter_direction = add(&rec->normal, &delta);

    if(near_zero(&scatter_direction))
       scatter_direction = rec->normal;

    *scattered = (ray) {rec->p, scatter_direction};
    *attenuation = the_material->albedo;
    return true;
  }
  else if(the_material->type == METAL) {
    vec3 reflected = reflect(&r_in->direction, &rec->normal);
    *scattered = (ray) {rec->p, reflected};
    *attenuation = the_material->albedo;
    return true;
  }
  else {
    fprintf(stderr, "Unknown matiral type found, exiting\n");
    exit(1);
  }
}
