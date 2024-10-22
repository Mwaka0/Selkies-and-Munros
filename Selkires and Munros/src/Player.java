import java.io.Serializable;

/**
 * Player Class
 * <p>Contains the logic for the player of the game</p>
 * @author Dylan Mwakasekele
 * @version 16/11/23
 */

public class Player implements java.io.Serializable {

	//fields
	private String username;
	private String image;
	private int x;
	private int y;
	private int[] pos;
	//If it's the player turn or not
	private Boolean playerTurn;
	//if the player's turn should be skiped
	private Boolean skipTurn;


	//Constructor
	/**
     * <p>Initialises the values of a Player object</p>
     */
	public Player() {
		username = "";
		image = "";
		x = 0;
		y = 9;
		pos = new int[2];
		pos[0] = x;
		pos[1] = y;
		playerTurn = true;
		skipTurn = false;
	}

	//Methods

	/**
	 * <p>Change the username field of the player object</p>
	 * @param newUN the new username which will be set
	 */
	public void setUserName(String newUN) {
		username = newUN;
	}

	/**
	 * <p>Chages the image of the player object</p>
	 * @param newImage the new image for the player
	 */
	public void setImage(String newImage) {
		image = newImage;
	}

	/**
	 * <p>determines where the player will move to</p>
	 * @param diceRoll how many spaces the player will move
	 */
	public void move(int diceRoll) {
		int temp;
		int diff;
		temp = x;
		//checkinng which direction the player will move  
		if (y % 2 == 0) {
			x -= diceRoll;
			if(x < 0) {
				y --;
				diff = temp;
				x = (diceRoll - diff) - 1;
			}	
		}
		else {
			x+= diceRoll;
			if(x > 9) {
				y --;
				diff = calculateDiffernce(9, temp);
				x = 9 - (diceRoll - diff) + 1;
			}
		}
		//checking to see if Player has reached the final postion 
		if (y < 0) {
			y = 0;
			x = 0;
		}
		updateInternalPos();
	}

	/**
	 * <p>Updates the pos[] to a new postion</p>
	 * @param newX the new x coordinate
	 * @param newY the new y coordinate
	 */
	public void updatePostion(int newX, int newY) {
		//Setting new postion
		x = newX;
		y = newY;
		updateInternalPos();
	}

	/**
	 * <p>Updates the pos[] with the x and y values within the class</p>
	 */
	private void updateInternalPos() {
		pos[0] = x;
		pos[1] = y;
	}

	/**
	 * <p>Calculates the differnce of two numbers </p>
	 * @param num1 first number
	 * @param num2 second number
	 * @return the differnece of the two numbers
	 */
	private int calculateDiffernce(int num1, int num2) {
		int differnce;
		if (num1 > num2) {
			differnce = num1 - num2;
			return differnce;
		}
		else {
			differnce = num2 - num1;
			return differnce; 
		}
	}

	/**
	 * <p>Gets the current postion of the Player objectn returns it</p>
	 * @return current postion of Player
	 */
	public int[] getPostion() {
		return pos;
	}

	/**
	 * <p>Gets the image of the player</p>
	 * @return current image of player
	 */
	public String getImage() {
		return image;
	}

	/**
	 * <p>Gets the username of the player object</p>
	 * @return current username of player
	 */
	public String getUserName() {
		return username;
	}

	/**
	 * <p>sets if it's the players turn or not</p>
	 * @param newTurn the state of the playerTurn
	 */
	public void setPlayerTurn(boolean newTurn) {
		playerTurn = newTurn;
	}
	
	/**
	 * <p>Gets the value of playerTurn</p>
	 * @return if value of playerTurn(true or flase)
	 */
	public boolean getPlayerTurn() {
		return playerTurn;
	}

	/**
	 * <p>Skips the turn of the player if true or false</p>
	 * @param turn new boolean value
	 */
	public void skipTurn(boolean turn){
		skipTurn = turn;
	}

	/**
	 * <p>gets the value of skipTurn</p>
	 *@return the value of skipTurn
	 */
	public boolean getSkipTurn() {
		return skipTurn;
	}
}