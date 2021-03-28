package org.dream.tool.auditlog.processor;

import org.dream.tool.auditlog.CurrentContext;
import org.dream.tool.auditlog.AttributeAccess;
import org.dream.tool.auditlog.matedata.AuditLogInfo;

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
		attributeAccess.put(AuditLogInfo.MASSAGE_ATTR , templateResolve.doResolve(currentContext));
	}
}
