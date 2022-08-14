package com.xinchen.project.core.common;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/8/14 14:12
 */
public class SystemException extends RuntimeException{

  private Integer code;

  public SystemException(Integer code) {
    this.code = code;
  }

  public SystemException(String message, Integer code) {
    super(message);
    this.code = code;
  }

  public SystemException(String message, Throwable cause, Integer code) {
    super(message, cause);
    this.code = code;
  }

  public SystemException(Throwable cause, Integer code) {
    super(cause);
    this.code = code;
  }

  public SystemException(String message, Throwable cause, boolean enableSuppression,
                         boolean writableStackTrace, Integer code) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.code = code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public Integer getCode() {
    return code;
  }
}
