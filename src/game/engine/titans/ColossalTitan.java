package game.engine.titans;

public class ColossalTitan extends Titan {

	public static final int TITAN_CODE=4;

	public int getTITAN_CODE() {
		return TITAN_CODE;
	}

	public ColossalTitan(int baseHealth, int baseDamage,
			int heigthInMeters,int distanceFromBase,int speed,int resourcesValue,int dangerLevel) {



		super( baseHealth,baseDamage,
				heigthInMeters, distanceFromBase, speed,resourcesValue, dangerLevel);

	}

	//Overrides Mobil move method
	@Override
	public boolean move(){

        setDistance(getDistance() - getSpeed());
		setSpeed(getSpeed() + 1);
        return hasReachedTarget();

    }







}
