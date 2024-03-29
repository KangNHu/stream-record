package org.codingeasy.streamrecord.core.processor;

import org.codingeasy.streamrecord.core.CurrentContext;

/**
 * 处理策略
 *
 * @author : KangNing Hu
 */
public class ProcessorStrategy {


  /**
   * 表达式策略名称
   */
  public static final String EXPRESSION_NAME = "expression";

  /**
   * 路由实现策略名称
   */
  public static final String ROUTE_NAME = "route";

  /**
   * 表达式实现
   */
  public static final ProcessorStrategy EXPRESSION = new ProcessorStrategy(EXPRESSION_NAME,
      new ExpressionProcessor());

  /**
   * 路由方式实现
   */
  public static final ProcessorStrategy ROUTE = new ProcessorStrategy(ROUTE_NAME,
      new MethodRouteProcessor());


  /**
   * 策略名称
   */
  private String name;


  /**
   * 处理器
   */
  private Processor processor;


  public ProcessorStrategy(String name, Processor processor) {
    this.name = name;
    this.processor = processor;
  }


  /**
   * 记录处理
   */
  public void process(CurrentContext currentContext) {
    this.processor.process(currentContext);
  }


  public String getName() {
    return name;
  }

  public Processor getProcessor() {
    return processor;
  }
}
