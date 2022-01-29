package com.game.tank;

import java.awt.Color;
import java.awt.Graphics;

import com.game.tank.abstractfactory.BaseExplode;

/**
 * @author ruomengjiang
 * @date 2022年1月15日
 */
public class Explode extends BaseExplode{
	public static int WIDTH = ResourceManager.explodes[0].getWidth();
	public static int HEIGHT = ResourceManager.explodes[0].getHeight();
	
	private int x,y;
	
	private TankFrame tf = null;
	
	private int step = 0;

	public Explode(int x, int y, TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.tf = tf;
		
		//new Thread(()->new Audio("audio/explode.wav").play()).start();
	}
	
	/**
	 * 画出explode
	 * @param g
	 */
	public void paint(Graphics g) {

		g.drawImage(ResourceManager.explodes[step++], x, y, null);
		if(step>=ResourceManager.explodes.length) {
			tf.explodes.remove(this);
		}
	}
	
}
