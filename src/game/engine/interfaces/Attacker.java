package game.engine.interfaces;

public interface Attacker {

    int getDamage();

    default int attack(Attackee target){

        target.takeDamage(getDamage());

        if(target.isDefeated()) return target.getResourcesValue();
        else return 0;

    }
}
