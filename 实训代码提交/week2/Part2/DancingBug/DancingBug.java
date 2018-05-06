import info.gridworld.actor.Bug;

/**
 * A <code>BoxBug</code> traces out a square "box" of a given size. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class DancingBug extends Bug
{
    private int steps;
    private int[] dancing; // 控制虫子跳舞的数组

    /**
     * Constructs a box bug that traces a square of a given side length
     * @param length the side length
     */
    public DancingBug(int[] dances)
    {
        steps = 0;
        dancing = dances;
    }

    public void dance(int num){
    	for (int i = 0; i < num; i++ ) {
    		turn();
    	}	
    }
    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
        if(steps == dancing.length){
        	steps = 0; //重置
        }
        dance(dancing[steps]);
        if (canMove())
            move();
        else
            turn();   
        steps++;
    }
}
