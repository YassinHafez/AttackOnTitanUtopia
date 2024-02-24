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
    private final int[][] PHASES_APPROACHING_TITANS;
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


    }

    private void initializeLanes(int numOfLanes){
        for (int i = 0 ; i < numOfLanes; i++ ){
            Lane newLane = new Lane(new Wall(WALL_BASE_HEALTH));
            lanes.add(newLane);
            originalLanes.add(newLane);
        }
    }
}
