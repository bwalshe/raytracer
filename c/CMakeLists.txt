cmake_minimum_required(VERSION 3.11)

project(Raytracing)

add_subdirectory(cunit/CUnit)
add_executable(draw_scene src/draw_scene.c 
                          src/ray.c
			  src/color.c
                          src/hittable.c
			  src/material.c
                          src/vec3.c)
target_link_libraries(draw_scene PRIVATE m)
target_compile_features(draw_scene PUBLIC c_std_17)

add_executable(test_vec3 tests/test_vec3.c src/vec3.c)
target_include_directories(test_vec3 PRIVATE src)
target_link_libraries(test_vec3 PRIVATE m cunit)
target_compile_features(test_vec3 PUBLIC c_std_17)
