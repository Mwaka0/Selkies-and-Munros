/**
 * Obstacle Class
 * <p>Sub Class of Tile, contains a pointer with stores a postion relative to the grid in the board class</P>
 * @author Dylan Mwakasekele
 * @version 20/11/23
 */


public class Obstacle extends Tile {

	//Fields
	private int [] pointer;

	//Constructor
	/**
     * <p>Initialises the values of a Obstacle object</p>
	 * @param newType the type of object e.g tile
	 * @param newDefaultImage the new default image of the object
     * @param newPointer the postion of where the player will end up
     */
	public Obstacle(String newType, String newDefaultImage, int[] newPointer) {
		super(newType, newDefaultImage);
		pointer = newPointer;
	}

	/**
	 * <p>Gets the postion of the top of the obstacle</p>
	 * @return the postion where the player will go to
	 */
	public int[] getPointer() {
		return pointer;
	} 
}