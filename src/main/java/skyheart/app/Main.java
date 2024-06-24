package skyheart.app;

import skyheart.domain.Programme;
import skyheart.domain.Schedule;
import skyheart.exceptions.SkyHeartException;
import skyheart.persistence.JsonDataLoader;
import skyheart.programme.ProgrammeSolution;
import skyheart.scorer.ScheduleSolutionScorer;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<Programme> programmeList = JsonDataLoader.getProgrammeData();
		Schedule emptySchedule = JsonDataLoader.getScheduleData();
		
		try {
			ProgrammeSolution programmeSolution = new ProgrammeSolution();
			List<Programme> programmeFilterList = new ArrayList<Programme>();
			
			// Stage One: Filter list of acceptable programmes and return results
			programmeFilterList = programmeSolution.checkProgramRulesFromSkyHeart(programmeList);
		
			// Stage Two:
			ScheduleSolutionScorer scheduleSolution = new ScheduleSolutionScorer();
			Schedule scheduleComplete = new Schedule();
			// assign combinations of slots and programmes for a given day.
			scheduleComplete = scheduleSolution.assignCombinationsSlotsProgrammes(emptySchedule, programmeFilterList);
			// score outcome using the Scorer class
			Integer scoreCalculated = scheduleSolution.CalculateScore(scheduleComplete);
			System.out.println("Score = "+scoreCalculated);
			
		} catch (SkyHeartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
