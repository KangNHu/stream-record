package org.codingeasy.streamrecord.examples.expression;

import com.alibaba.fastjson.JSONObject;
import org.codingeasy.streamrecord.springboot.annotation.EnableStreamRecord;
import org.codingeasy.streamrecord.core.CurrentContext;
import org.codingeasy.streamrecord.core.DefaultRecordProducer;
import org.codingeasy.streamrecord.core.RecordProducer;
import org.codingeasy.streamrecord.core.Pipeline;
import org.codingeasy.streamrecord.core.matedata.RecordInfoWrapper;
import org.codingeasy.streamrecord.examples.pojo.User;
import org.codingeasy.streamrecord.examples.service.UserService;
import org.codingeasy.streamrecord.examples.service.impl.UserServiceImpl;
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
@EnableStreamRecord
public class SimpleExpressionExample {



	//自定义一个全局的默认管道
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
		};
	}


	//自定义一个全局日志生成器
	@Bean
	public RecordProducer producer(){
		return new DefaultRecordProducer() {
			@Override
			public RecordInfoWrapper doProduce(CurrentContext currentContext) {
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
