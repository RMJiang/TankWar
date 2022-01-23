package com.game.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ruomengjiang
 * @date 2022年1月13日
 */
public class TankFrame extends Frame{

	Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);
	List<Bullet> bullets = new ArrayList<>();
	List<Tank> tanks = new ArrayList<>();
	List<Explode> explodes = new ArrayList<>();
	
	static final int GAME_WIDTH = 1080, GAME_HEIGHT = 960;
	
	public TankFrame() {
		//设置窗口大小
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		//设置能否改变窗口大小 设置为否
		this.setResizable(false);
		//设置窗口标题
		this.setTitle("Tank War");
		//显示窗口设置为可见
		this.setVisible(true);
		
		//解决窗口关闭问题，添加一个window的监听器，监听windowclosing这件事情
		//WindowAdapter实现了一个Windowlistener的接口
		this.addWindowListener(new WindowAdapter() {
			//重写windowclosing方法
			@Override
			public void windowClosing(WindowEvent e) {
				//关闭windows窗口
				System.exit(0);
			}
			
		});
		
		//增加键盘的监听
		this.addKeyListener(new MyKeyListener());
	}
	
	//双缓冲防止闪烁
	Image offScreenImage = null;
	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("子弹的数量：" + bullets.size(), 10, 60);
		g.drawString("敌人的数量：" + tanks.size(), 10, 80);
		g.drawString("爆炸的数量：" + explodes.size(), 10, 100);
		g.setColor(c);
		myTank.paint(g);
		//bullets
		for(int i=0; i < bullets.size(); i++) {
			bullets.get(i).paint(g);
		}
		//tanks
		for(int i=0; i < tanks.size(); i++) {
			tanks.get(i).paint(g);
		}
		//explodes
		for(int i=0; i < explodes.size(); i++) {
			explodes.get(i).paint(g);
		}
		//碰撞
		for(int i=0;i<bullets.size();i++) {
			for(int j=0;j<tanks.size();j++) {
				bullets.get(i).collideWith(tanks.get(j));
			}
		}

	}
	
	class MyKeyListener extends KeyAdapter {
		
		boolean bL = false;
		boolean bU = false;
		boolean bR = false;
		boolean bD = false;
		
		//按键时调用
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT :
				bL = true;
				break;
			case KeyEvent.VK_UP :
				bU = true;
				break;
			case KeyEvent.VK_RIGHT :
				bR = true;
				break;
			case KeyEvent.VK_DOWN :
				bD = true;
				break;

			default:
				break;
			} 
			
			setMainTankDir();
		}
		
		//释放键时候调用
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT :
				bL = false;
				break;
			case KeyEvent.VK_UP :
				bU = false;
				break;
			case KeyEvent.VK_RIGHT :
				bR = false;
				break;
			case KeyEvent.VK_DOWN :
				bD = false;
				break;
			case KeyEvent.VK_CONTROL :
				myTank.fire();
				break;

			default:
				break;
			} 
			setMainTankDir();
		}

		/**
		 * 设置主Tank方向
		 */
		private void setMainTankDir() {
			if(!bL && !bU && !bR && !bD) {
				myTank.setMoving(false);
			}
			else {
				myTank.setMoving(true);
				if(bL) myTank.setDir(Dir.LEFT);
				if(bU) myTank.setDir(Dir.UP);
				if(bR) myTank.setDir(Dir.RIGHT);
				if(bD) myTank.setDir(Dir.DOWN);
			}
		}
		
	}
	
}
