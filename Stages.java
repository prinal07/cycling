package cycling;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Stages{
    private StageType stage_type;
    private String stage_name;
    private int stage_length;
    private int stageId;
    private int raceId; 
    private String description;
    private String stage_state;
    public static int total_stages = 0;
    private Double location;
    private double length;
    private LocalDateTime startTime;

    private int[] segment_ids_in_stage;
    private static int segment_counter = 0;
    private SegmentType segment_type;



    HashMap <String, Stages> stages_hashmap = new HashMap<String, Stages>();

    public void createStage(SegmentType segment_type , StageType stage_type){
        int temp_segment_id;
        int segment_id_ctr = 0;

        this.segment_type = segment_type;
        this.stage_type = stage_type;
        this.stageId = stage_id_counter + 10;
        
        temp_segment_id = ++segment_id_ctr;
        segment_ids_in_stage[segment_counter++] = temp_segment_id;
    }

    public Stages(String stageName, String description, double length, LocalDateTime startTime){
        this.stage_name = stageName;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
    }

    public void setStageId(int stageId){
        this.stageId = stageId;
    }

    public int[] getStageSegment(){
        return segment_ids_in_stage;
    }

    

    private void addStage(int stageId, Double location){
        createStage(segment_type, stage_type);

        //called in all the addIntermediate method calls
    }

    private void addStage(int stageId, Double location, SegmentType type, Double averageGradient, Double length){
        //called in all the addcategorizedclimb method calls

    }

}
    

