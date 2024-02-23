package game.engine.titans;

public abstract class Titan{

	private int baseHealth;
	private int currentHealth;
	private int baseDamage;
	private int heigthInMeters;
	private int distanceFromBase;
	private int speed;
	private int resourcesValue;
	private int dangerLevel;
	private boolean isAlive;
	

	public Titan(int baseHealth, int baseDamage,
			int heigthInMeters,int distanceFromBase,int speed,int resourcesValue,int dangerLevel) {
		
		this.baseHealth = baseHealth;
		this.baseDamage = baseDamage;
		this.heigthInMeters = heigthInMeters;
		this.distanceFromBase = distanceFromBase;
		this.speed = speed;
		this.resourcesValue = resourcesValue;
		this.dangerLevel = dangerLevel;
		this.currentHealth = baseHealth;



	}



	public int compareTo(Titan o) {

		if(this.distanceFromBase < o.distanceFromBase) return 1;
		if(this.distanceFromBase == o.distanceFromBase) return 0;
		return -1;

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





}
