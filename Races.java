package cycling;
import java.util.HashMap;

public class Races {
    private int racedId;
    private String name;
    private String description;
    private int number_of_stages;
    private int total_length; 
    private Stages[] all_stage_objects;
    private HashMap <String, Stages> stages_hashmap; 
    //The sum of all the stages' length

    public void groupStages(Stages[] some_list_of_stage_objects){

    }
    
    public Races(String name, String description){
        this.name = name;
        this.description = description;
    }

    //not sure if this is needed...
    public void setStagesHashMap(HashMap<String, Stages> stages_hashmap){
        this.stages_hashmap = stages_hashmap;
    }
}





//how and where will the list of stage objects be created and parsed to the addStageToRace method? 