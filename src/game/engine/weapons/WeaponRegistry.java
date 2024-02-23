package game.engine.weapons;

public class WeaponRegistry {
    
    private final int code;
    private final int price;
    private final int damage;
    private final String name;
    private final int minRange;
    private final int maxRange;

    public WeaponRegistry(int code, int price){
        this.code = code;
        this.price = price;
        this.damage = 0;
        this.name = null;
        this.minRange = 0;
        this.maxRange = 0;
    }

    public WeaponRegistry(int code, int price, int damage, String name){

        this.code = code;
        this.price = price;
        this.damage = damage;
        this.name = name;
        this.maxRange = 0;
        this.minRange = 0;
        

    }

    public WeaponRegistry(int code, int price, int damage, String name, int minRange, int maxRange){

        this.code = code;
        this.price = price;
        this.damage = damage;
        this.name = name;
        this.minRange = minRange;
        this.maxRange = maxRange;

    }


}
