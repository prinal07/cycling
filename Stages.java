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
    private String stage_state = "";
    // SEGMENT: Contains segments.
    // RESULTS: Results exist.
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
    private double tempSegmentLengthTotal = 0;

    private ArrayList<Integer> ridersPoints = new ArrayList<>(); // uses the sortedRiderIds as reference for order of
                                                                 // entry.

    private ArrayList<ArrayList<Integer>> sortedRiderIdsBySegment = new ArrayList<>();
    // index 0: ArrayList of sorted Riders Ids by Sprint Segment
    // index 1: 

    private ArrayList<Integer> ridersMountainPts = new ArrayList<>();
    
    // uses the sortedRiderIds to add corresponding values,
    // hence the swapSortElapsedTimes must be executed before this can be used.

    private int segments_in_stage = 0;
    public static int segment_counter = 0; // Total segments in entirety.
    public static HashMap<Integer, Stages> stages_hashmap = new HashMap<>();

    public static HashMap<Integer, Integer> segment_and_stage_ids = new HashMap<>();

    private ArrayList<Integer> stage_private_segments = new ArrayList<>();
    private ArrayList<SegmentType> segment_values = new ArrayList<>();

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

    public void removeRider(int riderId){
        Riders.allRidersIds.remove(riderId);
        Riders.riders_hashmap.remove(riderId);
        removeResults(riderId);
    }

    public void removeResults(int riderId){
        stages_results.get(riderId);
        int index = sortedRiderIds.indexOf(riderId);
        // int a = this.sortedRiderIdsBySegment.get(0).get(index);
        int b = this.ridersMountainPts.get(index);
        int c= sortedRiderIds.get(index);
        int d= ridersPoints.get(index);
        LocalTime[] e= sortedElapsedRiderResults.get(index);
        LocalTime f= adjustedElapsedTimes.get(index);

        System.out.println(" b: "+ b + " c: " +c + " d: "+ d+ " e: "+ e+ " f: "+ f);


        stages_results.remove(riderId);
        //int index = sortedRiderIds.indexOf(riderId);
        System.out.println("Index: "+ index);
        // this.sortedRiderIdsBySegment.get(0).remove(index);
        this.ridersMountainPts.remove(index);
        sortedRiderIds.remove(index);
        ridersPoints.remove(index);
        sortedElapsedRiderResults.remove(index);
        adjustedElapsedTimes.remove(index);

    }

    public boolean containsResults(int riderId) {
        return this.stages_results.containsKey(riderId);
    }


    public void addToSegmentLengthTotal(double length) {
        this.tempSegmentLengthTotal = tempSegmentLengthTotal + length;
    }

    public double getSegmentTotalLength() {
        return this.tempSegmentLengthTotal;
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

    public double getStageLength() {
        return this.length;
    }

    public void addToElapsedSortedArrays(int index, int riderId, LocalTime[] checkpoints) {
        this.sortedElapsedRiderResults.add(index, checkpoints);
        this.sortedRiderIds.add(index, riderId);
        this.adjustedElapsedTimes.add(index, LocalTime.of(00, 00, 00));
        this.ridersPoints.add(0);
        this.ridersMountainPts.add(0);
        registeredRiders++;


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
        registeredRiders++;
    }

    public void setState(String state) {
        this.stage_state = state;
    }

    public String getState() {
        return this.stage_state;
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
    }

    public void updateResults(int riderId, LocalTime[] oldcheckpoints, LocalTime[] newCheckpoints) {
        stages_results.replace(riderId, oldcheckpoints, newCheckpoints);
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

    public ArrayList<Integer> getRidersRankedArrayList() {
        return sortedRiderIds;
    }

    public LocalTime[] getRidersRankedAdjustedList() {
        LocalTime[] rankedAdjList = adjustedElapsedTimes.toArray(new LocalTime[] {});
        return rankedAdjList;
    }

    public void addToRiderPoints(int index, int points) {
        int oldPoints = this.ridersPoints.get(index);
        this.ridersPoints.set(index, oldPoints + points);
    }

    public void addToRidersMountainPts(int index, int points) {
        int oldPoints = this.ridersMountainPts.get(index);
        this.ridersMountainPts.set(index, oldPoints + points);
    }

    public int[] getRidersPoints() {
        int[] riders_points = this.ridersPoints.stream().mapToInt(Integer::intValue).toArray();
        return riders_points;
    }

    public ArrayList<Integer> getAllRidersMtPoints() {
        return ridersMountainPts;
    }

    public int getIndexInSortedArrays(int riderId) {
        return sortedRiderIds.indexOf(riderId);
    }

    public LocalTime[] getRidersSortedResultsOfRider(int index) {
        return (this.sortedElapsedRiderResults.get(index));
    }

    public ArrayList<LocalTime[]> getRidersSortedResultsArraylist() {
        return (this.sortedElapsedRiderResults);
    }

    public void searchSpecificSegmentAndSort(SegmentType type, int typeIndex) {
        this.sortedRiderIdsBySegment.add(typeIndex, sortedRiderIds);

        if (segment_values.contains(type) == true) {
            int index = segment_values.indexOf(type);

            ArrayList<Integer> list = new ArrayList<>();
            list.addAll(sortedRiderIdsBySegment.get(typeIndex));

            for (int riderIndex1 = 0; riderIndex1 < sortedRiderIds.size(); riderIndex1++) {
                LocalTime[] checkpoint1 = stages_results.get(sortedRiderIds.get(riderIndex1));
                LocalTime segmentTime = checkpoint1[index + 1];

                for (int riderIndex2 = 1; riderIndex2 < sortedRiderIds.size() - 1; riderIndex2++) {
                    LocalTime[] checkpoint2 = stages_results.get(sortedRiderIds.get(riderIndex2));
                    LocalTime segmentTime2 = checkpoint2[index + 1];

                    if (segmentTime2.isBefore(segmentTime)) {
                        list.set(riderIndex1, sortedRiderIds.get(riderIndex2));
                        list.set(riderIndex2, sortedRiderIds.get(riderIndex1));
                    }

                    else {
                        list.set(riderIndex1, sortedRiderIds.get(riderIndex1));
                        list.set(riderIndex2, sortedRiderIds.get(riderIndex2));
                    }

                }

            }
            this.sortedRiderIdsBySegment.set(typeIndex, list);

        }

    }

    public int getSortedRiderIndexBySegment(int typeIndex, int riderId) {
        ArrayList<Integer> temp_arraylist = this.sortedRiderIdsBySegment.get(typeIndex);
        return temp_arraylist.indexOf(riderId);
    }

    public String getStageName() {
        return this.stage_name;
    }

    public int getStageId() {
        return this.stageId;
    }

    public void deleteStage(int stageId) {
        // Deleting Stage related data...
        total_stages -= 1;
        stages_hashmap.remove(stageId);

        // Deleting segments related data...
        segment_counter -= stage_private_segments.size();
        segment_values.clear();
        for (int segment : stage_private_segments) {
            segment_and_stage_ids.remove(segment);
        }
        stage_private_segments.clear();

        // Deleting Results related data...
        stages_results.clear();
        ridersPoints.clear();

    }

    public void resetArrayListValues(int arrayListSize) {
        for (int x = 0; x < arrayListSize; x++) {
            System.out.println(raceId);
            Races.races_hashmap.get(raceId).addToAdjustedElapsedTimes(x, LocalTime.of(00, 00, 00));
        }
    }

}
