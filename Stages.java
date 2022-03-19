package cycling;

import java.lang.ProcessBuilder.Redirect.Type;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private HashMap<Integer, LocalTime[]> stages_results = new HashMap<>(); //key: riderId, value: checkpoint array

    //private int[] segment_ids_in_stage;
    private ArrayList<Integer> segments_ids_in_stage = new ArrayList<>();
    
    //public static int[] all_segment_ids;
    //public ArrayList<Integer> all_segment_ids = new ArrayList<>();
    
    private int segments_in_stage = 0;
    public static int segment_counter = 0; //Total segments in entirety.
    public static HashMap <Integer, Stages> stages_hashmap; 

    public static HashMap <Integer, Integer> segment_and_stage_ids;
    
    //private int [] stage_private_segments;
    private ArrayList<Integer> stage_private_segments = new ArrayList<>();

    //private SegmentType [] segment_values;
    private ArrayList<SegmentType> segment_values;

    public Stages(String stageName, String description, double length, LocalDateTime startTime){
        this.stage_name = stageName;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
    }

    public void setStageId(int stageId){
        this.stageId = stageId;
    }

    public int[] getStageSegments(){
        int [] int_stage_segments = segments_ids_in_stage.stream().mapToInt(Integer::intValue).toArray();
        return int_stage_segments;
    }

    public double getLength(){
        return this.length;
    }

    public void addSegmentIdAndValue(int segmentId, SegmentType segmentType){
        this.stage_private_segments.add(segmentId);
        this.segment_values.add(segmentType);
        segments_in_stage++;
    }

    public static void addSegmentToHashMap(int segmentId, int stageId){
        Stages.segment_and_stage_ids.put(segmentId, stageId);
    }

    public SegmentType getSegmentValue(int segmentId){
        return (segment_values.get(stage_private_segments.indexOf(segmentId)));
    }

    // public int searchSegments(int segmentId){
    //     int pos = 0;
    //     for (int y = 0; y< this.stage_private_segments.size(); y++){
    //         if (this.stage_private_segments.get(y) == segmentId){
    //             pos = y;
    //         }
    //         else{
    //             //add some exception and/or assertion
    //         }
    //     }
    //     return pos;
    // }

    public void removeSegmentFromObjectList(int segmentId){
        // int [] copy = new int [stage_private_segments.length - 1];

        // for (int i = 0, j = 0; i< stage_private_segments.length; i++){
        //     if (i != index){
        //         copy[j++] = this.stage_private_segments[i];
        //     }
        // }
        // this.stage_private_segments = copy;
        segment_and_stage_ids.remove(segmentId);
        stage_private_segments.remove(segmentId);
        segment_values.remove(stage_private_segments.indexOf(segmentId));
    }

    public int getTotalSegmentsInStage(){
        return segments_in_stage;
    }

    public void addResultsToStage(int riderId, LocalTime... checkpoints){
        stages_results.put(riderId, checkpoints);
    }

}
    

