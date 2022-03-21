package cycling;

import java.lang.String;

public class Riders{   //I changed it from Teams extending Riders, to Riders extends Teams
    private int teamID;
    private int yearOfBirth;
    private String name;
    public static int total_riders = 0;
    private int riderId;
    //private java.time.LocalTime adjustedElapsedTimeInStage;
    //there are many stages that 1 rider participates in.

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

    public void removeRider(int riderId){
        //Need to finish this
    }
    

}


