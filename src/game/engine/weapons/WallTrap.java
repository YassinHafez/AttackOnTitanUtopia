package game.engine.weapons;

public class WallTrap extends Weapon {

	public static final int WEAPON_CODE=4;


	public int getWEAPON_CODE() {
		return WEAPON_CODE;
	}


	public WallTrap(int baseDamage) {
		super(baseDamage);
	}



}
