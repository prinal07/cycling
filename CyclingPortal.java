package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * CyclingPortal interface.
 *
 * @author Prinal Patel
 * @author Tharun Veluri
 * @version 1.1
 *
 */
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

	/**
	 * Get the races currently created in the platform.
	 *
	 * @return An array of race IDs in the system or an empty array if none exists.
	 */
	@Override
	public int[] getRaceIds() {
		if (Races.total_races == 0) {
			int[] empty = new int[1];
			return empty;
		} else {
			return Races.getRaceIds();
		}
	}

	/**
	 * The method creates a staged race in the platform with the given name and
	 * description.
	 *
	 * @param name        Race’s name.
	 * @param description Race’s description (can be null).
	 * @throws IllegalNameException If the name already exists in the platform.
	 * @throws InvalidNameException If the name is null, empty, has more than 30
	 *                              characters, or has white spaces.
	 * @return the unique ID of the created race.
	 *
	 */
	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		// This if statement checks if the name is null, empty, has more than 30
		// characters or has any whitespaces.
		if (name == null || name.length() > 30 || name.equals("") || name.contains(" ")) {
			throw new InvalidNameException(
					// Throws an InvalidNameException if the conditions are met.
					"The name can't be null, empty, be more than 30 characters long or have whitespaces in it");
		}

		// This statement checks if the name already exists in the platform.
		// Only thrown if the total number of races is more than 0.
		if (Races.total_races > 0) {
			Collection<Races> raceObjects = Races.races_hashmap.values();
			for (Races obj : raceObjects) {
				if (obj.getName().equals(name)) {
					// Throws an IllegalNameException if the name conditions are met.
					throw new IllegalNameException("This name already exists in the platform");
				}
			}

		}

		// Creates a new race
		Races race = new Races(name, description);
		Races.total_races++;
		int local_ctr = Races.raceIdCtr++;
		int local_race_id = local_ctr * 10;
		race.setRaceId(local_race_id);

		Races.races_hashmap.put(local_race_id, race);

		// Returns the raceId
		return (local_race_id);

	}

	/**
	 * Get the details from a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return Any formatted string containing the race ID, name, description, the
	 *         number of stages, and the total length (i.e., the sum of all stages’
	 *         length).
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		// Checks if the raceId matches to any in the system.
		Collection<Races> raceObjects = Races.races_hashmap.values();
		Boolean flag2 = false;
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				flag2 = true;
			}
		}

		if (flag2 == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This raceId is not found");
		}

		String name = Races.races_hashmap.get(raceId).getName();
		String description = Races.races_hashmap.get(raceId).getDescription();
		int number_of_stages = Races.races_hashmap.get(raceId).getNumberOfStages();
		String length = Races.races_hashmap.get(raceId).getTotalLength();

		String raceDetails = "Name: " + name + "," + "Description: " + description + "," + "Number of Stages: "
				+ number_of_stages + "," + "Length: " + length;

		return raceDetails;

	}

	/**
	 * The method removes the race and all its related information, i.e., stages,
	 * segments, and results.
	 *
	 * @param raceId The ID of the race to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {

		// This method loops throug the races hashmap to check if the raceID is in the
		// system
		Collection<Races> raceObjects = Races.races_hashmap.values();
		Boolean flag4 = false;
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				flag4 = true;
			}
		}

		if (flag4 == false) {
			// If the condtions are met then an IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("This raceId is not found");
		}

		Races.races_hashmap.get(raceId).deleteRace();
	}

	/**
	 * The method queries the number of stages created for a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return The number of stages created for the race.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {

		// This method loops throug the races hashmap to check if the raceID is in the
		// system
		Collection<Races> raceObjects = Races.races_hashmap.values();
		Boolean flag = false;
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				flag = true;
			}
		}
		if (flag == false) {
			// If the condtions are met then an IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("This raceId is not found");

		}

		int number_of_stages = Races.races_hashmap.get(raceId).getNumberOfStages();
		return number_of_stages;
	}

	/**
	 * Creates a new stage and adds it to the race.
	 *
	 * @param raceId      The race which the stage will be added to.
	 * @param stageName   An identifier name for the stage.
	 * @param description A descriptive text for the stage.
	 * @param length      Stage length in kilometres.
	 * @param startTime   The date and time in which the stage will be raced. It
	 *                    cannot be null.
	 * @param type        The type of the stage. This is used to determine the
	 *                    amount of points given to the winner.
	 * @return the unique ID of the stage.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 * @throws IllegalNameException     If the name already exists in the platform.
	 * @throws InvalidNameException     If the new name is null, empty, has more
	 *                                  than 30.
	 * @throws InvalidLengthException   If the length is less than 5km.
	 */
	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {

		// This if statement checks if the name is null, empty, has more than 30
		// characters or has any whitespaces.
		if (stageName == null || stageName.length() > 30 || stageName.equals("") || stageName.contains(" ")) {
			// Throws an InvalidNameException if the conditions are met.
			throw new InvalidNameException(
					"The name can't be null, empty, be more than 30 characters long or have whitespaces in it");
		}

		// Checks if the length is less than 5km.
		if (length < 5) {
			// Thrown an InvalidLengthException if true
			throw new InvalidLengthException("The Length has to be longer than 5km");
		}

		// Checks if the raceId matches to any in the system.
		Collection<Races> raceObjects = Races.races_hashmap.values();
		Boolean flag = false;
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				flag = true;
				break;
			}
		}
		if (flag == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This raceId is not found");
		}

		if (Stages.total_stages != 0) {
			// Checks if the stageName is already in the system.
			Collection<Stages> stageObject = Stages.stages_hashmap.values();
			for (Stages obj : stageObject) {
				if (obj.getStageName().equals(stageName)) {
					// If conditions are met then IllegalNameException is thrown
					throw new IllegalNameException("This Stage Name is not found");
				}
			}
		}

		Stages stage = new Stages(stageName, description, length, startTime, raceId, type);
		int local_total_stages = Stages.counter++;
		int temp_stage_id = local_total_stages * 2;
		stage.setStageId(temp_stage_id);

		Races.races_hashmap.get(raceId)
				.addToStagesHashMap(temp_stage_id, stage, length);

		return temp_stage_id;
	}

	/**
	 * Retrieves the list of stage IDs of a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return The list of stage IDs ordered (from first to last) by their sequence
	 *         in the
	 *         race.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {

		// Checks if the raceId matches to any in the system.
		Collection<Races> raceObjects = Races.races_hashmap.values();
		boolean flag = false;
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				flag = true;
			}
		}
		if (flag == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This raceId is not found");
		}

		int[] stages_ids = Races.races_hashmap.get(raceId).getStageIds();
		return stages_ids;
	}

	/**
	 * Gets the length of a stage in a race, in kilometres.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @return The stage’s length.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {

		// Checks if the stageId is stages_hashmap
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		Boolean flag = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag = true;
			}
		}
		if (flag == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This stageId is not found");
		}

		double stage_length = Stages.stages_hashmap.get(stageId).getLength();
		return stage_length;
	}

	/**
	 * Removes a stage and all its related data, i.e., segments and results.
	 *
	 * @param stageId The ID of the stage being removed.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {

		// Checks if the stageId is stages_hashmap
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		Boolean flag3 = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag3 = true;

			}
		}

		if (flag3 == false) {
			// If the conditions are met then IDNotRecognised is thrown
			throw new IDNotRecognisedException("This Stage ID is not found");
		}

		Stages.stages_hashmap.get(stageId).deleteStage(stageId);
	}

	/**
	 * Adds a climb segment to a stage.
	 *
	 * @param stageId         The ID of the stage to which the climb segment is
	 *                        being added.
	 * @param location        The kilometre location where the climb finishes within
	 *                        the stage.
	 * @param type            The category of the climb - {@link SegmentType#C4},
	 *                        {@link SegmentType#C3}, {@link SegmentType#C2},
	 *                        {@link SegmentType#C1}, or {@link SegmentType#HC}.
	 * @param averageGradient The average gradient for the climb.
	 * @param length          The length of the climb in kilometre.
	 * @return The ID of the segment created.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidLocationException   If the location is out of bounds of the
	 *                                    stage length.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 * @throws InvalidStageTypeException  Time-trial stages cannot contain any
	 *                                    segment.
	 */
	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {

		// Checks if the stageId is in stages_hashmap
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		Boolean flag = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag = true;
			}

		}
		if (flag == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("this StageId is not found");
		}

		String state = Stages.stages_hashmap.get(stageId).getState();
		// Checks if the stage is able to perform an action in its current state
		if (state.equals("RESULTS") || state != ("waiting for results")) {
		}

		else {
			// If not incompatible then an InvalidStageStateException is thrown
			throw new InvalidStageStateException("Results not yet added.");
		}

		// Checks if the the location thats being assigned is outside the bounds of the
		// stage
		if ((Stages.stages_hashmap.get(stageId).getSegmentTotalLength() + location) > Stages.stages_hashmap.get(stageId)
				.getLength()) {
			// If conditions are met then an InvalidLocationException is thrown
			throw new InvalidLocationException("Location of segment is out of bounds of the stage.");
		}

		// Checks if the stage type is a time trial that contains segments
		if (Stages.stages_hashmap.get(stageId).getStageType() == StageType.TT) {
			// If conditions are met then the InvalidStageTypeException is thrown
			throw new InvalidStageTypeException("Cannot add segments to a time-trial stage");
		}

		Stages stage = Stages.stages_hashmap.get(stageId);
		int temp_segment_id = (Stages.segment_counter) * 2;
		Stages.segment_counter++;

		stage.addSegmentIdAndValue(temp_segment_id, type);
		Stages.addSegmentToHashMap(temp_segment_id, stageId);
		stage.addToSegmentLengthTotal(length);
		stage.setState("Preparing");

		return temp_segment_id;
	}

	/**
	 * Adds an intermediate sprint to a stage.
	 *
	 * @param stageId  The ID of the stage to which the intermediate sprint segment
	 *                 is being added.
	 * @param location The kilometre location where the intermediate sprint finishes
	 *                 within the stage.
	 * @return The ID of the segment created.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidLocationException   If the location is out of bounds of the
	 *                                    stage length.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 * @throws InvalidStageTypeException  Time-trial stages cannot contain any
	 *                                    segment.
	 */
	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {

		// Checks if the stageId is in stages_hashmap
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		Boolean flag = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag = true;
			}
		}
		if (flag == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("this StageId is not found");
		}

		String state = Stages.stages_hashmap.get(stageId).getState();
		// Checks if the stage is able to perform an action in its current state
		if (state.equals("waiting for results")) {
			// If it is waiting for results, an InvalidStageStateException is thrown
			throw new InvalidStageStateException("Results not yet added.");
		}

		else {
		}

		// Checks if the the location thats being assigned is outside the bounds of the
		// stage
		if ((Stages.stages_hashmap.get(stageId).getSegmentTotalLength() + location) > Stages.stages_hashmap.get(stageId)
				.getLength()) {
			// If conditions are met then an InvalidLocationException is thrown
			throw new InvalidLocationException("Location of segment is out of bounds of the stage.");
		}

		// Checks if the stage type is a time trial that contains segments
		if (Stages.stages_hashmap.get(stageId).getStageType() == StageType.TT) {
			// If conditions are met then the InvalidStageTypeException is thrown
			throw new InvalidStageTypeException("Cannot add segments to a time-trial stage");
		}
		Stages stage = Stages.stages_hashmap.get(stageId);
		int temp_segment_id = (Stages.segment_counter) * 2;
		Stages.segment_counter++;

		stage.addSegmentIdAndValue(temp_segment_id, SegmentType.valueOf("SPRINT"));
		Stages.addSegmentToHashMap(temp_segment_id, stageId);
		stage.setState("Preparing");

		return temp_segment_id;
	}

	/**
	 * Removes a segment from a stage.
	 *
	 * @param segmentId The ID of the segment to be removed.
	 * @throws IDNotRecognisedException   If the ID does not match to any segment in
	 *                                    the system.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 */
	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {

		// checks if the segmentId is in the system
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		int localCtr = 0;
		for (Stages obj : stageObject) {
			int[] ids = obj.getStageSegments();
			for (int id : ids) {
				if (id != (segmentId)) {
					// If the conditions are met then IDNotRecognisedException is thrown
					throw new IDNotRecognisedException("This Segment ID is not found in the system");
				}
			}
		}

		int stageId = Stages.segment_and_stage_ids.get(segmentId);
		// System.out.println(stageId);
		String state = Stages.stages_hashmap.get(stageId).getState();
		// System.out.println(state);

		// Checks if the stage is able to perform an action in its current state
		if (state.equals("waiting for results")) {
			// If it is waiting for results, an InvalidStageStateException is thrown
			throw new InvalidStageStateException("Results not yet added.");
		}

		else {
		}

		Stages.stages_hashmap.get(stageId)
				.removeSegmentFromObjectList(segmentId);
	}

	/**
	 * Concludes the preparation of a stage. After conclusion, the stage’s state
	 * should be "waiting for results".
	 *
	 * @param stageId The ID of the stage to be concluded.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 */
	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {

		// Checks if the stageId is in stages_hashmap
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		Boolean flag = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag = true;
			}
		}
		if (flag == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This stageId is not found");
		}

		String state = Stages.stages_hashmap.get(stageId).getState();
		// Checks if the stage is able to perform an action in its current state
		if (state.equals("RESULTS") || state != ("waiting for results")) {
		}

		else {
			// If not incompatible then an InvalidStageStateException is thrown
			throw new InvalidStageStateException("Results not yet added.");
		}

		Stages.stages_hashmap.get(stageId).setState("waiting for results");

	}

	/**
	 * Retrieves the list of segment (mountains and sprints) IDs of a stage.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @return The list of segment IDs ordered (from first to last) by their
	 *         location in the
	 *         stage.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
	@Override
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		Boolean flag = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag = true;
			}
		}

		if (flag == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This stageId is not found");
		}

		int[] stage_segments = Stages.stages_hashmap.get(stageId).getStageSegments();
		return stage_segments;
	}

	/**
	 * Creates a team with name and description.
	 *
	 * @param name        The identifier name of the team.
	 * @param description A description of the team.
	 * @return The ID of the created team.
	 * @throws IllegalNameException If the name already exists in the platform.
	 * @throws InvalidNameException If the new name is null, empty, has more than
	 *                              30 characters.
	 */
	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {

		Collection<Teams> teamObjects = Teams.teamsHashMap.values();
		for (Teams obj : teamObjects) {
			if (obj.getName().equals(name)) {
				// If conditions are met then IllegalNameException is thrown
				throw new IllegalNameException("Team with this Team Name already exists!");
			}
		}
		// This if statement checks if the name is null, empty, has more than 30
		// characters or has any whitespaces.
		if (name == null || name.length() > 30 || name.equals("") || name.contains(" ")) {
			throw new InvalidNameException(
					// Throws an InvalidNameException if the conditions are met.
					"The name can't be null, empty, be more than 30 characters long or have whitespaces in it");
		}

		int local_team_id;
		local_team_id = Teams.team_counter++;
		Teams.total_teams++;
		Teams team = new Teams(name, description, local_team_id);
		Teams.teamsHashMap.put(local_team_id, team);

		return local_team_id;
	}

	/**
	 * Removes a team from the system.
	 *
	 * @param teamId The ID of the team to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 */
	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		// Checks if the id matches any id in the system.
		Collection<Teams> teamobject = Teams.teamsHashMap.values();
		Boolean flag6 = false;
		for (Teams obj : teamobject) {
			if (obj.getTeamId() == (teamId)) {
				flag6 = true;

			}
		}

		if (flag6 == false) {
			// If the conditions are met then IDNotRecognisedException is thrown.
			throw new IDNotRecognisedException("The ID does not match to any team in the system");
		}

		Teams.remove(teamId);

	}

	/**
	 * Get the list of teams’ IDs in the system.
	 *
	 * @return The list of IDs from the teams in the system. An empty list if there
	 *         are no teams in the system.
	 *
	 */
	@Override
	public int[] getTeams() {
		int[] list = Teams.teamId_list.stream().mapToInt(Integer::intValue).toArray();
		return list;
	}

	/**
	 * Get the riders of a team.
	 *
	 * @param teamId The ID of the team being queried.
	 * @return A list with riders’ ID.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 */
	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {

		// Checks if the id matches to any id in the system
		Collection<Teams> teamobject = Teams.teamsHashMap.values();
		Boolean flag = false;
		for (Teams obj : teamobject) {
			if (obj.getTeamId() == (teamId)) {
				flag = true;
			}
		}
		if (flag == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This teamId is not found");
		}

		int[] riderIdList = Teams.teamsHashMap.get(teamId).getRiderIdList();

		return riderIdList;
	}

	/**
	 * Creates a rider.
	 *
	 * @param teamID      The ID rider’s team.
	 * @param name        The name of the rider.
	 * @param yearOfBirth The year of birth of the rider.
	 * @return The ID of the rider in the system.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 * @throws IllegalArgumentException If the name of the rider is null or the year
	 *                                  of birth is less than 1900.
	 */
	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		// Checks if the id matches to any id in the system.
		Boolean flag = false;
		Collection<Teams> teamobject = Teams.teamsHashMap.values();
		for (Teams obj : teamobject) {
			if (obj.getTeamId() == (teamID)) {
				flag = true;
			} else {
				flag = false;
			}
		}

		if (flag = true) {
		}

		else {
			// If the conditions are met then IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("The ID does not match to any team in the system");
		}

		// Checks if the name of the rider is null and if the year of birth is less than
		if (name == null || yearOfBirth < 1900) {
			// If conditions are met then IllegalArgumentException is thrown
			throw new IllegalArgumentException(
					"Name cannot be null and year of birth cannot be less than 1900. #retire");
		}
		int local_rider_id;
		Riders rider = new Riders(name, yearOfBirth);
		local_rider_id = Riders.rider_counter += 1;
		rider.setRiderId(local_rider_id);
		Riders.total_riders++;

		Teams.teamsHashMap.get(teamID)
				.addRider(local_rider_id, teamID);

		Riders.allRidersIds.add(local_rider_id);
		Riders.riders_hashmap.put(local_rider_id, rider);
		return local_rider_id;

	}

	/**
	 * Removes a rider from the system. When a rider is removed from the platform,
	 * all of its results should be also removed. Race results must be updated.
	 *
	 * @param riderId The ID of the rider to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any rider in the
	 *                                  system.
	 */
	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		// Checks if the riderId mathces to any rider in the system
		ArrayList<Integer> riderIds = Riders.allRidersIds;
		if (riderIds.contains(riderId) == false) {
			// If false then IDNotRecognisedException is thrown.
			throw new IDNotRecognisedException("The ID does not match to any rider in the system");
		}

		Riders.removeRider(riderId);

	}

	/**
	 * Record the times of a rider in a stage.
	 *
	 * @param stageId     The ID of the stage the result refers to.
	 * @param riderId     The ID of the rider.
	 * @param checkpoints An array of times at which the rider reached each of the
	 *                    segments of the stage, including the start time and the
	 *                    finish line.
	 * @throws IDNotRecognisedException    If the ID does not match to any rider or
	 *                                     stage in the system.
	 * @throws DuplicatedResultException   Thrown if the rider has already a result
	 *                                     for the stage. Each rider can have only
	 *                                     one result per stage.
	 * @throws InvalidCheckpointsException Thrown if the length of checkpoints is
	 *                                     not equal to n+2, where n is the number
	 *                                     of segments in the stage; +2 represents
	 *                                     the start time and the finish time of the
	 *                                     stage.
	 * @throws InvalidStageStateException  Thrown if the stage is not "waiting for
	 *                                     results". Results can only be added to a
	 *                                     stage while it is "waiting for results".
	 */
	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {
		// Checks if the id matches to any stage in the system
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		Boolean flag = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag = true;
			}
		}
		if (flag == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This stageId is not found");
		}

		// Checks if the riderId matches to any rider in the list
		ArrayList<Integer> riderIds = Riders.allRidersIds;
		if (riderIds.contains(riderId) == false) {
			// If condition is met then IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("The ID does not match to any rider in the system");
		}

		// Checks if the lengths of the checkpoints is not equal to the number of
		// segments in the stage + 2
		if (checkpoints.length != Stages.stages_hashmap.get(stageId).getTotalSegmentsInStage() + 2) {
			// If the conditions are met then InvalidCheckpointException is thrown
			throw new InvalidCheckpointsException(
					"Each race result should contain the times for each segment within a stage, along with the start and finish time.");
		}

		// Checks if the rider already has a result for the stage
		if (Stages.stages_hashmap.get(stageId).containsResults(riderId) == true) {
			// If the rider does have a result then DuplicatedResultException is thrown.
			throw new DuplicatedResultException("Riders results already exists.");
		}

		String state = Stages.stages_hashmap.get(stageId).getState();
		// System.out.println(state);
		// Checks if the stage is able to perform an action in its current state
		if (state.equals("Preparing") || state != ("waiting for results")) {
			// If not incompatible then an InvalidStageStateException is thrown
			throw new InvalidStageStateException("Results not yet added.");
		}

		Stages stage = Stages.stages_hashmap.get(stageId);
		stage.addResultsToStage(riderId, checkpoints);

		int[] segments = Stages.stages_hashmap.get(stageId).getStageSegments();
		for (int x = 0; x < checkpoints.length - 2; x++) {
			Riders.riders_hashmap.get(riderId).addSegmentResultsToHashMap(segments[x], checkpoints[x + 1]);
		}

		LocalTime startTime = checkpoints[0];
		String checkHour = checkpoints[checkpoints.length - 1].toString().substring(
				checkpoints[checkpoints.length - 1].toString().lastIndexOf(':') - 2,
				checkpoints[checkpoints.length - 1].toString().lastIndexOf(':'));
		String checkMin = checkpoints[checkpoints.length - 1].toString().substring(3, 5);
		Duration duration;

		if (checkpoints[checkpoints.length - 1].toString().length() > 5) {
			String checkSec = checkpoints[checkpoints.length - 1].toString().substring(6, 8);
			LocalTime finishTime = LocalTime.of(Integer.parseInt(checkHour), Integer.parseInt(checkMin),
					Integer.parseInt(checkSec));

			// System.out.println("Time as localtime: " + finishTime);
			duration = Duration.between(startTime, finishTime);
			// System.out.println("Duration: " + duration);

		}

		else {
			LocalTime finishTime = LocalTime.of(Integer.parseInt(checkHour), Integer.parseInt(checkMin));
			duration = Duration.between(startTime, finishTime);
			System.out.println("Duration: " + duration);
		}

		int intHours = (int) duration.toHours();
		int intMins = (int) ((duration.getSeconds() % (60 * 60)) / 60);
		int intSecs = (int) (duration.getSeconds() % 60);

		LocalTime elapsedTime = LocalTime.of(intHours, intMins, intSecs);
		LocalTime[] checkpoints_copy = new LocalTime[checkpoints.length + 1];
		checkpoints_copy[checkpoints.length] = elapsedTime;

		// System.out.println("Elapsed Time: " + elapsedTime);

		// stage object with elapsed time added to rider's private hashmap
		Riders.riders_hashmap.get(riderId).addStageResultsToRidersHashMap(stage, elapsedTime);
		stage.updateResults(riderId, checkpoints, checkpoints_copy);

		int index = stage.getNumberOfRegisteredRiders();

		if (index == 0) {
			stage.addToElapsedSortedArrays(index, riderId, checkpoints);
		}

		else if (index >= 1) {
			stage.getRidersRankingInStage(stageId);
			stage.addToElapsedSortedArrays(index, riderId, checkpoints);

		}
	}

	/**
	 * Get the times of a rider in a stage.
	 *
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @return The array of times at which the rider reached each of the segments of
	 *         the stage and the total elapsed time. The elapsed time is the
	 *         difference between the finish time and the start time. Return an
	 *         empty array if there is no result registered for the rider in the
	 *         stage.
	 * @throws IDNotRecognisedException If the ID does not match to any rider or
	 *                                  stage in the system.
	 */
	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// Checks if the id matches any of the riders in the system
		ArrayList<Integer> list = Riders.allRidersIds;
		ArrayList<Integer> riderIds = list;
		if (riderIds.contains(riderId) == false) {
			// If conditions are met then IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("The ID does not match to any rider in the system");
		}

		// Checks if the id mathces any of the riders in the system
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		Boolean flag = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag = true;
			}
		}

		if (flag == false) {
			// If the Id does not match then IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("This StageID is not found");
		}

		LocalDateTime startTime = Stages.stages_hashmap.get(stageId).getStartTime();
		if (startTime != null) {
			// LocalTime[] results = Stages.stages_hashmap.get(stageId)
			// .getRidersResults(riderId);

			// return results;
			int[] segments = Stages.stages_hashmap.get(stageId).getStageSegments();
			LocalTime[] array = new LocalTime[segments.length + 1];
			int ctr = 0;
			for (int segmentId : segments) {
				array[ctr] = Riders.riders_hashmap.get(riderId).getSegmentSpecificResult(segmentId);
				ctr++;
			}
			// Adds the elapsed time after the segment times.
			array[ctr] = Riders.riders_hashmap.get(riderId).getElapsedTimeInStage(Stages.stages_hashmap.get(stageId));

			return array;

		} else {
			LocalTime[] emptyArray = new LocalTime[0];
			return emptyArray;
		}
	}

	/**
	 * For the general classification, the aggregated time is based on the adjusted
	 * elapsed time, not the real elapsed time. Adjustments are made to take into
	 * account groups of riders finishing very close together, e.g., the peloton. If
	 * a rider has a finishing time less than one second slower than the
	 * previous rider, then their adjusted elapsed time is the smallest of both. For
	 * instance, a stage with 200 riders finishing "together" (i.e., less than 1
	 * second between consecutive riders), the adjusted elapsed time of all riders
	 * should be the same as the first of all these riders, even if the real gap
	 * between the 200th and the 1st rider is much bigger than 1 second. There is no
	 * adjustments on elapsed time on time-trials.
	 *
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @return The adjusted elapsed time for the rider in the stage. Return an empty
	 *         array if there is no result registered for the rider in the stage.
	 * @throws IDNotRecognisedException If the ID does not match to any rider or
	 *                                  stage in the system.
	 */
	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// Checks if the id matches any of the riders in the system
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		Boolean flag = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag = true;
			}
		}

		if (flag == false) {
			// If the Id does not match then IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("This Stage ID is not found");
		}

		// Checks if the id matches any of the riders in the system
		ArrayList<Integer> riderIds = Riders.allRidersIds;
		if (riderIds.contains(riderId) == false) {
			// If conditions are met then IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("The ID does not match to any rider in the system");
		}
		if (Stages.stages_hashmap.get(stageId).containsResults(riderId) == true) {
			calculateAdjustedElapsedTimes(stageId);
			int index = Stages.stages_hashmap.get(stageId).getIndexOfSortedRiderId(riderId);
			LocalTime time = Stages.stages_hashmap.get(stageId).getRiderAdjElapsedTime(index);
			// System.out.println("THIS ONE BOIS");
			return time;

		} else {
			return (LocalTime.of(23, 59));
			// Specification says that this should return an empty array if there is no
			// result registered for the rider or stage in the system.
			// But the method return type is LocalTime! Hence, we are just returning the
			// time 23:59 instead.
		}
	}

	/**
	 * Removes the stage results from the rider.
	 *
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @throws IDNotRecognisedException If the ID does not match to any rider or
	 *                                  stage in the system.
	 */
	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// Checks if the id matches any of the riders in the system
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		Boolean flag = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag = true;
			}
		}

		if (flag == false) {
			// If the Id does not match then IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("This Stage ID is not found");
		}

		ArrayList<Integer> riderIds = Riders.allRidersIds;
		// Checks if the id matches any of the riders in the system
		if (riderIds.contains(riderId) == false) {
			// If conditions are met then IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("The ID does not match to any rider in the system");
		} else {
			Stages.stages_hashmap.get(stageId).removeResults(riderId);
		}

	}

	/**
	 * Get the riders finished position in a a stage.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @return A list of riders ID sorted by their elapsed time. An empty list if
	 *         there is no result for the stage.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		// Checks if the id matches any of the riders in the system
		Boolean flag = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag = true;
			}
		}
		if (flag == false) {
			// If the Id does not match then IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("This Stage ID is not found");
		}

		// int[] list = Stages.stages_hashmap.get(stageId).getRidersRankList();
		if (Stages.stages_hashmap.get(stageId).getNumberOfRegisteredRiders() != 0) {

			return Stages.stages_hashmap.get(stageId).getRidersRankList();
		} else {
			int[] emptyList = new int[1];
			return emptyList;
		}
	}

	/**
	 * Get the adjusted elapsed times of riders in a stage.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of adjusted elapsed times sorted by their finish
	 *         time. An empty list if there is no result for the stage. These times
	 *         should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		// Checks if the id matches any of the riders in the system
		Boolean flag = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag = true;
			}
		}
		if (flag == false) {
			// If the Id does not match then IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("This Stage ID is not found");
		}

		int[] IdsSortedByElapsedTime = getRidersRankInStage(stageId);
		LocalTime[] adjustedList = new LocalTime[IdsSortedByElapsedTime.length];
		int index = 0;
		for (int id : IdsSortedByElapsedTime) {
			LocalTime elapsedTime = Riders.riders_hashmap.get(id)
					.getElapsedTimeInStage(Stages.stages_hashmap.get(stageId));
			adjustedList[index] = elapsedTime;
			index++;
		}
		// LocalTime[] list =
		// Stages.stages_hashmap.get(stageId).getRidersRankedAdjustedList();
		if (Stages.stages_hashmap.get(stageId).getNumberOfRegisteredRiders() != 0) {
			return adjustedList;
		} else {
			LocalTime[] emptyList = new LocalTime[1];
			return emptyList;
		}
	}

	/**
	 * Get the number of points obtained by each rider in a stage.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of points each riders received in the stage, sorted
	 *         by their elapsed time. An empty list if there is no result for the
	 *         stage. These points should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		// Checks if the id matches any of the riders in the system
		Boolean flag = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag = true;
				break;
			}
		}
		if (flag == false) {
			// System.out.println("UGH");
			// If the Id does not match then IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("This Stage ID is not found");
		}
		if (Stages.stages_hashmap.get(stageId).getState() == "Preparing") {

			calculateAllRidersPointsInStage(stageId, "SPRINT");

			return Stages.stages_hashmap.get(stageId).getRidersPoints();
		} else {
			int[] array = new int[1];
			return array;
		}

	}

	
	/** 
	 * @param stageId
	 * @param pointsDistributionChoice
	 */
	public void calculateAllRidersPointsInStage(int stageId, String pointsDistributionChoice) {
		Stages stage = Stages.stages_hashmap.get(stageId);
		// int[] riderIds = stage.getRidersRankList();
		int[] riderIds = stage.getRidersRankList();
		int index = 0;
		ArrayList<SegmentType> segments = stage.getAllSegmentValues();
		StageType type = stage.getStageType();

		if (pointsDistributionChoice.equals("Sprint")) {
			int typeIndex = 0;

			// for (int x=0; x<riderIds.length;x++){
			for (int id : riderIds) {

				switch (type) {
					case FLAT:
						stage.addToRiderPoints(index, flat[index], id);

					case MEDIUM_MOUNTAIN:
						stage.addToRiderPoints(index, medium_mountain[index], id);
						stage.addToRidersMountainPts(index, medium_mountain[index]);

					case HIGH_MOUNTAIN:
						stage.addToRiderPoints(index, high_mountain[index], id);
						stage.addToRidersMountainPts(index, medium_mountain[index]);

					case TT:
						stage.addToRiderPoints(index, time_trial[index], id);
				}

				index++;

				if (type != StageType.TT) {
					// there are no segments in a TT Stage.
				}

				else {
					for (int u = 0; u < segments.size(); u++) {
						SegmentType segment = segments.get(u);
						// for (SegmentType segment : segments) {
						if (segment == SegmentType.valueOf("SPRINT")) {
							stage.searchSpecificSegmentAndSort(SegmentType.valueOf("SPRINT"), typeIndex);
							int final_index = stage.getSortedRiderIndexBySegment(typeIndex, index);

							stage.addToRiderPoints(index, intermediate_sprint[final_index], id);
						}
					}
				}
			}

		}

		// else if (pointsDistributionChoice.equals("GC")) {
		// 	int typeIndex = 1;

		// 	int[] IdsSortedByElapsedTime = Stages.stages_hashmap.get(stageId).getRidersRankList();
		// 	LocalTime[] adjustedList = new LocalTime[IdsSortedByElapsedTime.length];
		// 	int ctr = 0;
		// 	for (int id : IdsSortedByElapsedTime) {
		// 		LocalTime elapsedTime = Riders.riders_hashmap.get(id)
		// 				.getElapsedTimeInStage(Stages.stages_hashmap.get(stageId));
		// 		adjustedList[ctr] = elapsedTime;
		// 		ctr++;
		// 	}
		// 	int counter2 = 0;
		// 	for (int riderId: IdsSortedByElapsedTime){
		// 		for()
		// 		LocalTime adjustedTime = Stages.stages_hashmap.get(stageId).getRiderAdjElapsedTime(counter2++);

				
				
		// 	}

		// 	// int [] adjustedTimes = Stages.stages_hashmap.get(stageId).getRidersRankedAdjustedList();

		// }

		// else if (pointsDistributionChoice.equals("Mountain")) {
		// 	int typeIndex = 2;
		// 	stage.searchSpecificSegmentAndSort(SegmentType.valueOf(""), typeIndex);
		// }
	}

	/**
	 * Get the number of mountain points obtained by each rider in a stage.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of mountain points each riders received in the stage,
	 *         sorted by their finish time. An empty list if there is no result for
	 *         the stage. These points should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		Collection<Stages> stageObject = Stages.stages_hashmap.values();
		// Checks if the id matches any of the riders in the system
		Boolean flag = false;
		for (Stages obj : stageObject) {
			if (obj.getStageId() == (stageId)) {
				flag = true;
			}
		}
		if (flag == false) {
			// If the Id does not match then IDNotRecognisedException is thrown
			throw new IDNotRecognisedException("This Stage ID is not found");
		}
		if (Stages.stages_hashmap.get(stageId).getState() == "RESULTS") {
			int[] mtPoints = Stages.stages_hashmap.get(stageId).getAllRidersMtPoints().stream()
					.mapToInt(Integer::intValue)
					.toArray();
			return mtPoints;
		} else {
			int[] array = new int[1];
			return array;
		}

	}

	/**
	 * Method empties this MiniCyclingPortalInterface of its contents and resets all
	 * internal counters.
	 */
	@Override
	public void eraseCyclingPortal() {

		// Erase all hashmaps
		Races.races_hashmap.clear();
		Stages.stages_hashmap.clear();
		Stages.segment_and_stage_ids.clear();
		Teams.teamsHashMap.clear();
		Riders.riders_hashmap.clear();

		// Reset all counters
		Races.total_races = 0;
		Stages.total_stages = 0;
		Stages.segment_counter = 0;
		Teams.total_teams = 0;
		Teams.id_index = 0;
		Teams.riderCount = 0;
		Riders.total_riders = 0;
	}

	/**
	 * Method saves this MiniCyclingPortalInterface contents into a serialised file,
	 * with the filename given in the argument.
	 *
	 * @param filename Location of the file to be saved.
	 * @throws IOException If there is a problem experienced when trying to save the
	 *                     store contents to the file.
	 */
	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(filename);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.close();
	}

	/**
	 * Method should load and replace this MiniCyclingPortalInterface contents with
	 * the
	 * serialised contents stored in the file given in the argument.
	 *
	 * @param filename Location of the file to be loaded.
	 * @throws IOException            If there is a problem experienced when trying
	 *                                to load the store contents from the file.
	 * @throws ClassNotFoundException If required class files cannot be found when
	 *                                loading.
	 */
	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream(filename);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		in.close();
		fileIn.close();
	}

	/**
	 * The method removes the race and all its related information, i.e., stages,
	 * segments, and results.
	 *
	 * @param name The name of the race to be removed.
	 * @throws NameNotRecognisedException If the name does not match to any race in
	 *                                    the system.
	 */
	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		Collection<Races> raceObjects = Races.races_hashmap.values();
		for (Races obj : raceObjects) {
			if (obj.getName() != name) {
				// If the conditions are met then an exception is thrown.
				throw new NameNotRecognisedException("This raceId is not found");
			}
		}
		Collection<Races> races = Races.races_hashmap.values();
		for(Races race: races){
			if (race.getName().equals(name)){
				race.deleteRace();
			}
		}
	}

	/**
	 * Get the general classification times of riders in a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return A list of riders’ times sorted by the sum of their adjusted elapsed
	 *         times in all stages of the race. An empty list if there is no result
	 *         for any stage in the race. These times should match the riders
	 *         returned by {@link #getRidersGeneralClassificationRank(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// Checks if the raceId matches to any in the system.
		Collection<Races> raceObjects = Races.races_hashmap.values();
		Boolean flag2 = false;
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				flag2 = true;
			}
		}

		if (flag2 == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This raceId is not found");
		}

		int[] ids = Races.races_hashmap.get(raceId).getStageIds();
		// System.out.println(Stages.stages_hashmap.get(ids[0]).getNumberOfRegisteredRiders());
		Stages.stages_hashmap.get(ids[0])
				.resetArrayListValues(Stages.stages_hashmap.get(ids[0]).getNumberOfRegisteredRiders());

		for (int stageId : ids) {
			LocalTime[] elapsedTime = Stages.stages_hashmap.get(stageId).getRidersRankedAdjustedList();
			ArrayList<Integer> riderslist = Stages.stages_hashmap.get(stageId).getRidersRankedArrayList();

			for (int b = 0; b < riderslist.size(); b++) {
				Races.races_hashmap.get(raceId).addToAdjustedElapsedTimes(b, elapsedTime[b]);
				Races.riders_and_adj_elapsed_time.put(elapsedTime[b], riderslist.get(b));
			}
		}

		ArrayList<LocalTime> sortedTimes = Races.races_hashmap.get(raceId).getAdjElapsedTimes();
		ArrayList<Integer> finalSortedRiders = new ArrayList<>();

		for (int q = 0; q < sortedTimes.size(); q++) {
			int localVar = Races.riders_and_adj_elapsed_time.get(sortedTimes.get(q));
			finalSortedRiders.add(localVar);
		}

		LocalTime[] finalSortedRidersArray = sortedTimes.toArray(new LocalTime[] {});

		return finalSortedRidersArray;
	}

	/**
	 * Get the overall points of riders in a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return A list of riders’ points (i.e., the sum of their points in all stages
	 *         of the race), sorted by the total elapsed time. An empty list if
	 *         there is no result for any stage in the race. These points should
	 *         match the riders returned by
	 *         {@link #getRidersGeneralClassificationRank(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// Checks if the raceId matches to any in the system.
		Collection<Races> raceObjects = Races.races_hashmap.values();
		Boolean flag2 = false;
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				flag2 = true;
			}
		}

		if (flag2 == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This raceId is not found");
		}

		int[] stageIds = Races.races_hashmap.get(raceId).getStageIds();
		int[] totalPoints = new int[Stages.stages_hashmap.get(stageIds[0]).getNumberOfRegisteredRiders()];

		for(int x=0; x<totalPoints.length;x++){
		// for (int stageId : stageIds) {
			int[] points = getRidersPointsInStage(stageIds[x]);
				totalPoints[x] = totalPoints[x] + points[x];
		}
		return totalPoints;
	}

	
	/** 
	 * @param raceId
	 * @return int[]
	 * @throws IDNotRecognisedException
	 */
	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// Checks if the raceId matches to any in the system.
		Collection<Races> raceObjects = Races.races_hashmap.values();
		Boolean flag5 = false;
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				flag5 = true;
			}
		}

		if (flag5 == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This raceId is not found");
		}
		ArrayList<Integer> sortedPointsByTotalElapsedTime = new ArrayList<>();

		ArrayList<Integer> riders = Races.races_hashmap.get(raceId).getRidersByGCRankArrayList();
		ArrayList<Integer> totalPoints = new ArrayList<>(Races.races_hashmap.get(raceId).getRidersByGCRank().length);
		ArrayList<LocalTime> totalElapsedTimes = new ArrayList<>(
				Races.races_hashmap.get(raceId).getRidersByGCRank().length);
		HashMap<Integer, LocalTime> indexAndTotalElapsedTimeHashMap = new HashMap<>();

		for (int stageId : Races.races_hashmap.get(raceId).getStageIds()) {

			ArrayList<Integer> sorted_rider_ids = Stages.stages_hashmap.get(stageId).getRidersRankedArrayList();
			ArrayList<LocalTime[]> results = Stages.stages_hashmap.get(stageId).getRidersSortedResultsArraylist();

			int[] mtStagePoints = getRidersMountainPointsInStage(stageId);
			for (int d = 0; d < mtStagePoints.length; d++) {
				if (totalPoints.isEmpty()) {
					totalPoints.add(d, mtStagePoints[d]);
				}

				else {
					int oldPoints = totalPoints.get(d);
					totalPoints.set(d, oldPoints + mtStagePoints[d]);
				}

				LocalTime[] checkpoints = results.get(d);
				if (totalElapsedTimes.isEmpty()) {
					totalElapsedTimes.add(d, checkpoints[checkpoints.length - 1]);
					indexAndTotalElapsedTimeHashMap.put(sorted_rider_ids.get(d), checkpoints[checkpoints.length - 1]);
				}

				else {
					LocalTime oldElapsedTime = totalElapsedTimes.get(d);
					int hour1 = oldElapsedTime.getHour();
					int min1 = oldElapsedTime.getHour();
					int sec1 = oldElapsedTime.getHour();

					int hour2 = checkpoints[checkpoints.length - 1].getHour();
					int min2 = checkpoints[checkpoints.length - 1].getHour();
					int sec2 = checkpoints[checkpoints.length - 1].getHour();

					LocalTime newElapsedTime = LocalTime.of(hour1 + hour2, min1 + min2, sec1 + sec2);

					totalElapsedTimes.set(d, newElapsedTime);
					indexAndTotalElapsedTimeHashMap.put(sorted_rider_ids.get(d), newElapsedTime);

				}

			}

		}

		ArrayList<Integer> finalSortedRiderIdsArr = new ArrayList<>(totalElapsedTimes.size());
		for (int i = 0; i < totalElapsedTimes.size(); i++) {
			LocalTime one = totalElapsedTimes.get(i);
			for (int y = 1; y < totalElapsedTimes.size() - 1; y++) {
				LocalTime two = totalElapsedTimes.get(y);

				if (two.isBefore(one)) {
					finalSortedRiderIdsArr.set(i, riders.get(y));
					finalSortedRiderIdsArr.set(y, riders.get(i));
				}

				else {
					finalSortedRiderIdsArr.set(i, riders.get(i));
					finalSortedRiderIdsArr.set(y, riders.get(y));

				}

			}

			for (int id : finalSortedRiderIdsArr) {
				sortedPointsByTotalElapsedTime.add(totalPoints.get(id));
			}

		}

		int[] sortedPointsByTotalElapsedTimeArray = sortedPointsByTotalElapsedTime.stream().mapToInt(Integer::intValue)
				.toArray();

		return sortedPointsByTotalElapsedTimeArray;
	}

	/**
	 * Get the general classification rank of riders in a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return A ranked list of riders’ IDs sorted ascending by the sum of their
	 *         adjusted elapsed times in all stages of the race. That is, the first
	 *         in this list is the winner (least time). An empty list if there is no
	 *         result for any stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// Checks if the raceId matches to any in the system.
		Collection<Races> raceObjects = Races.races_hashmap.values();
		Boolean flag2 = false;
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				flag2 = true;
			}
		}

		if (flag2 == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This raceId is not found");
		}

		ArrayList<LocalTime> times = Races.races_hashmap.get(raceId).getAdjElapsedTimes();
		for (int t = 0; t < times.size(); t++) {
			for (int c = 1; c < times.size() - 1; c++) {
				if (times.get(c).isBefore(times.get(t))) {
					int stageid = Races.races_hashmap.get(raceId).getStageIds()[0];
					ArrayList<Integer> riderIds = Stages.stages_hashmap.get(stageid).getRidersRankedArrayList();

					Races.races_hashmap.get(raceId).addRiderIdToAdjElapsedList(t, riderIds.get(c));
					Races.races_hashmap.get(raceId).addRiderIdToAdjElapsedList(c, riderIds.get(t));
				}
			}
		}

		int[] stageIds = Races.races_hashmap.get(raceId).getStageIds();
		boolean flag = true;
		for (int id : stageIds) {
			if (Stages.stages_hashmap.get(id).getState() != "RESULTS") {
				flag = false;
				break;
			}
		}
		if (flag == true) {
			int[] emptyArray = new int[1];
			return emptyArray;

		} else {
			return Races.races_hashmap.get(raceId).getRidersByGCRank();

		}

	}

	/**
	 * Get the ranked list of riders based on the points classification in a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return A ranked list of riders’ IDs sorted descending by the sum of their
	 *         points in all stages of the race. That is, the first in this list is
	 *         the winner (more points). An empty list if there is no result for any
	 *         stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// Checks if the raceId matches to any in the system.
		Collection<Races> raceObjects = Races.races_hashmap.values();
		Boolean flag2 = false;
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				flag2 = true;
			}
		}

		if (flag2 == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This raceId is not found");
		}

		return null;
	}

	/**
	 * Get the ranked list of riders based on the mountain classification in a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return A ranked list of riders’ IDs sorted descending by the sum of their
	 *         mountain points in all stages of the race. That is, the first in this
	 *         list is the winner (more points). An empty list if there is no result
	 *         for any stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// Checks if the raceId matches to any in the system.
		Collection<Races> raceObjects = Races.races_hashmap.values();
		Boolean flag2 = false;
		for (Races obj : raceObjects) {
			if (obj.getRaceId() == (raceId)) {
				flag2 = true;
			}
		}

		if (flag2 == false) {
			// If the conditions are met then an exception is thrown.
			throw new IDNotRecognisedException("This raceId is not found");
		}
		return null;
	}

	
	/** 
	 * @param stageId
	 */
	public void calculateAdjustedElapsedTimes(int stageId) {

		if (Stages.stages_hashmap.get(stageId).getNumberOfRegisteredRiders() == 1) {

		} else {

			int index = 0;
			LocalTime a = Stages.stages_hashmap.get(stageId).getRiderAdjElapsedTime(index);
			LocalTime b = Stages.stages_hashmap.get(stageId).getRiderAdjElapsedTime(++index);

			// LocalTime a =
			// Stages.stages_hashmap.get(stageId).getElapsedTimeInArray(index);
			// LocalTime b =
			// Stages.stages_hashmap.get(stageId).getElapsedTimeInArray(++index);

			while (index < Stages.stages_hashmap.get(stageId).getNumberOfRegisteredRiders() - 1) {

				int aInSeconds = a.toSecondOfDay();
				int bInSeconds = b.toSecondOfDay();
				if ((bInSeconds - aInSeconds) <= 1) {
					Stages.stages_hashmap.get(stageId).setValueInAdjustedTimeArray(index, a);
					Stages.stages_hashmap.get(stageId).setValueInAdjustedTimeArray(index + 1, a);
				}

				index++;
				if (index < Stages.stages_hashmap.get(stageId).getNumberOfRegisteredRiders() - 1) {
					a = b;
					b = Stages.stages_hashmap.get(stageId).getNextElapsedTimeInArray(index);
				}

			}

		}

	}

}
