
import info.gridworld.grid.AbstractGrid; 
import info.gridworld.grid.Location;  
import java.util.ArrayList;

//ϡ������̳������
public class SparseBoundedGrid<E> extends AbstractGrid<E>{
	private SparseGridNode[] occupantArray; // ����ͷ
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
    //�õ�����
    public int getNumRows()
    {
        return Row;
    }

    public int getNumCols()
    {
        return Col;
    }
    //�ж�λ���Ƿ�Ϸ�
    public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }
    //����ռ�ݵ�λ�÷���ArrayList��
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> theLocations = new ArrayList<Location>();
        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++)
        {
        	SparseGridNode p = occupantArray[r]; //�õ�����ͷ
        	while(p != null){
        		Location loc = new Location(r, p.getCol());
                theLocations.add(loc);
                p = p.getNext(); //��ͷ��ǰ�ƶ�
        	}
        }
        return theLocations;
    }

    public E get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        SparseGridNode p = occupantArray[loc.getRow()]; //�õ�����ͷ
        while(p != null){
        	//�ҵ�����ͬ��
        	if(p.getCol() == loc.getCol()){
        		return (E)p.getOccupant();
        	}
        	p = p.getNext();
        }
        //�Ҳ������
        return null;
    }

    public E put(Location loc, E obj)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");

        // ��ɾ�����������Ѵ��ڵ�����
        E oldOccupant = remove(loc);
        SparseGridNode p = occupantArray[loc.getRow()]; //�õ�����ͷ
        occupantArray[loc.getRow()] = new SparseGridNode(obj, loc.getCol(), p); //���µ�Ԫ�ؼ�����ͷ������ֵΪ����ͷ
        return oldOccupant;
    }

    public E remove(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        //�ҵ�Ҫɾ����Ԫ��
        E temp = get(loc);
        if(temp == null) 
        	return null; //����ɾ��

        SparseGridNode p = occupantArray[loc.getRow()]; //�õ�����ͷ
        if(p != null){
        	if(loc.getCol() == p.getCol()){//���ͷԪ�ص��������
        		occupantArray[loc.getRow()] = p.getNext(); //ֱ�ӽ���ͷ��ǰ�ƶ�
        	}
        	else{
        		SparseGridNode q = p.getNext();
        		while(q != null){
        			//������ʱ��ָ����ǰ�ƶ�
        			if(q.getCol() != loc.getCol()){
        				p = p.getNext();
        				q = q.getNext();
        			}
        		}
        		//�ҵ�Ԫ��
        		if(q != null){
        			SparseGridNode next_node = q.getNext();
        			p.setNext(next_node);
        		}
        	}
        }
        return temp; //����ɾ��Ԫ��
    }
}
