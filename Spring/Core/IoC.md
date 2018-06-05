# Inversion of Control (IoC) container

控制反转容器。IOC也叫做dependency injection (DI)

org.springframework.beans 和 org.springframework.context 包是IOC的基础

BeanFactory 接口提供一种能管理任何类型对象的高级配置机制

ApplicationContext 是BeanFactory的子接口，增加了易与AOP集成的功能

WebApplicationContext web 应用，消息资源处理、事件发布

被IOC容器管理的应用对象叫做Beans

bean是一个实例化，组装并由Spring IoC容器管理的对象

org.springframework.context.ApplicationContext接口代表IOC容器，实例化、组装、管理Bean

IOC容器通过读取配置信息来实例化、组装、管理Bean。

配置信息可以来自三个方面：
- XML文件
- 基于Java注解：从Spring2.5开始
- 基于Java代码：从Spring3.0开始，使用@Configuration, @Bean, @Import and @DependsOn注解

 ClassPathXmlApplicationContext 或 FileSystemXmlApplicationContext 读取外部XML文件

## XML配置Bean

使用<bean/>定义bean,它的顶级是<beans/>。
基于Java的配置典型的是使用对于一个使用@Configuration注解的类，在其中方法上使用@Bean定义Bean

### 定义Bean

```xml
<bean id="petStore" class="org.springframework.samples.jpetstore.services.PetStoreServiceImpl">
    <property name="accountDao" ref="accountDao"/>
    <property name="itemDao" ref="itemDao"/>
    <!-- additional collaborators and configuration for this bean go here -->
</bean>
```

id: 定义bean的唯一身份标识
class: bean类型，使用类的全限定名
name: JavaBean属性名称
ref: 依赖的bean，使用bean的唯一身份标识

## 初始化容器

```java
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");

// 变体
GenericApplicationContext context = new GenericApplicationContext();
new XmlBeanDefinitionReader(context).loadBeanDefinitions("services.xml", "daos.xml");
context.refresh();
```



从其它xml文件导入bean
```xml
<beans>
    <import resource="services.xml"/>
    <import resource="resources/messageSource.xml"/>
    <import resource="/resources/themeSource.xml"/>

    <bean id="bean1" class="..."/>
    <bean id="bean2" class="..."/>
</beans>
```
> 不推荐使用../导入额外bean定义。除了相对路径还可以使用绝对路径file:C:/config/services.xml或classpath:/config/services.xml

## Groovy定义Bean

```groovy
beans {
    dataSource(BasicDataSource) {
        driverClassName = "org.hsqldb.jdbcDriver"
        url = "jdbc:hsqldb:mem:grailsDB"
        username = "sa"
        password = ""
        settings = [mynew:"setting"]
    }
    sessionFactory(SessionFactory) {
        dataSource = dataSource
    }
    myService(MyService) {
        nestedBean = { 
            AnotherBean bean -> dataSource = dataSource
        }
    }
}
```
## Bean

容器内部使用BeanDefinition定义Bean

包含以下配置源: 
- 类全限定名
- Bean行为(scope、lifecycle、callbacks等)
- 定义依赖的Bean
- 其它设置,如连接池数量等

class: 实例化beans
name: 命名beans
scope: bean 范围
constructor arguments: 依赖注入
properties: 依赖注入
autowiring mode: 自动装配
lazy-initialization mode: 懒加载
initialization method: 初始化回调
destruction method: 销毁回调

name:
每个Bean都可以有一个或多个身份标识.这身份标识在容器中必须是唯一的。一个bean通常
只有一个身份标识，如果要有超过一个，这其它的被认为的别名

多个别名之间使用, ; 空格进行分割


