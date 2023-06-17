package main;

/**
 * Throws an exception if we attempt to start the game with less
 * {@link main.Athlete Athletes} than required for a full {@link main.Team
 * Team}.
 *
 * @author mdi48
 * @version 1.01
 **/
public class InvalidTeamException extends IllegalStateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTeamException(String message) {
		super(message);
	}

}
