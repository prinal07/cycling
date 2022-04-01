package cycling; 

import java.util.*;

public class Teams {
    private String name;
    private String description;
    public static int total_teams = 0;
    private int teamId;
    public static int team_counter = 0;
    public static int riderCount = 0;
    
    public static ArrayList<Integer> teamId_list = new ArrayList<>(22);
    private ArrayList<Integer> riders_list = new ArrayList<>(9);
    public static HashMap<Integer, Teams> teamsHashMap = new HashMap<>();

    public static int id_index;

    public Teams(){  
    }
       /**
	* The method is a constructor method that creates a Teams object with attribute assignment.
	*
	* @param name The name of the team.
	* @param description The description of the stage.
    * @param teamId The ID of the team being queried
	*/
    public Teams(String name, String description, int teamId){
        this.name = name;
        this.description = description;
        this.teamId = teamId;
        teamId_list.add(teamId);
    }
    
    
    /** 
     * @return int
     */
    public int getTeamId(){
        return(this.teamId);
    }
    /**
	* The method sets the team ID
	*
	*/
    public void setTeamId(int teamId){
        this.teamId = teamId;
    }
    
    /**
	* The method gets the list of the ridersId
	*
	* @return Array of the riderIds
	*/
    public int[] getRiderIdList(){
        int[] array = riders_list.stream().mapToInt(Integer::intValue).toArray();
        return array;
    }
    
    /**
	* The method removes all riders from the Team.
	*
	*/
    public void removeAllRidersFromTeam(){
        this.riders_list.clear();
    }

    /**
	* The method allows for riders to be added to created teams.
	*
	*/
    public void addRider(int riderId, int teamId){
        for (int i:teamId_list){
            if (i == teamId){
                riders_list.add(riderId);
                riderCount++;
            }
            else{
                //add exception and assertion...
            }
        }
    }
    
    /**
	* The method removes the team details.
	*
	*/
    public static void remove(int teamId){
        teamsHashMap.get(teamId).removeAllRidersFromTeam();
        teamsHashMap.remove(teamId);
        total_teams -= 1;

        for (int x = 0; x<teamId_list.size();x++){
            if (teamId_list.get(x) == teamId){
                teamId_list.remove(x);
            }
        }
    }
    /**
	* The method gets the Team name
	*
    * @return the name
	*/
    public String getName(){
        return this.name;

    }

}
