import static org.junit.Assert.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

import org.junit.Test;

public class JumperTest {
	@Test 
	public void testTurn() {
		ActorWorld world = new ActorWorld();
		Jumper jumper = new Jumper();
		world.add(new Location(2, 2), jumper);
		//初始方向为东边
		jumper.setDirection(Location.EAST);
		assertEquals(Location.EAST, jumper.getDirection());
		jumper.turn();
		jumper.turn();
		//两次转向后的方向应该为南边
		assertEquals(Location.SOUTH, jumper.getDirection());
		jumper.turn();
		jumper.turn();
		//再两次转向后的方向应该为西边
		assertEquals(Location.WEST, jumper.getDirection());
		jumper.turn();
		jumper.turn();
		//再两次转向后的方向应该为北边
		assertEquals(Location.NORTH, jumper.getDirection());
	}
	@Test
	public void testAct() {
		ActorWorld world = new ActorWorld();
		Jumper jumper = new Jumper();
		world.add(new Location(2, 2), jumper);
		jumper.act();
		assertEquals("(0, 2)", jumper.getLocation().toString());
		//检查石头在中间情况
		jumper.moveTo(new Location(5,5));
		world.add(new Location(4, 5),new Rock());
		jumper.act();
		assertEquals("(3, 5)", jumper.getLocation().toString()); 
		//检查跳跃路径上有jumper,是否转方向
		jumper.moveTo(new Location(5,5));
		jumper2 = new Jumper();
		world.add(new Location(4, 5),jumper2);
		jumper.act();
		assertEquals(Location.NORTHEAST, jumper.getDirection()); 
		//检查第一个网格为空，第二个网格为花朵，是否继续前进
		jumper.moveTo(new Location(6,6));
		jumper.setDirection(NORTH);
		world.add(new Location(4,6),new Flower());
		assertEquals("(4, 6)", jumper.getLocation().toString()); 
		//检查第一个网格为空，第二个网格为岩石，是否转向
		jumper.moveTo(new Location(6,6));
		jumper.setDirection(NORTH);
		world.add(new Location(4,6),new Rock());
		assertEquals(Location.NORTHEAST, jumper.getDirection());
		//检查前进第一个网格为边缘，是否转向
		jumper.moveTo(new Location(1,0));
		jumper.setDirection(NORTH);
		assertEquals(Location.NORTHEAST, jumper.getDirection());
		//检查前进第二个网格超出边缘，是否转向
		jumper.moveTo(new Location(1,1));
		jumper.setDirection(NORTH);
		assertEquals(Location.NORTHEAST, jumper.getDirection());
	}

	public static void main(String args[]) {
        Result result = JUnitCore.runClasses(Jumper.class);
        for(Failure failure : result.getFailures())
            System.out.println(failure.toString());
        System.out.println(result.wasSuccessful());
    } 
}
