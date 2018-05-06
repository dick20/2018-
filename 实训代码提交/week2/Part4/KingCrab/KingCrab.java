import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;


/**
 * A <code>QuickCrab</code> looks at a limited set of neighbors when it eats and moves.
 * <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class KingCrab extends CrabCritter
{
    public KingCrab()
    {
        setColor(Color.BLACK);
    }
    
    public void processActors(ArrayList<Actor> actors)
    {
        for(Actor a : actors){
            //�鿴�Ƿ����ƶ�
            if(!moveObject(a)){
                //�����ƶ�������ֱ��ɾ��
                a.removeSelfFromGrid();
            }
        }
    }

    private boolean moveObject(Actor a){
        //�ҳ�����з�����Ŀ���λ��
        ArrayList<Location> locs = getGrid().getEmptyAdjacentLocations(a.getLocation());
        Location temp = getLocation();
        for(Location loc : locs){
        	//�ƶ����������з����1�ĵط�
            if(distance(temp, loc) > 1){
                a.moveTo(loc);
                return true;
            }
        }
        return false;
    }
    //����������룬����ȡ��
    private int distance(Location loc1, Location loc2){
        int col1 = loc1.getCol();
        int col2 = loc2.getCol();
        int row1 = loc1.getRow();
        int row2 = loc2.getCol();
        double dis = Math.sqrt((col1 - col2) * (col1 - col2) + (row1 - row2) * (row1 - row2));
        return (int)Math.ceil(dis);
    }   
}
