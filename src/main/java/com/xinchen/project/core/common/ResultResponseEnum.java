package com.xinchen.project.core.common;

/**
 * 交互结果枚举类
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/8/14 12:47
 */
public enum ResultResponseEnum implements ResultResponse {
  /**
   * Success result enum.
   */
  SUCCESS(0,"成功"),
  /**
   * Fail result enum.
   */
  FAIL(-1,"失败"),
  VALIDATE_FAILED(10001,"参数校验失败")
  ;

  private final Integer code;

  private final String message;

  ResultResponseEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public Integer getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
