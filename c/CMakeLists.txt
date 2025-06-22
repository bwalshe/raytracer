cmake_minimum_required(VERSION 3.11)

project(Raytracing)

add_subdirectory(cunit/CUnit)
add_executable(make_ppm src/make_ppm.c src/vec3.c src/color.c)
target_link_libraries(make_ppm PRIVATE m)

add_executable(test_vec3 tests/test_vec3.c src/vec3.c)
target_include_directories(test_vec3 PRIVATE src)
target_link_libraries(test_vec3 PRIVATE m cunit)
