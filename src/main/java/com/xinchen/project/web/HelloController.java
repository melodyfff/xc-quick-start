package com.xinchen.project.web;

import com.xinchen.project.core.common.SystemException;
import com.xinchen.project.core.common.validation.Mobile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * see {@link RequestResponseBodyMethodProcessor}
 *
 * 请求中通过添加Header: Accept-Language 改变语言, 观察resources/i18n,以及 application.yml中的配置以实现国际化
 * Accept-Language: en-US,en;q=0.9
 * Accept-Language: zh-CN,zh;q=0.9
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/8/14 13:09
 */
@RestController
@RequestMapping
public class HelloController {
  private static final Logger log = LoggerFactory.getLogger(HelloController.class);

  @GetMapping("/hello")
  public TestDto hello(@RequestParam("name") String name){

    if (!StringUtils.hasText(name)){
      throw new SystemException("测试失败",400);
    }

    return new TestDto(100);
  }

  @PostMapping("/hello/validate")
  public TestDto helloValidate(@RequestBody @Valid TestDto test){
    return test;
  }

  @GetMapping("/hello/async")
  public CompletableFuture<TestDto> helloAsync()  {
    // 异步执行 由于主线程要使用结果，所以主线程会阻塞
    return CompletableFuture.supplyAsync(()->{
      // 模拟异步功能执行业务
      try {
        TimeUnit.SECONDS.sleep(2);
        log.info(">>> mission completed!");
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      return new TestDto(200);
    });
  }

  static class TestDto{
    @Min(value = 10)
    private Integer num;
    @Mobile
    private String mobile;

    public TestDto(Integer num) {
      this.num = num;
    }

    public TestDto(Integer num, String moble) {
      this.num = num;
      this.mobile = moble;
    }

    public Integer getNum() {
      return num;
    }
    public void setNum(Integer num) {
      this.num = num;
    }

    public String getMobile() {
      return mobile;
    }

    public void setMobile(String mobile) {
      this.mobile = mobile;
    }
  }
}
