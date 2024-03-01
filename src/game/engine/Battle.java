package game.engine;

import game.engine.titans.*;
import game.engine.weapons.factory.*;
import game.engine.dataloader.DataLoader;
import game.engine.lanes.*;
import game.engine.base.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;



public class Battle {
    private static final int[][] PHASES_APPROACHING_TITANS = {{1,1,1,2,1,3,4},
                                                            {2,2,2,1,3,3,4},
                                                            {4,4,4,4,4,4,4}};
    private final int WALL_BASE_HEALTH;
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
        this.WALL_BASE_HEALTH = 10000;
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
}
