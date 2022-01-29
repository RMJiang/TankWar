package com.game.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import com.game.tank.abstractfactory.BaseTank;

/**
 * @author ruomengjiang
 * @date 2022年1月15日
 */
public class Tank extends BaseTank{
	//x y坐标
	int x,y;
	//方向
	Dir dir = Dir.DOWN;
	//速度
	private static final int SPEED = 5;
	//静止
	private boolean moving = true;
	//是否存活
	private boolean living = true;
	//随机数
	private Random random = new Random();
	//阵营
	Group group = Group.BAD;
	
	Rectangle rect = new Rectangle();
	
	public static int WIDTH = ResourceManager.goodTankU.getWidth();
	public static int HEIGHT = ResourceManager.goodTankU.getHeight();
	
	TankFrame tf = null;
	
	FireStrategy fs;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf = tf;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
		
		if(group == Group.GOOD) {
			String goodFSName = (String)PropertyManager.get("goodFS");
			
			try {
				fs=(FireStrategy)Class.forName(goodFSName).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		else {
			fs = new DefaultFireStrategy();
		}
	}
	
	/**
	 * 画出tank
	 * @param g
	 */
	public void paint(Graphics g) {
		
		if(!living) {
			tf.tanks.remove(this);
		}
		
		switch (dir) {
			case LEFT:
				g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankL : ResourceManager.badTankL, x, y, null);
				break;
			case UP:
				g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankU : ResourceManager.badTankU, x, y, null);
				break;
			case RIGHT:
				g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankR : ResourceManager.badTankR, x, y, null);
				break;
			case DOWN:
				g.drawImage(this.group == Group.GOOD ? ResourceManager.goodTankD : ResourceManager.badTankD, x, y, null);
				break;
		}
		
		//移动
		move();
	}

	/**
	 * 移动
	 */
	private void move() {
		//判断是否是停止状态
		if(!moving) {
			return;
		}
		//判断方向
		switch(dir) {
		case LEFT:
			x-=SPEED;
			break;
		case UP:
			y-=SPEED;
			break;
		case RIGHT:
			x+=SPEED;
			break;
		case DOWN:
			y+=SPEED;
			break;
		}
		
		if(this.group == Group.BAD && random.nextInt(100) > 95) {
			this.fire();
		}
		if(this.group == Group.BAD && random.nextInt(100)> 95) {
			randomDir();
		}
		
		boundsCheck();
		
		//update rect
		rect.x = this.x;
		rect.y = this.y;
		
	}

	/**
	 * 边界检测
	 */
	private void boundsCheck() {
		if (this.x<0) {
			x=0;
		}
		if (this.y <= 30) {
			y=30;
		}
		if (this.x>TankFrame.GAME_WIDTH-Tank.WIDTH) {
			x=TankFrame.GAME_WIDTH - Tank.WIDTH;
		}
		if (this.y> TankFrame.GAME_HEIGHT-Tank.HEIGHT) {
			y=TankFrame.GAME_HEIGHT - Tank.HEIGHT;
		}
	}

	/**
	 * 随机改变方向
	 */
	private void randomDir() {

		this.dir = Dir.values()[random.nextInt(4)];
	}

	/**
	 * 开火 打出bullets
	 */
	public void fire() {
		fs.fire(this);
	}

	/**
	 * tank die
	 */
	public void die() {
		this.living = false;
	}
	
}
