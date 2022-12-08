package com.xinchen.project.core.cache;

import com.xinchen.project.StartApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/12/4 19:05
 */
class FakerServiceTest extends StartApplicationTests {

  @Autowired
  private FakerService fakerService;
  @Test
  void noCacheTest() {
    fakerService.magicNoCache();
    fakerService.magicNoCache();
  }

  @Test
  void cacheQueryTest() {
    fakerService.magicQueryCache();
    fakerService.magicQueryCache();
  }
  @Test
  void cacheUpdateTest() {
    //
    fakerService.magicUpdateCache();
    fakerService.magicUpdateCache();
  }
}