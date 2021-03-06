package com.game.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author ruomengjiang
 * @date 2022年1月15日
 */
public class Bullet {
	private static final int SPEED = 10;
	
	public static int WIDTH = ResourceManager.bulletD.getWidth();
	public static int HEIGHT = ResourceManager.bulletD.getHeight();
	
	Rectangle rect = new Rectangle();
	
	private int x,y;
	private Dir dir;
	//子弹是否存活(出界)
	private boolean living = true;
	//阵营
	private Group group = Group.BAD;
	
	TankFrame tf = null;
	
	public Bullet(int x, int y, Dir dir, Group group, TankFrame tf) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf = tf;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
	}
	
	/**
	 * 画出bullet
	 * @param g
	 */
	public void paint(Graphics g) {
		
		if(!living) {
			tf.bullets.remove(this);
		}
		
		switch (dir) {
		case LEFT:
			g.drawImage(ResourceManager.bulletL, x, y, null);
			break;
		case UP:
			g.drawImage(ResourceManager.bulletU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(ResourceManager.bulletR, x, y, null);
			break;
		case DOWN:
			g.drawImage(ResourceManager.bulletD, x, y, null);
			break;
	}
		//移动
		move();
	}
	
	/**
	 * 子弹移动
	 */
	private void move() {
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
		
		//update rect
		rect.x = this.x;
		rect.y = this.y;
		
		if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT ) {
			living = false;
		}
	}

	/**
	 * bullet与tank相撞
	 * @param tank
	 */
	public void collideWith(Tank tank) {
		if (this.group == tank.getGroup()) {
			return;
		} 
		//TODO: 用一个rect来记录bullet的位置
		//Rectangle rect1 = new Rectangle(this.x,this.y,WIDTH,HEIGHT);
		//Rectangle rect2 = new Rectangle(tank.getX(),tank.getY(),tank.WIDTH,tank.HEIGHT);
		if(rect.intersects(tank.rect)) {
			tank.die();
			this.die();
			int eX = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
			int eY = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
			tf.explodes.add(new Explode(eX,eY,tf));
		}
	}

	/**
	 * bullet die
	 */
	private void die() {
		this.living = false;
	}
	
	
	
}
