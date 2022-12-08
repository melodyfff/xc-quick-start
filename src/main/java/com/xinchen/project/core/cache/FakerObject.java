package com.xinchen.project.core.cache;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/12/4 19:08
 */
class FakerObject {

  private String id;
  private String key;

  public String getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id id
   * @return this
   */
  public FakerObject setId(String id) {
    this.id = id;
    return this;
  }

  public String getKey() {
    return key;
  }

  /**
   * Sets the key.
   *
   * @param key key
   * @return this
   */
  public FakerObject setKey(String key) {
    this.key = key;
    return this;
  }
}
