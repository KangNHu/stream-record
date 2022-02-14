- 支持spel表达式和注解的方式进行方法调用记录
- 支持方法路由的方式进行方法调用记录
- 支持全局和局部记录数的统一处理
- 支持同步和异步的方法调用记录
- 对spring boot进行了无缝的集成
- 支持用户自定义扩展

#### 使用说明

##### 快速开始

**启动类**

```java
@SpringBootApplication
@EnableStreamRecord
public class Application{
 
  public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args)
	}
}
```

**定义全局记录生成器**

```java
@Bean
public RecordProducer producer(){
  return new DefaultRecordProducer() {
    @Override
    public RecordInfoWrapper doProduce(CurrentContext currentContext) {
      System.out.println("调用自定义记录生成器")
      return super.doProduce(currentContext);
    }
  };
	}

```

**定义全局记录管道**

```java
@Bean
	public Pipeline pipeline(){
		return auditLogInfoWrapper -> {
			//如果是多个结果
			if (auditLogInfoWrapper.isMultiple()){
				System.out.printf("管道中已有多个结果:%s" , JSONObject.toJSONString(auditLogInfoWrapper.getRecordInfos()));
			}
			//如果只有一个结果
			else {
				System.out.printf("管道中已有多个结果:%s" , JSONObject.toJSONString(auditLogInfoWrapper.getRecordInfo()));
			}
      //对记录结果进行处理，如存到 db 或发到mq
      ...
		};
	}
```

**在自己的业务方法上添加注解**

```java
@Record("'这是一个简单的日志记录，用户的名称是：' + #user.name")
@Override
public void simple(@Search User user) {
	
}
```

##### 注解说明



1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
