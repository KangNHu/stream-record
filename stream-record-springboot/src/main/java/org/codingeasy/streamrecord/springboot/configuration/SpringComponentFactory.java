package org.codingeasy.streamrecord.springboot.configuration;

import org.codingeasy.streamrecord.core.ComponentFactory;
import org.codingeasy.streamrecord.core.annotation.Record;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.Assert;

/**
* 基于Spring ioc容器的组件工厂  
* @author : KangNing Hu
*/
public class SpringComponentFactory  implements ComponentFactory, BeanFactoryAware {

	// spring ioc 容器
	private DefaultListableBeanFactory beanFactory;

	@Override
	public Object createComponent(Class clazz) {
		if (clazz == Record.Void.class){
			return new Record.Void();
		}
		String[] beans = beanFactory.getBeanNamesForType(clazz);
		Assert.notEmpty(beans , String.format("没有找到类型为%s的组件" , clazz.getTypeName()));
		return beanFactory.getBean(beans[0] , clazz);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;
		Assert.notNull(beanFactory , "没有找到spring 容器");
	}
}
