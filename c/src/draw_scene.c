#include <float.h>
#include <stdio.h>

#include "color.h"
#include "hittable.h"
#include "material.h"
#include "ray.h"
#include "vec3.h"

#define DIV(A, B) ((double)A) / (B)

typedef struct {
  double aspect_ratio; // Ratio of image width over height
  int image_width;     // Rendered image width in pixel count
  int image_height;    // Rendered image height
  int samples_per_pixel;
  int max_depth;
  point3 center;      // Camera center
  point3 pixel00_loc; // Location of pixel 0, 0
  vec3 pixel_delta_u; // Offset to pixel to the right
  vec3 pixel_delta_v; // Offset to pixel below
} camera;

void initialise_camera(camera *the_camera, int image_width, double aspect_ratio,
                       int samples_per_pixel) {
  the_camera->image_width = image_width;
  the_camera->aspect_ratio = aspect_ratio;
  the_camera->samples_per_pixel = samples_per_pixel;
  the_camera->max_depth = 10;

  int image_height = image_width / aspect_ratio;
  the_camera->image_height = (image_height < 1) ? 1 : image_height;

  double focal_length = 1.0;
  double viewport_height = 2.0;
  double viewport_width = viewport_height * DIV(image_width, image_height);
  the_camera->center = (point3){0.0, 0.0, 0.0};

  vec3 viewport_u = {viewport_width, 0, 0};
  vec3 viewport_v = {0, -viewport_height, 0};

  the_camera->pixel_delta_u = div_vec(&viewport_u, image_width);
  the_camera->pixel_delta_v = div_vec(&viewport_v, image_height);

  point3 viewport_upper_left = the_camera->center;

  viewport_upper_left =
      sub(&viewport_upper_left, &(point3){0, 0, focal_length});
  point3 delta = mul(&viewport_u, 0.5);
  viewport_upper_left = sub(&viewport_upper_left, &delta);
  delta = mul(&viewport_v, 0.5);
  viewport_upper_left = sub(&viewport_upper_left, &delta);

  delta = add(&the_camera->pixel_delta_u, &the_camera->pixel_delta_v);
  delta = mul(&delta, 0.5);
  the_camera->pixel00_loc = add(&viewport_upper_left, &delta);
}

color ray_color(ray *r, int depth, world *the_world) {
  if (depth <= 0)
    return (color){0.0, 0.0, 0.0};
  hit_record rec;
  if (hit_world(the_world, r, 0.001, DBL_MAX, &rec)) {
    ray scattered;
    color attenuation;
    if (scatter(rec.mat, r, &rec, &attenuation, &scattered)) {
      color c = ray_color(&scattered, depth - 1, the_world);
      c.x *= attenuation.x;
      c.y *= attenuation.y;
      c.z *= attenuation.z;
      return c;
    }
    return (color){0.0, 0.0, 0.0};
  }

  vec3 unit_direction = normal_vec(&r->direction);
  double a = 0.5 * (unit_direction.y + 1.0);
  color gray = {1.0, 1.0, 1.0};
  gray = mul(&gray, 1.0 - a);
  color blue = {0.5, 0.7, 1.0};
  blue = mul(&blue, a);
  return add(&blue, &gray);
}

ray get_ray(camera *the_camera, int x, int y) {
  double jittered_x = x + UNIT_RAND();
  double jittered_y = y + UNIT_RAND();
  vec3 u_trans = mul(&the_camera->pixel_delta_u, jittered_x);
  vec3 v_trans = mul(&the_camera->pixel_delta_v, jittered_y);

  point3 pixel_uv = the_camera->pixel00_loc;
  pixel_uv = add(&pixel_uv, &u_trans);
  pixel_uv = add(&pixel_uv, &v_trans);

  vec3 ray_origin = the_camera->center;
  ray output = {ray_origin, pixel_uv};
  return output;
}

void render(camera *the_camera, world *the_world, FILE *out) {

  fprintf(out, "P3\n%d %d\n255\n", the_camera->image_width,
          the_camera->image_height);
  for (int j = 0; j < the_camera->image_height; ++j) {
    for (int i = 0; i < the_camera->image_width; ++i) {

      color c = {0.0, 0.0, 0.0};
      for (int s = 0; s < the_camera->samples_per_pixel; ++s) {
        ray r = get_ray(the_camera, i, j);
        color sample = ray_color(&r, the_camera->max_depth, the_world);
        c = add(&c, &sample);
      }
      c = div_vec(&c, the_camera->samples_per_pixel);
      write_color(out, &c);
    }
  }
}

int main() {

  camera the_camera;
  initialise_camera(&the_camera, 400, 16.0 / 9.0, 10);

  world the_world;
  init_world(&the_world, 4);

  material ground = {LAMBERTIAN, {0.8, 0.8, 0.0}};
  material center = {LAMBERTIAN, {0.1, 0.2, 0.5}};
  material left = {METAL, {0.8, 0.8, 0.8}};
  material right = {METAL, {0.8, 0.6, 0.2}};

  add_sphere(&the_world, 0.0, -100.5, -1, 100, &ground);
  add_sphere(&the_world, 0.0, 0.0, -1.2, 0.5, &center);
  add_sphere(&the_world, -1.0, 0.0, -1.0, 0.5, &left);
  add_sphere(&the_world, 1.0, 0.0, -1.0, 0.5, &right);

  render(&the_camera, &the_world, stdout);

  free_world(&the_world);
}
