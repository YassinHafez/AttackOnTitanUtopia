package game.engine.dataloader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;

public class DataLoader {

    private static final String TITANS_FILE_NAME = "titans.csv";
    private static final String WEAPONS_FILE_NAME = "weapons.csv";


    public static HashMap<Integer, TitanRegistry> readTitanRegistry() throws IOException{

        HashMap<Integer, TitanRegistry> titanMap = new HashMap<>();
        BufferedReader buffer = new BufferedReader(new FileReader(TITANS_FILE_NAME));
       
        String line = buffer.readLine();
        String[] array;

        while(line != null){

            array = line.split(",");
           
            TitanRegistry myRegistry = new TitanRegistry(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]), 
                            Integer.parseInt(array[3]), Integer.parseInt(array[4]), Integer.parseInt(array[5]), Integer.parseInt(array[6]));
            
            titanMap.put(Integer.parseInt(array[0]), myRegistry);

            line = buffer.readLine();
            
        }

        buffer.close();

        return titanMap;
        

        
    } 

    public static HashMap<Integer, WeaponRegistry> readWeaponRegistry() throws IOException{

        HashMap<Integer, WeaponRegistry> weaponMap = new HashMap<>();
        BufferedReader buffer = new BufferedReader(new FileReader(WEAPONS_FILE_NAME));
       
        String line = buffer.readLine();

        while(line != null){

            String[] array;
            array = line.split(",");
            int arrayLength = array.length;
            WeaponRegistry myRegistry;
            if(arrayLength == 2){
                myRegistry = new WeaponRegistry(Integer.parseInt(array[0]), Integer.parseInt(array[1]));
            }
            else if(arrayLength ==4) {
                myRegistry = new WeaponRegistry(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]), 
                            array[3]);
            }
            else{
                myRegistry = new WeaponRegistry(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]), 
                            array[3], Integer.parseInt(array[4]), Integer.parseInt(array[5]));
            }

            // WeaponRegistry myRegistry = new WeaponRegistry(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]), 
            //                 array[3], Integer.parseInt(array[4]), Integer.parseInt(array[5]));
            
            weaponMap.put(Integer.parseInt(array[0]), myRegistry);

            line = buffer.readLine();
            
        }
        buffer.close();

        return weaponMap;
        

        
    } 




}
