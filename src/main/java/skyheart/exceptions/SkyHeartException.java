package skyheart.exceptions;

/**
* This class is a custom exception in application that displays 
* a message when a programme failure happens.
*  
* @author  Bruna Vita
* @version 1.0
* @since   2018-09-06 
*/
public class SkyHeartException extends Exception {
	private static final long serialVersionUID = 1L;

	public SkyHeartException(String message) {
        super (message);
    }
}
