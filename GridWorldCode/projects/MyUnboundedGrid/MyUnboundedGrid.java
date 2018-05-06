package MyUnboundedGrid;
import info.gridworld.grid.AbstractGrid; 
import info.gridworld.grid.Location;  
import java.util.ArrayList;
import info.gridworld.grid.*;

import java.util.ArrayList;

//�?疏网格继承虚基类
public class MyUnboundedGrid<E> extends AbstractGrid<E>{
	private Object[][] occupantArray;
	private int Row;

	public MyUnboundedGrid()
    {
    	Row = 16;
        occupantArray = new Object[Row][Row];
    }
    public MyUnboundedGrid(int num)
    {
        Row = num;
        occupantArray = new Object[Row][Row];
    }
    //得到行列
    public int getNumRows()
    {
        return -1;
    }

    public int getNumCols()
    {
        return -1;
    }
    //判断位置是否合法
    public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && 0 <= loc.getCol();
    }
    //将被占据的位置放入ArrayList�?
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        //遍历全图
        for(int row = 0; row < Row; row++){
            for(int col = 0; col < Row; col++){
                Location loc = new Location(row,col);
                if(get(loc) != null){
                    locs.add(loc);
                }
            }
        }
        return locs;
    } 

    public E get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        //超出边界
        if(loc.getRow() >= Row || loc.getCol() >= Row){
            return null;
        }
        return (E) occupantArray[loc.getRow()][loc.getCol()]; 
    }

    public E put(Location loc, E obj)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");
        //超出边界
        if(loc.getRow() >= Row || loc.getCol() >= Row){
            int new_size = Row;
            while(loc.getRow() >= new_size || loc.getCol() >= new_size){
                new_size *= 2; //扩大两�??
            }
            //新建
            Object [][] new_array = new Object[new_size][new_size];
            //复制
            for(int row = 0; row < Row; row++){
                for(int col = 0; col < Row; col++){
                    new_array[row][col] = occupantArray[row][col];
                }
            } 
            Row = new_size;
            //转移
            occupantArray = new_array;
        }
        E old_occupant = get(loc);
        //插入
        occupantArray[loc.getRow()][loc.getCol()] = obj;  
        return old_occupant; //返回原来�?
    }

    public E remove(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if(loc.getRow() >= Row || loc.getCol() >= Row){ //超出边界
            return null;
        }
        E temp_occupant = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = null;  
        return temp_occupant;
    }
}
