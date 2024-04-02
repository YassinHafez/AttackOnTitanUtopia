package game.engine.interfaces;

public interface Attacker {

    int getDamage(); 

    default int attack(Attackee target){

        return target.takeDamage(getDamage());
        //Returns resources of target if killed
        

    }
}
