package org.codingeasy.streamrecord.core;

/**
 * 组件工厂
 *
 * @author : KangNing Hu
 */
public interface ComponentFactory {

  /**
   * 创建组件
   */
  Object createComponent(Class clazz);
}
