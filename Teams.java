package cycling; 

import java.util.*;

public class Teams {
    private String name;
    private String description;
    public static int total_teams = 0;
    private int teamId;
    public static int id_index = 0; 
    public static int riderCount = 0;
    
    public static int[] teamId_list = new int[22];  //googled the number of teams, and feel like should be some fixed number, which can be changed if needed.
    //public static ArrayList<Integer> teamIds = new ArrayList<>(22);

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
        teamId_list[id_index++] = teamId;
        //teamIds.add(teamId);
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

    public void remove(int riderId){

    }

}
