import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;
import java.awt.Color;

/**
 * A <code>Jumper</code> is an actor that can jump. It ¡°jumps¡± over rocks and flowers. It does not leave anything behind it when it jumps. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class Jumper extends Actor
{
    /**
     * Constructs a Yellow Jumper.
     */
    public Jumper()
    {
        setColor(Color.YELLOW);
    }

    /**
     * Constructs a Jumper of a given color.
     * @param JumperColor the color for this Jumper
     */
    public Jumper(Color JumperColor)
    {
        setColor(JumperColor);
    }

    /**
     * Jumps if it can jump, turns otherwise.
     */
    public void act()
    {
        if (canJump())
            jump();
        else
            turn();
    }

    /**
     * Turns the Jumper 45 degrees to the right without changing its location.
     */
    public void turn()
    {
        setDirection(getDirection() + Location.HALF_RIGHT);
    }

    /**
     * It ¡°jumps¡± over rocks and flowers. It does not leave anything behind it when it jumps.
     */
    public void jump()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        Location jump_next = next.getAdjacentLocation(getDirection());

        if (gr.isValid(jump_next))
            moveTo(jump_next);
        else
            removeSelfFromGrid();
    }

    /**
     * Tests whether this Jumper can move forward into a location that is empty or
     * contains a flower.
     * @return true if this Jumper can move.
     */
    public boolean canJump()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        Location jump_next = next.getAdjacentLocation(getDirection()); 
        //if the jumper faces other Bugs or the edge, it cannot jump
        if (!gr.isValid(next))
            return false;
        Actor neighbor = gr.get(next);
        if( !( (neighbor instanceof Flower) || (neighbor instanceof Rock) || (neighbor == null) ) ) 
            return false;
        //if the destination of the jumper is not flowers of null, it cannot jump
        if (!gr.isValid(jump_next))
            return false;
        neighbor = gr.get(jump_next);
        return (neighbor == null) || (neighbor instanceof Flower);
        // ok to move into empty location or onto flower
        // not ok to move onto any other actor
    }
}
