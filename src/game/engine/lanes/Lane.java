package game.engine.lanes;

import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane implements Comparable{


	private final Wall laneWall;
	private int dangerLevel;
	private final PriorityQueue<Titan> titans;
	private final ArrayList<Weapon> weapons;

	public Lane(Wall laneWall) {

		this.laneWall = laneWall;
		titans = new PriorityQueue<>();
		weapons = new ArrayList<>();
		dangerLevel = 0;
	}


	public int compareTo(Object o) {
		Lane otherLane = (Lane) o;
		
		if (this.dangerLevel > otherLane.dangerLevel) return -1;
		if (this.dangerLevel == otherLane.dangerLevel) return 0;
		return 1;
		
	}


}

