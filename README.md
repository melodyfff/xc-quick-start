# Getting Started

单体架构项目快速开发脚手架

### Guides

The following guides illustrate how to use some features concretely:

* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

#### 更新清单
- 全局异常处理
- 自定义注解校验参数
- 支持国际化
- 针对`webmvc`的单元测试
- 序列化统一为Gson
- 增加Mybatis支持
- 增加HBase支持

#### 国际化部分
请求中通过`Header`中的 `Accept-Language` 改变语言, 观察`resources/i18n`,以及 `application.yml`中的配置以实现国际化
```html
Accept-Language: en-US,en;q=0.9
Accept-Language: zh-CN,zh;q=0.9
```
### Reference Documentation

