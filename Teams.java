package cycling; 

import java.util.*;

public class Teams {
    private String name;
    private String description;
    public static int total_teams = 0;
    private int teamId;
    public static int ctr = 0; 
    public static int riderCount = 0;
    public static int[] teamId_list = new int[22];  //changed this to normal list as getTeams says the return type needs to be an int[], google says 22 teams in grand tour...
    private int[] riders_list = new int[9];
    public static HashMap<Integer, Teams> teamsHashMap = new HashMap<>();

    public Teams(){  
    }
   
    public Teams(String name, String description){
        this.name = name;
        this.description = description;
    }
    
    //not sure if it is needed
    public int getTeamId(){
        return(this.teamId);
    }

    public void setTeamId(int teamId){
        this.teamId = teamId;
    }

    public void addTeamIdToList(int teamId){
        teamId_list[ctr++] = teamId;
    } 
    
    public int[] getRiderIdList(){
        return riders_list;
    }
//addRider is needed to add a rider to a new team after being removed
    public void addRider(int riderId, int teamId){
        for (int i:teamId_list){
            if (teamId == teamId_list[i]){
                riders_list[riderCount++] = (riderId);
            }
            else{
                //add exception and assertion...
            }
        }
    }

    public void removeRider(int riderId){
    }

}
