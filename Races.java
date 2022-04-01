package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.random.RandomGenerator.LeapableGenerator;

public class Races {
    private int raceId;
    private String name;
    private String description;
    private int number_of_stages = 0;
    private int total_length; // The sum of all the stages' length
    private Stages[] all_stage_objects; /// not used and needed... remove...
    public static int total_races = 0;
    private int[] all_segments;

    //private ArrayList<LocalTime> ridersAdjustedElapsedTimeInRace = new ArrayList<>();
    private ArrayList<Integer> riderIdsByAdjElapsedTimeInRace = new ArrayList<>();
    private HashMap<Integer, LocalTime> ridersAdjustedElapsedTimeInRace = new HashMap<>();
    public static HashMap<LocalTime, Integer> riders_and_adj_elapsed_time = new HashMap<>();
    public static ArrayList<Integer> raceIds = new ArrayList<>();
    public static int raceIdCtr = 0;


    // private HashMap<Integer, Stages> stages = new HashMap<>();


    private ArrayList<Integer> ridersTotalMtPoints = new ArrayList<>();

    public static HashMap<Integer, Races> races_hashmap = new HashMap<>();
    // private int[] stageIdArray = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private ArrayList<Integer> stageIdArray = new ArrayList<>(20);
    // google says there are 21 stages in the grand tour


    public Races() {
    }
    /**
	* The method gets the name of the race.
	*
    * @return The name of the race
	*/
    public String getName() {
        return this.name;
    }
    /**
	* The method creates a race object and with attribute assignment.
	*
	*/
    public Races(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
	* The method sets the race id.
	*
    * @param raceId The Id of the race being queried
    * @return the name of the race
	*/
    public void setRaceId(int raceId) {
        this.raceId = raceId;
        raceIds.add(raceId);
    }

    /**
	* The method gets the race id.
	*
    * @return the race id
	*/
    public int getRaceId() {
        return (this.raceId);
    }

    /**
	* The method gets all the race ids
	*
    * @return all the race ids
    */
    public static int[] getRaceIds() {
        return raceIds.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
	* The method gets the number of stages.
	*
    * @return the number of stages
	*/
    public int getNumberOfStages() {
        return (this.number_of_stages);
    }

    /**
	* The method gets the stage ids
	*
    * @return the name of the race
	*/
    public int[] getStageIds() {
        int[] array = stageIdArray.stream().mapToInt(Integer::intValue).toArray();
        return array;
    }

    /**
	* The method adds the stage object to the hashmap
	*
    * @param key Stage Id
    * @param stage A Stages object
    * @param length Length of the stage.
	*/
    public void addToStagesHashMap(int key, Stages stage, double length) {
        Stages.stages_hashmap.put(key, stage);
        int local_number_of_stages = this.number_of_stages++;
        stageIdArray.add(key);
        this.total_length = (int) (total_length + length);
    }

    /**
	* The method gets the race description
	*  
    * @return Description
	*/
    public String getDescription() {
        return this.description;
    }

    /**
	* The method gets the total length of the stage
	*
    * @return the total length of the stage
	*/
    public String getTotalLength() {
        double local_total = 0;
        for (int x = 0; x < stageIdArray.size(); x++) {
            local_total = local_total + Stages.stages_hashmap.get(stageIdArray.get(x)).getStageLength();
        }
        return String.valueOf(local_total);
    }

    /**
	* The method deletes the race from the system
	*
	*/
    public void deleteRace() {
        // Deleting Stages and corresponding Results...
        for (int t = 0; t < stageIdArray.size(); t++) {
            Stages.stages_hashmap.get(stageIdArray.get(t)).deleteStage(stageIdArray.get(t));
        }

        // Deleting race details...
        races_hashmap.remove(this.raceId);

    }

    /**
	* This method get all of the Race Objects
	*
    * @return Collection of Races objects.
	*/
    public static Collection<Races> getRaceObjects() {
        return races_hashmap.values();
    }

    /**
	*  This method adds the riders adjusted elapsed time in race to a HashMap
	*
    * @param index the key where riders data is
    * @param time the adjusted elaspsed time 
    * @return the name of the race
	*/
    public void addToAdjustedElapsedTimes(int index, LocalTime time) {
        if (index == 0) {
            this.ridersAdjustedElapsedTimeInRace.put(index, time);
        } else {
            LocalTime oldTime = this.ridersAdjustedElapsedTimeInRace.get(index);
            int hours = time.getHour();
            int mins = time.getMinute();
            int sec = time.getSecond();

            int oldhours = oldTime.getHour();
            int oldmins = oldTime.getMinute();
            int oldsec = oldTime.getSecond();

            this.ridersAdjustedElapsedTimeInRace.put(index,
                    LocalTime.of(oldhours + hours, mins + oldmins, sec + oldsec));
        }

    }

    /**
	* The methods adds the rider's adjusted elapsed time to a hashmap
	*
    * @param riderId The Id of the race being queried
    * @param time The time
    * @return the name of the race
	*/
    public void addtoAdjHashMap(int riderId, LocalTime time) {
        this.ridersAdjustedElapsedTimeInRace.put(riderId, time);
    }

    /**
	* The method returns the Adjusted Elapsed Times
	*
    * @return Adjusted Elapsed Times in an ArrayList.
	*/
    public ArrayList<LocalTime> getAdjElapsedTimes() {
        ArrayList<LocalTime> elapsedTimesList = new ArrayList<>();
        Collection<LocalTime> values = this.ridersAdjustedElapsedTimeInRace.values();
        for (LocalTime time: values){
            elapsedTimesList.add(time);
        }

        return elapsedTimesList;
    }
    /**
	* The method adds the rider ID to corresponding AdjustedElapsed Rider Id list
	*
    * @param index Position to be added.
    * @param riderId Rider's ID.
	*/
    public void addRiderIdToAdjElapsedList(int index, int riderId) {
        this.riderIdsByAdjElapsedTimeInRace.set(index, riderId);
    }

    /**
	* This method gets the position of the riderID in the adjusted elapsed arrays.
	*
    * @param riderId Rider's ID.
    * @return Position/index of the riderId in AdjustElapsed Arrays.
	*/
    public int getIndexForAdjArrays(int riderId) {
        return this.riderIdsByAdjElapsedTimeInRace.indexOf(riderId);
    }

    /**
	* The method gets the rider by General Classification rank.
	*
    * @return the list of the riders by General Classification rank
	*/
    public int[] getRidersByGCRank() {
        int[] list = this.riderIdsByAdjElapsedTimeInRace.stream().mapToInt(Integer::intValue).toArray();
        return list;
    }

    /**
	* The method gets the RiderIDs by Adjusted Elapsed Time in Race.
	*
    * @return ArrayList of riderIds sorted by General Classification rank.
	*/
    public ArrayList<Integer> getRidersByGCRankArrayList() {
        return this.riderIdsByAdjElapsedTimeInRace;
    }

    /**
	* The method removes the riders results 
	*
    * @param riderId The ID of the rider being queried.
	*/
    public void removeRidersResults(int riderId){
        this.riderIdsByAdjElapsedTimeInRace.remove(riderId);
        this.ridersAdjustedElapsedTimeInRace.remove(riderId);
        this.ridersTotalMtPoints.remove(riderId);
    }

    /**
	* This method checks whether a Rider is in a Race
	*
    * @param riderId The ID of the rider being queired
    * @return Boolean value (True or False)
	*/
    public Boolean riderIsInRace(int riderId){
        if(this.riderIdsByAdjElapsedTimeInRace.contains(riderId)){
            return true;
        }
        else{
            return false;
        }
    }
}