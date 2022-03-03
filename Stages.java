package cycling;

import java.util.ArrayList;
import java.util.List;

public class Stages{
    private String segment_type;
    private String stage_type;
    private String stage_name;
    private int stage_length;
    private int stageId;
    private int[] segment_ids_in_stage;
    private static int segment_counter = 0;
    private static int stage_id_counter = 0;
    private double location;


    public void createStage(String segment_type , String stage_type){
        int temp_segment_id;
        int segment_id_ctr = 0;

        this.segment_type = segment_type;
        this.stage_type = stage_type;
        this.stageId = stage_id_counter + 10;
        
        temp_segment_id = ++segment_id_ctr;
        segment_ids_in_stage[segment_counter++] = temp_segment_id;
    }

    public int[] getStageSegment(){
        return segment_ids_in_stage;
    }

    

    private void addStage(int stageId, Double location){
        //called in all the addIntermediate method calls
    }

    private void addStage(int stageId, Double location, SegmentType type, Double averageGradient, Double length){
        //called in all the addcategorizedclimb method calls

    }
    
}
