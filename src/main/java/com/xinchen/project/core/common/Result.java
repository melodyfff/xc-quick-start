package com.xinchen.project.core.common;

import static com.xinchen.project.core.common.ResultResponseEnum.FAIL;
import static com.xinchen.project.core.common.ResultResponseEnum.SUCCESS;

import java.io.Serializable;

/**
 * The type Result.
 *
 * @param <T> the type parameter
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/8/14 12:52
 */
public class Result<T> implements Serializable {

  private Integer code;
  private String message;
  private T data;

  /**
   * Instantiates a new Result.
   *
   * @param code    the code
   * @param message the message
   * @param data    the data
   */
  public Result(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }


  /**
   * Success result.
   *
   * @param <T>  the type parameter
   * @param data the data
   * @return the result
   */
  public static <T> Result<T> success(T data){
    return new Result<>(SUCCESS.getCode(),SUCCESS.getMessage() , data);
  }

  /**
   * Success result.
   *
   * @param <T>          the type parameter
   * @param responseEnum the response enum
   * @param data         the data
   * @return the result
   */
  public static <T> Result<T> success(ResultResponseEnum responseEnum,T data){
    return new Result<>(responseEnum.getCode(),responseEnum.getMessage() , data);
  }

  /**
   * Success result.
   *
   * @param <T>     the type parameter
   * @param message the message
   * @param data    the data
   * @return the result
   */
  public static <T> Result<T> success(String message,T data){
    return new Result<>(SUCCESS.getCode(),message , data);
  }

  /**
   * Fail result.
   *
   * @param <T>     the type parameter
   * @param message the message
   * @return the result
   */
  public static <T> Result<T> fail(String message){
    return new Result<>(FAIL.getCode(), message, null);
  }

  /**
   * Fail result.
   *
   * @param <T>  the type parameter
   * @param data the data
   * @return the result
   */
  public static <T> Result<T> fail(T data){
    return new Result<>(FAIL.getCode(),FAIL.getMessage() , data);
  }

  /**
   * Fail result.
   *
   * @param <T>     the type parameter
   * @param message the message
   * @param data    the data
   * @return the result
   */
  public static <T> Result<T> fail(String message,T data){
    return new Result<>(FAIL.getCode(),message , data);
  }

  public static <T> Result<T> fail(Integer code,String message){
    return new Result<>(code,message , null);
  }
  /**
   * Fail result.
   *
   * @param <T>          the type parameter
   * @param responseEnum the response enum
   * @return the result
   */
  public static <T> Result<T> fail(ResultResponseEnum responseEnum){
    return new Result<>(responseEnum.getCode(),responseEnum.getMessage() , null);
  }

  /**
   * New result result.
   *
   * @param <T>     the type parameter
   * @param code    the code
   * @param message the message
   * @param data    the data
   * @return the result
   */
  public static <T> Result<T> newResult(Integer code, String message, T data){
    return new Result<>(code, message, data);
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
