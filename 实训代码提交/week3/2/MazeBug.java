package info.gridworld.maze;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	public Location next;
	public Location last;
	public boolean isEnd = false;
	public Stack<ArrayList<Location>> crossLocation = new Stack<ArrayList<Location>>();
	public Integer stepCount = 0;
	//final message has been shown
	boolean hasShown = false;
	
	//四个方向移动的统计次数数组,四个方向选择次数默认都是1，如果第一个节点选择向左，则向左次数加1，回溯时需要减少。
	private int[] dir_count = {1,1,1,1};

	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public MazeBug() {
		setColor(Color.GREEN);
		last = new Location(0, 0);
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		//先判断游戏是否结束
		isEnd = isReachEnd();
		boolean willMove = canMove();
		//初始化，先预测一个方向
		if(stepCount==0){
			dirPredic();
			//加入栈中
			ArrayList<Location> first = new ArrayList<Location>();
			Location loc = getLocation();
			first.add(loc);
			crossLocation.add(first);
		}
		if (isEnd == true) {
		//to show step count when reach the goal		
			if (hasShown == false) {
				stepCount++;
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		}
		//有空余位置可走
		else if (willMove) {
			move();
			//increase step count when move 
			stepCount++;
		} 
		//没有空余位置可走
		else{
			returnLastPos();
			//increase step count when return 
			stepCount++;
		}
		//test for dir_count
		for(int i = 0; i < 4; i++){
			System.out.print(dir_count[i] + " ");
		}
		System.out.println();
		
	}
	//当无法找到空余位置时，要退栈操作，回到前一个位置再重新选择方向
	private void returnLastPos() {
		//若栈中只有一个元素且需要退栈时，这幅图没有可通行位置
		if(crossLocation.size() > 1){
			crossLocation.pop();
			ArrayList<Location> old = crossLocation.peek();
			//该ArrayList的第一个元素为要返回的节点
			Location root = old.get(0);
			Location loc = getLocation();
			//移动到前一个位置，类似于move函数
			Grid<Actor> gr = getGrid();
			int dir = (int)loc.getDirectionToward(root)/90;
			if (gr == null)
				return;
			if (gr.isValid(root)) {
				setDirection(loc.getDirectionToward(root));
				moveTo(root);
			} 
			else{
				removeSelfFromGrid();
			}
			Flower flower = new Flower(getColor());
			//花的位置即昆虫所走过的位置
			flower.putSelfInGrid(gr, loc);
			//减少该方向的移动次数
			switch (dir) {
				case 0:
					dir_count[2]--;
					break;
				case 1:
					dir_count[3]--;
					break;
				case 2:
					dir_count[0]--;
					break;
				case 3:
					dir_count[1]--;
					break;
			}
		}
	}
	//用于减少步数，在迷宫开始前先预设要走的大致方向
	private void dirPredic() {
		//找到目的地的位置
		ArrayList<Location> occupied = getGrid().getOccupiedLocations();
		for( Location loc : occupied ){
			if ( getGrid().get(loc) instanceof Rock && getGrid().get(loc).getColor().equals(Color.RED)){
				//南北方向判断
				if(getLocation().getRow() < loc.getRow()){
					dir_count[2] = 5;
					dir_count[0] = 1;
				}
				else{
					dir_count[0] = 5;
					dir_count[2] = 1;
				}
				//东西方向判断
				if(getLocation().getCol() < loc.getCol()){
					dir_count[1] = 5;
					dir_count[3] = 1;
				}
				else{
					dir_count[3] = 5;
					dir_count[1] = 1;
				}
				break;
			}
		}
	}

	//判断是否已经到达终点
	public boolean isReachEnd(){
		ArrayList<Location> locs = new ArrayList<Location>();
		locs = getAllLocation(getLocation());
		for(Location loc:locs){
			//判断下一个位置是否为红色石头
			if((getGrid().get(loc) instanceof Rock) && (getGrid().get(loc).getColor()).equals(Color.RED)){
				setDirection(getLocation().getDirectionToward(loc));
				return true;
			}
		}
		return false;
	}
	//用于检测是否有红石头
	public ArrayList<Location> getAllLocation(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return null;
		ArrayList<Location> locs = new ArrayList<Location>();
        //从东南西北四个方向查找能到达的位置
        int dir = Location.NORTH;
        for(int i = 0; i < 4; i++){
            Location loca = loc.getAdjacentLocation(dir);
            if (gr.isValid(loca)){
                locs.add(loca);
            }
            dir = dir + Location.RIGHT;
        }
		return locs;
	}

	/**
	 * Find all positions that can be move to.
	 * 
	 * @param loc
	 *            the location to detect.
	 * @return List of positions.
	 */
	public ArrayList<Location> getValid(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return null;
		ArrayList<Location> valid = new ArrayList<Location>();
        //从东南西北四个方向查找能到达的位置
        int dir = Location.NORTH;
        for(int i = 0; i < 4; i++){
            Location loca = loc.getAdjacentLocation(dir);
            if (gr.isValid(loca) && getGrid().get(loca) == null ){
                valid.add(loca);
            }
            dir = dir + Location.RIGHT;
        }
		return valid;
	}
	/**
	 * Tests whether this bug can move forward into a location that is flower
	 * 
	 * @return true if this bug can move.
	 */
	public boolean canMove() {
		ArrayList<Location> locs = getValid(getLocation());
		if(locs.size() == 0){
			return false;
		}
		return true;
	}
	/**
	 * Moves the bug forward, putting a flower into the location it previously
	 * occupied.
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return;
		Location loc = getLocation();
		//得到所有能走的位置
		ArrayList<Location> locs = getValid(loc);
		findMaxCount(locs);
		if (gr.isValid(next)) {
			setDirection(loc.getDirectionToward(next));
			moveTo(next);
			//入新栈，并维护旧栈操作
			ArrayList<Location> old = crossLocation.pop();
			old.add(next);
			crossLocation.push(old);	
			ArrayList<Location> latest = new ArrayList<Location>();
			latest.add(next);
			crossLocation.push(latest);
		} 
		else{
			removeSelfFromGrid();
		}
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, loc);
		//花的位置即昆虫所走过的位置
	}

	private void findMaxCount(ArrayList<Location> locs){
		//找出这些位置中可能性最大的
		Location loc = getLocation();
		int max = 0;
		int max_point = 0;
		for(int i = 0; i < locs.size(); i++){
			int direction = loc.getDirectionToward(locs.get(i))/90;
			if(dir_count[direction] > max){
				max = dir_count[direction];
				max_point = i;
			}
		}
		//只有一个位置可走
		if(locs.size() == 1){
			next = locs.get(0);
			//该方向次数加一
			dir_count[loc.getDirectionToward(locs.get(0))/90]++;
		}
		else{
			//根据系统时间为种子生成随机数0到10区间的整数
			Random r = new Random();
			int num = r.nextInt(10);
			//70%的可能性
			if(num <= 6){
				next = locs.get(max_point);
				//该方向次数加一
				dir_count[loc.getDirectionToward(locs.get(max_point))/90]++;
			}
			//30%的可能性,默认走第一个
			else{
				next = locs.get(0);
				//该方向次数加一
				dir_count[loc.getDirectionToward(locs.get(0))/90]++;
			}
		}
	}

}