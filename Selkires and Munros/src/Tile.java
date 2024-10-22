import java.io.Serializable;

/**
 * Tile Class
 * <p>Tiles are the point on the grid containg vaious valuse which help with the logic of the game</p>
 * @author Dylan Mwakasekele
 * @version 16/11/23
 */

public class Tile implements java.io.Serializable {

	//fields
	private String type;
	private String image;
	private String defaultImage;

	//Constructor
	/**
     * <p>Initialises the values of a Tile object</p>
     */
	public Tile() {
		type = "Tile";
		defaultImage = ".";
		image = defaultImage;
	}

	/**
	 * <p>Second constructor to intialise the values of a Tile object</p>
	 * @param newType the type of object e.g tile
	 * @param newDefaultImage the new default image of the object
	 */
	public Tile(String newType, String newDefaultImage) {
		type = newType;
		defaultImage = newDefaultImage;
		image = defaultImage;
	}

	//Methods

	/**
	 * <p>Sets the image of the object</p>
	 * @param newImage the new image of the object
	 */
	public void setImage(String newImage) {
		image = newImage;
	}

	/**
	 * <p>gets the image and returns it</p>
	 * @return the image used by the object
	 */
	public String getImage() {
		return image;
	}

	/**
	 * <p>gets the defaultImage and returns it</p>
	 * @return the defaultImage used by the object
	 */
	public String getDefaultImage() {
		return defaultImage;
	}

	/**
	 * <p>gets the type value of the object</p>
	 * @return the type of the object
	 */
	public String getType() {
		return type;
	}

	/**
	 * <p>Resets the image of the object to its default value</p>
	 */
	public void reset() {
		image = defaultImage;
	}
}