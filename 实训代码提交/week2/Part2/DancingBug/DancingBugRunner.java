
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

/**
 * This class runs a world that contains box bugs. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class DancingBugRunner
{
    public static void main(String[] args)
    {
    	int[] dance = {3,1,3};
        ActorWorld world = new ActorWorld();
        DancingBug bob = new DancingBug(dance);
        world.add(new Location(4, 4), bob);
        world.show();
    }
}