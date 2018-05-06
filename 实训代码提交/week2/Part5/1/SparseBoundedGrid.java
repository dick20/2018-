
import info.gridworld.grid.AbstractGrid; 
import info.gridworld.grid.Location;  
import java.util.ArrayList;

//稀疏网格继承虚基类
public class SparseBoundedGrid<E> extends AbstractGrid<E>{
	private SparseGridNode[] occupantArray; // 链表头
	private int Col;
	private int Row;

	public SparseBoundedGrid(int rows, int cols)
    {
    	Col = cols;
    	Row = rows;
        if (rows <= 0)
            throw new IllegalArgumentException("rows <= 0");
        if (cols <= 0)
            throw new IllegalArgumentException("cols <= 0");
        occupantArray = new SparseGridNode[rows];
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
        ArrayList<Location> theLocations = new ArrayList<Location>();
        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++)
        {
        	SparseGridNode p = occupantArray[r]; //得到链表头
        	while(p != null){
        		Location loc = new Location(r, p.getCol());
                theLocations.add(loc);
                p = p.getNext(); //链头向前移动
        	}
        }
        return theLocations;
    }

    public E get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        SparseGridNode p = occupantArray[loc.getRow()]; //得到链表头
        while(p != null){
        	//找到列相同的
        	if(p.getCol() == loc.getCol()){
        		return (E)p.getOccupant();
        	}
        	p = p.getNext();
        }
        //找不到情况
        return null;
    }

    public E put(Location loc, E obj)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");

        // 先删除在网格中已存在的物体
        E oldOccupant = remove(loc);
        SparseGridNode p = occupantArray[loc.getRow()]; //得到链表头
        occupantArray[loc.getRow()] = new SparseGridNode(obj, loc.getCol(), p); //将新的元素加入链头，并赋值为新链头
        return oldOccupant;
    }

    public E remove(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        //找到要删除的元素
        E temp = get(loc);
        if(temp == null) 
        	return null; //无需删除

        SparseGridNode p = occupantArray[loc.getRow()]; //得到链表头
        if(p != null){
        	if(loc.getCol() == p.getCol()){//检查头元素的特殊情况
        		occupantArray[loc.getRow()] = p.getNext(); //直接将链头向前移动
        	}
        	else{
        		SparseGridNode q = p.getNext();
        		while(q != null){
        			//不等于时，指针向前移动
        			if(q.getCol() != loc.getCol()){
        				p = p.getNext();
        				q = q.getNext();
        			}
        		}
        		//找到元素
        		if(q != null){
        			SparseGridNode next_node = q.getNext();
        			p.setNext(next_node);
        		}
        	}
        }
        return temp; //返回删除元素
    }
}
