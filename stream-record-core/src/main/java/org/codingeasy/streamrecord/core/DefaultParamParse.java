package org.codingeasy.streamrecord.core;

import org.codingeasy.streamrecord.core.matedata.ParamNode;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * 默认参数解析器
 *
 * @author : KangNing Hu
 */
public class DefaultParamParse implements ParamParse {

  @Override
  public void parse(CurrentContext currentContext) {
    //获取属性存储器
    AttributeAccess attributeAccess = currentContext.getAttributeAccess();
    //获取参数元数据
    List<ParamNode>[] params = currentContext.getRecordDefinition().getParams();
    //获取方法运行时入参数
    InterceptMethodWrapper interceptMethodWrapper = currentContext.getInterceptMethodWrapper();
    for (int i = 0, length = params.length; i < length; i++) {
      Object paramValue = interceptMethodWrapper.get(i);
      List<ParamNode> paramNodes = params[i];
      if (!CollectionUtils.isEmpty(paramNodes)) {
        //解析参数
        for (ParamNode paramNode : paramNodes) {
          attributeAccess.putAll(paramNode.parseParam(paramValue));
        }
      }
    }
  }
}
