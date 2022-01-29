package com.game.tank.abstractfactory;

/**
 * @author ruomengjiang
 * @date 2022年1月28日
 */

import com.game.tank.Dir;
import com.game.tank.Group;
import com.game.tank.TankFrame;

public abstract class GameFactory {
	public abstract BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf);
	public abstract BaseExplode createExplode(int x, int y, TankFrame tf);
	public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf);
}

