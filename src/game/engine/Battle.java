package game.engine;

import game.engine.titans.*;
import game.engine.weapons.Weapon;
import game.engine.weapons.factory.*;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidCSVFormat;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.*;
import game.engine.base.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
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


    //********************************************************************************************
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
            Titan titanToAdd = currentRegistry.spawnTitan(titanSpawnDistance);

            approachingTitans.add(titanToAdd);



        }

    }

    public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException, InvalidLaneException{

       
            if(lane.isLaneLost() || !lanes.contains(lane)) throw new InvalidLaneException();

            FactoryResponse purchaseResponse = weaponFactory.buyWeapon(resourcesGathered, weaponCode);
            resourcesGathered = purchaseResponse.getRemainingResources();
            lane.addWeapon(purchaseResponse.getWeapon());
            
            performTurn();

    }

    public void passTurn(){
        performTurn();
    }

    private void addTurnTitansToLane(){

        if(approachingTitans.isEmpty()) refillApproachingTitans();

        Lane safestLane = lanes.remove();

        for(int i = 0; i < numberOfTitansPerTurn; i++){

            
            safestLane.addTitan(approachingTitans.remove(0));
           
            if(approachingTitans.isEmpty()) refillApproachingTitans();

        }
        lanes.add(safestLane);

    }

    private void moveTitans(){

        for (Lane lane : lanes) {         
            for(Titan titan : lane.getTitans()){
                titan.move();
            }
        }
    }

    private int performWeaponsAttacks(){
        int resources =0;

        Stack<Lane> laneStack = new Stack<>();
        while(!lanes.isEmpty()){
            Lane currentLane = lanes.remove();
            resources += currentLane.performLaneWeaponsAttacks();
            laneStack.push(currentLane);
        }

        while(!laneStack.isEmpty()) lanes.add(laneStack.pop());

        resourcesGathered += resources;
        score += resources;
        return resources;
    }

    private int performTitansAttacks(){

        int totalResourcesGathered = 0;


        Stack<Lane> stackOfLanes = new Stack<>();
        while(!lanes.isEmpty()) stackOfLanes.push(lanes.remove());

        while(!stackOfLanes.isEmpty()){

            Lane currentLane = stackOfLanes.pop();
            for (Titan titan : currentLane.getTitans()) {

                if(titan.getDistance() <= 0) 
                totalResourcesGathered += titan.attack(currentLane.getLaneWall());
                
            }

            if(!currentLane.isLaneLost()) lanes.add(currentLane);


        }

        return totalResourcesGathered;
    }

    private void updateLanesDangerLevels(){

        Stack<Lane> temp = new Stack<>();
        while(!lanes.isEmpty()){
            Lane currentLane = lanes.remove();
            currentLane.updateLaneDangerLevel();
            temp.add(currentLane);
        }
        while(!temp.isEmpty()) lanes.add(temp.pop());

    }

    private void finalizeTurns(){

        
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
    

    private void performTurn(){

        moveTitans();
        performWeaponsAttacks();
        performTitansAttacks();
        addTurnTitansToLane();
        updateLanesDangerLevels();
        finalizeTurns();
        

    }

    public boolean isGameOver(){

        boolean anyLaneAlive = false;

        for (Lane lane : lanes) {
            if(!lane.isLaneLost())
                anyLaneAlive = true;
        }

        return !anyLaneAlive;

    }




}



