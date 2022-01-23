package com.game.tank;

/**
 * @author ruomengjiang
 * @date 2022年1月13日
 */
public class T {

	public static void main(String[] args) throws InterruptedException {
		
		TankFrame tf = new TankFrame();
		
		int initTankCount = Integer.parseInt((String)PropertyManager.get("initTankCount"));
		
		//初始化敌方tank
		for(int i=0;i<initTankCount;i++) {
			tf.tanks.add(new Tank(50 + i*80, 200, Dir.DOWN, Group.BAD, tf));
		}
		//new Thread(()->new Audio("audio/war1.wav").loop()).start();
		
		//循环让tank每隔50ms自动运动
		while(true) {
			Thread.sleep(50);
			tf.repaint();
		}
		
		
	}

}

