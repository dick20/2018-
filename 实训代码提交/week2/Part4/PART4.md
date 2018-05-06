1.
>* act()
>* getActors()
>* processActors(ArrayList<Actor> actors)
>* getMoveLocation()
>* selectMoveLocation(ArrayList<Location> locs)
>* makeMove(Location loc)

2.
>* getActors()
>* processActors(ArrayList<Actor> actors)
>* getMoveLocation()
>* selectMoveLocation(ArrayList<Location> locs)
>* makeMove(Location loc)

3.
是的。Critter的子类不一定是从它的附近的网格来寻找Actor，所以重写getActors()非常合理。
```java
	public ArrayList<Actor> getActors()
    {
        return getGrid().getNeighbors(getLocation());
    }
```

4.
>* 吃掉Actors，将它们从网格中消除
>* 改变Actors的颜色
>* 改变Actors的方向或位置
```java
	public void processActors(ArrayList<Actor> actors)
    {
        for (Actor a : actors)
        {
            if (!(a instanceof Rock) && !(a instanceof Critter))
                a.removeSelfFromGrid();
        }
    }
```
5.
>* getMoveLocation();首先通过这个函数找到附近可以到达的点，排除一些点已经被石头或虫子占有。
>* selectMoveLocation(ArrayList<Location> locs);从get到的空余的点中随机找到一个点来移动，如果找不到空余的点则返回原来的位置
>* makeMove(Location loc);用选择好的点来移动，若loc为null，则将这个Critter从网格中移除
```java
	public ArrayList<Location> getMoveLocations()
    {
        return getGrid().getEmptyAdjacentLocations(getLocation());
    }

	public Location selectMoveLocation(ArrayList<Location> locs)
    {
        int n = locs.size();
        if (n == 0)
            return getLocation();
        int r = (int) (Math.random() * n);
        return locs.get(r);
    }

	public void makeMove(Location loc)
    {
        if (loc == null)
            removeSelfFromGrid();
        else
            moveTo(loc);
    }
```

6.
Critter如果不写构造函数，那么Java会自动会为它建一个缺省的构造函数。由于Critter继承Actor，所以它不写构造函数就意味着直接调用Actor的构造函数生成一个颜色为蓝色的，方向为正北方的Actor。
```java
	public class Critter extends Actor{···};
	public Actor()
    {
        color = Color.BLUE;
        direction = Location.NORTH;
        grid = null;
        location = null;
    }
```

set8
1.
因为Critter类的act函数调用了getActors,processActors,makeMove等等函数，而在ChameleonCritter中重写了processActors，所以不需要改变act也会实现变色的功能。
```java
	public void act()
    {
        if (getGrid() == null)
            return;
        ArrayList<Actor> actors = getActors();
        processActors(actors);
        ArrayList<Location> moveLocs = getMoveLocations();
        Location loc = selectMoveLocation(moveLocs);
        makeMove(loc);
    }

```
2.
因为在ChameleonCritter类中重写的makeMove函数中先确定好了它的方向，再调用makeMove实现代码的重用的同时，不会影响功能。
```java
 	public void makeMove(Location loc)
    {
        setDirection(getLocation().getDirectionToward(loc));
        super.makeMove(loc);
    }
```

3.
类似于Bug中留下花朵一样，先要记录Critter未移动的位置，再它移动后，再在之前的位置上留下一朵颜色相同的花朵。实现代码如下：
```java
	public void makeMove(Location loc)
    {
    	Location flower_loc = getLocation();
        setDirection(getLocation().getDirectionToward(loc));
        super.makeMove(loc);
        //当Critter移动
        if(!flower_loc.equals(loc)){
			Flower f = new Flower(getColor());
			f.putSelfInGrid(getGrid(), flower_loc);
    	}
    }
```

4.
因为ChameleonCritter中的getActors函数与父类的功能相同，所以不必再次重写，充分发挥代码的重用性。
```java
	public ArrayList<Actor> getActors()
    {
        return getGrid().getNeighbors(getLocation());
    }
```

5.
因为ChameleonCritter已经继承了Actor中getLocation函数，Actor的子类都会有这个方法，所以没有必要包括。
```java
public class Actor{
	···
	public Location getLocation()
    {
        return location;
    }
    ···
}
```


6.
可以通过getGrid()方法，该方法同样是继承Actor类所得到的。
```java
public class Actor{
	···
	public Grid<Actor> getGrid()
    {
        return grid;
    }
    ···
}	
```

set9
1.
因为CrabCritter类中所需的proceActors函数功能与父类Critt一样，所以没有必要重写，实现代码的重用。
```java
	public void processActors(ArrayList<Actor> actors)
    {
        for (Actor a : actors)
        {
            if (!(a instanceof Rock) && !(a instanceof Critter))
                a.removeSelfFromGrid();
        }
    }
```

2.
首先CrabCritter先调用getActors函数，得到前面，左前方，右前方三个位置的网格，查看这些位置是否为空，若不为空就加入ArrayList中；然后传入processActors中执行，将ArrayList中的Actor元素remove掉。不会全部吃完附近的actors，那些不在前方，左前方，右前方的actors不会被吃掉。
```java
	public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        int[] dirs =
            { Location.AHEAD, Location.HALF_LEFT, Location.HALF_RIGHT };
        for (Location loc : getLocationsInDirections(dirs))
        {
            Actor a = getGrid().get(loc);
            if (a != null)
                actors.add(a);
        }

        return actors;
    }
```

3.
getLocationsInDirections函数是用于得到CrabCritter所要吃掉东西的三个位置，在该函数中不仅仅得到这三个位置，还判断这些位置是否合法，将合法的位置返回用与下一步的执行。
```java
 	public ArrayList<Location> getLocationsInDirections(int[] directions)
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid gr = getGrid();
        Location loc = getLocation();
    
        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            if (gr.isValid(neighborLoc))
                locs.add(neighborLoc);
        }
        return locs;
    }    
```

4.
因为CrabCritter位置(3,4)且方向为正南方，所以能吃的位置分别是(4,4)与(4,3)与(4,5)三个位置。

5.
相似点：两者的移动都是随机选择可以移动的位置，并不会转向；一次移动距离只是一格。
不同点：当没有东西阻挡时，CrabCritter只会随机向左或向右两个点，而Critter可以随机附近的八个点；当有东西阻挡无法随机时，CrabCritter会随机向左或向右转90度，而Critter已经无法移动。

6.
当它随机出来的位置不满足可移动情况时，它会留在原地。此时在makeMove函数中判断它的位置是否改变，若没有改变则证明它需要转向。
```java
	public void makeMove(Location loc)
    {
        if (loc.equals(getLocation()))
        {
            double r = Math.random();
            int angle;
            if (r < 0.5)
                angle = Location.LEFT;
            else
                angle = Location.RIGHT;
            setDirection(getDirection() + angle);
        }
        else
            super.makeMove(loc);
    }
```

7.
因为在Critter基类中已经设定，Critter所有子类也只能吃Actor类中非石头的东西，而不能吃Critter。而CrabCritter显然不属于Actor类，所以它无法被自己人吃掉。
```java
	public void processActors(ArrayList<Actor> actors)
    {
        for (Actor a : actors)
        {
        	//不吃石头跟Critter类
            if (!(a instanceof Rock) && !(a instanceof Critter))
                a.removeSelfFromGrid();
        }
    }
```