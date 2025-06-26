#include "color.h"

static double clamp(double x) {
  if(x < 0.0) return 0.0;
  if(x > 1.0) return 1.0;
  return x;
}

void write_color(FILE *out, color *pixel_color) {
  int r = 255.999 * clamp(pixel_color->x);
  int g = 255.999 * clamp(pixel_color->y);
  int b = 255.999 * clamp(pixel_color->z);

  fprintf(out, "%d %d %d\n", r, g, b);
}
