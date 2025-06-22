#include <stdio.h>

#include "color.h"
#include "ray.h"
#include "vec3.h"


#define DIV(A, B) ((double)A) / (B)

color ray_color(ray *r) {

    vec3 unit_direction = unit_vec(&r->direction);
    double a = 0.5*(unit_direction.y + 1.0);
    color gray = {1.0, 1.0, 1.0};
    gray = mul(&gray, 1.0 - a);
    color blue = {0.5, 0.7, 1.0};
    blue = mul(&blue, a);
    return add(&blue, &gray);
}

int main() {

  double aspect_ratio = 16.0/9.0;
  int image_width = 400;
  int image_height = image_width / aspect_ratio;
  image_height = (image_height < 1) ? 1 : image_height;

  double focal_length = 1.0;
  double viewport_height = 2.0;
  double viewport_width = viewport_height * DIV(image_width, image_height);
  point3 camera_center = {0, 0, 0};

  vec3 viewport_u = {viewport_width, 0, 0};
  vec3 viewport_v = {0, -viewport_height, 0};

  vec3 pixel_delta_u = div_vec(&viewport_u, image_width);
  vec3 pixel_delta_v = div_vec(&viewport_v, image_height);

  point3 viewport_upper_left = camera_center;
  viewport_upper_left = sub(&viewport_upper_left, &(point3){0, 0, focal_length});
  point3 delta = mul(&viewport_u, 0.5);
  viewport_upper_left = sub(&viewport_upper_left, &delta);
  delta = mul(&viewport_v, 0.5);
  viewport_upper_left = sub(&viewport_upper_left, &delta);

  delta = add(&pixel_delta_u, &pixel_delta_v);
  delta = mul(&delta, 0.5);
  point3 pixel00_loc = add(&viewport_upper_left, &delta);

  printf("P3\n%d %d\n255\n", image_width, image_height);
  for (int j = 0; j < image_height; j++) {
    for (int i = 0; i < image_width; i++) {
      vec3 u = mul(&pixel_delta_u, i);
      vec3 v = mul(&pixel_delta_v, j);
      point3 pixel_center = pixel00_loc;
      pixel_center = add(&pixel_center, &u);
      pixel_center = add(&pixel_center, &v);
      ray r;
      r.origin = camera_center;
      r.direction = sub(&pixel_center, &camera_center);

      color c = ray_color(&r);
      write_color(stdout, &c);
    }
  }
}
