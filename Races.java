package cycling;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    public Races(){
    }

    public String getName(){
        return this.name;
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

    public int[] getStageIds(){
        return this.stageIdArray;
    }

    public void addToStagesHashMap(int key, Stages stage){
        Stages.stages_hashmap.put(key, stage);
        int local_number_of_stages = this.number_of_stages++;
        stageIdArray[local_number_of_stages] = key;
    }

    public int[] getStagesIds(){
        return this.stageIdArray;
    }
    
    public static void sortRaceStages(int raceId, LocalDateTime[]array){
		Arrays.sort(array);
		}

    public String getDescription(){
        return this.description;
    }

    public String getTotalLength(){
        int local_total = 0;
        for (int x = 0; x<stageIdArray.length; x++){
            local_total = local_total + Stages.stages_hashmap.get(stageIdArray[x]).getStageLength();
        }
    }

}





//how and where will the list of stage objects be created and parsed to the addStageToRace method? 