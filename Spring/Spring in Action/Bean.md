# Bean

## 生命周期

- Singletion: 在一个应用中只存在一个实例(默认生命周期)
- Prototype: 每次注入的时候创建一个新的实例
- Session: 在Web应用中每个Session创建一个实例
- Request: 在Web应用中每次请求都创建一个实例

`@Scope`: 来注解生命周期,一般配合`@Component`和`@Bean`使用

```java
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Notepad { ... }
```

## 运行时注入值

- Property placeholders
- The Spring Expression Language(SpEL)

### Property placeholders

```java
@Configuration
@PropertySource("classpath:/com/soundsystem/app.properties")
public class ExpressiveConfig {
    @Autowired
    Environment env;

    @Bean
    public BlankDisc disc() {
        return new BlankDisc(env.getProperty("disc.title"),env.getProperty("disc.artist"));
    }
}
```

Environment方法说明
```java
String getProperty(String key)
String getProperty(String key, String defaultValue)
T getProperty(String key, Class<T> type)
T getProperty(String key, Class<T> type, T defaultValue)
getRequiredProperty(String key) //必须填写
containsProperty(String key) //检查属性是否存在
getPropertyAsClass(String key, Class t) //属性解析为Class对象
String[] getActiveProfiles() // Returns an array of active profile names
String[] getDefaultProfiles() //Returns an array of default profile names
boolean acceptsProfiles(String... profiles) //Returns true if the environment supports the given profile(s)
```

占位符`${ ... }`
```xml
<bean id="sgtPeppers" class="soundsystem.BlankDisc" 
    c:_title="${disc.title}" 
    c:_artist="${disc.artist}"/>
```

如果使用组件扫描和自动注入，此时是没有配置文件的需要下面方式进行配置(非硬编码)
```java
public BlankDisc(@Value("${disc.title}") String title, @Value("${disc.artist}") String artist)
{
    this.title = title;
    this.artist = artist;
}
```

为了使用Property占位符需要配置
`PropertyPlaceholderConfigurer` 或 `PropertySourcesPlaceholderConfigurer` Bean, 在Spring 3.1后
推荐使用`PropertySourcesPlaceholderConfigurer`
```java
@Bean
public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
}
```

### The Spring Expression Language

SpEL技巧：
- 通过ID引用Bean
- 调用方法或访问对象属性
- 值的数学、逻辑、关系操作
- 正则表达式匹配
- 集合操作

使用`#{ ... }`定义SpEL表达式

```java
#{T(System).currentTimeMillis()} //T代表java.lang.System调用currentTimeMillis方法
#{sgtPeppers.artist} // 访问ID为sgtPeppers的artist属性
#{systemProperties['disc.title']} // 访问系统属性

public BlankDisc(
    @Value("#{systemProperties['disc.title']}") String title,
    @Value("#{systemProperties['disc.artist']}") String artist) {
    this.title = title;
    this.artist = artist;
}
```

**SpEL字面值**
可以是浮点数、字符串、Boolean值

```java
#{3.14159} // 浮点数
#{9.87E4} // 科学计数 98700
#{'Hello'} // 字符串
#{false} // Boolean值
```

**引用Bean、Property、Methods**

> 通过ID应用其它Bean

```java
#{sgtPeppers} // 引用其它Bean,通过ID
#{sgtPeppers.artist} // 应用Bean的artist属性
#{artistSelector.selectArtist()} // 调用方法
#{artistSelector.selectArtist().toUpperCase()} // 如果方法有返回值，可以继续调用返回值的方法
#{artistSelector.selectArtist()?.toUpperCase()} // 判断空指针异常 ？.确保左边表达式返回值非空
```

**在SpEL中使用类型**

>`T()`引用指定类型,访问静态方法和常量

```java
T(java.lang.Math)
T(java.lang.Math).PI
T(java.lang.Math).random()
```

**SpEL操作符**
- 算术: + - * / % ^
- 逻辑: and, or, not, |
- 比较: <, lt, >, gt, ==, eq, <=, le, >=, ge
- 条件: ?: (ternary), ?: (Elvis)
- 正则: matches

`.?[]` 选择操作符，主要用于过滤集合元素。`.^[]` 选择第一个匹配的元素 `.$[]` 最后一个匹配元素
`.![]` 映射操作符，获取属性到另外一个集合

```java
#{2 * T(java.lang.Math).PI * circle.radius}
#{T(java.lang.Math).PI * circle.radius ^ 2}
#{disc.title + ' by ' + disc.artist}
#{counter.total == 100} // 等价 #{counter.total eq 100}
#{scoreboard.score > 1000 ? "Winner!" : "Loser"} // 三元运算符
#{disc.title ?: 'Rattle and Hum'} // 如果disc.title不为null则返回Rattle and Hum
#{admin.email matches '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com'} // 匹配E-mail
#{jukebox.songs[4].title} // 获取Bean属性songs集合第五个值
#{jukebox.songs[T(java.lang.Math).random() * jukebox.songs.size()].title} // 随机值
#{'This is a test'[3]} // 取字符串第四个字符。为s
// 取每个元素的指定元素执行内部表达式值，如果为true则放入另外一个集合
#{jukebox.songs.?[artist eq 'Aerosmith']} // 选择songs集合中属性artist等于Aerosmith的元素
#{jukebox.songs.^[artist eq 'Aerosmith']} // 取匹配第一个值
#{jukebox.songs.![title]} // 获取songs中title属性到另外一个集合
#{jukebox.songs.?[artist eq 'Aerosmith'].![title]}
```























