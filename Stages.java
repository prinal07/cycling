package cycling;

import java.lang.ProcessBuilder.Redirect.Type;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javax.management.openmbean.ArrayType;
import javax.swing.text.html.StyleSheet.ListPainter;

public class Stages {
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
    private int lastRiderElapsedTime = 0;
    private int registeredRiders = 0;

    private HashMap<Integer, LocalTime[]> stages_results = new HashMap<>(); // key: riderId, value: checkpoint array
    private ArrayList<Integer> sortedRiderIds = new ArrayList<>();
    private ArrayList<LocalTime[]> sortedElapsedRiderResults = new ArrayList<>();

    // private HashMap<Integer, LocalTime[]> adjusted_elapsed_stages_results = new
    // HashMap<>(); // key: riderId, value: checkpoint array

    // private int[] segment_ids_in_stage;
    private ArrayList<Integer> segments_ids_in_stage = new ArrayList<>();

    // public static int[] all_segment_ids;
    // public ArrayList<Integer> all_segment_ids = new ArrayList<>();

    private int segments_in_stage = 0;
    public static int segment_counter = 0; // Total segments in entirety.
    public static HashMap<Integer, Stages> stages_hashmap;

    public static HashMap<Integer, Integer> segment_and_stage_ids;

    // private int [] stage_private_segments;
    private ArrayList<Integer> stage_private_segments = new ArrayList<>();

    // private SegmentType [] segment_values;
    private ArrayList<SegmentType> segment_values;

    public Stages(String stageName, String description, double length, LocalDateTime startTime) {
        this.stage_name = stageName;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    public void addToElapsedSortedArrays(int index, int riderId, LocalTime[] checkpoints) {
        this.sortedElapsedRiderResults.add(index, checkpoints);
        this.sortedRiderIds.add(index, riderId);
    }

    public LocalTime getLastElapsedTimeInArray(int index) {
        LocalTime[] array = this.sortedElapsedRiderResults.get(index - 1);
        return array[array.length - 1];
    }

    public void swapSortElapsedTimes(int currentPos, int lastPos, LocalTime currentElapsedTime, LocalTime prevTime) {
        while (currentElapsedTime.isBefore(prevTime)) {
            Integer temp_current = this.sortedRiderIds.get(currentPos);
            LocalTime[] temp_current_results = this.sortedElapsedRiderResults.get(currentPos);

            sortedElapsedRiderResults.add(lastPos, temp_current_results);
            sortedRiderIds.add(lastPos, temp_current);

            currentElapsedTime = prevTime;

            try {
                prevTime = getLastElapsedTimeInArray(currentPos);
            } catch (IndexOutOfBoundsException e) {
                //is this correct?
                break;
            }
        }
    }

    public int[] getStageSegments() {
        int[] int_stage_segments = segments_ids_in_stage.stream().mapToInt(Integer::intValue).toArray();
        return int_stage_segments;
    }

    public double getLength() {
        return this.length;
    }

    public int getNumberOfRegisteredRiders() {
        return this.registeredRiders;
    }

    public void addSegmentIdAndValue(int segmentId, SegmentType segmentType) {
        this.stage_private_segments.add(segmentId);
        this.segment_values.add(segmentType);
        segments_in_stage++;
    }

    public static void addSegmentToHashMap(int segmentId, int stageId) {
        Stages.segment_and_stage_ids.put(segmentId, stageId);
    }

    public SegmentType getSegmentValue(int segmentId) {
        return (segment_values.get(stage_private_segments.indexOf(segmentId)));
    }

    public void removeSegmentFromObjectList(int segmentId) {
        segment_and_stage_ids.remove(segmentId);
        stage_private_segments.remove(segmentId);
        segment_values.remove(stage_private_segments.indexOf(segmentId));
    }

    public int getTotalSegmentsInStage() {
        return segments_in_stage;
    }

    public void addResultsToStage(int riderId, LocalTime... checkpoints) {
        stages_results.put(riderId, checkpoints);
    }

    public LocalTime[] getRidersResults(int riderId) {
        return stages_results.get(riderId);

    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void calculateAdjustedElapsedTime() {

    }

    public ArrayList<Integer> getResultsKeys() {
        Set<Integer> keySet = this.stages_results.keySet();
        ArrayList<Integer> listOfKeys = new ArrayList<>(keySet);
        // check whether this works...
        return (listOfKeys);
    }

    public ArrayList<LocalTime[]> getResultsValues() {
        Collection<LocalTime[]> collection = this.stages_results.values();
        ArrayList<LocalTime[]> listOfValues = new ArrayList<>(collection);
        // check whether this works...
        return (listOfValues);
    }

    public void sortResultsAccordingToElapsedTime(){
        ArrayList<Integer> listOfKeys = getResultsKeys();
        ArrayList<LocalTime[]> listOfValues = getResultsValues();

        for(int x = 0; x< listOfKeys.size(); x++){
            LocalTime[] temp_array = listOfValues.get(x);
            temp_array[temp_array.length - 1];

        }

    }

    public Boolean resultsExistCheck(int riderId) {
        ArrayList<Integer> keys = getResultsKeys();

    }

    public void updateResultsArrayLists(){
        this.sortedRiderIds = 
    }

}
