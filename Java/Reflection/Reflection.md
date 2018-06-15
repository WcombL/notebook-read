# Reflection

反射为在运行时查看类、接口、属性、方法，而不是在编译时知道的类名、方法等。通过反射能够创建对象实例、调用方法、设置和获取属性值

反射在数组、注解、泛型、动态代理、动态类加载和重加载中的应用

## Java Reflection - Classes

**类信息**
- Class Name
- Class Modifies(public, private, synchronized etc.)
- Package Info
- Superclass
- Implemented Interfaces
- Constructors
- Methods
- Fields
- Annotations

**Class Name**
- getName: 类名(包含包名)
- getSimpleName: 类名(不包含包名)

**Class Modifies(public, private, synchronized etc.)**

```java
int modifiers = aClass.getModifiers();
Modifier.isAbstract(int modifiers)
Modifier.isFinal(int modifiers)
Modifier.isInterface(int modifiers)
Modifier.isNative(int modifiers)
Modifier.isPrivate(int modifiers)
Modifier.isProtected(int modifiers)
Modifier.isPublic(int modifiers)
Modifier.isStatic(int modifiers)
Modifier.isStrict(int modifiers)
Modifier.isSynchronized(int modifiers)
Modifier.isTransient(int modifiers)
Modifier.isVolatile(int modifiers)
```
**Package Info**
```java
Package package = aClass.getPackage();
```
**Superclass**
```java
Class superclass = aClass.getSuperclass();
```
**Implemented Interfaces**
```java
Class[] interfaces = aClass.getInterfaces();
```
> 只返回该类实现的接口，父类实现的接口不返回

**Constructors**
```java
Constructor[] constructors = aClass.getConstrustors();
```
**Methods**
**Fields**
**Annotations**


## 访问私有属性
```java
privateStringMethod.setAccessible(true);
```


## Generics

**泛型返回值**
```java
Method method = MyClass.class.getMethod("getStringList", null);

Type returnType = method.getGenericReturnType();

if(returnType instanceof ParameterizedType){
    ParameterizedType type = (ParameterizedType) returnType;
    Type[] typeArguments = type.getActualTypeArguments();
    for(Type typeArgument : typeArguments){
        Class typeArgClass = (Class) typeArgument;
        System.out.println("typeArgClass = " + typeArgClass);
    }
}
```

**泛型参数**
```java
method = Myclass.class.getMethod("setStringList", List.class);

Type[] genericParameterTypes = method.getGenericParameterTypes();

for(Type genericParameterType : genericParameterTypes){
    if(genericParameterType instanceof ParameterizedType){
        ParameterizedType aType = (ParameterizedType) genericParameterType;
        Type[] parameterArgTypes = aType.getActualTypeArguments();
        for(Type parameterArgType : parameterArgTypes){
            Class parameterArgClass = (Class) parameterArgType;
            System.out.println("parameterArgClass = " + parameterArgClass);
        }
    }
}
```

**泛型属性**
```java
Field field = MyClass.class.getField("stringList");

Type genericFieldType = field.getGenericType();

if(genericFieldType instanceof ParameterizedType){
    ParameterizedType aType = (ParameterizedType) genericFieldType;
    Type[] fieldArgTypes = aType.getActualTypeArguments();
    for(Type fieldArgType : fieldArgTypes){
        Class fieldArgClass = (Class) fieldArgType;
        System.out.println("fieldArgClass = " + fieldArgClass);
    }
}
```

## Array
```java
import java.lang.reflect.Array;
int[] intArray = (int[]) Array.newInstance(int.class, 3);

int[] intArray = (int[]) Array.newInstance(int.class, 3);

Array.set(intArray, 0, 123);
Array.set(intArray, 1, 456);
Array.set(intArray, 2, 789);

System.out.println("intArray[0] = " + Array.get(intArray, 0));
System.out.println("intArray[1] = " + Array.get(intArray, 1));
System.out.println("intArray[2] = " + Array.get(intArray, 2));

Class stringArrayClass = String[].class;
Class intArray = Class.forName("[I");
Class stringArrayClass = Class.forName("[Ljava.lang.String;");
```

## Dynamic Proxies

使用场景
- 数据库连接和业务管理
- 单元测试的动态模拟对象
- DI容器适应自定义工厂界面
- AOP：方法拦截
