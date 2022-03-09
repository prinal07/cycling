package cycling;
import java.util.HashMap;

public class Races {
    private int raceId;
    private String name;
    private String description;
    private int number_of_stages = 0;
    private int total_length;     //The sum of all the stages' length
    private Stages[] all_stage_objects; ///not used and needed...  remove...
    public static int total_races = 0;
    private int[] all_segments;

    public static HashMap <Integer, Races> races_hashmap; 
    private int [] stageIdArray = new int[21]; //google says there are 21 stages in the grand tour

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

    public int getNumberOfStages(){
        return (this.number_of_stages);
    }

    public void addToStagesHashMap(int key, Stages stage){
        Stages.stages_hashmap.put(key, stage);
        int local_number_of_stages = this.number_of_stages++;
        stageIdArray[local_number_of_stages] = key;
    }

    public int[] getStagesIds(){
        return this.stageIdArray;
        
    }
}





//how and where will the list of stage objects be created and parsed to the addStageToRace method? 