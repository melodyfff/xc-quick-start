package com.xinchen.project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 *
 * SpringBoot基础之MockMvc单元测试 ： https://blog.csdn.net/wo541075754/article/details/88983708
 *
 *
 *
 * 1、mockMvc.perform执行一个请求。
 * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
 * 3、ResultActions.param添加请求传值
 * 4、ResultActions.accept(MediaType.TEXT_HTML_VALUE))设置返回类型
 * 5、ResultActions.andExpect添加执行完成后的断言。
 * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
 *   比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
 * 7、ResultActions.andReturn表示执行完成后返回相应的结果。
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/8/14 13:14
 */
@SpringBootTest
@AutoConfigureMockMvc
public class WebTests {

  @Autowired
  private MockMvc mvc;

  @Test
  void ok() throws Exception {
    mvc.perform(
            get("/hello?name=")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content("{}")

    )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code").value(400)) //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
            .andDo(print())
        .andReturn();
  }

  @Test
  void okValidate() throws Exception {
    mvc.perform(
                    post("/hello/validate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            // 设置国际化语言 中文： zh-CN,zh;q=0.9 英文：en-US,en;q=0.9
                            .header(HttpHeaders.ACCEPT_LANGUAGE,"en-US,en;q=0.9")
                            .content("{\"num\":1}")

            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code").value(10001))
            // 这里可以会乱序
            .andExpect(jsonPath("$.message").value("wrong phone format;must be greater than or equal to 10;"))
//            .andDo(print())
            .andReturn();

    mvc.perform(
                    post("/hello/validate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"num\":100,\"mobile\":\"13342312456\"}")

            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").value("成功"))
//            .andDo(print())
            .andReturn();
  }

  @Test
  void okSync() throws Exception {
    // 异步测试
    MvcResult mvcResult = mvc.perform(get("/hello/async"))
            .andExpect(request().asyncStarted())
            .andDo(log())
            .andReturn();

    mvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(0))
            .andDo(print());

  }
}
