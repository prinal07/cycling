package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Races {
    private int raceId;
    private String name;
    private String description;
    private int number_of_stages = 0;
    private int total_length; // The sum of all the stages' length
    private Stages[] all_stage_objects; /// not used and needed... remove...
    public static int total_races = 0;
    private int[] all_segments;
    private ArrayList<LocalTime> ridersAdjustedElapsedTimeInRace = new ArrayList<>();
    private ArrayList<Integer> riderIdsByAdjElapsedTimeInRace = new ArrayList<>();
    public static HashMap<LocalTime, Integer> riders_and_adj_elapsed_time = new HashMap<>();
    public static ArrayList<Integer> raceIds = new ArrayList<>();

    private ArrayList<Integer> ridersTotalMtPoints = new ArrayList<>();

    public static HashMap<Integer, Races> races_hashmap = new HashMap<>();
    private int[] stageIdArray = new int[21]; // google says there are 21 stages in the grand tour

    public Races() {
    }

    public String getName() {
        return this.name;
    }

    public Races(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
        raceIds.add(raceId);
    }

    public int getRaceId() {
        return (this.raceId);
    }

    public static int[] getRaceIds(){
        return raceIds.stream().mapToInt(Integer::intValue).toArray();
    }

    public void setTotalMtResults(ArrayList<Integer> points){
        this.ridersTotalMtPoints.addAll(points);

    }
    public int getNumberOfStages() {
        return (this.number_of_stages);
    }

    public int[] getStageIds() {
        return this.stageIdArray;
    }

    public void addToStagesHashMap(int key, Stages stage) {
        Stages.stages_hashmap.put(key, stage);
        int local_number_of_stages = this.number_of_stages++;
        stageIdArray[local_number_of_stages] = key;
        this.total_length = (int) (total_length + stage.getLength());
    }

   
    public static void sortRaceStages(int raceId, LocalDateTime[] array) {
        Arrays.sort(array);
    }

    public String getDescription() {
        return this.description;
    }

    public String getTotalLength() {
        double local_total = 0;
        for (int x = 0; x < stageIdArray.length; x++) {
            local_total = local_total + Stages.stages_hashmap.get(stageIdArray[x]).getStageLength();
        }
        return String.valueOf(local_total);
    }

    public void deleteRace(int raceId) {
        // Deleting Stages and corresponding Results...
        for (int t = 0; t < stageIdArray.length; t++) {
            Stages.stages_hashmap.get(stageIdArray[t]).deleteStage(stageIdArray[t]);
        }

        // Deleting race details...
        races_hashmap.remove(raceId);

    }

    public static Collection<Races> getRaceObjects() {
        return races_hashmap.values();
    }

    public void addToAdjustedElapsedTimes(int index, LocalTime time) {
        LocalTime oldTime = this.ridersAdjustedElapsedTimeInRace.get(index);
        int hours = time.getHour();
        int mins = time.getMinute();
        int sec = time.getSecond();

        int oldhours = oldTime.getHour();
        int oldmins = oldTime.getMinute();
        int oldsec = oldTime.getSecond();

        this.ridersAdjustedElapsedTimeInRace.set(index, LocalTime.of(oldhours + hours, mins + oldmins, sec + oldsec));
    }

    public void addtoAdjArrayList(int index, LocalTime value) {
        this.ridersAdjustedElapsedTimeInRace.add(index, value);
        this.riderIdsByAdjElapsedTimeInRace.add(0);
    }

    public ArrayList<LocalTime> getAdjElapsedTimes(){
        return this.ridersAdjustedElapsedTimeInRace;
    }

    public void addRiderIdToAdjElapsedList(int index, int riderId){
        this.riderIdsByAdjElapsedTimeInRace.set(index, riderId);
    }

    public int[] getRidersByGCRank(){
        int[] list = this.riderIdsByAdjElapsedTimeInRace.stream().mapToInt(Integer::intValue).toArray();
        return list;
    }

    public ArrayList<Integer> getRidersByGCRankArrayList(){
        return this.riderIdsByAdjElapsedTimeInRace;
    }
}

// how and where will the list of stage objects be created and parsed to the
// addStageToRace method?