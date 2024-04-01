package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class VolleySpreadCannon extends Weapon {

	private final int minRange;
	private final int maxRange;
	public static final int WEAPON_CODE=3;

	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}

	public VolleySpreadCannon(int baseDamage,int minRange,int maxRange) {

		super(baseDamage);
		this.minRange=minRange;
		this.maxRange=maxRange;
	}

	public int getMinRange() {
		return minRange;
	}

	public int getMaxRange() {
		return maxRange;
	}

	@Override
	int turnAttack(PriorityQueue<Titan> laneTitans) {

		int totalResourcesReceived = 0;
		int titansInLane = laneTitans.size();


		if(titansInLane == 0) return 0;

		for (int i = 0; i < titansInLane; i++) {

			Titan frontTitan = laneTitans.remove();
			if(frontTitan.getDistance() <= maxRange && frontTitan.getDistance() >= minRange) 
				totalResourcesReceived += frontTitan.takeDamage(this.getDamage());

			if(!frontTitan.isDefeated())
			laneTitans.add(frontTitan);
			
		}
		return totalResourcesReceived;
	}


}
