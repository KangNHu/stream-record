package org.easy.boot.log.log.core.processor;

import org.easy.boot.log.log.core.CurrentContext;
import org.easy.boot.log.log.core.AttributeAccess;
import org.easy.boot.log.log.core.matedata.LogInfo;

/**
* 基于表达式实现的处理器
 * @see
* @author : KangNing Hu
*/
public class ExpressionProcessor implements Processor {


	//模版解析器
	private TemplateResolve templateResolve;

	/**
	 * 设置模版解析器
	 * @param templateResolve 模版解析器
	 */
	public void setTemplateResolve(TemplateResolve templateResolve) {
		this.templateResolve = templateResolve;
	}

	@Override
	public void process(CurrentContext currentContext) {
		//获取上下文属性
		AttributeAccess attributeAccess = currentContext.getAttributeAccess();
		//设置日志消息
		attributeAccess.put(LogInfo.MASSAGE_ATTR , templateResolve.doResolve(currentContext));
	}
}
