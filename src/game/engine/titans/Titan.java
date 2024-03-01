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
	
	public int getDistance() {
		return distanceFromBase;
	}

	public void setDistance(int distance) {
		 distanceFromBase = distance;
	}
	
	public int getBaseHealth() {
		return baseHealth;
	}

	public int getDangerLevel() {
		return dangerLevel;
	}

	public int getHeightInMeters() {
		return heightInMeters;
	}

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



	public int compareTo(Titan o) {

		if(this.distanceFromBase < o.distanceFromBase) return -1;
		if(this.distanceFromBase == o.distanceFromBase) return 0;
		return 1;

	}

	public int compareTo(Object o) {

		Titan otherTitan = (Titan) o;
		
		if(this.distanceFromBase < otherTitan.distanceFromBase) return -1;
		if(this.distanceFromBase == otherTitan.distanceFromBase) return 0;
		return 1;

	}


	public int getCurrentHealth() {
		return currentHealth;
	}


	public void setCurrentHealth(int currentHealth) {
		if(currentHealth < 0 ) 
			this.currentHealth = 0;
		else
			this.currentHealth = currentHealth;
	}


	public int getDamage() {
		return baseDamage;
	}


	public int getDistanceFromBase() {
		return distanceFromBase;
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
