package scorer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import skyheart.domain.Programme;
import skyheart.domain.Schedule;
import skyheart.domain.Slot;
import skyheart.exceptions.SkyHeartException;
import skyheart.persistence.JsonDataLoader;
import skyheart.scorer.ScheduleSolutionScorer;

public class ScheduleSolutionScorerTest {
	
	List<Programme> programmeList;
	Schedule emptySchedule;
	
	ScheduleSolutionScorer scheduleSolutionScorer;
	
	@BeforeEach
	public void setUp() {
		programmeList = JsonDataLoader.getProgrammeData();
		emptySchedule = JsonDataLoader.getScheduleData();
	
		scheduleSolutionScorer = new ScheduleSolutionScorer();
	}

	@Test
	public void checkPositionOfProgrammesTest() { 
		List<Slot> slotList = new ArrayList<>(emptySchedule.getSlots());
		Programme programmeBug = new Programme("There's A Bug In My Test!", "Sky", 3, "comedy", "15", 0);
		Programme programmeDev = new Programme("I'm A Developer Get Me Out Of Here", "Sky", 6, "romance", "12", 0);
		Programme programmeStar = new Programme("Star Crossed Coders", "Sky", 8, "romance", "18", 0);
		Programme programmeBugAgain = new Programme("There's A Bug In My Test!", "Sky", 3, "comedy", "15", 0);
		Programme programmeDevAgain = new Programme("I'm A Developer Get Me Out Of Here", "Sky", 6, "romance", "12", 0);
	
		slotList.get(0).setProgramme(programmeBug);
		slotList.get(1).setProgramme(programmeDev);

		Assertions.assertTrue(scheduleSolutionScorer.checkPositionOfProgrammes(slotList, 2, programmeStar));
		
		slotList.get(2).setProgramme(programmeStar);
		Assertions.assertTrue(scheduleSolutionScorer.checkPositionOfProgrammes(slotList, 3, programmeBugAgain));
		
		slotList.get(3).setProgramme(programmeBugAgain);
		Assertions.assertTrue(scheduleSolutionScorer.checkPositionOfProgrammes(slotList, 4, programmeDevAgain));
	}

	@Test
	public void checkPositionOfProgrammesFailTest() { 
		List<Slot> slotList = new ArrayList<>(emptySchedule.getSlots());
		Programme programmeBug = new Programme("There's A Bug In My Test!", "Sky", 3, "comedy", "15", 0);
		Programme programmeDev = new Programme("I'm A Developer Get Me Out Of Here", "Sky", 6, "romance", "12", 0);
		Programme programmeStar = new Programme("Star Crossed Coders", "Sky", 8, "romance", "18", 0);
		Programme programmeDevAgain = new Programme("I'm A Developer Get Me Out Of Here", "Sky", 6, "romance", "12", 0);
	
		slotList.get(0).setProgramme(programmeBug);
		slotList.get(1).setProgramme(programmeDev);
		slotList.get(2).setProgramme(programmeStar);
				
		Assertions.assertFalse(scheduleSolutionScorer.checkPositionOfProgrammes(slotList, 3, programmeDevAgain));
	}

	@Test
	public void calculateScoreTest() throws SkyHeartException { //(Schedule scheduleSolution){
		
		List<Slot> slotList = new ArrayList<>(emptySchedule.getSlots());
			
		Programme programmeBug = new Programme("There's A Bug In My Test!", "Sky", 3, "comedy", "15", -5);
		Programme programmeDev = new Programme("I'm A Developer Get Me Out Of Here", "Sky", 6, "romance", "12", 0);
		Programme programmeStar = new Programme("Star Crossed Coders", "Sky", 8, "romance", "18", -10);
		Programme programmeDevAgain = new Programme("I'm A Developer Get Me Out Of Here", "Sky", 6, "romance", "12", -20);
	
		slotList.get(0).setProgramme(programmeBug);
		slotList.get(1).setProgramme(programmeDev);
		slotList.get(2).setProgramme(programmeStar);
		slotList.get(3).setProgramme(programmeDevAgain);
		
		Predicate<Slot> slotPredicate = p-> p.getAssignedProgramme() == null;
		slotList.removeIf(slotPredicate);
		
		Schedule newSchedule = new Schedule();
		newSchedule.setSlots(slotList);
		
		Integer scoreExpected = -35;
		Integer scoreCalculated = scheduleSolutionScorer.CalculateScore(newSchedule);
		
		Assertions.assertEquals(scoreExpected, scoreCalculated);
	}	
	
	@Test
	public void calculateScoreFailTest() throws SkyHeartException { //(Schedule scheduleSolution){
		
		List<Slot> slotList = new ArrayList<>(emptySchedule.getSlots());
		Programme programmeBug = new Programme("There's A Bug In My Test!", "Sky", 3, "comedy", "15", -5);
		Programme programmeDev = new Programme("I'm A Developer Get Me Out Of Here", "Sky", 6, "romance", "12", 0);
		Programme programmeStar = new Programme("Star Crossed Coders", "Sky", 8, "romance", "18", -10);
		Programme programmeDevAgain = new Programme("I'm A Developer Get Me Out Of Here", "Sky", 6, "romance", "12", -20);
	
		slotList.get(0).setProgramme(programmeBug);
		slotList.get(1).setProgramme(programmeDev);
		slotList.get(2).setProgramme(programmeStar);
		slotList.get(3).setProgramme(programmeDevAgain);
		
		Schedule newSchedule = new Schedule();
		newSchedule.setSlots(slotList);
		
		Assertions.assertThrows(SkyHeartException.class, () -> scheduleSolutionScorer.CalculateScore(newSchedule));	
	}	
	
	@Test
	public void assignCombinationsSlotsProgrammesFailTest() throws SkyHeartException {
		List<Programme> programmeListAux= new ArrayList<>();
		
		Assertions.assertEquals(19, emptySchedule.getSlots().size());
		Assertions.assertNull(emptySchedule.getSlots().get(0).getAssignedProgramme());
		
		Schedule scheduleFilled = scheduleSolutionScorer.assignCombinationsSlotsProgrammes(emptySchedule, programmeListAux);

		Assertions.assertEquals(19, scheduleFilled.getSlots().size());
		Assertions.assertNull(emptySchedule.getSlots().get(0).getAssignedProgramme());
	}
	
	@Test
	public void assignCombinationsSlotsProgrammesTest() throws SkyHeartException { 
		Assertions.assertEquals(19, emptySchedule.getSlots().size());
		Assertions.assertNull(emptySchedule.getSlots().get(0).getAssignedProgramme());
		
		Schedule scheduleFilled = scheduleSolutionScorer.assignCombinationsSlotsProgrammes(emptySchedule, programmeList);

		Assertions.assertEquals(19, scheduleFilled.getSlots().size());
		Assertions.assertNotNull(emptySchedule.getSlots().get(0).getAssignedProgramme());
	}

	@Test
	public void checkDifferentProgrammesTest() { 
		
		List<Slot> slotList = new ArrayList<>(emptySchedule.getSlots());
		Programme programmeComedy = new Programme("There's A Bug In My Test!", "Sky", 3, "comedy", "15", 0);
		Programme programmeRomance = new Programme("I'm A Developer Get Me Out Of Here", "Sky", 6, "romance", "12", 0);

		Assertions.assertTrue(scheduleSolutionScorer.checkDifferentProgrammes(slotList, 0, programmeRomance));
		
		slotList.get(0).setProgramme(programmeComedy);
		Assertions.assertTrue(scheduleSolutionScorer.checkDifferentProgrammes(slotList, 1, programmeRomance));
	}

	@Test
	public void checkDifferentProgrammesFailTest() { 
		List<Slot> slotList = new ArrayList<>(emptySchedule.getSlots());
		Programme programmeComedy = new Programme("There's A Bug In My Test!", "Sky", 3, "comedy", "15", 0);
		Programme programmeRomance = new Programme("I'm A Developer Get Me Out Of Here", "Sky", 6, "romance", "12", 0);
		
		slotList.get(0).setProgramme(programmeComedy);
		Assertions.assertFalse(scheduleSolutionScorer.checkDifferentProgrammes(slotList, 1, programmeComedy));
		
		slotList.get(1).setProgramme(programmeRomance);
		Assertions.assertFalse(scheduleSolutionScorer.checkDifferentProgrammes(slotList, 2, programmeRomance));
	}

	@Test
	public void checkHourCertificateProgrammeTest() throws SkyHeartException { 
		String startTime = "08:00";
		String certificate = "15";		
		Assertions.assertTrue(scheduleSolutionScorer.checkHourCertificateProgramme(startTime, certificate));
		
		startTime = "22:00";
		certificate = "18";	
		Assertions.assertTrue(scheduleSolutionScorer.checkHourCertificateProgramme(startTime, certificate));
		
		startTime = "22:00";
		certificate = "15";	
		Assertions.assertTrue(scheduleSolutionScorer.checkHourCertificateProgramme(startTime, certificate));
	} 
	
	@Test
	public void checkHourCertificateProgrammeFailTest() throws SkyHeartException { 
		String startTime = "08:00";
		String certificate = "18";		
		Assertions.assertFalse(scheduleSolutionScorer.checkHourCertificateProgramme(startTime, certificate));
	} 
		
	@Test
	public void checkHourCertificateProgrammeExceptionTest() throws SkyHeartException { 
		Assertions.assertThrows(SkyHeartException.class, () -> scheduleSolutionScorer.checkHourCertificateProgramme(null, null));
		
		Assertions.assertThrows(SkyHeartException.class, () -> scheduleSolutionScorer.checkHourCertificateProgramme(null, "15"));
		
		Assertions.assertThrows(SkyHeartException.class, () -> scheduleSolutionScorer.checkHourCertificateProgramme("21:00", null));
	}
	
	@Test
	public void checkProgrammesTwiceTest() { 
		List<Programme> auxProgrammeAlreadyAssign = new ArrayList<Programme>();
		
		Programme programmeComedy = new Programme("There's A Bug In My Test!", "Sky", 3, "comedy", "15", 0);
		Programme programmeRomance = new Programme("I'm A Developer Get Me Out Of Here", "Sky", 6, "romance", "12", 0);

		auxProgrammeAlreadyAssign.add(programmeComedy);
		auxProgrammeAlreadyAssign.add(programmeRomance);
		
		Assertions.assertFalse(scheduleSolutionScorer.checkProgrammesTwice(auxProgrammeAlreadyAssign, programmeComedy));
	}
	
	@Test
	public void checkProgrammesTwiceFailTest() { 
		List<Programme> auxProgrammeAlreadyAssign = new ArrayList<Programme>();
		
		Programme programmeComedy = new Programme("There's A Bug In My Test!", "Sky", 3, "comedy", "15", 0);
		Programme programmeRomance = new Programme("I'm A Developer Get Me Out Of Here", "Sky", 6, "romance", "12", 0);

		auxProgrammeAlreadyAssign.add(programmeComedy);
		auxProgrammeAlreadyAssign.add(programmeComedy);
		auxProgrammeAlreadyAssign.add(programmeRomance);
		
		Assertions.assertTrue(scheduleSolutionScorer.checkProgrammesTwice(auxProgrammeAlreadyAssign, programmeComedy));
	}
	
	@Test
	public void scoreCounterTest() { 
		Boolean matchCertificateHour = true;
		Boolean matchDifferentProgrammes = true;
		Boolean matchProgrammesTwice = false;
		Boolean matchPositionOfogrammes = true;
		Integer score = 0;
		
		Assertions.assertEquals(scheduleSolutionScorer.scoreCounter(score, matchCertificateHour, matchDifferentProgrammes, matchProgrammesTwice, matchPositionOfogrammes), 0);
				
		matchCertificateHour = true;
		matchDifferentProgrammes = true;
		matchProgrammesTwice = true;
		matchPositionOfogrammes = true;
		score = -5;
		
		Assertions.assertEquals(scheduleSolutionScorer.scoreCounter(score, matchCertificateHour, matchDifferentProgrammes, matchProgrammesTwice, matchPositionOfogrammes), -10);
		
		matchCertificateHour = true;
		matchDifferentProgrammes = true;
		matchProgrammesTwice = true;
		matchPositionOfogrammes = false;
		score = -10;
		
		Assertions.assertEquals(scheduleSolutionScorer.scoreCounter(score, matchCertificateHour, matchDifferentProgrammes, matchProgrammesTwice, matchPositionOfogrammes), -20);
		
		
		matchCertificateHour = true;
		matchDifferentProgrammes = false;
		matchProgrammesTwice = true;
		matchPositionOfogrammes = false;
		score = -15;
		
		Assertions.assertEquals(scheduleSolutionScorer.scoreCounter(score, matchCertificateHour, matchDifferentProgrammes, matchProgrammesTwice, matchPositionOfogrammes), -30);
		
		matchCertificateHour = false;
		matchDifferentProgrammes = false;
		matchProgrammesTwice = true;
		matchPositionOfogrammes = false;
		score = -5;
		
		Assertions.assertEquals(scheduleSolutionScorer.scoreCounter(score, matchCertificateHour, matchDifferentProgrammes, matchProgrammesTwice, matchPositionOfogrammes), -25);
	}
	
	@Test
	public void scoreCounterFailTest() { 
		Boolean matchCertificateHour = false;
		Boolean matchDifferentProgrammes = true;
		Boolean matchProgrammesTwice = false;
		Boolean matchPositionOfogrammes = true;
		Integer score = 0;
		
		Assertions.assertNotEquals(scheduleSolutionScorer.scoreCounter(score, matchCertificateHour, matchDifferentProgrammes, matchProgrammesTwice, matchPositionOfogrammes), 0);
	}
}
