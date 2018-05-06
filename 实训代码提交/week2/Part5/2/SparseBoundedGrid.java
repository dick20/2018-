import info.gridworld.grid.AbstractGrid; 
import info.gridworld.grid.Location;  
import java.util.ArrayList;
import info.gridworld.grid.*;

import java.util.Map; 
import java.util.HashMap;
import java.util.ArrayList;

//稀疏网格继承虚基类
public class SparseBoundedGrid<E> extends AbstractGrid<E>{
	private Map<Location, E> occupantMap;
	private int Col;
	private int Row;

	public SparseBoundedGrid(int rows, int cols)
    {
    	Col = cols;
    	Row = rows;
    	occupantMap = new HashMap<Location, E>();
        if (rows <= 0)
            throw new IllegalArgumentException("rows <= 0");
        if (cols <= 0)
            throw new IllegalArgumentException("cols <= 0");
    }
	//得到行列
    public int getNumRows()
    {
        return Row;
    }

    public int getNumCols()
    {
        return Col;
    }
  //判断位置是否合法
    public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }
  //将被占据的位置放入ArrayList中
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> a = new ArrayList<Location>();
        for (Location loc : occupantMap.keySet()){
        	a.add(loc);
        }
        return a;
    } 

    public E get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        return occupantMap.get(loc); //直接调用Map中get函数
    }

    public E put(Location loc, E obj)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");

        return occupantMap.put(loc,obj); //直接调用Map中put函数
    }

    public E remove(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        
        return occupantMap.remove(loc); //直接调用Map中remove函数
    }
}
