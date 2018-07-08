# AOP

## 术语

**advice**
切面是做什么的。定义一个切面的what和when。即切面执行什么任务和什么时候执行

- Before: 方法调用之前执行
- After: 方法执行完成后执行，无论方法执行结果是什么
- After-returning: 方法成功执行后执行
- After-throwing: 方法抛出异常后执行
- Around: 在方法前和方法后执行

**pointcuts**
切面插入应用执行的点

可以是方法的调用、异常抛出、属性修改等

**join points**
定义一个切面的where

一个join points可以匹配多个pointcuts

**aspectj**
组合advice和pointcuts

**introduction**
在一个已经存在的class中添加新的方法或属性

**Weaving**
为应用切面目标对象创建一个新的代理对象的过程

执行时机:
- Compile time: 在目标对象编译时执行，要求要有特殊的编译器。AspectJ的weaving是这种方式
- Class load time: 在目标对象载入到JVM中，要求要有特定的ClassLoader在应用引用目标类之前修改目标类
    的字节码。AspectJ5的load-time weaving(LTW)支持这种方式
- Runtime: 应用执行期间。典型是AOP容器动态生产代理对象。Spring AOP是这种方式

## Spring AOP实现方式
- 基于代理的经典Spring AOP;
- 纯POJO切面;
- @AspectJ注解驱动的切面;
- 注入式AspectJ切面(适用于Spring各版本)

> Spring AOP构建在动态代理基础之上,因此,Spring对AOP的支持局限于方法拦截

1. Spring通知是Java编写的
2. Spring在运行时通知对象
3. Spring只支持方法级别的连接点

**AspectJ指示器**
- arg(): 限制连接点匹配参数为指定类型的执行方法
- @args(): 限制连接点匹配参数有指定注解标注的执行方法
- execution(): 用于匹配是连接点的执行方法
- this(): 限制连接点匹配AOP代理的bean引用为指定类型的类
- target: 限制连接点匹配目标对象为指定类型的类
- @target(): 限制连接点匹配特定的执行对象，这些对象对应的类要具有指定类型的注解
- within(): 限制连接点匹配指定的类型
- @within(): 限制连接点匹配指定注解所标注的类型(当使用Spring AOP时，方法定义在由指定的注解所标注的类里)
- @annotation: 限制匹配带有指定注解的连接点
- bean(): 使用bean的ID来标识bean

**连接点关系**
&& and
|| or
! not

```java
// 当perform方法执行时执行通知
execution(* com.lilei.Performance.perform(..))
execution: 在方法执行时触发
*: 返回任意类型
(..): 使用任意参数

execution(* concert.Performance.perform(..)) && within(concert.*)
```