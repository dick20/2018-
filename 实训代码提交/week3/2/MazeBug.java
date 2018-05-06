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
	
	//�ĸ������ƶ���ͳ�ƴ�������,�ĸ�����ѡ�����Ĭ�϶���1�������һ���ڵ�ѡ�����������������1������ʱ��Ҫ���١�
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
		//���ж���Ϸ�Ƿ����
		isEnd = isReachEnd();
		boolean willMove = canMove();
		//��ʼ������Ԥ��һ������
		if(stepCount==0){
			dirPredic();
			//����ջ��
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
		//�п���λ�ÿ���
		else if (willMove) {
			move();
			//increase step count when move 
			stepCount++;
		} 
		//û�п���λ�ÿ���
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
	//���޷��ҵ�����λ��ʱ��Ҫ��ջ�������ص�ǰһ��λ��������ѡ����
	private void returnLastPos() {
		//��ջ��ֻ��һ��Ԫ������Ҫ��ջʱ�����ͼû�п�ͨ��λ��
		if(crossLocation.size() > 1){
			crossLocation.pop();
			ArrayList<Location> old = crossLocation.peek();
			//��ArrayList�ĵ�һ��Ԫ��ΪҪ���صĽڵ�
			Location root = old.get(0);
			Location loc = getLocation();
			//�ƶ���ǰһ��λ�ã�������move����
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
			//����λ�ü��������߹���λ��
			flower.putSelfInGrid(gr, loc);
			//���ٸ÷�����ƶ�����
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
	//���ڼ��ٲ��������Թ���ʼǰ��Ԥ��Ҫ�ߵĴ��·���
	private void dirPredic() {
		//�ҵ�Ŀ�ĵص�λ��
		ArrayList<Location> occupied = getGrid().getOccupiedLocations();
		for( Location loc : occupied ){
			if ( getGrid().get(loc) instanceof Rock && getGrid().get(loc).getColor().equals(Color.RED)){
				//�ϱ������ж�
				if(getLocation().getRow() < loc.getRow()){
					dir_count[2] = 5;
					dir_count[0] = 1;
				}
				else{
					dir_count[0] = 5;
					dir_count[2] = 1;
				}
				//���������ж�
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

	//�ж��Ƿ��Ѿ������յ�
	public boolean isReachEnd(){
		ArrayList<Location> locs = new ArrayList<Location>();
		locs = getAllLocation(getLocation());
		for(Location loc:locs){
			//�ж���һ��λ���Ƿ�Ϊ��ɫʯͷ
			if((getGrid().get(loc) instanceof Rock) && (getGrid().get(loc).getColor()).equals(Color.RED)){
				setDirection(getLocation().getDirectionToward(loc));
				return true;
			}
		}
		return false;
	}
	//���ڼ���Ƿ��к�ʯͷ
	public ArrayList<Location> getAllLocation(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return null;
		ArrayList<Location> locs = new ArrayList<Location>();
        //�Ӷ��������ĸ���������ܵ����λ��
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
        //�Ӷ��������ĸ���������ܵ����λ��
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
		//�õ��������ߵ�λ��
		ArrayList<Location> locs = getValid(loc);
		findMaxCount(locs);
		if (gr.isValid(next)) {
			setDirection(loc.getDirectionToward(next));
			moveTo(next);
			//����ջ����ά����ջ����
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
		//����λ�ü��������߹���λ��
	}

	private void findMaxCount(ArrayList<Location> locs){
		//�ҳ���Щλ���п���������
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
		//ֻ��һ��λ�ÿ���
		if(locs.size() == 1){
			next = locs.get(0);
			//�÷��������һ
			dir_count[loc.getDirectionToward(locs.get(0))/90]++;
		}
		else{
			//����ϵͳʱ��Ϊ�������������0��10���������
			Random r = new Random();
			int num = r.nextInt(10);
			//70%�Ŀ�����
			if(num <= 6){
				next = locs.get(max_point);
				//�÷��������һ
				dir_count[loc.getDirectionToward(locs.get(max_point))/90]++;
			}
			//30%�Ŀ�����,Ĭ���ߵ�һ��
			else{
				next = locs.get(0);
				//�÷��������һ
				dir_count[loc.getDirectionToward(locs.get(0))/90]++;
			}
		}
	}

}