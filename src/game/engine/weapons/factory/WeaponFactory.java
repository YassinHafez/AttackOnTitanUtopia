package game.engine.weapons.factory;
import game.engine.weapons.*;

import java.io.IOException;
import java.util.HashMap;

public class WeaponFactory {
    private final HashMap<Integer, WeaponRegistry> weaponShop;

    public WeaponFactory() throws IOException{
        this.weaponShop = game.engine.dataloader.DataLoader.readWeaponRegistry();
    }
}
