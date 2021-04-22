package org.easy.boot.log.log.examples.expression;

import com.alibaba.fastjson.JSONObject;
import org.easy.boot.log.configuration.EnableLog;
import org.easy.boot.log.log.core.CurrentContext;
import org.easy.boot.log.log.core.DefaultLogProducer;
import org.easy.boot.log.log.core.LogProducer;
import org.easy.boot.log.log.core.Pipeline;
import org.easy.boot.log.log.core.matedata.LogInfoWrapper;
import org.easy.boot.log.log.examples.pojo.User;
import org.easy.boot.log.log.examples.service.UserService;
import org.easy.boot.log.log.examples.service.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.stream.Collectors;

/**
* 简单的列子  
* @author : KangNing Hu
*/
@SpringBootApplication
@EnableLog
public class SimpleExpressionExample {



	//自定义一个全局的默认管道
	@Bean
	public Pipeline pipeline(){
		return auditLogInfoWrapper -> {
			//如果是多个结果
			if (auditLogInfoWrapper.isMultiple()){
				System.out.printf("管道中已有多个结果:%s" , JSONObject.toJSONString(auditLogInfoWrapper.getLogInfos()));
			}
			//如果只有一个结果
			else {
				System.out.printf("管道中已有多个结果:%s" , JSONObject.toJSONString(auditLogInfoWrapper.getLogInfo()));
			}
		};
	}


	//自定义一个全局日志生成器
	@Bean
	public LogProducer producer(){
		return new DefaultLogProducer() {
			@Override
			public LogInfoWrapper doProduce(CurrentContext currentContext) {
				try {
					System.out.printf("日志的属性参数 %s" ,currentContext.getAttributeAccess()
							.entrySet().stream().map(item -> item.getKey() + ":" + item.getValue()).collect(Collectors.joining(",")));
				}catch (Exception e){
					e.printStackTrace();
				}
				return super.doProduce(currentContext);
			}
		};
	}



	@Bean
	public UserService userService(){
		return new UserServiceImpl();
	}




	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(SimpleExpressionExample.class, args);
		UserService userService = configurableApplicationContext.getBean(UserService.class);
		User user = new User();
		user.setName("小玩");
		userService.simple(user);
		Thread.sleep(10000L);
		configurableApplicationContext.close();
	}




}
