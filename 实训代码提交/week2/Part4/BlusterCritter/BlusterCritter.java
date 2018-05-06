package BlusterCritter;


import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A <code>BlusterCritter</code> takes on the color of neighboring actors as
 * it moves through the grid. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class BlusterCritter extends Critter
{
    private int courage;
    public BlusterCritter(int c){
        super();
        courage = c;
    }

    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        Location loc = getLocation(); //�õ���ǰλ��
        //��ǰλ�õ�����ǰ��������λ
        for(int raw = loc.getRow() - 2; raw <= loc.getRow() + 2; raw++){
            for(int column = loc.getCol() - 2; column <= loc.getCol() + 2; column++){
                Location loc2 = new Location(raw, column);
                //���жϸõ��Ƿ�Ϸ�
                if(getGrid().isValid(loc2)){
                	Actor a = getGrid().get(loc2);
                	//�������Լ�����ĵ�
                	if (a != null && a != this)
                		actors.add(a);
                }
            }
        }
        return actors;
    }

    public void processActors(ArrayList<Actor> actors)
    {
        int n = actors.size();
        //������������ֵ���䰵
        if (n > courage){
            Color c = getColor();
            int red = (int) (c.getRed());
            int green = (int) (c.getGreen());
            int blue = (int) (c.getBlue());
            if(red > 5) red -= 5;
            if(green > 5) green -= 5;
            if(blue > 5) blue -= 5;
            setColor(new Color(red, green, blue));
        }
        //����С������ֵ������
        else{
            Color c = getColor();
            int red = (int) (c.getRed() );
            int green = (int) (c.getGreen());
            int blue = (int) (c.getBlue());
            if(red < 250) red += 5;
            if(green < 250) green += 5;
            if(blue < 250) blue += 5;
            setColor(new Color(red, green, blue));
        }
        return;
    }
}
