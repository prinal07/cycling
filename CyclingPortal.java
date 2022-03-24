package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.management.InvalidApplicationException;

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
		if (name == null || name.length() > 30 || name.equals("") || name.contain(" ")) {
			throw new InvalidNameException(
					"The name can't be null, empty, be more than 30 characters long or have whitespaces in it");
		}

		Collection<Races> raceObjects = Races.races_hashmap.values();
		for (Races obj : raceObjects) {
			if (obj.getName().equals(name)) {
				throw new IllegalNameException("This name already exists in the platform");
				break;
			}
		}

		Races race = new Races(name, description);
		int local_total_races = Races.total_races++;
		int local_race_id = local_total_races * 10;
		race.setRaceId(local_race_id);
		Races.races_hashmap.put(local_race_id, race);

		return (local_race_id);
	}

	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {

		Collection<Races> raceObjects = Races.races_hashmap.values();
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				throw new IDNotRecognisedException("This raceId is not found");
			}
		}

		String name = Races.races_hashmap.get(raceId).getName();
		String description = Races.races_hashmap.get(raceId).getDescription();
		int number_of_stages = Races.races_hashmap.get(raceId).getNumberOfStages();
		String length = Races.races_hashmap.get(raceId).getTotalLength();

		String raceDetails = "Name: " + name + "," + "Description: " + description + "," + "Number of Stages: "
				+ number_of_stages + "," + "Length: " + length;

		return raceDetails;

	}

	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		Collection<Races> raceObjects = Races.races_hashmap.values();
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				throw new IDNotRecognisedException("This raceId is not found");
			}
		}
	}

	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		Collection<Races> raceObjects = Races.races_hashmap.values();
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				throw new IDNotRecognisedException("This raceId is not found");
			}
		}

		int number_of_stages = Races.races_hashmap.get(raceId).getNumberOfStages();
		return number_of_stages;
	}

	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {

		if (stageName == null || stageName.length() > 30 || stageName.equals("") || stageName.contains(" ")) {
			throw new InvalidNameException(
					"The name can't be null, empty, be more than 30 characters long or have whitespaces in it");
		}

		if (length < 5) {
			throw new InvalidLengthException("The Length has to be longer than 5km");
		}

		Collection<Races> raceObjects = Races.races_hashmap.values();
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				throw new IDNotRecognisedException("This raceId is not found");
			}
		}

		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		for (Stages obj : stageObject) {
			if (obj.getStageName().equals(stageName)) {
				throw new IllegalNameException("This Stage Name is not found");
			}
		}

		Stages stage = new Stages(stageName, description, length, startTime, raceId, type);
		int local_total_stages = Stages.total_stages;
		int temp_stage_id = local_total_stages * 2;
		stage.setStageId(temp_stage_id);

		Races.races_hashmap.get(raceId)
				.addToStagesHashMap(temp_stage_id, stage);

		return temp_stage_id;
	}

	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {

		Collection<Races> raceObjects = Races.races_hashmap.values();
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				throw new IDNotRecognisedException("This raceId is not found");
			}
		}

		int[] stages_ids = Races.races_hashmap.get(raceId).getStagesIds();
		return stages_ids;
	}

	public double getStageLength(int stageId) throws IDNotRecognisedException {

		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				throw new IDNotRecognisedException("This Stage ID is not found");
			}
		}
		double stage_length = Stages.stages_hashmap.get(stageId).getLength();
		return stage_length;
	}

	public void removeStageById(int stageId) throws IDNotRecognisedException {

		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				throw new IDNotRecognisedException("This Stage ID is not found");
			}
		}
	}

	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {

		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				throw new IDNotRecognisedException("This Stage ID is not found");
			}
		}

		String state = Stages.stages_hashmap.get(stageId).getState();
		if (state.equals("RESULTS")) {
		}

		else {
			throw new InvalidStageStateException("Results not yet added.");
		}

		if ((Stages.stages_hashmap.get(stageId).getSegmentTotalLength() + location) > Stages.stages_hashmap.get(stageId)
				.getLength()) {
			throw new InvalidLocationException("Location of segment is out of bounds of the stage.");
		}

		if (Stages.stages_hashmap.get(stageId).getStageType() == StageType.TT) {
			throw new InvalidStageTypeException("Cannot add segments to a time-trial stage");
		}

		Stages.stages_hashmap.get(stageId).addToSegmentLengthTotal(length);
		Stages.stages_hashmap.get(stageId).setState("SEGMENTS");

		return 0;
	}

	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				throw new IDNotRecognisedException("This Stage ID is not found");
			}
		}

		String state = Stages.stages_hashmap.get(stageId).getState();
		if (state.equals("RESULTS")) {
		}

		else {
			throw new InvalidStageStateException("Results not yet added.");
		}

		if ((Stages.stages_hashmap.get(stageId).getSegmentTotalLength() + location) > Stages.stages_hashmap.get(stageId)
				.getLength()) {
			throw new InvalidLocationException("Location of segment is out of bounds of the stage.");
		}

		if (Stages.stages_hashmap.get(stageId).getStageType() == StageType.TT) {
			throw new InvalidStageTypeException("Cannot add segments to a time-trial stage");
		}
		Stages stage = Stages.stages_hashmap.get(stageId);
		int temp_segment_id = (Stages.segment_counter) * 2;

		stage.addSegmentIdAndValue(temp_segment_id, SegmentType.valueOf("SPRINT"));
		Stages.addSegmentToHashMap(stageId, temp_segment_id);
		stage.setState("SEGMENTS");

		return temp_segment_id;
	}

	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {

		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		int localCtr = 0;
		for (Stages obj : stageObject) {
			int[] ids = obj.getStageSegments();
			for (int id : ids) {
				if (id != (segmentId)) {
					throw new IDNotRecognisedException("This Segment ID is not found in the system");
				}
			}
		}

		int stageId = Stages.segment_and_stage_ids.get(segmentId);
		String state = Stages.stages_hashmap.get(stageId).getState();

		if (state.equals("RESULTS")) {
		}

		else {
			throw new InvalidStageStateException("Results not yet added.");
		}

		Stages.stages_hashmap.get(stageId)
				.removeSegmentFromObjectList(segmentId);
	}

	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				throw new IDNotRecognisedException("This Stage ID is not found");
			}
		}

		String state = Stages.stages_hashmap.get(stageId).getState();
		if (state.equals("RESULTS")) {
		}

		else {
			throw new InvalidStageStateException("Results not yet added.");
		}

	}

	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				throw new IDNotRecognisedException("This Stage ID is not found");
			}
		}

		int[] stage_segments = Stages.stages_hashmap.get(stageId).getStageSegments();
		return stage_segments;
	}

	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		Collection<Teams> objects = Teams.teamsHashMap.values();
		for (Teams obj : objects) {
			if (obj.getName().equals(name)) {
				throw new IllegalNameException("This name already exists in the platform");
			}
		}

		if (name == null || name.length() > 30 || name.equals("") || name.contains(" ")) {
			throw new InvalidNameException(
					"The name can't be null, empty, be more than 30 characters long or have whitespaces in it");
		}

		int local_team_id;
		Teams team = new Teams(name, description);
		local_team_id = ++Teams.total_teams;
		team.setTeamId(local_team_id);
		team.addTeamIdToList(local_team_id);

		Teams.teamsHashMap.put(local_team_id, team);

		return local_team_id;
	}

	public void removeTeam(int teamId) throws IDNotRecognisedException {
		Collection<Teams> teamobject = Teams.teamsHashMap.values();
		for (Teams obj : teamobject) {
			if (obj.getTeamId() == (teamId)) {
				throw new IDNotRecognisedException("The ID does not match to any team in the system");
			}
		}
	}

	public int[] getTeams() {
		return Teams.teamId_list;
	}

	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		Collection<Teams> teamobject = Teams.teamsHashMap.values();
		for (Teams obj : teamobject) {
			if (obj.getTeamId() == (teamId)) {
				throw new IDNotRecognisedException("The ID does not match to any team in the system");
			}
		}

		int[] riderIdList = Teams.teamsHashMap.get(teamId).getRiderIdList();

		return riderIdList;
	}

	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		Collection<Teams> teamobject = Teams.teamsHashMap.values();
		for (Teams obj : teamobject) {
			if (obj.getTeamId() == (teamID)) {
				throw new IDNotRecognisedException("The ID does not match to any team in the system");
			}
		}

		if (name == null || yearOfBirth < 1900) {
			throw new IllegalArgumentException(
					"Name cannot be null and year of birth cannot be less than 1900. #retire");
		}
		int local_rider_id;
		Riders rider = new Riders(name, yearOfBirth);
		local_rider_id = ++Riders.total_riders;
		rider.setRiderId(local_rider_id);

		Teams.teamsHashMap.get(teamID)
				.addRider(local_rider_id, teamID);

		Riders.allRidersIds.add(local_rider_id);
		Riders.riders_hashmap.put(local_rider_id, rider);
		// Teams.teamsHashMap.get(teamID).getRiderIdList();
		return local_rider_id;

	}

	public void removeRider(int riderId) throws IDNotRecognisedException {
		ArrayList<Integer> riderIds = Riders.allRidersIds;
		if (riderIds.contains(riderId) == false) {
			throw new IDNotRecognisedException("The ID does not match to any rider in the system");
		}
	}

	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException, InvalidStageStateException {
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				throw new IDNotRecognisedException("This Stage ID is not found");
			}
		}

		if (Stages.stages_hashmap.get(stageId).containsResults(riderId) == false){
			throw new DuplicatedResultException("Riders results already exists.");
		}

		if (checkpoints.length != Stages.stages_hashmap.get(stageId).getTotalSegmentsInStage() + 2){
			throw new InvalidCheckpointsException("Each race result should contain the times for each segment within a stage, along with the start and finish time.");
		}

		String state = Stages.stages_hashmap.get(stageId).getState();
		if (state.equals("RESULTS")) {
		}

		else {
			throw new InvalidStageStateException("Results not yet added.");
		}

		Stages stage = Stages.stages_hashmap.get(stageId);
		stage.addResultsToStage(riderId, checkpoints);
		stage.setState("RESULTS");

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
		calculateAllRidersPointsInStage(stageId, "SPRINT");

		return Stages.stages_hashmap.get(stageId).getRidersPoints();

	}

	public void calculateAllRidersPointsInStage(int stageId, String pointsDistributionChoice) {
		Stages stage = Stages.stages_hashmap.get(stageId);
		int[] riderIds = stage.getRidersRankList();
		int index = 0;
		ArrayList<SegmentType> segments = stage.getAllSegmentValues();
		StageType type = stage.getStageType();

		if (pointsDistributionChoice.equals("Sprint")) {
			int typeIndex = 0;

			for (int id : riderIds) {

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

				index++;
			}

			for (int u = 0; u < segments.size(); u++) {
				SegmentType segment = segments.get(u);
				// for (SegmentType segment : segments) {
				if (segment == SegmentType.valueOf("SPRINT")) {
					stage.searchSpecificSegmentAndSort(SegmentType.valueOf("SPRINT"), typeIndex);
					int final_index = stage.getSortedRiderIndexBySegment(typeIndex, index);

					stage.addToRiderPoints(index, intermediate_sprint[final_index]);
				}
			}

		}

		else if (pointsDistributionChoice.equals("GC")) {
			int typeIndex = 1;

		}

		else if (pointsDistributionChoice.equals("Mountain")) {
			int typeIndex = 2;

			stage.searchSpecificSegmentAndSort(SegmentType.valueOf("M"), typeIndex);

		}

		else {
			// add exception
		}
	}

	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		calculateAllRidersPointsInStage(stageId, "Mountain");

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
