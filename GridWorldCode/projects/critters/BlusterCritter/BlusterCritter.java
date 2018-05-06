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
        Location loc = getLocation(); //得到当前位置
        //当前位置的左右前后两个单位
        for(int raw = loc.getRow() - 2; raw <= loc.getRow() + 2; raw++){
            for(int column = loc.getCol() - 2; column <= loc.getCol() + 2; column++){
                Location loc2 = new Location(raw, column);
                //先判断该点是否合法
                if(getGrid().isValid(loc2)){
                	Actor a = getGrid().get(loc2);
                	//不能是自己本身的点
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
        //个数大于勇气值，变暗
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
        //个数小于勇气值，变亮
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
