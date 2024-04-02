package game.engine.weapons;

import java.util.PriorityQueue;
import java.util.Stack;

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
		
		if(titansInLane == 0) return 0;

		Stack<Titan> temp = new Stack<>();

		//Remove from queue and push to stack 
		//while 5 titans not attacked and queue not emptied

		for(int i =0; i<5 && !laneTitans.isEmpty(); i++){

			Titan frontTitan = laneTitans.remove();
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
