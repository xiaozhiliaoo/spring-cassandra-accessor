# 为什么需要spring-cassandra-accessor?

spring-cassandra-accessor基于[DataStax Java Driver 3.7](https://docs.datastax.com/en/developer/java-driver/3.7/index.html) 进行封装的。

[accessor文档](https://docs.datastax.com/en/developer/java-driver/3.7/manual/object_mapper/using/index.html#accessors)

之前使用accessor时候方式如下：

```java

@Accessor
public interface UserAccessor {
    @Query("SELECT * FROM user")
    Result<User> getAll();
}
UserAccessor userAccessor = manager.createAccessor(UserAccessor.class);
Result<User> users = userAccessor.getAll();
```

但是每次使用时候，都这样会很麻烦，因为很多重复代码，而且不方便管理，于是想着使用时候如下：

```java
@Accessor
public interface UserAccessor {
    @Query("SELECT * FROM user")
    Result<User> getAll();
}

@Autowired
private UserAccessor userAccessor;
```

那么就需要使用Spring能力将带有@Accessor注解的接口扫描进Bean容器中，并且需要带有自动配置功能，所以设计了如下方案（参考mybatis-spring）：

<img width="1143" alt="cassandra-accessor" src="https://github.com/user-attachments/assets/21bc4897-d6bc-4220-aad9-5f2f0db459ea">



# 思路

1. 第一步：AccessorFactoryBean（手动配置）
2. 第二步：AccessorScannerConfigurer（手动配置）
3. 第三步：AccessorScannerRegistrar（注解配置）

