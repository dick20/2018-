1.
	sideLength变量定义虫子移动完一条box的边界时，它所走过的格子数
	```java
	public BoxBug(int length)
    {
        steps = 0;
        sideLength = length;
    }
	//当虫子未走完一条边界时
	if (steps < sideLength && canMove())
    {
        move();
        steps++;
    }
    ```

2.
	steps变量是统计虫子在移动中移动的步数，用于判断是否已经超过既定的边界
	如果step小于sideLength时，表示虫子在移动中仍未走完边界
	```java
	if (steps < sideLength && canMove())
    {
        move();
        steps++;
    }
	```
3.
	当step等于sideLength时，证明虫子已经走完一条边界的长度，此时需要转向继续移动。而一次turn是转向45°，而到达边界时的转向需要90°，故需要两次turn。
	```java
	/**
     * Turns the bug 45 degrees to the right without changing its location.
     */
    public void turn()
    {
        setDirection(getDirection() + Location.HALF_RIGHT);
    }
    ```
4.
	因为BoxBug类继承了Bug类，而在Bug类中move方法为公有public属性。所以BoxBug中也可以使用父类的move方法。
	```java
	public class BoxBug extends Bug;

	public void move()
    {
        ...
    }
	```

5.
	答案是肯定的，因为当BoxBug建立后，也就是无法再通过其他方法改变该类里面的sideLength私有变量，所以也就无法改变BoxBug的大小。
	```java
	private int sideLength;

    public BoxBug(int length)
    {
        steps = 0;
        sideLength = length;
    }
	```

6.
	会改变，当BoxBug遇到其他虫子或岩石挡在它的前面时，它会转向并开始新的box路径。

7.
	>* 当BoxBug刚建立时，初始化steps为0
	>* 当它走完一条边，也就是steps等于sideLength时
	>* 当它被挡住，转向并开始新的box路径时
	```java
	public BoxBug(int length)
    {
        steps = 0;
        sideLength = length;
    }
	//steps等于sideLength时
    else
    {
        turn();
        turn();
        steps = 0;
    }
    ```