/**
 * <pre>
 * @Document 标识了要持久化到mongodb的DO。
 * @Id 文档的唯一标识，在mongodb中是objectId
 * @Indexed 标识当前字段需要添加索引，添加索引后的字段，在进行查询操作的时候会提高速度，unique = true设置为唯一索引
 * @CompoundIndex 标识一个联合索引
 * @Field 对当前字段的额外内容进行定义，主要是用来定义集合中字段实际名称
 * @Transient 标识此字段为java属性而非mongodb字段
 * @DbRef 关联另一个Document对象，存入的是文档的引用，如果不使用这个注解，存入的是内容。不过即使使用@DbRef，mongodb本身并不维护关联数据，也就是说需要手动将数据插入到被关联文档。
 * </pre>
 */
package com.xinchen.project.core.orm.mongo;