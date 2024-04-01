package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class WallTrap extends Weapon {

	public static final int WEAPON_CODE=4;


	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}


	public WallTrap(int baseDamage) {
		super(baseDamage);
	}

	@Override
	int turnAttack(PriorityQueue<Titan> laneTitans) {

		int totalResourcesReceived = 0;
		int titansInLane = laneTitans.size();
		
		if(titansInLane == 0) return 0;

		Titan frontTitan = laneTitans.peek();
		if(frontTitan.hasReachedTarget()) totalResourcesReceived += frontTitan.takeDamage(this.getDamage());

		if(frontTitan.isDefeated()) laneTitans.remove();

		return totalResourcesReceived;
	}



}
