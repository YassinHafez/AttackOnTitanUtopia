package game.engine.titans;

public class AbnormalTitan extends Titan {


	public static final int TITAN_CODE=2;


	public int getTITAN_CODE() {
		return TITAN_CODE;
	}


	public AbnormalTitan(int baseHealth, int baseDamage,
			int heigthInMeters,int distanceFromBase,int speed,int resourcesValue,int dangerLevel) {


		super( baseHealth, baseDamage,
				heigthInMeters, distanceFromBase, speed,resourcesValue, dangerLevel);

	}







}
