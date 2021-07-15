package org.codingeasy.streamrecord.springboot.configuration;

import org.codingeasy.streamrecord.core.annotation.RecordService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

import java.util.List;

/**
*    注册 {@link RecordService} 标注的类 到spring 中
 *    @see RecordService
* @author : KangNing Hu
*/
public class RecordServiceBeanDefinitionRegister   implements ApplicationContextAware ,
		BeanDefinitionRegistryPostProcessor , Ordered {


	private ApplicationContext applicationContext;

	/**
	 * 注册 {@link RecordService} 标注的类 到spring 中
	 * @see RecordService
	 * @param beanDefinitionRegistry bean 定义注册器 默认实现为{@link org.springframework.beans.factory.support.DefaultListableBeanFactory}
	 * @throws BeansException bean 的异常
	 */
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
		//获取spring boot的包路径
		List<String> packages = AutoConfigurationPackages.get(this.applicationContext);
		RecordServiceDefinitionScanner scanner = new RecordServiceDefinitionScanner(beanDefinitionRegistry);
		scanner.scan(packages.toArray(new String[]{}));
	}




	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}



	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
