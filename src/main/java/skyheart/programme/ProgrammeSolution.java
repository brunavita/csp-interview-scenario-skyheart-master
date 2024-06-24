package skyheart.programme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import skyheart.domain.Programme;
import skyheart.exceptions.SkyHeartException;

/**
* This class has all the methods about Programmes validation. 
* The method checkProgramRulesFromSkyHeart receive as a parameter the list with all programs and returns another filtered list according to Sky Heart rules 
* The method checkRomanceProgramme verify the genre "Romance" rule.
* The method checkProviderProgramme verify the provider rule.
* The method checkComedyProgramme verify the genre "Comedy" rule.
*  
* @author  Bruna Vita
* @version 1.0
* @since   2018-09-07
*/

public class ProgrammeSolution {

	public List<Programme> checkProgramRulesFromSkyHeart(List<Programme> programmeList) throws SkyHeartException {
		
		if(programmeList ==null || programmeList.isEmpty()) {
			throw new SkyHeartException("There is no Programmes available.");
		}
		
		List<Programme> filteredListProgramme = new ArrayList<Programme>();
		
		for (Programme programme : programmeList) {
			Boolean matchProvider = checkProviderProgramme(programme.getProvider());
			Boolean matchGenreComedy = checkRomanceProgramme(programme.getGenre());
			Boolean matchGenreRomance = checkComedyProgramme(programme.getGenre());
			
			if(matchProvider && (matchGenreComedy || matchGenreRomance)) {
				filteredListProgramme.add(programme);
			}
		}
		
		Collections.sort(filteredListProgramme, new Comparator<Programme>() {
			public int compare(Programme p1, Programme p2) {
                if(p1.getViewershipRating() < p2.getViewershipRating()) return 1;
                if(p1.getViewershipRating() > p2.getViewershipRating()) return -1;
                return 0;
            }
        }); 
		
		return filteredListProgramme;
	}
	
	public Boolean checkRomanceProgramme(String genre) throws SkyHeartException {
		
		if(genre == null)
		{
			throw new SkyHeartException("There is an inconsistent data in your file.");
		}
		
		return "romance".equalsIgnoreCase(genre);
		
	}
	
	public Boolean checkProviderProgramme(String provider) throws SkyHeartException {
		
		if(provider == null)
		{
			throw new SkyHeartException("There is an inconsistent data in your file.");
		}
		
		return "Sky".equalsIgnoreCase(provider);
		
	}
	
	public Boolean checkComedyProgramme(String genre) throws SkyHeartException {
		
		if(genre == null)
		{
			throw new SkyHeartException("There is an inconsistent data in your file.");
		}
		
		return "comedy".equalsIgnoreCase(genre);
		
	}

}
