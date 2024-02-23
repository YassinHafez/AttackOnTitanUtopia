package game.engine.base;

public class Wall {


	private final int baseHealth;
	private int currentHealth;
	private final int resourcesValue = -1;

	public Wall(int baseHealth) {
		this.baseHealth = baseHealth;
		currentHealth = baseHealth;
	}


	public int getBaseHealth() {
		return baseHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}


	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}





}

/*public Wall(int baseHealth) {
		this.baseHealth=baseHealth;

	}
	public Wall(int baseHealth, int currentHealth) {

		this.baseHealth=baseHealth;
		this.currentHealth=currentHealth;

	}*/

