set10
1.
isValid()是在Grid的接口函数，判断位置是否合法。UnboundedGrid类以及BoundedGrid类提供了这个方法的实现

```java
public interface Grid<E>
{
    /**
     * Checks whether a location is valid in this grid. <br />
     * Precondition: <code>loc</code> is not <code>null</code>
     * @param loc the location to check
     * @return <code>true</code> if <code>loc</code> is valid in this grid,
     * <code>false</code> otherwise
     */
    boolean isValid(Location loc);
}
```

2.
getValidAdjacentLocations()函数直接调用了isValid()函数来判断位置是否合法，而其他函数没有直接调用isValid()，但是调用getValidAdjacentLocatons()函数，也就是它们间接调用isValid()来判断位置，因为getValidAdjacentLocatons()传递的参数本来就已经合法了。
```java
	public ArrayList<Location> getValidAdjacentLocations(Location loc)
    {
        ArrayList<Location> locs = new ArrayList<Location>();

        int d = Location.NORTH;
        for (int i = 0; i < Location.FULL_CIRCLE / Location.HALF_RIGHT; i++)
        {
            Location neighborLoc = loc.getAdjacentLocation(d);
            if (isValid(neighborLoc))
                locs.add(neighborLoc);
            d = d + Location.HALF_RIGHT;
        }
        return locs;
    }
```

3.
getNeighbors(Location loc)调用了Grid接口中的getOccupiedAdjacentLocations()以及get()这两个函数。getOccupiedAdjacentLocations()的实现在AbstractGrid类，而get()的实现在UnboundedGrid类。
```java
	public ArrayList<E> getNeighbors(Location loc)
    {
        ArrayList<E> neighbors = new ArrayList<E>();
        for (Location neighborLoc : getOccupiedAdjacentLocations(loc))
            neighbors.add(get(neighborLoc));
        return neighbors;
    }
```
4.
get()的实现在UnboundedGrid类，如果位置为空时，则返回null；而如果位置有对象时，返回该对象。getValidAdjacentLocations()调用该函数仅仅是用来判断该位置是否有对象，若无则为空余位置。

```java
	public E get(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        return occupantMap.get(loc);
    }
```
5.
若替换，那么函数就不是返回八个方向的合法空闲位置，而仅仅返回东南西北这四个方向的合法空闲位置。
```java
	public ArrayList<Location> getValidAdjacentLocations(Location loc)
    {
        ArrayList<Location> locs = new ArrayList<Location>();

        int d = Location.NORTH;
        for (int i = 0; i < Location.FULL_CIRCLE / Location.HALF_RIGHT; i++)
        {
            Location neighborLoc = loc.getAdjacentLocation(d);
            if (isValid(neighborLoc))
                locs.add(neighborLoc);
            d = d + Location.HALF_RIGHT;
        }
        return locs;
    }
```

set11
1.
可以从构造函数看出，rows与cols不能小于等于0，代表网格个数必须大于等于1
```java
 	public BoundedGrid(int rows, int cols)
    {
        if (rows <= 0)
            throw new IllegalArgumentException("rows <= 0");
        if (cols <= 0)
            throw new IllegalArgumentException("cols <= 0");
        occupantArray = new Object[rows][cols];
    }
```
2.
在getNumCols()函数中通过返回occupantArray[0].length来得到列。因为根据构造函数保证了行数一定大于0，所以theGrid[0]是非空的。
```java
	public int getNumCols()
    {
        // Note: according to the constructor precondition, numRows() > 0, so
        // theGrid[0] is non-null.
        return occupantArray[0].length;
    }
```
3.
可以通过isValid()函数可以看出，位置的行列必须大于等于0而且不能超过构造函数所构造的最大行列数。
```java
	public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }
```
4.
getOccupiedLocations()的返回值是一个Location的ArrayList。这个函数时间复杂度为O(r*c)。
```java
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
```
5.
get()函数的返回值为E，所需要的参数为一个Location变量，它的时间复杂度为O(1)。
```java
 	public E get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        return (E) occupantArray[loc.getRow()][loc.getCol()]; // unavoidable warning
    }
```

6.
当位置Location不合法或者传入的obj为空时，该函数会抛出异常。它的时间复杂度为O(1)。
```java
	public E put(Location loc, E obj)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");

        // Add the object to the grid.
        E oldOccupant = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = obj;
        return oldOccupant;
    }
```

7.
remove()函数返回类型为E，当从一个空余位置移动一个物体时，函数不会抛出异常，只是返回值为null。它的时间复杂度为O(1)。
```java
	public E remove(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        
        // Remove the object from the grid.
        E r = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = null;
        return r;
    }
```
8.
remove(), get(), put()这三个函数的时间复杂度都仅为O(1),一定是最有效的实现。而getOccupiedLocations()函数的时间复杂度为O(r*c),如果我们换用其它数据结构储存对象（如HashMap，TreeMap等等)，就不必用两次for循环查找。

```java
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
public E get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        return (E) occupantArray[loc.getRow()][loc.getCol()]; // unavoidable warning
    }
public E put(Location loc, E obj)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");

        // Add the object to the grid.
        E oldOccupant = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = obj;
        return oldOccupant;
    }
public E remove(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        
        // Remove the object from the grid.
        E r = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = null;
        return r;
    }
```

set12
1.
要想在UnboundedGrid类中使用HashMap必须在Location类中实现hashCode()函数来生成唯一的hash对应码。然后还要实现equals()函数来查找hash表对应的元素。要想使用TreeMap就需要实现接口Comparable，而接口中需要实例化一个比较函数compareTo()函数。在给出的Location()类满足上述要求，实现了这三个函数。
```java
    public boolean equals(Object other)
    {
        if (!(other instanceof Location))
            return false;

        Location otherLoc = (Location) other;
        return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
    }
    public int hashCode()
    {
        return getRow() * 3737 + getCol();
    }
    public int compareTo(Object other)
    {
        Location otherLoc = (Location) other;
        if (getRow() < otherLoc.getRow())
            return -1;
        if (getRow() > otherLoc.getRow())
            return 1;
        if (getCol() < otherLoc.getCol())
            return -1;
        if (getCol() > otherLoc.getCol())
            return 1;
        return 0;
    }
```
2.
因为这个无边界网格无法判断位置是否合法，但是实现是利用HashMap，如果位置为null的情况，返回也会是null值，仅表示该位置没有对象，而不是位置不合法。这显然与无边界网格的定义不符合，所以需要在这三个函数判断位置是否为null。

因为在有边界网格中，如果传入位置为null时，会在isValid函数中抛出异常，不会访问到这个位置，所以在有边界网格不需要在这三个函数判断位置是否为null。

```java
	public E get(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        return occupantMap.get(loc);
    }

    public E put(Location loc, E obj)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        if (obj == null)
            throw new NullPointerException("obj == null");
        return occupantMap.put(loc, obj);
    }

    public E remove(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        return occupantMap.remove(loc);
    }
```

3.
在利用HashMap实现的无边界网格中，这三个函数的平均复杂度为O(1);而利用TreeMap实现的无边界网格中，由于需要在一颗树中查找数据，所以这三个函数的平均复杂度变为O(log n)，n为树的节点数目，即网格中被占据的元素个数。

```java
	public E get(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        return occupantMap.get(loc);
    }

    public E put(Location loc, E obj)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        if (obj == null)
            throw new NullPointerException("obj == null");
        return occupantMap.put(loc, obj);
    }

    public E remove(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        return occupantMap.remove(loc);
    }
```

4.
如果利用TreeMap替代HashMap实现无边界网格，那么他们最大的区别就是储存的方式以及储存元素的顺序。TreeMap利用的是Location中的compareTo函数实现一棵平衡二叉树，元素排列是由大到小；而HashMap利用的hash函数，即Location中equal()以及hashCode()两个函数，按照一定的映射关系存储元素。

```java
	public boolean equals(Object other)
    {
        if (!(other instanceof Location))
            return false;

        Location otherLoc = (Location) other;
        return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
    }
    public int hashCode()
    {
        return getRow() * 3737 + getCol();
    }
    public int compareTo(Object other)
    {
        Location otherLoc = (Location) other;
        if (getRow() < otherLoc.getRow())
            return -1;
        if (getRow() > otherLoc.getRow())
            return 1;
        if (getCol() < otherLoc.getCol())
            return -1;
        if (getCol() > otherLoc.getCol())
            return 1;
        return 0;
    }
```

5.
理所当然，map这一数据结构也可以使用在有边界网格中，但是用二维数组会更加好。因为利用hashmap查找元素索引时，当元素特别多，那么这个查找可能会重复数十次，甚至会出现查找不到位置的情况，显然是非常浪费时间的。
```java
	public boolean equals(Object other)
    {
        if (!(other instanceof Location))
            return false;

        Location otherLoc = (Location) other;
        return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
    }
    public int hashCode()
    {
        return getRow() * 3737 + getCol();
    }
```

code exercise
1.
getOccupiedLocation(),get(),put(),remove()这四个函数不需要改变就可以直接使用