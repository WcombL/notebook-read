# Tex数学公式

[LaTex 数学表达式](https://www.zybuluo.com/codeep/note/163962#3%E5%A6%82%E4%BD%95%E8%BE%93%E5%85%A5%E6%8B%AC%E5%8F%B7%E5%92%8C%E5%88%86%E9%9A%94%E7%AC%A6)

**行中公式**

```
$ 数学公式 $  
```

**独立公式**

```  
$$ 数学公式 $$
```

**自动编号的公式**

```
\begin{equation}
数学公式
\label{eq:当前公式名}
\end{equation}
```

> 自动编号后的公式可在全文任意处使用 `\eqref{eq:公式名}` 语句引用


## 输入上下标
`^`表示上标，`_`表示下标。如果上下标的内容多于一个字符，需要用 {} 将这些内容括成一个整体。上下标可以嵌套，也可以同时使用

$$ x^{y^z}=(1+{\rm e}^x)^{-2xy^w} $$

如果要在左右两边都有上下标，可以用 \sideset 命令

$$ \sideset{^1_2}{^3_4}\bigotimes $$

## 输入括号和分隔符
`()`、`[]` 和 `|` 表示符号本身，使用 `\{\}` 来表示 `{}` 。当要显示大号的括号或分隔符时，要用 `\left` 和 `\right` 命令


| : 输入  | 显示 |  输入   | 显示 |
| ------- | ---- | ------- | ---- |
| \langle |  <   | \rangle |  >   |
| \lceil  |      | \rceil  |      |
| \lfloor |      | \rfloor |      |
| \lbrace |  {   | \rbrace |  }   |

$$ f(x,y,z) = 3y^2z \left( 3+\frac{7x+5}{1+y^2} \right) $$

有时候要用 `\left.` 或 `\right.` 进行匹配而不显示本身。

$$ \left. \frac{{\rm d}u}{{\rm d}x} \right| \_{x=0} $$


## 输入分数

通常使用 `\frac {分子} {分母}` 命令产生一个分数，分数可嵌套

如果分式很复杂，亦可使用 分子 `\over` 分母 命令，此时分数仅有一层

$$\frac{a-1}{b-1} \quad and \quad {a+1\over b+1}$$

## 输入开方

使用 `\sqrt [根指数，省略时为2] {被开方数}` 命令输入开方

$$\sqrt{2} \quad and \quad \sqrt[n]{3}$$

## 输入省略号

数学公式中常见的省略号有两种，`\ldots` 表示与文本底线对齐的省略号，`\cdots` 表示与文本中线对齐的省略号

$$f(x_1,x_2,\underbrace{\ldots}_{\rm ldots} ,x_n) = x_1^2 + x_2^2 + \underbrace{\cdots}_{\rm cdots} + x_n^2$$

## 输入矢量

使用 `\vec{矢量} `来自动产生一个矢量。也可以使用 `\overrightarrow` 等命令自定义字母上方的符号

$$\vec{a} \cdot \vec{b}=0$$

$$\overleftarrow{xy} \quad and \quad \overleftrightarrow{xy} \quad and \quad \overrightarrow{xy}$$
