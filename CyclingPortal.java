package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class CyclingPortal implements CyclingPortalInterface {

	public static int[] flat = { 50, 30, 20, 18, 16, 14, 12, 10, 8, 7, 6, 5, 4, 3, 2 };
	public static int[] medium_mountain = { 30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2 };
	public static int[] high_mountain = { 20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
	public static int[] time_trial = { 20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
	public static int[] intermediate_sprint = { 20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };

	public static int[] C4 = { 1, 0, 0, 0, 0, 0, 0, 0 };
	public static int[] C3 = { 2, 1, 0, 0, 0, 0, 0, 0 };
	public static int[] C2 = { 5, 3, 2, 1, 0, 0, 0, 0 };
	public static int[] C1 = { 10, 8, 6, 4, 2, 1, 0, 0 };
	public static int[] HC = { 20, 15, 12, 10, 8, 6, 4, 2 };

	public int[] getRaceIds() {
		return null;
	}

	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		Races race = new Races(name, description);
		int local_total_races = Races.total_races++;
		int local_race_id = local_total_races * 10;
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
		int number_of_stages = Races.races_hashmap.get(raceId).getNumberOfStages();
		return number_of_stages;
	}

	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		Stages stage = new Stages(stageName, description, length, startTime, raceId, type);
		int local_total_stages = Stages.total_stages;
		int temp_stage_id = local_total_stages * 2;
		stage.setStageId(temp_stage_id);

		Races.races_hashmap.get(raceId)
				.addToStagesHashMap(temp_stage_id, stage);

		return temp_stage_id;
	}

	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		int[] stages_ids = Races.races_hashmap.get(raceId).getStagesIds();
		return stages_ids;
	}

	public double getStageLength(int stageId) throws IDNotRecognisedException {
		double stage_length = Stages.stages_hashmap.get(stageId).getLength();
		return stage_length;
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
		Stages stage = Stages.stages_hashmap.get(stageId);
		int temp_segment_id = (Stages.segment_counter) * 2;

		stage.addSegmentIdAndValue(temp_segment_id, SegmentType.valueOf("SPRINT"));
		Stages.addSegmentToHashMap(stageId, temp_segment_id);

		// stage.addSegmentIdToArray(temp_segment_id, stageId);
		// stage.addSegmentDataToArray(SegmentType.SPRINT);

		// might have to introduce another counter variable, using segment_counter might
		// cause problemms in addSegmentIdToArray

		/////// LOOK AT THIS FUNCTION AGAIN, segment ids are in a simple array, is the
		/////// order
		/////// they're inserted in preserved? like after a segment is removed what
		/////// happens to the order?

		return temp_segment_id;
	}

	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
		int stageId = Stages.segment_and_stage_ids.get(segmentId);

		Stages.stages_hashmap.get(stageId)
				.removeSegmentFromObjectList(segmentId);
	}

	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {

	}

	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		int[] stage_segments = Stages.stages_hashmap.get(stageId).getStageSegments();
		return stage_segments;
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
		// add exception for id not recognised
		int[] riderIdList = Teams.teamsHashMap.get(teamId).getRiderIdList();

		return riderIdList;
	}

	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		int local_rider_id;
		Riders rider = new Riders(name, yearOfBirth);
		local_rider_id = ++Riders.total_riders;
		rider.setRiderId(local_rider_id);

		Teams.teamsHashMap.get(teamID)
				.addRider(local_rider_id, teamID);
		// Teams.teamsHashMap.get(teamID).getRiderIdList();
		return local_rider_id;

	}

	public void removeRider(int riderId) throws IDNotRecognisedException {

	}

	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {

		Stages stage = Stages.stages_hashmap.get(stageId);
		stage.addResultsToStage(riderId, checkpoints);

		LocalTime startTime = checkpoints[0];

		String hour = startTime.minusHours(checkpoints[checkpoints.length - 1].getHour()).toString();
		String minute = startTime.minusMinutes(checkpoints[checkpoints.length - 1].getMinute()).toString();
		String seconds = startTime.minusSeconds(checkpoints[checkpoints.length - 1].getSecond()).toString();
		String nanoOfSecond = startTime.minusNanos(checkpoints[checkpoints.length - 1].getNano()).toString();

		LocalTime elapsedTime = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute),
				Integer.parseInt(seconds), Integer.parseInt(nanoOfSecond));

		checkpoints[checkpoints.length] = elapsedTime;
		// check if any of this logic works...

		int index = stage.getNumberOfRegisteredRiders();

		if (index == 0) {
			stage.addToElapsedSortedArrays(index, riderId, checkpoints);
		}

		else {
			LocalTime prevTime = stage.getLastElapsedTimeInArray(index);

			if (elapsedTime.isBefore(prevTime)) {
				stage.swapSortElapsedTimes(index, index - 1, elapsedTime, prevTime);
			}

		}
	}

	// remeber to decrement numberOfRegisteredRiders in the removeRiders(in
	// removerider)??, removeResults, and more (maybe, think)...

	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		LocalTime[] results = Stages.stages_hashmap.get(stageId)
				.getRidersResults(riderId);

		LocalDateTime startTime = Stages.stages_hashmap.get(stageId).getStartTime();
		if (startTime != null) {
			String hour = startTime.minusHours(results[results.length - 1].getHour()).toString();
			String minute = startTime.minusMinutes(results[results.length - 1].getMinute()).toString();
			String seconds = startTime.minusSeconds(results[results.length - 1].getSecond()).toString();
			String nanoOfSecond = startTime.minusNanos(results[results.length - 1].getNano()).toString();

			LocalTime elapsedTime = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute),
					Integer.parseInt(seconds), Integer.parseInt(nanoOfSecond));

			results[results.length] = elapsedTime;
			// check if any of this logic works...
		} else {
			LocalTime[] emptyArray = new LocalTime[0];
			return emptyArray;
		}
		// Return an empty array if there is no result registered for the rider in the
		// stage. //need to add some state attribute to check for this
		return results;
	}

	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		calculateAdjustedElapsedTimes(0, stageId);
		int index = Stages.stages_hashmap.get(stageId).getIndexOfSortedRiderId(riderId);
		LocalTime time = Stages.stages_hashmap.get(stageId).getRiderAdjElapsedTime(index);
		return time;
	}

	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {

	}

	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		int[] list = Stages.stages_hashmap.get(stageId).getRidersRankList();
		if (Stages.stages_hashmap.get(stageId).getNumberOfRegisteredRiders() != 0) {
			return list;
		} else {
			int[] emptyList = new int[1];
			return emptyList;
		}

		// An empty list if there is no result for the stage.
	}

	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		LocalTime[] list = Stages.stages_hashmap.get(stageId).getRidersRankedAdjustedList();
		if (Stages.stages_hashmap.get(stageId).getNumberOfRegisteredRiders() != 0) {
			// can change this if to use a boolean method in stages, that checks if
			// sortedRiderIds.isEmpty()
			return list;
		} else {
			LocalTime[] emptyList = new LocalTime[1];
			return emptyList;
		}
	}

	// this method below can be ccalled multiple time in the getRidersPointsInRace
	// method
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		int raceId = Stages.stages_hashmap.get(stageId).getRaceId();
		int[] stageIds = Races.races_hashmap.get(raceId).getStageIds();

		for (int stage : stageIds) {
			StageType type = Stages.stages_hashmap.get(stage).getStageType();
			int[] riderIds = Stages.stages_hashmap.get(stage).getRidersRankList();
			for (int rider : riderIds) {
				// Only need to run the inner points loop 15 times, as points
				// are only assigned to the first 15 places.

				for (int x = 0; x < 15; x++) {
					results Stages.stages_hashmap.get(stage).getRidersSortedResults();
					switch (type) {
						case FLAT:

						case MEDIUM_MOUNTAIN:

						case HIGH_MOUNTAIN:

						case TT:

					}
				}
			}
		}
		return null;
	}

	public void calculateOneRidersPointsInStage(int stageId, int riderId, String pointsDistributionChoice) {
		int local_score_total = 0;
		Stages stage = Stages.stages_hashmap.get(stageId);
		int[] riderIds = stage.getRidersRankList();
		int index = stage.getIndexInSortedArrays(riderId);
		LocalTime[] rider_results = stage.getRidersSortedResultsOfRider(index);
		ArrayList<SegmentType> segments = stage.getAllSegmentValues();
		StageType type = stage.getStageType();
		int registeredRiderCtr = stage.getNumberOfRegisteredRiders();

		//for (int id : riderIds) {
			for (LocalTime time : rider_results) {

				if (pointsDistributionChoice.equals("Sprint")) {

					switch (type) {
						case FLAT:
							stage.addToRiderPoints(index, flat[index]);

						case MEDIUM_MOUNTAIN:
							stage.addToRiderPoints(index, medium_mountain[index]);

						case HIGH_MOUNTAIN:
							stage.addToRiderPoints(index, high_mountain[index]);

						case TT:
							stage.addToRiderPoints(index, time_trial[index]);

					}

					for (SegmentType segment : segments) {
						if (segment == SegmentType.valueOf("SPRINT")) {
							LocalTime [] times = stage.getRidersResults(riderId);

							LocalTime sprint_time = times[segments.indexOf(SegmentType.valueOf("SPRINT"))+1];
//NOW I HAVE TO COMPARE THESE TIMES WITH THE OTHERS, AS THEY MAY HAVE FINISHED THE SPECIFC SEGMENT IN A LOWER TIME

						}
					}

				}

				else if (pointsDistributionChoice.equals("GC")) {

				}

				else if (pointsDistributionChoice.equals("Mountain")) {

				}

				else {
					// add exception
				}
			}
		//}
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

	public void calculateAdjustedElapsedTimes(int index, int stageId) {
		LocalTime a = Stages.stages_hashmap.get(stageId).getElapsedTimeInArray(index);
		LocalTime b = Stages.stages_hashmap.get(stageId).getElapsedTimeInArray(++index);

		while (index < Stages.stages_hashmap.get(stageId).getNumberOfRegisteredRiders()) {
			int aInSeconds = a.toSecondOfDay();
			int bInSeconds = b.toSecondOfDay();
			if ((bInSeconds - aInSeconds) <= 1) {
				Stages.stages_hashmap.get(stageId).setValueInAdjustedTimeArray(index, a);
				Stages.stages_hashmap.get(stageId).setValueInAdjustedTimeArray(index + 1, a);
			}

			a = b;
			b = Stages.stages_hashmap.get(stageId).getNextElapsedTimeInArray(++index);
		}

		// HOW DOES THIS MAKE SENSE, IT COULD TECHNICALLY ALSO BE ONE HOUR AHEAD, BUT
		// BEHIND BY A FEW NANOSECONDS??
	}

	public void calculateRidersPointsInStage(int riderId) {
		// can be used recursively in calculating points of all riders in stage, and
		// points in race.
	}

	public void calculateSprinterClassPointsOfRider(int riderId) {

	}

	public void calculateMountClassPointsOfRider(int riderId) {

	}

}
