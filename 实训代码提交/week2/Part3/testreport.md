#testreport

我的测试jumper的功能主要围绕两个方面，转向的正确性以及移动的正确性。

一.测试jumper能否正确转向
```java
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
```
二.测试jumper的移动又分为多个部分。
1. jumper前进方向的第一个格子为空时，分别对第二个格子里面的东西进行分类测试。
A.第二个格子为石头
```java
//检查第一个网格为空，第二个网格为岩石，是否转向
jumper.moveTo(new Location(6,6));
jumper.setDirection(NORTH);
world.add(new Location(4,6),new Rock());
assertEquals(Location.NORTHEAST, jumper.getDirection());
 ```
B.第二个格子为花朵
 ```java
//检查第一个网格为空，第二个网格为花朵，是否继续前进
jumper.moveTo(new Location(6,6));
jumper.setDirection(NORTH);
world.add(new Location(4,6),new Flower());
assertEquals("(4, 6)", jumper.getLocation().toString());
 ```
2.jumper前进方向的第一个格子不为空时，对里面的东西进行分类测试
A. 第一个格子为石头
```java
//检查石头在中间情况
jumper.moveTo(new Location(5,5));
world.add(new Location(4, 5),new Rock());
jumper.act();
assertEquals("(3, 5)", jumper.getLocation().toString());
```
B. 第一个格子为花朵或昆虫
```java
//检查花朵在中间情况
jumper.moveTo(new Location(5,5));
world.add(new Location(4, 5),new Flower());
jumper.act();
assertEquals("(3, 5)", jumper.getLocation().toString());
//检查昆虫在中间的情况
jumper.moveTo(new Location(5,5));
world.add(new Location(4, 5),new Bug());
jumper.act();
assertEquals("(3, 5)", jumper.getLocation().toString());
```
C. 第一个格子为另外一个jumper
```java
//检查跳跃路径上有jumper,是否转方向
jumper.moveTo(new Location(5,5));
jumper2 = new Jumper();
world.add(new Location(4, 5),jumper2);
jumper.act();
assertEquals(Location.NORTHEAST, jumper.getDirection());
```
3. jumper前进方向的边界问题
A. jumper的第二个格子越出边界
```java
//检查前进第二个网格超出边缘，是否转向
jumper.moveTo(new Location(1,1));
jumper.setDirection(NORTH);
assertEquals(Location.NORTHEAST, jumper.getDirection());
 ```
B. jumper的第一个格子越出边界
```java
//检查前进第一个网格为边缘，是否转向
jumper.moveTo(new Location(1,0));
jumper.setDirection(NORTH);
assertEquals(Location.NORTHEAST, jumper.getDirection());
 ```

------
测试效果如图所示：
