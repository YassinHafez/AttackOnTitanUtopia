package game.engine.interfaces;


//Attackee is an entity being attacked.
public interface Attackee {

    int getCurrentHealth();
    void setCurrentHealth(int health);
    int getResourcesValue();

    
    default boolean isDefeated(){
        return getCurrentHealth() <= 0;
    }

    default int takeDamage(int damage){

        setCurrentHealth(getCurrentHealth() - damage);
        
        if(isDefeated()) return getResourcesValue();
        return 0;
    }

    
    
}
