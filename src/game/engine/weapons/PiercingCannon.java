package game.engine.weapons;

public class PiercingCannon extends Weapon {

	private final int WEAPON_CODE=1;


	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}


	public PiercingCannon(int baseDamage) {

		super(baseDamage);

	}

}
