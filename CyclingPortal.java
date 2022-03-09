package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CyclingPortal implements CyclingPortalInterface{

    public int[] getRaceIds() {
		return null;
	}

	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		Races race = new Races(name, description);
		int local_total_races = Races.total_races ++;
		int local_race_id = local_total_races*10;
		race.setRaceId(local_race_id);
		Races.races_hashmap.put(local_race_id, race);

		return (local_race_id);
	}

	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		return null;
	}


	public void removeRaceById(int raceId) throws IDNotRecognisedException {

	}

	
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		return 0;
	}

	
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
				Stages stage = new Stages(stageName, description, length, startTime);
				int local_total_stages = Stages.total_stages++;
				int temp_stage_id = local_total_stages*2;
				stage.setStageId(temp_stage_id);
				
				Races race = Races.races_hashmap.get(raceId);
				race.addToStagesHashMap(temp_stage_id, stage);

		return temp_stage_id;
	}

	
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		Races race = Races.races_hashmap.get(raceId);
		race;
		return null;
	}

	
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		return 0;
	}

	
	public void removeStageById(int stageId) throws IDNotRecognisedException {

	}

	
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		return 0;
	}

	
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
				

		return 0;
	}

	
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {

	}

	
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {

	}

	
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		return null;
	}

	
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        int local_team_id;
        Teams team = new Teams(name, description);
        local_team_id = ++Teams.total_teams;
        team.setTeamId(local_team_id);
        team.addTeamIdToList(local_team_id);

        Teams.teamsHashMap.put(local_team_id, team);
        
        return local_team_id;
	}

	
	public void removeTeam(int teamId) throws IDNotRecognisedException {

	}

	public int[] getTeams() {
		return Teams.teamId_list;
	}

	
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        //add exception for id not recognised
		Teams local_team = Teams.teamsHashMap.get(teamId);
        int [] riderIdList = local_team.getRiderIdList();

        return riderIdList;
	}

	
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
                int local_rider_id;
                Riders rider = new Riders(name, yearOfBirth);
                local_rider_id = ++Riders.total_riders;
				rider.setRiderId(local_rider_id);

				Teams team = new Teams();
                team.addRider(local_rider_id, teamID);
                //figure out how to do this without instantiating an empty object. 
				//could avoid this by not using the addRider method, and by implementing the logic here.

				Teams.teamsHashMap.get(teamID).getRiderIdList();
				
        return local_rider_id;
                
	}

	
	public void removeRider(int riderId) throws IDNotRecognisedException {
		
	}

	
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {
		
	}

	
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
				return null;
	}

	
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
				return null;
	}

	
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		
	}

	
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
				return null;
	}

	
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
				return null;
	}

	
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
				return null;
	}

	
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
				return null;
	}

	
	public void eraseCyclingPortal() {
		
	}

	
	public void saveCyclingPortal(String filename) throws IOException {
		
	}

	
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		
	}

	
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		
	}

	
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
				return null;
	}

	
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
				return null;
	}

	
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
				return null;
	}

	
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
				return null;
	}

	
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
				return null;
	}

	
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
				return null;
	}

    
}
