package com.game.tank.abstractfactory;

import java.awt.Graphics;

import com.game.tank.Tank;

public abstract class BaseBullet {
	public abstract void paint(Graphics g);

	public abstract void collideWith(BaseTank tank);
}
