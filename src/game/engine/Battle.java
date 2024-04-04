package game.engine;

import game.engine.titans.*;
import game.engine.weapons.Weapon;
import game.engine.weapons.factory.*;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.*;
import game.engine.base.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;



public class Battle {
    private static final int[][] PHASES_APPROACHING_TITANS = {{1,1,1,2,1,3,4},
                                                            {2,2,2,1,3,3,4},
                                                            {4,4,4,4,4,4,4}};
    private static final int WALL_BASE_HEALTH = 10000;
    private int numberOfTurns;
    private int resourcesGathered;
    private BattlePhase battlePhase;
    private int numberOfTitansPerTurn;
    private int score;
    private int titanSpawnDistance;
    private final WeaponFactory weaponFactory;
    private final HashMap<Integer, TitanRegistry> titansArchives;
    private final ArrayList<Titan> approachingTitans;
    private final PriorityQueue<Lane> lanes;
    private final ArrayList<Lane> originalLanes;


    public ArrayList<Lane> getOriginalLanes() {
        return originalLanes;
    }


    public PriorityQueue<Lane> getLanes() {
        return lanes;
    }


    public WeaponFactory getWeaponFactory() {
        return weaponFactory;
    }

    public HashMap<Integer, TitanRegistry> getTitansArchives() {
        return titansArchives;
    }

    public ArrayList<Titan> getApproachingTitans() {
        return approachingTitans;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public int getResourcesGathered() {
        return resourcesGathered;
    }

    public void setResourcesGathered(int resourcesGathered) {
        this.resourcesGathered = resourcesGathered;
    }

    public BattlePhase getBattlePhase() {
        return battlePhase;
    }

    public void setBattlePhase(BattlePhase battlePhase) {
        this.battlePhase = battlePhase;
    }

    public int getNumberOfTitansPerTurn() {
        return numberOfTitansPerTurn;
    }

    public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn) {
        this.numberOfTitansPerTurn = numberOfTitansPerTurn;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTitanSpawnDistance() {
        return titanSpawnDistance;
    }

    public void setTitanSpawnDistance(int titanSpawnDistance) {
        this.titanSpawnDistance = titanSpawnDistance;
    }

    public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes, int initialResourcesPerLane) throws IOException{
        
        this.battlePhase = BattlePhase.EARLY;
        this.numberOfTitansPerTurn = 1;
        this.numberOfTurns = numberOfTurns;
        this.score = score;
        this.titanSpawnDistance = titanSpawnDistance;
        this.resourcesGathered = initialNumOfLanes * initialResourcesPerLane;
        this.titansArchives = game.engine.dataloader.DataLoader.readTitanRegistry();
        this.approachingTitans = new ArrayList<>();
        this.lanes = new PriorityQueue<>();
        this.originalLanes = new ArrayList<>();
        this.weaponFactory = new WeaponFactory();
        initializeLanes(initialNumOfLanes);

    }

    private void initializeLanes(int numOfLanes){
        for (int i = 0 ; i < numOfLanes; i++ ){
            Lane newLane = new Lane(new Wall(WALL_BASE_HEALTH));
            lanes.add(newLane);
            originalLanes.add(newLane);
        }
    }


    //*********************************************************
    //MILESTONE 2

    public void refillApproachingTitans(){

        approachingTitans.clear();
        int index = 0;

        switch (battlePhase) {
            case EARLY:
                index = 0;
                break;
            case INTENSE:
                index = 1;
                break;
            case GRUMBLING:
                index = 2;
                break;      
            default:
                break;
        }

        int[] currentPhaseCodes = PHASES_APPROACHING_TITANS[index];

        for (int i = 0; i < currentPhaseCodes.length; i++) {
            
            int currentTitanCode = currentPhaseCodes[i];
            TitanRegistry currentRegistry = titansArchives.get(currentTitanCode);

            //Attributes of Titan To Add


            int baseHealth = currentRegistry.getBaseHealth();
            int baseDamage = currentRegistry.getBaseDamage();
            int heightInMeters = currentRegistry.getHeightInMeters();
            int speed = currentRegistry.getSpeed();
            int resourcesValue = currentRegistry.getResourcesValue();
            int dangerLevel = currentRegistry.getDangerLevel();

            Titan titanToAdd;


            switch (currentTitanCode) {
                case 1:
                    titanToAdd = new PureTitan(baseHealth, baseDamage, heightInMeters, titanSpawnDistance, speed, resourcesValue, dangerLevel);
                    break;
                case 2:
                    titanToAdd = new PureTitan(baseHealth, baseDamage, heightInMeters, titanSpawnDistance, speed, resourcesValue, dangerLevel);
                    break;
                case 3:
                    titanToAdd = new PureTitan(baseHealth, baseDamage, heightInMeters, titanSpawnDistance, speed, resourcesValue, dangerLevel);
                    break;
                case 4:
                    titanToAdd = new PureTitan(baseHealth, baseDamage, heightInMeters, titanSpawnDistance, speed, resourcesValue, dangerLevel);
                    break;
            
                default:
                    titanToAdd = null;
                    break;
            }

            approachingTitans.add(titanToAdd);



        }

    }

    public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException, InvalidLaneException{

        try {
            
            if(lane.isLaneLost()) throw new InvalidLaneException();

            FactoryResponse purchaseResponse = weaponFactory.buyWeapon(resourcesGathered, weaponCode);
            resourcesGathered = purchaseResponse.getRemainingResources();
            lane.addWeapon(purchaseResponse.getWeapon());
            

        } catch (InsufficientResourcesException e) {
            
        } catch (InvalidLaneException e){

        }

    }

    public void passTurn(){

    }

    private void addTitansToLane(){

        //To get lane of least danger, find the last element in the Priority Queue

        Stack<Lane> temp = new Stack<>();
        while(!lanes.isEmpty()) temp.add(lanes.remove());
        Lane safestLane = temp.peek();
        while(!temp.isEmpty()) lanes.add(temp.pop());

        for(int i = 0; i < numberOfTitansPerTurn; i++){

            safestLane.addTitan(approachingTitans.removeFirst());
            if(approachingTitans.isEmpty()) refillApproachingTitans();

        }

    }

    private void moveTitans(){

        for (Lane lane : lanes) {         
            for(Titan titan : lane.getTitans()){
                titan.move();
            }
        }
    }

    private int performWeaponsAttacks(){

        int totalResourcesGathered = 0;

        for (Lane lane : lanes) {
            for (Weapon weapon : lane.getWeapons()) {
                totalResourcesGathered += weapon.turnAttack(lane.getTitans());       
            }
        }
        return totalResourcesGathered;
    }

    private int performTitansAttacks(){

        int totalResourcesGathered = 0;

        for (Lane lane : lanes) {
            for (Titan titan : lane.getTitans()) {

                if(titan.getDistance() <= 0) 
                totalResourcesGathered += titan.attack(lane.getLaneWall());
                
            }
        }
        return totalResourcesGathered;
    }

    private void updateLanesDangerLevels(){

        for (Lane lane : lanes) {
            lane.updateLaneDangerLevel();
        }

    }

    private void finalizeTurns(){

        boolean doubledTitans = true;
        numberOfTurns++;
        if(numberOfTurns < 15){        
            battlePhase = BattlePhase.EARLY;
        }else if(numberOfTurns < 30){
            battlePhase = BattlePhase.INTENSE;
        }else if(numberOfTurns >= 30){
            battlePhase = BattlePhase.GRUMBLING;
        }

        if(numberOfTurns > 30 && numberOfTurns % 5 == 0)
            numberOfTitansPerTurn *= 2;

    }


}



