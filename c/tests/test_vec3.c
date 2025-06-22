#include <CUnit/CUnitCI.h>


#include <vec3.h>


#define ASSERT_APPROX_EQUAL(ACTUAL, EXPECTED)\
   CU_ASSERT_DOUBLE_EQUAL(ACTUAL, EXPECTED, 1e-6)

#define ASSERT_VEC3_EQUAL(V, X, Y, Z)\
  ASSERT_APPROX_EQUAL(V.x, X)\
  ASSERT_APPROX_EQUAL(V.y, Y)\
  ASSERT_APPROX_EQUAL(V.z, Z)


/* Test that we can instantiate the struct and it has the right fields */
static void test_vec3_struct(void) {
  vec3 v = {1.0, 2.0, 3.0};
  ASSERT_APPROX_EQUAL(v.x, 1.0);
  ASSERT_APPROX_EQUAL(v.y, 2.0);
  ASSERT_APPROX_EQUAL(v.z, 3.0);
}

static void test_vec3_dot(void) {
  vec3 x = {1.0, 0.0, 0.0};
  vec3 y = {0.0, 1.0, 0.0};
  vec3 z = {0.0, 0.0, 1.0};

  ASSERT_APPROX_EQUAL(dot(&x, &y), 0.0);
  ASSERT_APPROX_EQUAL(dot(&x, &z), 0.0);
  ASSERT_APPROX_EQUAL(dot(&z, &y), 0.0);
  ASSERT_APPROX_EQUAL(dot(&x, &x), 1.0);
  ASSERT_APPROX_EQUAL(dot(&y, &y), 1.0);
  ASSERT_APPROX_EQUAL(dot(&z, &z), 1.0);
}

static void test_vec3_add(void) {
  vec3 x = {1.0, 0.0, 0.0};
  vec3 y = {0.0, 1.0, 0.0};
  vec3 z = {0.0, 0.0, 1.0};

  ASSERT_VEC3_EQUAL(add(&x, &y), 1.0, 1.0, 0.0);
  ASSERT_VEC3_EQUAL(add(&x, &z), 1.0, 0.0, 1.0);
  ASSERT_VEC3_EQUAL(add(&y, &z), 0.0, 1.0, 1.0);
}

static void test_vec3_mul(void) {
  vec3 x = {1.0, 0.0, 0.0};
  vec3 y = {0.0, 1.0, 0.0};
  vec3 z = {0.0, 0.0, 1.0};
  
  ASSERT_VEC3_EQUAL(mul(&x, 2.0), 2.0, 0.0, 0.0);
  ASSERT_VEC3_EQUAL(mul(&y, 2.0), 0.0, 2.0, 0.0);
  ASSERT_VEC3_EQUAL(mul(&z, 2.0), 0.0, 0.0, 2.0);
}

static void test_vec3_cross(void) {
  vec3 x = {1.0, 0.0, 0.0};
  vec3 y = {0.0, 1.0, 0.0};
  vec3 z = {0.0, 0.0, 1.0};
  
  ASSERT_VEC3_EQUAL(cross(&x, &y), 0.0, 0.0, 1.0);
  ASSERT_VEC3_EQUAL(cross(&y, &x), 0.0, 0.0, -1.0);
  ASSERT_VEC3_EQUAL(cross(&x, &z), 0.0, 1.0, 0.0);
  ASSERT_VEC3_EQUAL(cross(&z, &x), 0.0, -1.0, 0.0);
  ASSERT_VEC3_EQUAL(cross(&y, &z), 1.0, 0.0, 0.0);
  ASSERT_VEC3_EQUAL(cross(&z, &y), -1.0, 0.0, 0.0);
}

static void test_vec3_length_sq(void) {
  vec3 x = {2.0, 0.0, 0.0};
  vec3 y = {0.0, 2.0, 0.0};
  vec3 z = {0.0, 0.0, 2.0};
  vec3 v = {1.0, 1.0, 1.0};
  
  ASSERT_APPROX_EQUAL(length_sq(&x), 4.0);
  ASSERT_APPROX_EQUAL(length_sq(&y), 4.0);
  ASSERT_APPROX_EQUAL(length_sq(&z), 4.0);

  ASSERT_APPROX_EQUAL(length_sq(&v), 3.0);
}

static void test_vec3_length(void) {
  vec3 x = {2.0, 0.0, 0.0};
  vec3 y = {0.0, 2.0, 0.0};
  vec3 z = {0.0, 0.0, 2.0};
  vec3 v = {1.0, 2.0, 2.0};
  
  ASSERT_APPROX_EQUAL(length(&x), 2.0);
  ASSERT_APPROX_EQUAL(length(&y), 2.0);
  ASSERT_APPROX_EQUAL(length(&z), 2.0);

  ASSERT_APPROX_EQUAL(length(&v), 3.0);
}



CUNIT_CI_RUN("my-suite", CUNIT_CI_TEST(test_vec3_struct),
             CUNIT_CI_TEST(test_vec3_dot),
             CUNIT_CI_TEST(test_vec3_add),
             CUNIT_CI_TEST(test_vec3_mul),
             CUNIT_CI_TEST(test_vec3_cross),
             CUNIT_CI_TEST(test_vec3_length_sq),
             CUNIT_CI_TEST(test_vec3_length)
             );
