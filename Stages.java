package cycling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;



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
    public static int[] all_segment_ids;
    // private SegmentType[] segment_values; 
    public static int[][] stages_and_segments_array; 
    private int segments_in_stage = 0;
    public static int segment_counter = 0; //Total segments in entirety.
    public static HashMap <Integer, Stages> stages_hashmap; 

    //public static HashMap <Integer, Integer> segmentIdsAndPos;
    //private HashMap <Integer, SegmentType> segmentValuesAndPos;
    //private static int index_ctr;
    //private HashMap<Integer, ArrayList> g = new HashMap<>(); //Too much work for simple data calls and assignment.

    public static HashMap <Integer, Integer> segment_and_stage_ids;
    private int [] stage_private_segments;
    private SegmentType [] segment_values;

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

    public void addSegmentIdAndValue(int segmentId, SegmentType segmentType){
        this.stage_private_segments[segments_in_stage] = segmentId;
        this.segment_values[segments_in_stage++] = segmentType;
    }

    public static void addSegmentToHashMap(int stageId, int segmentId){
        Stages.segment_and_stage_ids.put(segmentId, stageId);
    }
    
    
    // public void addSegmentIdToArray(int segmentId, int stageId){
    //     this.segment_ids_in_stage[segments_in_stage] = segmentId;
    //     //dont need the line below if using the segment_and_stage_ids hashmap
    //     //all_segment_ids[segment_counter] = segmentId;
    //     segment_counter++;
    //     //remove these above 2 lines to implement the search below.

    //     for(int i= 0; i<stages_and_segments_array[0].length;i++){
    //         //test if this is actually just iterating over the first column, which holds the stageIds
    //         if (stageId == stages_and_segments_array[i][0]){
    //             Stages stage = stages_hashmap.get(stageId);
    //             stages_and_segments_array[i][stage.getTotalSegmentsInStage()] = segmentId;
    //             break;
    //         }
    //     }
    //     Stages.segment_and_stage_ids.put(segmentId, stageId);  
    // }

    // public static int findSegment(int segmentId){
    //     int found_pos = 0; 
    //     //i can figure out the max value of the ids, so hence i could apply a search that uses the quicksort trick
    //     for(int pos: all_segment_ids){
    //         // int max_value = (all_segment_ids.length)*2 - 2;
    //         if (all_segment_ids[pos] == segmentId){
    //             found_pos = pos;
    //         } 
    //         else{
    //             //add some exception
    //         }
    //     }
    //     return found_pos;
    // }
    
    // public void addSegmentToStage(int stageId, int segmentId, SegmentType segmentValue){    

    // }

    public int searchSegments(int segmentId){
        int pos = 0;
        for (int y = 0; y< this.stage_private_segments.length; y++){
            if (this.stage_private_segments[y] == segmentId){
                pos = y;
            }
            else{
                //add some exception and/or assertion
            }
        }
        return pos;
    }

    public void removeSegmentFromObjectList(int index){
        int [] copy = new int [stage_private_segments.length - 1];

        for (int i = 0, j = 0; i< stage_private_segments.length; i++){
            if (i != index){
                copy[j++] = this.stage_private_segments[i];
            }
        }
        this.stage_private_segments = copy;
    }

    public int getTotalSegmentsInStage(){
        return segments_in_stage;
    }

}
    

