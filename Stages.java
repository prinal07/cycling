package cycling;

import java.util.List;

public class Stages{
    private String segment_type;
    private String stage_type;
    private String stage_name;
    private int stage_length;
    private int stageId;
    private List segment_ids_in_stage;
    private static int total_stages = 0;
    public static int stage_id_counter = 0;

    private void createSingleStage(String segment_type , String stage_type){
        int temp_segment_id;
        this.segment_type = segment_type;
        this.stage_type = stage_type;
        this.stageId = stage_id_counter + 10;
        
        temp_segment_id = ++total_stages;
        segment_ids_in_stage.add(temp_segment_id);

    }

    private groupStages(){
        
    }
    
}
