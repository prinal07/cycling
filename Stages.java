package cycling;

import java.time.LocalDateTime;
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

    private int[] segment_ids;
    private int segments_in_stage = 0;
    private SegmentType segment_type;
    public static int segment_counter = 0; //Total segments in entirety.
    public static HashMap <Integer, Stages> stages_hashmap; 

    public static HashMap <Integer, Integer> segmentIdsAndPos;
    private HashMap <Integer, SegmentType> segmentValuesAndPos;
    private static int index_ctr;

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
        return segment_ids;
    }

    public void addSegmentIdToArray(int segmentId){
        this.segment_ids[segments_in_stage++] = segmentId;

    }

    public int getTotalSegmentsInStage(){
        return segments_in_stage;
    }

}
    

