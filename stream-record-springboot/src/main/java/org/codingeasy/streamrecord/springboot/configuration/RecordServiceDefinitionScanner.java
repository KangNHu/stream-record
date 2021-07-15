package org.codingeasy.streamrecord.springboot.configuration;

import org.codingeasy.streamrecord.core.annotation.RecordService;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.List;

/**
* 记录service的扫描器
 * <p>用于被{@link RecordService}标注的类给spring 管理</p>
 * @see RecordService
* @author : KangNing Hu
*/
public class RecordServiceDefinitionScanner extends ClassPathBeanDefinitionScanner {


	public RecordServiceDefinitionScanner(BeanDefinitionRegistry definitionRegistry) {
		super(definitionRegistry);
		//重置spring 扫描注解
		resetFilters(false);
		//添加shiroFilter
		addIncludeFilter(new AnnotationTypeFilter(RecordService.class));
	}

}
