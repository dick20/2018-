
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;


/**
 * A <code>QuickCrab</code> looks at a limited set of neighbors when it eats and moves.
 * <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class QuickCrab extends CrabCritter
{
    public QuickCrab()
    {
        setColor(Color.ORANGE);
    }

    /**
     * @return list of empty locations immediately to the right and to the left
     */
    public ArrayList<Location> getMoveLocations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        int[] dirs =
            { Location.LEFT, Location.RIGHT };
        //寻找能否移动两步
        getLocationsInTwoSteps(locs, dirs);
        //不能则查看能否移动一步
        if (locs.size() == 0)
            return super.getMoveLocations();
        else
            return locs;
    }
    
    /**
     * Finds the valid adjacent locations of this critter in different
     * directions.
     * @param directions - an array of directions (which are relative to the
     * current direction)
     * @return a set of valid locations that are neighbors of the current
     * location in the given directions
     */
    public void getLocationsInTwoSteps(ArrayList<Location> locs, int[] directions)
    {
        Grid gr = getGrid();
        Location loc = getLocation();

        for (int i = 0; i < directions.length ;i++ ) {
            Location temp = loc.getAdjacentLocation(directions[i]);
            if (gr.isValid(temp) && gr.get(temp) == null){
                Location temp2 = temp.getAdjacentLocation(directions[i]);
                if(gr.isValid(temp2) && gr.get(temp2) == null)
                    locs.add(temp2);
            }  
        }
    }   
}
