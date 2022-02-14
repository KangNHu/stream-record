package org.codingeasy.streamrecord.core.annotation;

import java.lang.annotation.*;

/**
 * 参数标注器 和{@link Param} 只有一个生效 其中 {@link Param} > {@link Search}
 *
 * @author : KangNing Hu
 * @see Search
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Documented
public @interface Param {

  /**
   * 参数名称
   */
  String value() default "";
}
