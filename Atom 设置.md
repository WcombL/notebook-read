# Atom 设置

## 设置代理

修改`C:\Users\用户\.atom`下的文件.apmrc 在其中添加代理地址和端口，如下

```
http-proxy=http://127.0.0.1:1080
https-proxy=http://127.0.0.1:1080
strict-ssl=false
```

## markdown 书写和预览

Markdown语法当然也是与github语法完全同步

使用快捷键 `Ctrl + Shift + M` 则可打开Markdown的预览界面

## 常用快捷键

`Crtl+Shift+M`    开启Markdown实时预览

`Crtl+Shift+P`    打开命令窗口，可以运行各种菜单功能

`Crtl + T`    快速多文件切换

`Crtl + F`    文件内查找和替换

`Crtl + Shift + F`    多文件查找和替换

`Crtl + [`   对选中内容向左缩进

`Crtl + ]`    对选中内容向右缩进

`Crtl + \`    显示或隐藏目录树

`Crtl + m`    相应括号之间，html tag之间等跳转

`Crtl + Alt + B`    格式化代码（需要安装atom-beautify）

`Crtl + \``   调起CLI命令行界面（需要安装terminal-panel）

这是用来 \*演示\* 的 \_文本\_\`


## 常用插件

### minimap

minimap是一个预览全部代码的一个插件，同时能方便的移动到指定的文件位置

### atom-beautify

一个格式化代码的插件，支持HTML, CSS, JavaScript, PHP, Python, Ruby, Java, C, C++, C#, Objective-C,CoffeeScript, TypeScript, SQL等多种语言

- 安装后可以使用 `Crtl + Alt + B` 快捷键进行格式化
- 点击菜单“Packages”->“Atom Beautify”->“Beautify”进行格式化

### autocomplete-* 系列

autocomplete-\*系列包含各个语言的代码自动补全功能

- autocomplete-paths：填写路径的时候有Sug提示
- autocomplete-php：php代码提示补全
- autocomplete-java：java代码提示补全

### pigments

pigments是项目文件中，样式显色显示的的插件

### terminal-panel

用于执行命令并显示输出。打开终端面板快捷键：`Ctrl + \``

### file-icons

### atom-ide-ui

### docblockr

注释

### platformio-ide-terminal
命令行
