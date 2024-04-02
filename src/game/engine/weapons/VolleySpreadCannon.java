package game.engine.weapons;

import java.util.PriorityQueue;
import java.util.Stack;

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

		if(laneTitans.size() == 0) return 0;

		Stack<Titan> temp = new Stack<>();

		for (int i = 0; i < laneTitans.size(); i++) {

			Titan frontTitan = laneTitans.remove();
			int distance = frontTitan.getDistance();

			if(distance <= maxRange && distance >= minRange)
			totalResourcesReceived += frontTitan.takeDamage(getDamage());

			if(!frontTitan.isDefeated())
			temp.push(frontTitan);
			
		}

		while(!temp.isEmpty()){
			laneTitans.add(temp.pop());
		}
		
		return totalResourcesReceived;
	}


}
