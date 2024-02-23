package game.engine.weapons;

public class VolleySpreadCannon extends Weapon {

	private int minRange;
	private int maxRange;
	private int WEAPON_CODE=3;

	public VolleySpreadCannon(int baseDamage,int minRange,int maxRange) {

		super(baseDamage);
		this.minRange=minRange;
		this.maxRange=maxRange;
	}

	public int getMinRange() {
		return minRange;
	}

	public int getMaxRange() {
		return maxRange;
	}


}
