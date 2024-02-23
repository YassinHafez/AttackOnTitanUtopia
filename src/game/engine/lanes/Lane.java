package game.engine.lanes;

import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane {


	private Wall laneWall;
	private int dangerLevel;
	private PriorityQueue<Titan> titans;
	private ArrayList<Weapon> weapons;

	public Lane(Wall laneWall) {

		this.laneWall=laneWall;
	}


	public int compareTo(Lane o) {

		return o.dangerLevel;
	}


}

