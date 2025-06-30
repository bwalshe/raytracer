#include <math.h>
#include "color.h"

static double clamp(double x) {
  if(x < 0.0) return 0.0;
  if(x > 0.999) return 0.999;
  return x;
}

static double linear_to_gamma(double linear_component) {
  if(linear_component > 0)
    return sqrt(linear_component);
  return 0;
}

void write_color(FILE *out, color *pixel_color) {
  double r = linear_to_gamma(pixel_color->x);
  double g = linear_to_gamma(pixel_color->y);
  double b = linear_to_gamma(pixel_color->z);


  int rbyte = 256 * clamp(r);
  int gbyte = 256 * clamp(g);
  int bbyte = 256 * clamp(b);

  fprintf(out, "%d %d %d\n", rbyte, gbyte, bbyte);
}
