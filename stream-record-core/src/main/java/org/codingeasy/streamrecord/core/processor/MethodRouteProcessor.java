package org.codingeasy.streamrecord.core.processor;

import org.codingeasy.streamrecord.core.*;
import org.codingeasy.streamrecord.core.matedata.RecordDefinition;
import org.codingeasy.streamrecord.core.matedata.RecordDefinitionBuilder;
import org.codingeasy.streamrecord.core.matedata.RecordInfo;
import org.codingeasy.streamrecord.core.matedata.DefaultRecordDefinition;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static org.codingeasy.streamrecord.core.matedata.RecordDefinitionBuilder.*;

/**
 * 基于方法路由的处理策略
 *
 * @author : KangNing Hu
 */
public class MethodRouteProcessor implements Processor {


  @Override
  public void process(CurrentContext currentContext) {
    //获取路由的方法
    DefaultRecordDefinition recordDefinition = (DefaultRecordDefinition) currentContext
        .getRecordDefinition();
    //进行调用
    invokeRouteMethod(recordDefinition, currentContext);
  }


  /**
   * 路由目标方法调用
   *
   * @param recordDefinition 记录定义
   * @param currentContext 当前执行上下文
   */
  private void invokeRouteMethod(DefaultRecordDefinition recordDefinition,
      CurrentContext currentContext) {
    InterceptMethodWrapper interceptMethodWrapper = currentContext.getInterceptMethodWrapper();
    Method routeMethod = (Method) recordDefinition.get(ROUTE_METHOD);
    Class[] paramTypes = (Class[]) recordDefinition.get(ROUTE_METHOD_PARAM_TYPES);
    int length = paramTypes.length;
    Object[] params = new Object[length];
    for (int i = 0; i < length; i++) {
      Class<?> paramType = paramTypes[i];
      if (paramType.isAssignableFrom(InterceptMethodWrapper.class)) {
        params[i] = currentContext.getInterceptMethodWrapper();
      } else if (paramType.isAssignableFrom(AttributeAccess.class)) {
        params[i] = currentContext.getAttributeAccess();
      } else {
        params[i] = interceptMethodWrapper.get(i);
      }
    }

    Object value = ReflectionUtils
        .invokeMethod(routeMethod, getRouteTarget(currentContext), params);
    //如何返回的值不为空者作为记录信息存储
    if (value != null) {
      AttributeAccess attributeAccess = currentContext.getAttributeAccess();
      attributeAccess.put(RecordInfo.MASSAGE_ATTR, value.toString());
    }
  }


  /**
   * 获取路由的目标对象
   *
   * @param currentContext 当前记录上下文
   * @return 路由的目标对象
   */
  public Object getRouteTarget(CurrentContext currentContext) {
    DefaultRecordDefinition recordDefinition = (DefaultRecordDefinition) currentContext
        .getRecordDefinition();
    RecordContext recordContext = (RecordContext) currentContext.getRecordDefinitionRegistry();
    Class routeTargetClass = recordDefinition.getAttribute(ROUTE_TARGET);
    if (routeTargetClass == null) {
      throw new IllegalStateException("路由目标未指定");
    }
    return recordContext.createComponent(routeTargetClass);
  }

}