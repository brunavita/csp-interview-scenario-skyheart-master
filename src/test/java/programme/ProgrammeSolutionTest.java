package programme;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import skyheart.domain.Programme;
import skyheart.exceptions.SkyHeartException;
import skyheart.persistence.JsonDataLoader;
import skyheart.programme.ProgrammeSolution;

class ProgrammeSolutionTest {

	List<Programme> programmeList;

	ProgrammeSolution programmeSolution;

	@BeforeEach
	void setUp() throws Exception {
		programmeList = JsonDataLoader.getProgrammeData();
		programmeSolution = new ProgrammeSolution();
	}

	@Test
	void checkProgramContentRulesFromSkyHeartTest() throws SkyHeartException {
		Assertions.assertEquals(15, programmeList.size());
		List<Programme> filteredListProgramme = programmeSolution.checkProgramRulesFromSkyHeart(programmeList);

		Assertions.assertEquals(12, filteredListProgramme.size());
		Assertions.assertEquals(10, filteredListProgramme.get(0).getViewershipRating());
	}


	@Test
	void checkRomanceProgrammeTest() throws SkyHeartException {
		String genre = "romance";
		Assertions.assertTrue(programmeSolution.checkRomanceProgramme(genre));
	}

	@Test
	void checkProviderProgrammeTest() throws SkyHeartException {
		String provider = "Sky";
		Assertions.assertTrue(programmeSolution.checkProviderProgramme(provider));
	}

	@Test
	void checkComedyProgrammeTest() throws SkyHeartException {
		String genre = "comedy";
		Assertions.assertTrue(programmeSolution.checkComedyProgramme(genre));
	}

	@Test
	void checkRomanceProgrammeFailTest() throws SkyHeartException {
		String genre = "drama";
		Assertions.assertFalse(programmeSolution.checkRomanceProgramme(genre));
	}

	@Test
	void checkProviderProgrammeFailTest() throws SkyHeartException {
		String provider = "Warner Bros";
		Assertions.assertFalse(programmeSolution.checkProviderProgramme(provider));
	}

	@Test
	void checkComedyProgrammeFailTest() throws SkyHeartException {
		String genre = "action";
		Assertions.assertFalse(programmeSolution.checkComedyProgramme(genre));
	}

	@Test
	void checkEmptyListExceptionTest() throws SkyHeartException {

		List<Programme> programmeListAux= new ArrayList<>();
		Assert.assertEquals(programmeListAux, new ArrayList<>());

		Assertions.assertThrows(SkyHeartException.class, () -> programmeSolution.checkProgramRulesFromSkyHeart(programmeListAux));
	}
	
	@Test
	void checkNullListExceptionTest() throws SkyHeartException {

		List<Programme> programmeListAux= null;
		Assertions.assertThrows(SkyHeartException.class, () -> programmeSolution.checkProgramRulesFromSkyHeart(programmeListAux));
	}

	@Test
	void checkMissingProviderProgrammeTest() throws SkyHeartException {
		String provider = null;
		Assertions.assertThrows(SkyHeartException.class, () -> programmeSolution.checkProviderProgramme(provider));
	}
	
	@Test
	void checkMissingComedyGenreProgrammeTest() throws SkyHeartException {
		String genre = null;
		Assertions.assertThrows(SkyHeartException.class, () -> programmeSolution.checkComedyProgramme(genre));
	}
	
	@Test
	void checkMissingRomanceGenreProgrammeTest() throws SkyHeartException {
		String genre = null;
		Assertions.assertThrows(SkyHeartException.class, () -> programmeSolution.checkRomanceProgramme(genre));
	}


}

