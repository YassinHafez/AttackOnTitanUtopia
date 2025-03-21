package game.engine.lanes;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;


import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;


public class Lane implements Comparable{


	private final Wall laneWall;
	private int dangerLevel;
	private final PriorityQueue<Titan> titans;
	private final ArrayList<Weapon> weapons;


	public int getDangerLevel() {
		return dangerLevel;
	}

	public void setDangerLevel(int dangerLevel) {
		this.dangerLevel = dangerLevel;
	}


	public Lane(Wall laneWall) {

		this.laneWall = laneWall;
		titans = new PriorityQueue<>();
		weapons = new ArrayList<>();
		dangerLevel = 0;
	}

	public Wall getLaneWall() {
		return laneWall;
	}


	public PriorityQueue<Titan> getTitans() {
		return titans;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}



	//Lanes of highest danger are placed at the front of the queue
	public int compareTo(Object o) {
		Lane otherLane = (Lane) o;
		
		if (this.dangerLevel > otherLane.dangerLevel) return 1;
		if (this.dangerLevel == otherLane.dangerLevel) return 0;
		return -1;
		
	}

	public int compareTo(Lane o) {
		if (this.dangerLevel > o.dangerLevel) return 1;
		if (this.dangerLevel == o.dangerLevel) return 0;
		return -1;
	}


	//Milestone 2 Methods:

	public void addTitan(Titan titan){
		titans.add(titan);
	}

	public void addWeapon(Weapon weapon){
		weapons.add(weapon);
	}

	public void moveLaneTitansRecursion(){


		if(titans.isEmpty()) return;

		Titan frontTitan = titans.remove();
		frontTitan.move();

		moveLaneTitansRecursion();

		titans.add(frontTitan);


	}

	public void moveLaneTitans(){

		Stack<Titan> temp = new Stack<>();
		int size = titans.size();

		for (int i = 0; i < size; i++) {
			
			Titan currTitan = titans.remove();
			currTitan.move();
			temp.push(currTitan);

		}

		while(!temp.isEmpty()){
			titans.add(temp.pop());
		}



	}


	public int performLaneTitansAttacks(){

		int totalResourcesReceived = 0;
		Stack<Titan> temp = new Stack<>();
		int size = titans.size();

		for (int i = 0; i < size; i++) {
			
			Titan currTitan = titans.remove();

			if(currTitan.getDistance() <= 0)
			totalResourcesReceived += currTitan.attack(laneWall);

			temp.push(currTitan);

		}

		while(!temp.isEmpty()){
			titans.add(temp.pop());
		}

		return totalResourcesReceived;

	}

	public int performLaneWeaponsAttacks(){
		int totalResourcesGathered =0;
		//turn attack attacks all titans in a lane\
		for (Weapon weapon : weapons) {
			totalResourcesGathered += weapon.turnAttack(titans);
		}
		return totalResourcesGathered;
	}

	public boolean isLaneLost(){

		return laneWall.isDefeated();

	}

	public void updateLaneDangerLevel(){

		int dangerSum = 0;
		for (Titan titan : titans) {
			dangerSum += titan.getDangerLevel();
		}

		setDangerLevel(dangerSum);

	}


	public String toString(){
		return this.dangerLevel + "";
	}

	public static void main(String[] args) {
		Lane lane1 = new Lane(new Wall(100));
	}
	
	


}

