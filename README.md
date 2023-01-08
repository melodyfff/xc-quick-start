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
- 增加MongoDB支持

#### 国际化部分
请求中通过`Header`中的 `Accept-Language` 改变语言, 观察`resources/i18n`,以及 `application.yml`中的配置以实现国际化
```html
Accept-Language: en-US,en;q=0.9
Accept-Language: zh-CN,zh;q=0.9
```

#### Hbase部署测试
**Hbase启动**
```bash
# 注意16000为master默认端口，不放开则报错 Connection refused: no further information: localhost/127.0.0.1:16000
# 外部访问需要将该容器ID 添加到本地hosts映射： 127.0.0.1 505e3aeb3d42
docker run -d --name hbase -p 2181:2181 -p 16010:16010 -p 16020:16020 -p 16030:16030 -p 16000:16000 harisekhon/hbase
```

### Reference Documentation

#### Hbase
[Hbase 中文文档](http://www.hbase.org.cn/)  
[docker搭建hbase环境](https://blog.csdn.net/feinifi/article/details/121174846)  
[一文说清HBase Connection的使用](https://www.cnblogs.com/leojie/p/15023182.html)

#### MongoDB
**MongoDB部署**
```bash
docker run -d -p 27017:27017 --name mongodb mongo:4.2
```
[MongoDB教程](https://www.mongodb.org.cn/tutorial/)  
[Spring Data MongoDB](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)


