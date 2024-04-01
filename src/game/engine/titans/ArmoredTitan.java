package game.engine.titans;

public class ArmoredTitan extends Titan {

	public static final int TITAN_CODE=3;

	public int getTITAN_CODE() {
		return TITAN_CODE;
	}

	public ArmoredTitan(int baseHealth, int baseDamage,
			int heigthInMeters,int distanceFromBase,int speed,int resourcesValue,int dangerLevel) {

		super( baseHealth, baseDamage,
				heigthInMeters, distanceFromBase, speed,resourcesValue, dangerLevel);


	}

	//Overrides attackee takeDamage method
	@Override
	public int takeDamage(int damage){

		setCurrentHealth(getCurrentHealth() - damage/4);
        
        if(isDefeated()) return getResourcesValue();
        else return 0;

	}



}
