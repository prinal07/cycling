import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import javax.naming.ldap.HasControls;
import javax.sound.sampled.Port;

import cycling.BadCyclingPortal;
import cycling.BadMiniCyclingPortal;
import cycling.CyclingPortal;
import cycling.CyclingPortalInterface;
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidLengthException;
import cycling.InvalidLocationException;
import cycling.InvalidNameException;
import cycling.InvalidStageStateException;
import cycling.InvalidStageTypeException;
import cycling.MiniCyclingPortalInterface;
import cycling.SegmentType;
import cycling.StageType;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortalInterface interface -- note you
 * will want to increase these checks, and run it on your CyclingPortal class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 * @throws InvalidNameException
	 * @throws IllegalNameException
	 * @throws IDNotRecognisedException
	 * @throws IllegalArgumentException
	 * @throws InvalidLengthException
	 * @throws InvalidStageTypeException
	 * @throws InvalidStageStateException
	 * @throws InvalidLocationException
	 */
	public static void main(String[] args) throws IllegalNameException, InvalidNameException, IllegalArgumentException, IDNotRecognisedException, InvalidLengthException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		System.out.println("The system compiled and started the execution...");

		CyclingPortalInterface portal = new CyclingPortal() {
			
		};
//		CyclingPortalInterface portal = new BadCyclingPortal();

		//assert (portal.getRaceIds().length == 0)
		//		: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";



		//Comments are added to the tests that should not pass and should throw exceptions.

		portal.createRace("Race1", "description");
		portal.createRace("Race2", "description");
		portal.createRace("Race3", "description");

		portal.createRace("name", null);
		try {
			portal.createRace("name", "description2"); //same name. 
		} catch (Exception IllegalNameException) {
			System.out.print("Exception thrown.");
		}
		portal.createRace("", "description3"); //empty name.
		portal.createRace(null, "description4"); //empty name.
		portal.createRace("qwertyuiopasdfghjklzxcvbnmdfsrgergedrg", "description5"); //+30 characters
		portal.createRace("sdg fewf sfdew", "description6"); //whitespaces 


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		portal.createTeam("Team1", "description");
		portal.createTeam("Team2", "description");
		portal.createTeam("Team3", "description");

		portal.createTeam("Team3", "description2"); //same name.
		portal.createTeam(null, "description2"); //name is null.
		portal.createTeam("", "description2"); //name is empty.
		portal.createTeam("qwertyuiopasdfghjklzxcvbnmqwert", "description2"); //+30 characters.

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		int[] teamIds = portal.getTeams();
		portal.createRider(teamIds[0], "Rider1", 1902);
		portal.createRider(teamIds[0], "Rider2", 1940);
		portal.createRider(teamIds[0], "Rider3", 2003);

		portal.createRider(teamIds[1], "One", 1920);
		portal.createRider(teamIds[1], "Two", 2010);
		portal.createRider(teamIds[1], "Three", 2000);

		portal.createRider(teamIds[2], "RiderOne", 1910);
		portal.createRider(teamIds[2], "RiderTwo", 2009);
		portal.createRider(teamIds[2], "RiderThree", 1900);

		portal.createRider(200, "IdCheck", 2002); //teamId does not exist.
		portal.createRider(teamIds[0], null, 2002); //name is null.
		portal.createRider(teamIds[0], "YearCheck", 1899); //year is before 1900.


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		int[] raceIds = portal.getRaceIds();
		portal.addStageToRace(raceIds[0], "stage1", "description", 5.0, LocalDateTime.of(2022, Month.JULY, 29, 12, 00, 00), StageType.FLAT);
		portal.addStageToRace(raceIds[0], "stage2", "description", 6.0, LocalDateTime.of(2022, Month.JULY, 29, 16, 00, 00), StageType.MEDIUM_MOUNTAIN);
		portal.addStageToRace(raceIds[0], "stage3", "description", 7.0, LocalDateTime.of(2022, Month.JULY, 29, 20, 00, 00), StageType.HIGH_MOUNTAIN);
		portal.addStageToRace(raceIds[0], "stage4", "description", 5.5, LocalDateTime.of(2022, Month.JULY, 29, 23, 00, 00), StageType.TT);

		portal.addStageToRace(raceIds[1], "stageOne", "description", 5.0, LocalDateTime.of(2022, Month.JULY, 29, 12, 00, 00), StageType.FLAT);
		portal.addStageToRace(raceIds[1], "stageTwo", "description", 6.0, LocalDateTime.of(2022, Month.JULY, 29, 16, 00, 00), StageType.MEDIUM_MOUNTAIN);
		portal.addStageToRace(raceIds[1], "stageThree", "description", 7.0, LocalDateTime.of(2022, Month.JULY, 29, 20, 00, 00), StageType.HIGH_MOUNTAIN);
		portal.addStageToRace(raceIds[1], "stageFour", "description", 5.5, LocalDateTime.of(2022, Month.JULY, 29, 23, 00, 00), StageType.TT);

		portal.addStageToRace(raceIds[2], "stgOne", "description", 5.0, LocalDateTime.of(2022, Month.JULY, 29, 12, 00, 00), StageType.FLAT);
		portal.addStageToRace(raceIds[2], "stgTwo", "description", 6.0, LocalDateTime.of(2022, Month.JULY, 29, 16, 00, 00), StageType.MEDIUM_MOUNTAIN);
		portal.addStageToRace(raceIds[2], "stgThree", "description", 7.0, LocalDateTime.of(2022, Month.JULY, 29, 20, 00, 00), StageType.HIGH_MOUNTAIN);
		portal.addStageToRace(raceIds[2], "stgFour", "description", 5.5, LocalDateTime.of(2022, Month.JULY, 29, 23, 00, 00), StageType.TT);


		portal.addStageToRace(200, "RaceIdCheck", "description", 5.5, LocalDateTime.of(2022, Month.JULY, 29, 23, 00, 00), StageType.TT); //raceId does not exist.
		portal.addStageToRace(raceIds[0], "stage1", "description", 5.0, LocalDateTime.of(2022, Month.JULY, 29, 12, 00, 00), StageType.FLAT); //name exists.
		portal.addStageToRace(raceIds[0], null, "description", 5.0, LocalDateTime.of(2022, Month.JULY, 29, 12, 00, 00), StageType.FLAT); //name is null.
		portal.addStageToRace(raceIds[0], "", "description", 5.0, LocalDateTime.of(2022, Month.JULY, 29, 12, 00, 00), StageType.FLAT); //name is null.
		portal.addStageToRace(raceIds[0], "qwertyuiopasdfghjklzxcvbnmqwertyu", "description", 5.0, LocalDateTime.of(2022, Month.JULY, 29, 12, 00, 00), StageType.FLAT); //+30 characters.
		portal.addStageToRace(raceIds[0], "LengthCheck", "description", 4.9, LocalDateTime.of(2022, Month.JULY, 29, 12, 00, 00), StageType.FLAT); //name is null.


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


		int numberOfStages = portal.getNumberOfStages(raceIds[0]);
		System.out.print(numberOfStages);

		portal.getNumberOfStages(200); //raceId does not exist. 


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		int [] stages1 = portal.getRaceStages(raceIds[0]);
		for (int stage : stages1){
			System.out.println(stage + " ");
		}

		int [] stages2 = portal.getRaceStages(raceIds[1]);
		for (int stage : stages2){
			System.out.println(stage + " ");
		}
		
		int [] stages3 = portal.getRaceStages(raceIds[2]);
		for (int stage : stages3){
			System.out.println(stage + " ");
		}

		portal.getRaceStages(200); //raceId does not exist.


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		Double length1 = 3.0;
		Double gradient1 = 2.0;

		portal.addCategorizedClimbToStage(stages1[0], length1, SegmentType.C1, gradient1, length1);
		portal.addCategorizedClimbToStage(stages1[1], length1, SegmentType.C1, gradient1, length1);
		portal.addCategorizedClimbToStage(stages1[2], length1, SegmentType.C1, gradient1, length1);
		portal.addCategorizedClimbToStage(stages1[3], length1, SegmentType.C1, gradient1, length1);

		portal.addCategorizedClimbToStage(stages2[0], length1, SegmentType.C1, gradient1, length1);
		portal.addCategorizedClimbToStage(stages2[1], length1, SegmentType.C1, gradient1, length1);
		portal.addCategorizedClimbToStage(stages2[2], length1, SegmentType.C1, gradient1, length1);
		portal.addCategorizedClimbToStage(stages2[3], length1, SegmentType.C1, gradient1, length1);

		portal.addCategorizedClimbToStage(stages3[0], length1, SegmentType.C1, gradient1, length1);
		portal.addCategorizedClimbToStage(stages3[1], length1, SegmentType.C1, gradient1, length1);
		portal.addCategorizedClimbToStage(stages3[2], length1, SegmentType.C1, gradient1, length1);
		portal.addCategorizedClimbToStage(stages3[3], length1, SegmentType.C1, gradient1, length1);
	


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


		portal.getStageLength(stages1[0]); 
		portal.getStageLength(stages1[1]); 
		portal.getStageLength(stages1[2]); 
		portal.getStageLength(stages1[3]); 

		portal.getStageLength(stages2[0]); 
		portal.getStageLength(stages2[1]); 
		portal.getStageLength(stages2[2]); 
		portal.getStageLength(stages2[3]); 		

		portal.getStageLength(stages3[0]); 
		portal.getStageLength(stages3[1]); 
		portal.getStageLength(stages3[2]); 
		portal.getStageLength(stages3[3]); 
		

		portal.getStageLength(200); //stageId does not exist.


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	}

}
