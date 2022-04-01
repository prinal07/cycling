package cycling;

import java.lang.String;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.DoubleUnaryOperator;

public class Riders{   //I changed it from Teams extending Riders, to Riders extends Teams
    private int teamID;
    private int yearOfBirth;
    private String name;
    public static int total_riders = 0;
    public static int rider_counter = 0;
    private int riderId;
    private LocalTime[] riderResults; 
    
    public static ArrayList<Integer> allRidersIds = new ArrayList<>();

    public static HashMap<Integer, Riders> riders_hashmap = new HashMap<>();
    private HashMap<Stages, LocalTime> stageResults = new HashMap<>();
    private HashMap<Integer, LocalTime> segmentResults = new HashMap<>();  //Integer --> SegmentId, LocalTime --> Finish time - startTime
    private HashMap<Stages, Integer> segmentsPoints = new HashMap<>();
    private HashMap<Integer, Integer> stagePoints = new HashMap<>();



  	/**
	* The method creates a rider ID.
	*
	* @param teamID The ID of the team.
	* @param name The name of the rider.
    * @param yearOfBirth The year of birth of the rider.
	* @return The riderId of the rider.
	*/
    public int createRider(int teamID, String name, int yearOfBirth){
        this.name = name;
        this.teamID = teamID;
        this.yearOfBirth = yearOfBirth;
        riderId = ++total_riders;

        Teams team = new Teams();          
        team.addRider(riderId, teamID);

        return(riderId);
    }
    /**
	* The method adds the stage results to the riders hashmap.
	*
    * @param stage A Stages Object
    * @param duration Elapsed Time (Finish Time - Start Time)
	*/
    public void addStageResultsToRidersHashMap(Stages stage, LocalTime duration){
        this.stageResults.put(stage, duration);
    }

    /**
	* The method gets the elapsed time in stage.
	*
    * @param stage A Stages Object
    * @return the Elapsed Time in Stage
	*/
    public LocalTime getElapsedTimeInStage(Stages stage){
        return this.stageResults.get(stage);
    }

    /**
	* The method adds the Segment timings into a hashmap
	*
    * @param segmentId Segment's ID
    * @param time Rider's segment timing
	*/
    public void addSegmentResultsToHashMap(int segmentId, LocalTime time){
        this.segmentResults.put(segmentId, time);
    }

    /**
	* The method queries the rider's result of a specific segment
	*
    * @param segmentId The ID of the segment being queried
    * @return Rider's time on that segment
	*/
    public LocalTime getSegmentSpecificResult(int segmentId){
        return this.segmentResults.get(segmentId);
    }

    public Riders(){
    }
    
    /**
	* The method adds to the Stage points.
    *
	* @param points Points earned by Rider
    * @param stageId the ID of the stage being queried 
	*/
    public void addToStagePoints(int points, int stageId){
        if(this.stagePoints.containsKey(stageId)){
            Integer oldPoints = stagePoints.get(stageId);
            Integer newPoints = oldPoints + points;
            this.stagePoints.put(stageId, newPoints);
        }
        else{
            this.stagePoints.put(stageId, points);
        }        
    }
    
    /**
	* The method creates a Riders object with attribute assignment.
	*
	*/
    public Riders(String name, int yearOfBirth){
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    /**
	* The method gets the riderId.
	*
	* @return The riderId of the rider object.
	*/
    public int getRiderId(){
        return(this.riderId);
    }

    
    /** 
     * @param riderId
     */
    public void setRiderId(int riderId){
        this.riderId = riderId;
    }
  	/**
	* The method removes the a rider from the system.
	*
	* @param riderId The ID of the rider.
	*/
    public static void removeRider(int riderId){
        allRidersIds.remove(riderId);
        riders_hashmap.remove(riderId);
        ArrayList<Integer> raceIds = Races.raceIds;
        for (int x: raceIds){
            if(Races.races_hashmap.get(x).riderIsInRace(riderId) == true){
                Races.races_hashmap.get(x).removeRidersResults(riderId);
            }
        }
    }

    /**
	* The method deletes the rider's results.
	*
	*/
    public void deleteResults(){
        this.riderResults = new LocalTime[9];
        this.segmentResults.clear();
        this.segmentsPoints.clear();
        this.stagePoints.clear();
        this.stageResults.clear(); 
    }
    

}


