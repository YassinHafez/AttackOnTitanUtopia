package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class SniperCannon extends Weapon {

	public static final int WEAPON_CODE=2;

	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}

	public SniperCannon(int baseDamage) {
		super(baseDamage);
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {

		int totalResourcesReceived = 0;
		int titansInLane = laneTitans.size();

		if(titansInLane == 0) return 0;

		Titan frontTitan = laneTitans.peek();
		totalResourcesReceived += frontTitan.takeDamage(this.getDamage());

		if(frontTitan.isDefeated()) laneTitans.remove();
		
		return totalResourcesReceived;
	}

}
