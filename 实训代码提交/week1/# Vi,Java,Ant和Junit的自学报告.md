# Vi,Java,Ant和Junit的自学报告

------

## 一. Vi部分
首先，我们为什么要学Vi编辑器呢？当然是因为他十分十分重要啊！**vi编辑器是所有Unix及Linux系统下标准的编辑器，他就相当于windows系统中的记事本一样，它的强大不逊色于任何最新的文本编辑器。**

### 1. vim的模式切换
作为新手的我而言，要弄懂vim的使用方式，肯定离不开它最关键的部分——模式切换。它包括三个模式，命令模式，输入模式和末行模式。


> * 命令模式→输入模式：我所最为常用的就是使用字母i了，在当前光标所在字符的前面，转为输入模式。当然也有其他字符可以转换，当有需要的时候查文档即可
> * 输入模式→命令模式：按下ESC键即可
> * 命令模式→末行模式：输入字符即可转为末行模式
> * 末行模式→命令模式：按下ESC键 

![cmd-markdown-logo](https://www.zybuluo.com/static/img/logo.png)

### 2. vim如何打开文件
方法当然也有很多种，最简单的也是经常被我使用的就是，**vim后跟文件路径及文件名** ，如果文件存在，则打开编辑文件窗口，如果文件不存在，则创建文件。

### 3. vim如何关闭文件
我们可以在末行模式下输入以下字符：
>* w：保存
>* q：退出
>* wq 或 x：保存退出，wq 和 x 都是保存退出

### 4. vim如何删除字符
我们可以在命令模式下
>* x：删除光标所在处单个字符
>* #x：删除光标所在处及向后共#个字符
>*d命令跟 跳转命令组合使用 如：dw 表示删除光标所在位置到下一个单词词首所有字符
>*#dw，#de，#db ：#dw表示 删除当前光标所在处及向后第#个单词词首所有字符 
>* dd：删除当前光标所在行
>* #dd：删除包含当前光标所在行内的#行

### 5. vim如何撤销编辑操作
可以通过u或#u撤回一次或多次操作，也可以使用Ctrl+r。

### 5. vim如何移动光标

>* 逐字符移动
h：向左, l：向右, j：向下, k：向上
>* 以单词为单位移动
    w：移到下一个单词的词首
e：跳至当前或下一个单词的词尾
b：跳至当前或上一个单词的词首
>* 行内跳转
0：绝对行首
^：行首第一个非空白字符
$：绝对行尾


个人认为这些就是平时日常所用到的指令和操作方法，我在实训的第一阶段编写文件时，也没有用到太多花哨的功能。倘若需要的时候，上网查询用法即可。vim编辑器也是讲究熟能生巧，用得多了，自然指令也会牢记于心，编写文件或程序就会得心应手。

------

## 二. Java部分
个人感觉Java是类似与C++一类的高级语言，用法大同小异。我们通过大一对于C++语言以及类的学习，现在掌握Java的难度并不大。Java的写法还会更为简单。

学习一门语言肯定离不开HelloWorld程序，现在我就先来讲一下这个简单的入门程序。

```Java
public class helloworld{
	static public String str;
	public static void main(String[] args) {
		hello();
		System.out.println(str);
	}
	static public void hello(){
		str = "Hello World!";
	}
	String getStr(){
		return str;
	}
}
```
![cmd-markdown-logo](https://www.zybuluo.com/static/img/logo.png)

现在，我就对第一阶段所要做的简易计算器的几个关键函数学习了下。
1.图形化界面GUI的学习。其中包括JFrame，JLabel，JTextField，JButton等等。
2.界面布局。JFrame.Setlayout;我在这个计算器利用的是GridLayout布局，共两行五列。
3.给button添加事件处理。addActionListener()
4.事件处理包括两类，一类是两个数之间加减乘除，另一类是按下OK按钮的计算。

详细代码如下：

```Java
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.*;

public class Calculator extends JFrame{
    private JLabel op1 = new JLabel("",JTextField.CENTER);
    private JTextField num1 = new JTextField("12");
    private JTextField num2 = new JTextField("2");
    private JLabel op3 = new JLabel("",JTextField.CENTER);

	public Calculator(){
		JFrame frame = new JFrame("Mycalculator");
		frame.setLayout(new GridLayout(2,5,5,5));
		//num1,num2 are the operands
        num1.setHorizontalAlignment(JTextField.CENTER);
        num2.setHorizontalAlignment(JTextField.CENTER);
        JLabel op2 = new JLabel("=",JTextField.CENTER);
        
		frame.add(num1);
		frame.add(op1);
		frame.add(num2);
		frame.add(op2);
		frame.add(op3);
		//bu1~bu4 are the operators,bu5 is the equal operator
		JButton bu1 = new JButton("+");
		JButton bu2 = new JButton("-");
		JButton bu3 = new JButton("*");
		JButton bu4 = new JButton("/");
		JButton bu5 = new JButton("OK");
		
		frame.add(bu1);
		frame.add(bu2);
		frame.add(bu3);
		frame.add(bu4);
		frame.add(bu5);
		
		//calculate part, add listener
		bu1.addActionListener(new OperationButtonListener());
		bu2.addActionListener(new OperationButtonListener());
		bu3.addActionListener(new OperationButtonListener());
		bu4.addActionListener(new OperationButtonListener());
		bu5.addActionListener(new EqualButtonListener());
		//set the attribute of frame
		int width = 200;
		int length = 400;
		frame.setSize(length, width);
		frame.setLocationRelativeTo(null); 			//set the frame to middle
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	//+-x/,operators' listener
	private class OperationButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JButton temp = (JButton)e.getSource();
			op1.setText(temp.getLabel());
		}
	}
	//=, equal listener
	private class EqualButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				//get two numbers
				double number1 = Double.parseDouble(num1.getText());
				double number2 = Double.parseDouble(num2.getText());
				
				if(op1.getText().equals("+")){
					op3.setText(number1+number2+"");
				}
				else if(op1.getText().equals("-")){
					op3.setText(number1-number2+"");
				}
				else if(op1.getText().equals("*")){
					op3.setText(number1*number2+"");
				}
				else if(op1.getText().equals("/")){
					op3.setText(number1/number2+"");
				}
			}
			//show the wrong details
			catch(Exception err){
				op3.setText("Wrong!");
			}
		}
	}

	public static void main(String[] args){
		//run the calculator
		Calculator();
	}
}
```
运行结果如下：
![cmd-markdown-logo](https://www.zybuluo.com/static/img/logo.png)

## 三. Ant部分

初次阅读Ant的入门教程，感觉它就像是C++中的Makefile文件，方便程序员去编译实现并测试。Ant的构建文件是基于XML编写的，默认名称为build.xml。

 + project元素
     + project元素是Ant构件文件的根元素，Ant构件文件至少应该包含一个project元素，否则会发生错误。在每个project元素下，可包含多个target元素。以下是project元素的各属性。
     + name属性；用于指定project元素的名称。
     + default属性；用于指定project默认执行时所执行的target的名称。
     + basedir属性；用于指定基路径的位置。该属性没有指定时，使用Ant的构件文件的附目录作为基准目录。
 +  target元素
它为Ant的基本执行单元，它可以包含一个或多个具体的任务。多个target可以存在相互依赖关系。它有如下属性：
     + name属性；指定target元素的名称，这个属性在一个project元素中是唯一的。我们可以通过指定target元素的名称来指定某个target。
     + depends属性；用于描述target之间的依赖关系，若与多个target存在依赖关系时，需要以“,”间隔。Ant会依照depends属性中target出现的顺序依次执行每个target。被依赖的target会先执行。
     + if属性；用于验证指定的属性是否存在，若不存在，所在target将不会被执行。
     + unless属性；该属性的功能与if属性的功能正好相反，它也用于验证指定的属性是否存在，若不存在，所在target将会被执行。
     + description属性；该属性是关于target功能的简短描述和说明。
 +  property元素
     + 该元素可看作参量或者参数的定义，project的属性可以通过property元素来设 定，也可在Ant之外设定。
     + property元素可用作task的属性值。在task中是通过将属性名放在“${”和“}”之间，并放在task属性值的位置来实现的。

我们可以利用以上的一些属性并配合javac编译语句实现编译java文件，下面我就尝试编写ant文件来实现编译helloworld程序。

![cmd-markdown-logo](https://www.zybuluo.com/static/img/logo.png)  

## 四. Junit部分
JUnit是一个Java语言的单元测试框架。多数Java的开发环境都已经集成了JUnit作为单元测试的工具。使用 Junit 不需要创建 main()方法，而且每个测试方法一一对应，逻辑特别清晰。
>* 1.@Test: 测试方法
 + (expected=XXException.class)如果程序的异常和XXException.class一样，则测试通过
 + (timeout=100)如果程序的执行能在100毫秒之内完成，则测试通过
>* 2.@Ignore: 被忽略的测试方法：加上之后，暂时不运行此段代码
>* 3.@Before: 每一个测试方法之前运行
>* 4.@After: 每一个测试方法之后运行
>* 5.@BeforeClass: 方法必须必须要是静态方法（static 声明），所有测试开始之前运行，注意区分before，是所有测试方法
>* 6.@AfterClass: 方法必须要是静态方法（static 声明），所有测试结束之后运行，注意区分 @After

注意：编写测试类的原则：　
>* 测试方法上必须使用@Test进行修饰
>* 测试方法必须使用public void 进行修饰，不能带任何的参数
建一个源代码目录来存放我们的测试代码，即将测试代码和项目业务代码分开
>* 测试类所在的包名应该和被测试类所在的包名保持一致
>* 测试单元中的每个方法必须可以独立测试，测试方法间不能有任何的依赖
>* 测试类使用Test作为类名的后缀（不是必须）
>* 测试方法使用test作为方法名的前缀（不是必须）

下面，我要利用Junit来对我的程序进行白盒测试，当然我所测试的程序也是最简单的Helloworld程序，比对他输出的字符串是否匹配。

![cmd-markdown-logo](https://www.zybuluo.com/static/img/logo.png)  

接着，我还要利用Ant跟Junit结合在一起实现测试。
所以，我们就要修改下build.xml
![cmd-markdown-logo](https://www.zybuluo.com/static/img/logo.png) 

实验结果：
![cmd-markdown-logo](https://www.zybuluo.com/static/img/logo.png) 

## 五. 总结
    到此，实训的第一阶段就基本完成了。由于第一阶段都是一些配置环境以及学习一些工具，难度系数并不算太大。通过自己的学习和同学之间的交流，能够解决不少棘手的问题，挺有成就感的。实训都是一些比较新的内容，例如自学vim，java，ant，junit等内容，了解到了更多编程的知识，拓展了知识面。期待第二阶段的实训，肝起来！