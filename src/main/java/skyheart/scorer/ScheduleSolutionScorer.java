package skyheart.scorer;

/**
* This class has all the methods about Schedule validation. 
* The method assignCombinationsSlotsProgrammes receive as a parameter the empty schedule and programmes and returns another schedule filled with programmes according to the rules 
* The method checkDifferentProgrammes verify if there are not the programme in a row.
* The method checkPositionOfProgrammes verify if the programmes are separated according to the rule.
* The method checkProgrammesTwice verify there are more than twice the same programme.
* The method checkHourCertificateProgramme verify the hour and certificate rule.
* The method scoreCounter sum all the scores coming from the others methods verification.
* The method CalculateScore return the score calculation in the specific Schedule.
*  
* @author  Bruna Vita
* @version 1.0
* @since   2018-09-07 
*/
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import skyheart.domain.Programme;
import skyheart.domain.Schedule;
import skyheart.domain.Slot;
import skyheart.exceptions.SkyHeartException;

public class ScheduleSolutionScorer {

	private int score;

	public int getScore() {
		return score;
	}

	public int CalculateScore(Schedule scheduleSolution) throws SkyHeartException{
		
		int score = 0;
		
		for (Slot schedule : scheduleSolution.getSlots()) {
			
			if(schedule.getAssignedProgramme() == null)
			{
				throw new SkyHeartException("There is slots without program.");
			}
			
			score += schedule.getAssignedProgramme().getScore();
		}
						
		return score;
	}

	public Schedule assignCombinationsSlotsProgrammes(Schedule schedule, List<Programme> programmeFilterList) throws SkyHeartException {
		List<Programme> auxProgrammeAlreadyAssign = new ArrayList<Programme>();
		List<Slot> newSlotList = new ArrayList<Slot>();
		
		List<Slot> slotList = schedule.getSlots();
		
		for (int i = 0; i < slotList.size(); i++) {
			Slot slot = slotList.get(i);
				
			for (int j = 0; j < programmeFilterList.size(); j++) {
				Programme programme = programmeFilterList.get(j);
								
				Boolean matchCertificateHour = checkHourCertificateProgramme(slot.getStartTime(), programme.getCertificate());
				Boolean matchDifferentProgrammes = checkDifferentProgrammes(slotList, i, programme);
				Boolean matchProgrammesTwice = checkProgrammesTwice(auxProgrammeAlreadyAssign, programme);
				Boolean matchPositionOfogrammes = checkPositionOfProgrammes(slotList, i, programme);
				
				programme.setScore(scoreCounter(programme.getScore(), matchCertificateHour, matchDifferentProgrammes, matchProgrammesTwice, matchPositionOfogrammes));
				
				if(matchCertificateHour && matchDifferentProgrammes && !matchProgrammesTwice && matchPositionOfogrammes) 
				{
					slot.setProgramme(programme);
					auxProgrammeAlreadyAssign.add(programme);
					Collections.rotate(programmeFilterList.subList(j, programmeFilterList.size()-1), -1);					
					break;
				} 				
			}
			
			newSlotList.add(slot);
		}

		schedule.setSlots(newSlotList);
		return schedule;
	}

	public Boolean checkDifferentProgrammes(List<Slot> slotList, int index, Programme programme) {

		if(index == 0 || programme == null)
		{
			return true;
		}
		
		if(slotList.get(index - 1).getAssignedProgramme().getProgrammeName() == programme.getProgrammeName()) {
			return false;
		}
		
		return true;
	}

	public Boolean checkPositionOfProgrammes(List<Slot> slotList, int index, Programme programme) {

		if(index < 2)
		{
			return true;
		}
		
		if(slotList.get(index - 2).getAssignedProgramme().getProgrammeName() == programme.getProgrammeName() || 
				slotList.get(index - 1).getAssignedProgramme().getProgrammeName() == programme.getProgrammeName())
		{
			return false;
		}
		
		return true;
	}

	public int scoreCounter(int programmeScore, Boolean matchCertificateHour, Boolean matchDifferentProgrammes, Boolean matchProgrammesTwice, Boolean matchPositionOfogrammes) {

		int score = programmeScore;
		
		if(!matchCertificateHour) {
			score = score - 5;
		}
		
		if(!matchDifferentProgrammes) {
			score = score - 5;
		}
		
		if(matchProgrammesTwice) {
			score = score - 5;
		}
		
		if(!matchPositionOfogrammes) {
			score = score - 5;
		}
		
		return score;
	}

	public Boolean checkProgrammesTwice(List<Programme> auxProgrammeAlreadyAssign, Programme programme) {

		if(auxProgrammeAlreadyAssign.isEmpty() || programme == null)
		{
			return false;
		}

		Map<Programme, Integer> programmeCounter = new HashMap<>();
		for (Programme objProgramme : auxProgrammeAlreadyAssign) {
			
			Integer count = programmeCounter.get(objProgramme); 
			if (count == null) { 
				programmeCounter.put(objProgramme, 1); 
			} else { 
				programmeCounter.put(objProgramme, ++count); 
			} 
		}
		
		Set<Entry<Programme, Integer>> setProgrammeCounter = programmeCounter.entrySet(); 
		for (Entry<Programme, Integer> entryProgrammeCounter : setProgrammeCounter) { 
			if (entryProgrammeCounter.getValue() > 1 && entryProgrammeCounter.getKey() == programme) { 
				return true;
			} 
		}
		
		return false;
	}

	public Boolean checkHourCertificateProgramme(String startTime, String certificate ) throws SkyHeartException {

		if(certificate == null || startTime == null)
		{
			throw new SkyHeartException("There is an inconsistent data in your file.");
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime firstHourAllowed = LocalTime.parse("21:00", formatter);
		LocalTime lastHourAllowed = LocalTime.parse("05:00", formatter);
		
		LocalTime startTimeProgramme = LocalTime.parse(startTime, formatter);
		
		if(!"18".equals(certificate) || "18".equals(certificate) && startTimeProgramme.isBefore(lastHourAllowed) || startTimeProgramme.isAfter(firstHourAllowed)) {
			return true;
		}
		else {
			return false;
		}
	}

}
