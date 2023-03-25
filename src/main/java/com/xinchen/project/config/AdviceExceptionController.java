package com.xinchen.project.config;

import java.util.concurrent.TimeoutException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import com.xinchen.project.core.common.ResultResponseEnum;
import com.xinchen.project.core.common.SystemException;
import com.xinchen.project.core.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理
 *
 * 参数校验异常处理 ：handleMethodArgumentNotValidException（） ，参考https://mp.weixin.qq.com/s/7-lt6v9_O0Hc0Pmnzz_uIQ
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/8/14 13:46
 */
@ControllerAdvice
public class AdviceExceptionController {

  private static final Logger log = LoggerFactory.getLogger(AdviceExceptionController.class);

  /**
   * Handle exception result.
   *
   * @param req the req
   * @param e   the e
   * @return the result
   * @throws Exception the exception
   */
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public Result<String> handleException(HttpServletRequest req, Exception e) {
    log.error("Global-Exception: ",e);
    return Result.fail(e.getMessage());
  }

  /**
   * Handle http media type not acceptable exception result.
   *
   * @param e   the e
   * @return the result
   * @throws HttpMediaTypeNotAcceptableException the exception
   */
  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  @ResponseBody
  public Result<String>  handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
    log.error("Global-HttpMediaTypeNotAcceptableException: ",e);
    return Result.fail(e.getMessage());
  }

  /**
   * Handle exception system result.
   *
   * @param e   the e
   * @return the result
   * @throws SystemException the exception
   */
  @ExceptionHandler(SystemException.class)
  @ResponseBody
  public Result<String>  handleExceptionSystem(SystemException e) {
    log.error("Global-ExceptionSystem: ",e);
    return Result.fail(e.getCode(),e.getMessage());
  }

  /**
   * 自定义解析参数校验失败异常
   * {@code @RequestBody} 参数校验不通过时抛出的异常处理
   *
   * @param e   the e
   * @return the result
   * @throws MethodArgumentNotValidException the exception
   */
  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseBody
  public Result<String>  handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error("Global-MethodArgumentNotValidException: ",e);
    BindingResult bindingResult = e.getBindingResult();
    StringBuilder stringBuilder = new StringBuilder();

    for (ObjectError objectError : bindingResult.getAllErrors()) {
      stringBuilder.append(objectError.getDefaultMessage()).append(";");
    }
    return Result.fail(ResultResponseEnum.VALIDATE_FAILED.getCode(),stringBuilder.toString());
  }

  /**
   * 自定义解析参数校验失败异常
   * {@code @PathVariable} 和 {@code @RequestParam} 参数校验不通过时抛出的异常处理
   *
   * @param e   the e
   * @return the result
   * @throws ConstraintViolationException the exception
   */
  @ExceptionHandler({ConstraintViolationException.class})
  @ResponseBody
  public Result<String>  handleConstraintViolationException(ConstraintViolationException e) {
    log.error("Global-ConstraintViolationException: ",e);
    return Result.fail(ResultResponseEnum.VALIDATE_FAILED.getCode(),e.getMessage());
  }

  @ExceptionHandler({TimeoutException.class})
  @ResponseBody
  public Result<String>  handleTimeoutExceptionException(TimeoutException e) {
    log.error("Global-TimeoutException: ",e);
    return Result.fail(-1,"Task Run Timeout: "+e.getMessage());
  }
}
