package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
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
    public static int counter = 0;
    private Double location;
    private double length;
    private LocalDateTime startTime;
    private int registeredRiders = 0;
    private Boolean resultsExist = false;
    // could use this...


    // private HashMap<Integer, Integer> segmentsHashMap = new HashMap<>();
    private ArrayList<Riders> riders = new ArrayList<>();  //use this in registerResults...
    private ArrayList<LocalTime> adjustedElapsedTimes = new ArrayList<>();


    private HashMap<Integer, LocalTime[]> stages_results = new HashMap<>(); // key: riderId, value: checkpoint array
    private ArrayList<Integer> sortedRiderIds = new ArrayList<>();
    private ArrayList<LocalTime[]> sortedElapsedRiderResults = new ArrayList<>();
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

    /**
	* The method is a constructor method that creates a stage object with attribute assignment.
	*
	* @param stageName The name of the stage.
	* @param description The description of the stage.
    * @param length The length of the stage.
	*/
    public Stages(String stageName, String description, double length, LocalDateTime startTime, int raceId,
            StageType type) {
        this.stage_name = stageName;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.raceId = raceId;
        this.stage_type = type;
    }
  	/**
	* The method sets the stage Id.
	*
	* @param stageID The ID of the stage.
	*/
    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    /**
	* The method removes the rider from system.
	*
	* @param riderID The ID of the rider.
	*/
    public void removeRider(int riderId){
        Riders.allRidersIds.remove(riderId);
        Riders.riders_hashmap.remove(riderId);
        removeResults(riderId);
    }
  	/**
	* The method removes the riders results from the system.
	*
	* @param riderID The ID of the team.
	*/
    public void removeResults(int riderId){
        stages_results.remove(riderId);
        sortedRiderIds.remove(riderId);
        ridersPoints.remove(riderId);
        Riders.riders_hashmap.get(riderId).deleteResults();
    }
  	/**
	* The method returns a boolean depending on whether the stages results hashmap contains this riders data.
	*
	* @param riderId The ID of the rider.
    * @return The method returns a boolean depending on whether the stages results hashmap contains this riders data.
	*/
    public boolean containsResults(int riderId) {
        return this.stages_results.containsKey(riderId);
    }

 	/**
	* The method adds the segments lengths of the stage.
	*
	* @param length The ID of the rider.
	*/
    public void addToSegmentLengthTotal(double length) {
        this.tempSegmentLengthTotal = tempSegmentLengthTotal + length;
    }
 	/**
	* The method returns the total segments length.
	* 
    * @return The method returns the total segments length.
	*/
    public double getSegmentTotalLength() {
        return this.tempSegmentLengthTotal;
    }
 	/**
	* The method returns the race id.
	* 
    * @return The method returns the race id.
	*/
    public int getRaceId() {
        return this.raceId;
    }

 	/**
	* The method gets the stage type.
	* 
    * @return The method returns the stage type.
	*/
    public StageType getStageType() {
        return this.stage_type;
    }
    
	/**
	* The method returns the stage length.
	*
    * @return stage length.
	*/
    public double getStageLength() {
        return this.length;
    }
 	/**
	* The method adds riders ids and checkpoints to sorted array lists.
	* @param index position to add rider data on array list
    * @param riderId The riders Id
    * @param checkpoints It holds their segment timings.
	*/
    public void addToElapsedSortedArrays(int index, int riderId, LocalTime[] checkpoints) {
        this.sortedElapsedRiderResults.add(index, checkpoints);
        this.sortedRiderIds.add(index, riderId);
        
        this.adjustedElapsedTimes.add(index, Riders.riders_hashmap.get(riderId).getElapsedTimeInStage(this));
        this.ridersPoints.add(0);
        this.ridersMountainPts.add(0);

        registeredRiders++;


    }
 	/**
	* The method sorts and returns riders according to their elapsed time.
    *
    * @param stageId The Id of the stage being queried.
    * @return the sorted array list of riders objects.
	*/
    public ArrayList<Riders> getRidersRankingInStage(int stageId){
        for (int i=0; i<riders.size() -1; i++){
            for(int j=0; j<riders.size()-i-1;j++){
                if (riders.get(j).getElapsedTimeInStage(this).compareTo(riders.get(j+1).getElapsedTimeInStage(this))>0){
                    Collections.swap(riders, j, j+1);
                }
            }
        }
        return this.riders;
    }

    /**
	* This method returns the previous elapsed time from the elapsedTime array.
	*
	* @param index The position of current elapsed time.
    * @return Returns the elapsed time of the previous rider.
	*/
    public LocalTime getLastElapsedTimeInArray(int index) {
        LocalTime[] array = this.sortedElapsedRiderResults.get(index - 1);
        return array[array.length - 1];
    }

 	/**
	* This method gets the elapsed time for the rider at the position Index in the arraylists.
	* 
    *@param index The position of the required rider's results.
    * @return The method returns the race id.
	*/
    public LocalTime getElapsedTimeInArray(int index) {
        LocalTime[] array = this.sortedElapsedRiderResults.get(index);
        return array[array.length - 1];
    }

	/**
	* This method returns the Elapsed Time of the next rider. 
	*
	* @param index The position of the current rider's elapsed time data.
    * @return LocalTime Elapsed time of rider.
	*/
    public LocalTime getNextElapsedTimeInArray(int index) {
        LocalTime[] array = this.sortedElapsedRiderResults.get(index + 1);
        return array[array.length - 1];
    }
 	/**
	* The method sets a value in the adjusted times array.
	* 
    *@param index The position in which the time is stored.
	*/
    public void setValueInAdjustedTimeArray(int index, LocalTime time) {
        this.adjustedElapsedTimes.set(index, time);
    }


 	/**
	* The method sets the stages state.
	* 
    *@param state Predefined string for stage state: "Preparaing" or "Waiting for results"
	*/
    public void setState(String state) {
        this.stage_state = state;
    }
 	/**
	* The method.
	* @return The stages state
	*/
    public String getState() {
        return this.stage_state;
    }
    /**
	* The method gets the stages segements.
	* 
    * @return The stages segments as an integer.
	*/
    public int[] getStageSegments() {
        int[] int_stage_segments = stage_private_segments.stream().mapToInt(Integer::intValue).toArray();
        return int_stage_segments;
    }

    /**
	* This method returns the length of the stage. 
	*
    * @return Stage length
	*/
    public double getLength() {
        return this.length;
    }

    /**
	* This method returns the number of registered riders. 
	*
    * @return Integer number of the number of register riders.
	*/
    public int getNumberOfRegisteredRiders() {
        return this.registeredRiders;
    }

    /**
	* This method adds the Segments details to the Stages private attributes. 
	*
	* @param segmentId The Segment's id.
	* @param segmentType The Segment Type.
	*/
    public void addSegmentIdAndValue(int segmentId, SegmentType segmentType) {
        this.stage_private_segments.add(segmentId);
        this.segment_values.add(segmentType);
        segments_in_stage++;
    }
 	/**
	* This method adds a segment to a HashMap.
	* 
    * @param segmentId The ID of the segment being queried.
    * @param stageId The ID of the stage being queried
	*/
    public static void addSegmentToHashMap(int segmentId, int stageId) {
        Stages.segment_and_stage_ids.put(segmentId, stageId);
    }

    /**
	* This method returns the Segment's value
	*
	* @param segmentId The Segment's ID.
    * @return The corresponding segment value.
	*/
    public SegmentType getSegmentValue(int segmentId) {
        return (segment_values.get(stage_private_segments.indexOf(segmentId)));
    }
 	/**
	* This method returns the segments' values
	* 
    * @return Arraylist of the Segments' values.
	*/
    public ArrayList<SegmentType> getAllSegmentValues() {
        return (segment_values);
    }
 	/**
	* This method removes a segment from an object list.
	* 
    *@param segmentId The ID of the segment being queried
	*/
    public void removeSegmentFromObjectList(int segmentId) {
        segment_and_stage_ids.remove(segmentId);
        stage_private_segments.remove(segmentId);
        segment_values.remove(stage_private_segments.indexOf(segmentId));
    }

    /**
	* This method returns the total segments in the stage.
	*
    * @return Integer value of the number of segments in the stage.
	*/
    public int getTotalSegmentsInStage() {
        return segments_in_stage;
    }

    /**
	* This method add the Rider's results to the stages results hashmap. 
	*
	* @param riderId The rider ID 
    * @param checkpoints Array of LocalTime values of the rider's segments times, start time, and finish time.
	*/
    public void addResultsToStage(int riderId, LocalTime... checkpoints) {
        stages_results.put(riderId, checkpoints);
    }
 	/**
	* The method updates the results and replaces the old results
	* 
    *@param riderId ID of the rider being queried
    * @param oldcheckpoints The checkpoint array without elapsed time
    * @param newcheckpoints The checkpoint array with the elapsed time added as the last element.
    * @return The method returns the race id.
	*/
    public void updateResults(int riderId, LocalTime[] oldcheckpoints, LocalTime[] newCheckpoints) {
        stages_results.replace(riderId, oldcheckpoints, newCheckpoints);
    }

/**
	* This method returns the timings of the specific rider. 
	*
	* @param riderId The riderID, whose timings are being queried.
    * @return Array of rider's timings.
	*/
    public LocalTime[] getRidersResults(int riderId) {
        return stages_results.get(riderId);

    }
 	/**
	* This method gets the start time of the race.
	* 
    * @return The start time of the race.
	*/
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    /**
	* This method returns the rider Ids from the . 
	*
    * @return ArrayList of  of the HashMap Keys --> Rider Ids
	*/
    public ArrayList<Integer> getResultsKeys() {
        Set<Integer> keySet = this.stages_results.keySet();
        ArrayList<Integer> listOfKeys = new ArrayList<>(keySet);
        return (listOfKeys);
    }

    /**
	* This method returns the timings of the rider.
	*
    * @return The values of the HashMap --> timings.
	*/
    public ArrayList<LocalTime[]> getResultsValues() {
        Collection<LocalTime[]> collection = this.stages_results.values();
        ArrayList<LocalTime[]> listOfValues = new ArrayList<>(collection);
        return (listOfValues);
    }
 	/**
	* The method gets the sorted list of the riders in the system.
	* 
    *@param riderId The ID of the rider being queried.
    * @return The sorted list of riders in the syste.
	*/
    public int getIndexOfSortedRiderId(int riderId) {
        return this.sortedRiderIds.indexOf(riderId);
    }
 	/**
	* The method gets the riders adjusted elapsed time .
	* 
    * @param index The position of the required rider's results.
    * @return the riders adjusted elapsed time.
	*/
    public LocalTime getRiderAdjElapsedTime(int index) {
        return this.adjustedElapsedTimes.get(index);
    }

 	/**
	* This method gets the riders ranked list.
	* .
    * @return the riders ranked list.
	*/
    public int[] getRidersRankList(){
        ArrayList<Riders> ridersList = getRidersRankingInStage(this.stageId);
        int [] rankedIdList = new int [ridersList.size()];
        for (int x = 0; x<ridersList.size();x++){
            rankedIdList[x] = ridersList.get(x).getRiderId();
        }
        return rankedIdList;
    }

    /**
	* This method returns the sorted RiderIds. 
	*
    * @return Sorted Rider Ids by Elapsed Time.
	*/
    public ArrayList<Integer> getRidersRankedArrayList() {
        return sortedRiderIds;
    }

 	/**
	* This method gets the list of the riders ranks.
	* 
    * @return the adjusted ranked list.
	*/
    public LocalTime[] getRidersRankedAdjustedList() {
        LocalTime[] rankedAdjList = adjustedElapsedTimes.toArray(new LocalTime[] {});
        return rankedAdjList;
    }

 	/**
	* The method adds the riders points.
	* 
    * @param index The position of the required rider's points.
    * @param points The points of the riders
    * @param riderId The ID of the rider being queried
    * @return The method returns the race id.
	*/
    public void addToRiderPoints(int index, int points, int riderId) {
        int oldPoints = this.ridersPoints.get(index);
        this.ridersPoints.set(index, oldPoints + points);
        Riders.riders_hashmap.get(riderId).addToStagePoints(points, this.stageId);
    }

    /**
	* This method adds the mountain points separately. 
	*
	* @param index The position of the current rider's points data.
	*/
    public void addToRidersMountainPts(int index, int points) {
        int oldPoints = this.ridersMountainPts.get(index);
        this.ridersMountainPts.set(index, oldPoints + points);
    }

    /**
	* This method returns the riders points. 
	*
    * @return Array of riders points.
	*/
    public int[] getRidersPoints() {
        int[] riders_points = this.ridersPoints.stream().mapToInt(Integer::intValue).toArray();
        return riders_points;
    }

    /**
	* This method returns all riders' mountain points. 
	*
    * @return All riders' mountain points.
	*/
    public ArrayList<Integer> getAllRidersMtPoints() {
        return ridersMountainPts;
    }

    /**
	* This method returns the Index of the riderID in the sorted arrays. 
	*
	* @param riderId RiderID whose position in the arrays is to be found.
    * @return Position of the riderId in the sorted arrays.
	*/
    public int getIndexInSortedArrays(int riderId) {
        return sortedRiderIds.indexOf(riderId);
    }

     /**
	* This method returns the sorted Results array list. 
	*
    * @return Sorted Elapsed Rider Results
	*/
    public ArrayList<LocalTime[]> getRidersSortedResultsArraylist() {
        return (this.sortedElapsedRiderResults);
    }

 	/**
	* The method searches and sorts according to the SegmentType.
	* 
    * @param type SegmentType
    * @param typeIndex Predefined index for Sprint Sorted data.
	*/
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

 	/**
	* The method returns the index of the Rider sorted by a segment.
	* 
    * @param typeIndex Specific index for the sortingBySegment data.
    * @param riderId The ID of the rider being queried
    * @return Position of the rider in the data structure.
	*/
    public int getSortedRiderIndexBySegment(int typeIndex, int riderId) {
        ArrayList<Integer> temp_arraylist = this.sortedRiderIdsBySegment.get(typeIndex);
        return temp_arraylist.indexOf(riderId);
    }
 	/**
	* The method gets the Stages Name
	* 
    * @return the stages name .
	*/
    public String getStageName() {
        return this.stage_name;
    }
 	/**
	* The method gets the Stages ID
	* 
    * @return the stages ID .
	*/
    public int getStageId() {
        return this.stageId;
    }
 	/**
	* The method deletes the stages 
    *
	* @param stageId The Stage's ID
	*/
    public void deleteStage(int stageId) {
        // Deleting Stage related data...
        total_stages -= 1;
        stages_hashmap.remove(stageId);
        
        // Deleting segments related data...
        segments_in_stage -= stage_private_segments.size();
        segment_values.clear();
        for (int segment : stage_private_segments) {
            segment_and_stage_ids.remove(segment);
        }
        stage_private_segments.clear();

        // Deleting Results related data...
        stages_results.clear();
        ridersPoints.clear();
        adjustedElapsedTimes.clear();
        sortedRiderIds.clear();
        sortedElapsedRiderResults.clear();

    }

     /**
	* This method resets the list values by assigning a pre-determined value as an identifier.
	*
	* @param arrayListSize Size of the array list that has to be reset.
	*/
    public void resetArrayListValues(int arrayListSize) {
        for (int x = 0; x < arrayListSize; x++) {
            Races.races_hashmap.get(raceId).addToAdjustedElapsedTimes(x, LocalTime.of(00, 00, 00));
        }
    }

}
