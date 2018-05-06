1.
loc1.getRow();
```java
	/**
     * Gets the row coordinate.
     * @return the row of this location
     */
    public int getRow()
    {
        return row;
    }
```

2.
false.因为loc1与loc2的行数与列数不相等
```java
	public boolean equals(Object other)
    {
        if (!(other instanceof Location))
            return false;
        Location otherLoc = (Location) other;
        return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
    }
```

3. 
(4,4)。因为传入的参数是South，也就是loc1南边临近的点，所以行数+1得到新坐标
```java
	public Location getAdjacentLocation(int direction)
    {
        int adjustedDirection = (direction + HALF_RIGHT / 2) % FULL_CIRCLE;
        if (adjustedDirection < 0)
            adjustedDirection += FULL_CIRCLE;
        adjustedDirection = (adjustedDirection / HALF_RIGHT) * HALF_RIGHT;
        int dc = 0;
        int dr = 0;
        ······
        // 方向为南方
        else if (adjustedDirection == SOUTH)
            dr = 1;        
        return new Location(getRow() + dr, getCol() + dc);
    }
```

4. 
135degrees.
```java
public int getDirectionToward(Location target)
    {
        int dx = target.getCol() - getCol();
        int dy = target.getRow() - getRow();
        // y axis points opposite to mathematical orientation
        int angle = (int) Math.toDegrees(Math.atan2(-dy, dx));
        // mathematical angle is counterclockwise from x-axis,
        // compass angle is clockwise from y-axis
        int compassAngle = RIGHT - angle;
        // prepare for truncating division by 45 degrees
        compassAngle += HALF_RIGHT / 2;
        // wrap negative angles
        if (compassAngle < 0)
            compassAngle += FULL_CIRCLE;
        // round to nearest multiple of 45
        return (compassAngle / HALF_RIGHT) * HALF_RIGHT;
    }
```

5.
getAdjacentLocation根据传入的方向，可能是准确的方向，也肯能是未定义的方向，例如15，22之类不是45的倍数的数值。在该函数中将这个数字对360取模后，取最接近45倍数的数值；如果数值小于0，则加上360把它变正。将方向规则化后，只需判断八个方向来改变行列参数即可得到临近的点了。

```java
	public Location getAdjacentLocation(int direction)
    {
        // reduce mod 360 and round to closest multiple of 45
        int adjustedDirection = (direction + HALF_RIGHT / 2) % FULL_CIRCLE;
        if (adjustedDirection < 0)
            adjustedDirection += FULL_CIRCLE;

        adjustedDirection = (adjustedDirection / HALF_RIGHT) * HALF_RIGHT;
        int dc = 0;
        int dr = 0;
        if (adjustedDirection == EAST)
            dc = 1;
        else if (adjustedDirection == SOUTHEAST)
        {
            dc = 1;
            dr = 1;
        }
        else if (adjustedDirection == SOUTH)
            dr = 1;
        else if (adjustedDirection == SOUTHWEST)
        {
            dc = -1;
            dr = 1;
        }
        else if (adjustedDirection == WEST)
            dc = -1;
        else if (adjustedDirection == NORTHWEST)
        {
            dc = -1;
            dr = -1;
        }
        else if (adjustedDirection == NORTH)
            dr = -1;
        else if (adjustedDirection == NORTHEAST)
        {
            dc = 1;
            dr = -1;
        }
        return new Location(getRow() + dr, getCol() + dc);
    }
```

set4
1.
通过getOccupiedLocations().size()来得到Grid中的objects数目；因为getOccupiedLocation()返回一个ArrayList，再利用它的函数size()得到它的大小。
在BoundedGrid中可以通过减法来得到空余位置的数目，getNumRows()*getNumCols() - getOccupiedLocation().size()。
```java
// Grid中的接口
ArrayList<Location> getEmptyAdjacentLocations(Location loc);

//BoundedGrid中的实现
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> theLocations = new ArrayList<Location>();
        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++)
        {
            for (int c = 0; c < getNumCols(); c++)
            {
                // If there's an object at this location, put it in the array.
                Location loc = new Location(r, c);
                if (get(loc) != null)
                    theLocations.add(loc);
            }
        }

        return theLocations;
    }

	public int getNumRows()
    {
        return occupantArray.length;
    }

    public int getNumCols()
    {
        // Note: according to the constructor precondition, numRows() > 0, so
        // theGrid[0] is non-null.
        return occupantArray[0].length;
    }
```

2.通过isValid(new Location(10,10))来检测，若返回true则证明该点存在grid中，否则则不存在。
```java
	public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }
```

3.
因为Grid是接口，接口中的函数在其他类中实现。在本个project中，AbstractGrid实现了该接口的函数。UnboundedGrid和BoundedGrid通过继承虚基类AbstractGrid也实现了这些函数。
```java
//AbstractGrid虚基类中实现接口函数
public abstract class AbstractGrid<E> implements Grid<E>
```

4.我不认为返回一个数组是更好的设计。因为ArrayList的优点在不需要预先设定它的大小，它可以自动增加或减少，而数组显然没有这个优点。

set5
1.
每一个actor都有location,direction,color,Grid<Actor>这几个属性
```java
public class Actor
{
    private Grid<Actor> grid;
    private Location location;
    private int direction;
    private Color color;

    ······
}
```

2.
初始颜色为蓝色，初始方向为正北方
```java
	public Actor()
    {
        color = Color.BLUE;
        direction = Location.NORTH;
        grid = null;
        location = null;
    }
```

3.因为actor不仅有声明函数，还有实现函数，而接口中只声明而不实现。
```java
	//例如，实现了getColor函数，而不仅仅是声明
	/**
     * Gets the color of this actor.
     * @return the color of this actor
     */
    public Color getColor()
    {
        return color;
    }
```
4.
>* 1.不可以。当在BugRunner中测试时，一run起来就会出现非法错误，提示该actor已经存在，不能重复加入。
```java
//加入两次test昆虫
public class BugRunner {
	public static void main(String[] args){
		ActorWorld world = new ActorWorld();
		Bug	test = new Bug(); 
		world.add(test);
		test.putSelfInGrid(test.getGrid(),test.getLocation());
		world.show();
	}
}
```
>* 2.不可以。当在BugRunner中测试时，一run起来也会出现非法错误，提示该actor不存在，不能删除。
```java
//去掉两次test昆虫
public class BugRunner {
	public static void main(String[] args){
		ActorWorld world = new ActorWorld();
		Bug	test = new Bug(); 
		world.add(test);
		test.removeSelfFromGrid();
		test.removeSelfFromGrid();
		world.show();
	}
}
```
>* 3.可以。加入一个actor删除后，还可以再加入进网格中，不会报错
```java
//加入test昆虫，去掉再加入
public class BugRunner {
	public static void main(String[] args){
		ActorWorld world = new ActorWorld();
		Bug	test = new Bug(); 
		world.add(test);
		Grid g = test.getGrid(); //记录下test虫子的grid，以免remove后找不到
		world.show();
		test.removeSelfFromGrid();
		test.putSelfInGrid(g,new Location(3,3));
	}
}
```
5.可以通过setDirection(getDirection() + 90)方法来向右转向90°。
```java
public void setDirection(int newDirection)
{
	direction = newDirection % Location.FULL_CIRCLE;
	if (direction < 0)
		direction += Location.FULL_CIRCLE;
}
```

set6
1.
```java
if (!gr.isValid(next))
    return false;
```

2.
```java
Actor neighbor = gr.get(next);
return (neighbor == null) || (neighbor instanceof Flower);
```

3. isValid()和get()这两个方法是Grid接口的函数，并且包含在了canMove函数中。因为要利用这两个函数来判断昆虫的下一步是否在Grid中为合法位置，还有当位置为空和花朵时才能前进。
```java
	public boolean canMove()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        if (!gr.isValid(next))
            return false;
        Actor neighbor = gr.get(next);
        return (neighbor == null) || (neighbor instanceof Flower);
    }
```

4. getAdjacentLocation()方法是Loaction类中并包含在了canMove函数中。因为通过这个函数传入当前昆虫的方向来找到下一个应该前进的点，再进行判断。
```java
public boolean canMove()
    {
        ····
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        ····
    }
```

5.
getLocation(), getDirection(), getGrid()三个函数
```java
	public boolean canMove()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (!gr.isValid(next))
            return false;
        Actor neighbor = gr.get(next);
        return (neighbor == null) || (neighbor instanceof Flower);
        // ok to move into empty location or onto flower
        // not ok to move onto any other actor
    }
```

6.
虫子会从网格中移除，不再出现。
```java
	public void move()
    {
        ····
        if (gr.isValid(next))
            moveTo(next);
        //当移动位置不合法时，就从网格中移除
        else
            removeSelfFromGrid();
        ···
    }
```

7.
loc变量是有意义的，因为它保存了昆虫移动前的位置，而这个位置是用于建立一朵花，若没有这个变量，则无法得到移动前的位置了。
```java
	public void move()
    {
        Location loc = getLocation();
        ···
        flower.putSelfInGrid(gr, loc);
    }
```
8.
因为花朵的颜色是跟昆虫一样的，而且花朵生成的位置也是昆虫移动前的位置，所以可以看成昆虫离开后留下了一朵花。
```java
public void move()
    {
		······
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
		······
        Flower flower = new Flower(getColor());
        flower.putSelfInGrid(gr, loc);
    }
```
9.
在Bug类中调用move函数，当昆虫离开网格，它的位置会出现一朵鲜花。因为在removeSelfFromGrid后会有花的putSelfInGrid函数。
```java
	public void move()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (gr.isValid(next))
            moveTo(next);
        else
            removeSelfFromGrid();
        Flower flower = new Flower(getColor());
        flower.putSelfInGrid(gr, loc);
    }
```

10.
```java
	public void move()
    {
        Location loc = getLocation();
        ···
        flower.putSelfInGrid(gr, loc);
    }
```

11.
因为一次turn转45度，所以转180度需要4次转向。