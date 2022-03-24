package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.spi.ToolProvider;

import javax.print.attribute.standard.Copies;
import javax.swing.text.Segment;

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
    private int registeredRiders = 0;
    private Boolean resultsExist = false;
    // could use this...

    private HashMap<Integer, LocalTime[]> stages_results = new HashMap<>(); // key: riderId, value: checkpoint array
    private ArrayList<Integer> sortedRiderIds = new ArrayList<>();
    private ArrayList<LocalTime[]> sortedElapsedRiderResults = new ArrayList<>();
    private ArrayList<LocalTime> adjustedElapsedTimes = new ArrayList<>();

    private ArrayList<Integer> ridersPoints = new ArrayList<>(); // uses the sortedRiderIds as reference for order of
                                                                 // entry.

    // private ArrayList<Integer> positionByIntermediateSprint = new ArrayList<>();
    // private ArrayList<Integer> positionByC4 = new ArrayList<>();
    // private ArrayList<Integer> positionByC3 = new ArrayList<>();
    // private ArrayList<Integer> positionByC2 = new ArrayList<>();
    // private ArrayList<Integer> positionByC1 = new ArrayList<>();

    // uses the sortedRiderIds to add corresponding values,
    // hence the swapSortElapsedTimes must be executed before this can be used.

    private int segments_in_stage = 0;
    public static int segment_counter = 0; // Total segments in entirety.
    public static HashMap<Integer, Stages> stages_hashmap;

    public static HashMap<Integer, Integer> segment_and_stage_ids;

    private ArrayList<Integer> stage_private_segments = new ArrayList<>();
    private ArrayList<SegmentType> segment_values;

    public Stages(String stageName, String description, double length, LocalDateTime startTime, int raceId,
            StageType type) {
        this.stage_name = stageName;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.raceId = raceId;
        this.stage_type = type;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    public int getRaceId() {
        return this.raceId;
    }

    public void setResultsExistsBoolean() {
        // HAVENT USED THIS YET...
        this.resultsExist = true;
        // NEED TO MAKE THIS FALSE IN ANY METHOD WHERE RESULTS ARE REMOVED, CHECK IF
        // RESULTS ARE EMPTY FIRST
        // 21/03/22 - 14:19 --> havent decremented the total number of registered riders
        // anywhere yet, need to do this, this will be used to checkt the above.
    }

    public StageType getStageType() {
        return this.stage_type;
    }

    public void addToElapsedSortedArrays(int index, int riderId, LocalTime[] checkpoints) {
        this.sortedElapsedRiderResults.add(index, checkpoints);
        this.sortedRiderIds.add(index, riderId);
        this.adjustedElapsedTimes.add(index, LocalTime.of(00, 00, 00));
        this.ridersPoints.add(0);

    }

    public LocalTime getLastElapsedTimeInArray(int index) {
        LocalTime[] array = this.sortedElapsedRiderResults.get(index - 1);
        return array[array.length - 1];
    }

    public LocalTime getElapsedTimeInArray(int index) {
        LocalTime[] array = this.sortedElapsedRiderResults.get(index);
        return array[array.length - 1];
    }

    public LocalTime getNextElapsedTimeInArray(int index) {
        LocalTime[] array = this.sortedElapsedRiderResults.get(index + 1);
        return array[array.length - 1];
    }

    public void setValueInAdjustedTimeArray(int index, LocalTime time) {
        this.adjustedElapsedTimes.set(index, time);
    }

    public void swapSortElapsedTimes(int currentPos, int lastPos, LocalTime currentElapsedTime, LocalTime prevTime) {
        while (currentElapsedTime.isBefore(prevTime)) {
            Integer temp_current = this.sortedRiderIds.get(currentPos);
            LocalTime[] temp_current_results = this.sortedElapsedRiderResults.get(currentPos);

            sortedElapsedRiderResults.set(lastPos, temp_current_results);
            sortedRiderIds.set(lastPos, temp_current);
            // check whether this logic requires .set or .add

            currentElapsedTime = prevTime;

            try {
                prevTime = getLastElapsedTimeInArray(currentPos);
            } catch (IndexOutOfBoundsException e) {
                // is this correct?
                break;
            }
        }
    }

    public int[] getStageSegments() {
        int[] int_stage_segments = stage_private_segments.stream().mapToInt(Integer::intValue).toArray();
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

    public ArrayList<SegmentType> getAllSegmentValues() {
        return (segment_values);
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
        registeredRiders++;
    }
    // do i need this? elapsedsortedarrays are enough? check
    // registerRiderResultsInStage

    public LocalTime[] getRidersResults(int riderId) {
        return stages_results.get(riderId);

    }

    public LocalDateTime getStartTime() {
        return this.startTime;
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

    public int getIndexOfSortedRiderId(int riderId) {
        return this.sortedRiderIds.indexOf(riderId);
    }

    public LocalTime getRiderAdjElapsedTime(int index) {
        return this.adjustedElapsedTimes.get(index);
    }

    public int[] getRidersRankList() {
        int[] rankedIdList = sortedRiderIds.stream().mapToInt(Integer::intValue).toArray();
        return rankedIdList;
    }

    public LocalTime[] getRidersRankedAdjustedList() {
        LocalTime[] rankedAdjList = adjustedElapsedTimes.toArray(new LocalTime[] {});
        return rankedAdjList;
    }

    public void addToRiderPoints(int index, int points) {
        this.ridersPoints.set(index, points);
    }

    public int getIndexInSortedArrays(int riderId) {
        return sortedRiderIds.indexOf(riderId);
    }

    public LocalTime[] getRidersSortedResultsOfRider(int index) {
        return (this.sortedElapsedRiderResults.get(index));
    }

    public int searchSpecificSegmentAndSort(SegmentType type) {

        if (segment_values.contains(type) == true) {
            int index = segment_values.indexOf(type);
            int counter = 0;
                for (int rider : sortedRiderIds) {
                    LocalTime[] checkpoint = stages_results.get(rider);
                    LocalTime[] copy = Arrays.copyOf(checkpoint, checkpoint.length);
                    LocalTime current = copy[index + 1];
                    index++;

                    for (int i = counter + 1; i < sortedRiderIds.size() - 1; i++) {
                        LocalTime next = copy[i];
                        if (next.isBefore(current)) {
                            next = copy[index + 1];
                            current = next;
                        }
                    }

                }


        }

    }

}
