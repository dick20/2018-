
public class SparseGridNode
{
    private Object occupant;
    private int col;
    private SparseGridNode next;
    
    //���캯��
    public SparseGridNode(Object o, int num, SparseGridNode temp){
        occupant = o;
        col = num;
        next = temp;
    }
    //get��set����
    public Object getOccupant(){
        return occupant;
    }
    public void setOccupant(Object o){
        occupant = o;
    }
    public int getCol(){
        return col;
    }
    public void setCol(int num){
        col = num;
    }
    public SparseGridNode getNext(){
        return next;
    }
    public void setNext(SparseGridNode temp){
        next = temp;
    }   
}
