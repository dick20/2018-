
import info.gridworld.actor.Bug;
import info.gridworld.grid.Location;

/**
 * A <code>BoxBug</code> traces out a square "box" of a given size. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class ZBug extends Bug
{
    private int steps;
    private int sideLength;
    private int patten; // 0表示Z的最上面，1表示Z的中间，2表示Z的最下面

    /**
     * Constructs a box bug that traces a square of a given side length
     * @param length the side length
     */
    public ZBug(int length)
    {
        steps = 0;
        sideLength = length;
        patten = 0;
        setDirection(Location.EAST); //将虫子的方向先设为往东
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
        if (steps < sideLength && canMove() && patten < 3)
        {
            move();
            steps++;
        }
        else if (patten == 0)
        {
            setDirection(Location.SOUTHWEST);
            patten = 1; // 模式切换为Z的中间
            steps = 0;
        }
        else if (patten == 1){
        	setDirection(Location.EAST);
            patten = 2; // 模式切换为Z的最下面
            steps = 0;
        }
    }
}
