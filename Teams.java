package cycling; 

import java.util.*;

public class Teams {
    private String name;
    private String description;
    public static int total_teams = 0;
    private int teamId;
    public int[] teamId_list = new int[22];  //changed this to normal list as getTeams says the return type needs to be an int[], google says 22 teams in grand tour...
    private int[] riders_list = new int[9];

   
    public int createTeam(String name, String description){
        this.name = name;
        this.description = description;
        teamId = ++total_teams;
        teamId_list[total_teams - 1] = teamId;

        return(teamId);

    } 

//addRider is needed to add a rider to a new team after being removed
    public void addRider(int riderId, int teamId){
        for (int i:teamId_list){
            if (teamId == teamId_list[i]){
                riders_list[i] = (riderId);
            }
            else{
                //add exception and assertion...
            }
        }
    }

    public int[] getTeams(){
        return(this.teamId_list);
    }

    public int[] getTeamRiders(int teamId){
        return riders_list;
    }

    public void removeRider(int riderId){
    }

}
