package cycling;

import java.lang.String;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Riders{   //I changed it from Teams extending Riders, to Riders extends Teams
    private int teamID;
    private int yearOfBirth;
    private String name;
    public static int total_riders = 0;
    private int riderId;
    private LocalTime[] riderResults; 
    //private java.time.LocalTime adjustedElapsedTimeInStage;
    //there are many stages that 1 rider participates in.
    public static ArrayList<Integer> allRidersIds = new ArrayList<>();
    public static HashMap<Integer, Riders> riders_hashmap = new HashMap<>();


    public int createRider(int teamID, String name, int yearOfBirth){
        this.name = name;
        this.teamID = teamID;
        this.yearOfBirth = yearOfBirth;
        riderId = ++total_riders;

        Teams team = new Teams();          
        team.addRider(riderId, teamID);

        return(riderId);
    }

    public Riders(){
    }

    public Riders(String name, int yearOfBirth){
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }
    
    public int getRiderId(){
        return(this.riderId);
    }

    public void setRiderId(int riderId){
        this.riderId = riderId;
    }

    public static void removeRider(int riderId){
        allRidersIds.remove(riderId);
        riders_hashmap.remove(riderId);
    }
    

}


