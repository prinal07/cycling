package cycling;
import java.util.HashMap;
import java.util.Set;
import java.util.*;

public class Races {
    private int raceId;
    private String name;
    private String description;
    private int number_of_stages;
    private int total_length;     //The sum of all the stages' length
    private Stages[] all_stage_objects;
    public static int total_races = 0;
    private HashMap <Integer, Stages> stages_hashmap; 
    public static HashMap <Integer, Races> races_hashmap; 
    private int [] StageIdArray = new int[21]; //google says there are 21 stages in the grand tour

    public void groupStages(Stages[] some_list_of_stage_objects){

    }

    public Races(){
    }

    public Races(String name, String description){
        this.name = name;
        this.description = description;
    }

    public void setRaceId(int raceId){
        this.raceId = raceId;
    }

    public int getRaceId(){    
        return (this.raceId);
    }

    public void addToStagesHashMap(int key, Stages stage){
        this.stages_hashmap.put(key, stage);
    }

    public void 

    public int[] getStagesIds(){
        
    }
}





//how and where will the list of stage objects be created and parsed to the addStageToRace method? 