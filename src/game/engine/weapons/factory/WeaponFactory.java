package game.engine.weapons.factory;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.weapons.*;

import java.io.IOException;
import java.util.HashMap;

public class WeaponFactory {
    private final HashMap<Integer, WeaponRegistry> weaponShop;

    public HashMap<Integer, WeaponRegistry> getWeaponShop() {
        return weaponShop;
    }

    public WeaponFactory() throws IOException{
        this.weaponShop = game.engine.dataloader.DataLoader.readWeaponRegistry();
    }

    public FactoryResponse buyWeapon(int resources, int weaponCode) throws InsufficientResourcesException{

        Weapon weaponToBuy = null;
        WeaponRegistry registryOfWeaponToBuy = weaponShop.get(weaponCode);

        int remainingResources = resources - registryOfWeaponToBuy.getPrice();
        if(remainingResources < 0) throw new InsufficientResourcesException(resources);
        

        switch (weaponCode) {
            case 1:
                weaponToBuy = new PiercingCannon(registryOfWeaponToBuy.getDamage());
                break;
            case 2:
                weaponToBuy = new SniperCannon(registryOfWeaponToBuy.getDamage());
                break;
            case 3:
                weaponToBuy = new VolleySpreadCannon(registryOfWeaponToBuy.getDamage(), registryOfWeaponToBuy.getMinRange(), registryOfWeaponToBuy.getMaxRange());
                break;
            case 4:
                weaponToBuy = new WallTrap(registryOfWeaponToBuy.getDamage());
                break;
            default:
                break;
        }

        
        return new FactoryResponse(weaponToBuy, remainingResources);
        


    }

    public void addWeaponToShop(int code, int price){

        WeaponRegistry newReg = new WeaponRegistry(code, price);
        weaponShop.put(code, newReg);

    }

    public void addWeaponToShop(int code, int price, int damage, String name){

        WeaponRegistry newReg = new WeaponRegistry(code, price, damage, name);
        weaponShop.put(code, newReg);

    }

    public void addWeaponToShop(int code, int price, int damage, String name, int minRange, int maxRange){

        WeaponRegistry newReg = new WeaponRegistry(code, price, damage, name, minRange, maxRange);
        weaponShop.put(code, newReg);

    }
}
