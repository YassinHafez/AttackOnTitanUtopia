package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class PiercingCannon extends Weapon {

	public static final int WEAPON_CODE=1;


	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}


	public PiercingCannon(int baseDamage) {

		super(baseDamage);

	}


	@Override
	int turnAttack(PriorityQueue<Titan> laneTitans) {

		int totalResourcesReceived = 0;
		int titansInLane = laneTitans.size();
		int titansAttacked = 0;

		if(titansInLane == 0) return 0;

		for (int i = 0; i < titansInLane; i++) {

			Titan frontTitan = laneTitans.remove();

			//If 5 titans haven't been attacked, attack the front titan
			if(titansAttacked < 5){
				totalResourcesReceived += frontTitan.takeDamage(this.getDamage());
				titansAttacked++;
			}
				if(!frontTitan.isDefeated())
				laneTitans.add(frontTitan); //Adds titan to back of queue if alive
		
			
		}
		return totalResourcesReceived;
	}

}
