package game.engine.titans;


public abstract class Titan implements Comparable{

	private final int baseHealth;
	private int currentHealth;
	private final int baseDamage;
	private final int heightInMeters;
	private int distanceFromBase;
	private int speed;
	private final int resourcesValue;
	private final int dangerLevel;
	

	public Titan(int baseHealth, int baseDamage,
			int heightInMeters,int distanceFromBase, int speed,int resourcesValue,int dangerLevel) {
		
		this.baseHealth = baseHealth;
		this.baseDamage = baseDamage;
		this.heightInMeters = heightInMeters;
		this.distanceFromBase = distanceFromBase;
		this.speed = speed;
		this.resourcesValue = resourcesValue;
		this.dangerLevel = dangerLevel;
		this.currentHealth = baseHealth;



	}



	public int compareTo(Object o) {
		
		Titan titan = (Titan)o;

		if(this.distanceFromBase < titan.distanceFromBase) return -1;
		if(this.distanceFromBase == titan.distanceFromBase) return 0;
		return 1;

	}


	public int getCurrentHealth() {
		return currentHealth;
	}


	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}


	public int getBaseDamage() {
		return baseDamage;
	}


	public int getDistanceFromBase() {
		return distanceFromBase;
	}


	public void setDistanceFromBase(int distanceFromBase) {
		this.distanceFromBase = distanceFromBase;
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public int getResourcesValue() {
		return resourcesValue;
	}

	public String toString(){

		return distanceFromBase + "";

	}





}
