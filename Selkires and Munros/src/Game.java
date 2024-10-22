import java.util.Scanner;
import java.io.Serializable;

/**
 * Game Class
 * <p>Contains the main componets of the Selkie and Munros, and handles most of the logic for it</p>
 * @author Dylan Mwakasekele
 * @version 16/11/23
 */

public class Game implements java.io.Serializable {

	//fields
	// if the game should close or not
	private Boolean exit;
	//stores the last turn given to it 
	private int tempTurn;
	//number of players
	private int playerNum;
	//Array of player objects
	private Player[] users;
	//board object
	private Board board;
	//Game turn
	private int turn;
	//Path to the leaderBoard file
	private final String lbPath;

	//Constructor
	/**
     * <p>Initialises the values of a Menu object</p>
     */
	public Game() {
		exit = false;
		playerNum = 1;
		users = null;
		board = new Board();
		turn = 1;
		lbPath = "../data/LeaderBoard.txt";
	}

	//Methods
	
	/**
	 * <p>updates the screen</p>
	 * @param user the current player
	 */
	public void update(int user) {
		//Rolling dice
		rollDice(user);
		//Drawing baord
		board.draw();
		//Checking for win
		checkWin(user);
	}

	/**
	 *<p>intialises the game, asks the user player num and usernames. Then updates the board</P> 
	 */
	public void intialiseGame() {
		//Setting players
		updatePlayerCount();
		users = new Player[playerNum];
		for (int i = 0; i < playerNum ; i++) {
			users[i] = new Player();
			System.out.println("\nPlayer" + (i + 1));
			users[i].setUserName(enterUserName());
			users[i].setImage("\u001B[3" + (i + 1) + "m#P" + (i + 1) + "\u001B[0m");
		}
		board.updateBoard(0, 9, users[0].getImage());	
		board.draw();
	}

	/**
	 * <p>Intialises the board from a load state</p>
	 */
	public void intialiseLoad() {
		int[] userPos;
		for (int i = 0; i < users.length ; i++) {
			userPos = users[i].getPostion();
			board.updateBoard(userPos[0], userPos[1], users[i].getImage());
		}
		board.draw();
	}

	/**
	 * <p>Gets the username of the user</p>
	 * @param i the number in the array of the specific user
	 * @return the userName 
	 */
	public String getUser(int i) {
		return users[i].getUserName();

	}

	/**
	 * <p>Gets the lenght of the user array</p>
	 * @return the lenght of the array
	 */
	public int getUserslenght() {
		return users.length;
	}

	/**
	 * <p>Gets the current turn in the game</p>
	 * @return the current turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * <p>Set the game turn</p>
	 * @param newTurn the updated turn 
	 */
	public void setTurn(int newTurn) {
		turn = newTurn;
	}
	
	/**
	 * <p>Sets if its the user's turn or not</p>
	 * @param newTurn the state of the playerTurn
	 * @param user which player in the array
	 */
	public void setUsersTurn(boolean newTurn, int user) {
		users[user].setPlayerTurn(newTurn);
	}
	
	/**
	 * <p>Gets if its the currents user's turn</p>
	 * @param user the index of the player in the array
	 * @return turn value (true or flase)
	 */
	public boolean getUsersTurn(int user) {
		if (users[user].getSkipTurn()) {
			if ((tempTurn + 1) == turn) {
				System.out.println("MISS TURN");
				System.out.println();
				users[user].setPlayerTurn(false);
				users[user].skipTurn(false);	
			}
		}
		return users[user].getPlayerTurn();
	}

	/**
	 * <p>Gets the value of the exit varaibel</p>
	 * @return value of exit
	 */
	public boolean getExitStatus() {
		return exit;
	}

	/**
	 * <p>Sets the value of the exit varaibel</p>
	 * @param newExit new value of exit variabel (tru or false)
	 */
	public void setExitStatus(boolean newExit) {
		exit = newExit;
	}

	/**
	 * <p>Gets the username for the player from the user</p>
	 * @return the username for the player object
	 */
	private String enterUserName() {
		Scanner s = new Scanner(System.in);
		String uName;
		//Asking the player for their username
		System.out.print("\nEnter your username(Between 3-8 charters): ");
		uName = s.nextLine();
		while (uName.length() < 3 || uName.length() > 10) {
			System.out.println("\nInvalid choice please try again");
			System.out.print("Enter your username(Between 3-8 charters): ");
			uName = s.nextLine();
		}
		return uName;
	}

	/**
	 * <p>Asks the user how many player they want and then updates the playerNum</p>
	 */
	private void updatePlayerCount() {
		Scanner s = new Scanner(System.in);
		do {
			//Asking the user how many players they want 
			System.out.print("\nEnter how many players you want(Between 1-4):  ");
			while (!s.hasNextInt()) {
				System.out.println("\nInvalid choice please try again");
				System.out.print("\nEnter how many players you want(Between 1-4):  ");
				s.next(); 
			}
			playerNum = s.nextInt();
		} 
		while (playerNum > 4 || playerNum < 1 );
	}

	/**
	 * <p>Rolls the dice and moves the player</p>
	 * @param user the specific user doing the action e.g 0 for player1
	 */
	private void rollDice(int user) {
		int[] userPos;
		//Rolling the dice(1,6)
		int dice = GenerateRandom.randomInt(6, 1);
		//Updating the other player images on the board
		for (int i = 0; i < users.length; i++ ) {
			if (i != user) {
				userPos = users[i].getPostion();
				board.updateBoard(userPos[0], userPos[1], users[i].getImage());
			}
			
		}
		//Displaying to the player how many spaces they moved
		System.out.println("You moved " + dice + " spaces");
		//Updating the postion in the Player object
		users[user].move(dice);
		userPos = users[user].getPostion();
		//Checking what type of tile the player landed on
		checkTile(user, userPos);
	}
	/**
	 * <p>Checks the tile the player is on, and depending on what type changes the postion of the player</p>
	 * @param user the index of the player in the array
	 * @param the postion of the player
	 */
	private void checkTile(int user, int[] userPos) {
		String type = board.checkType(userPos);
		if (!type.equals("Tile")) {
			//Munro and Selkie tiles
			if (type.equals("Munro") || type.equals("Selkie") ) {
				//getting the postion the player will move to
				int [] pointer = board.checkPointer(userPos);
				String tileNum = board.checkDefaultImage(pointer);
				System.out.println("You landed on a " + type + " and will move to Tile: " + tileNum);
				users[user].updatePostion(pointer[0], pointer[1]);
				//Updating the player postion/image on the board
				board.updateBoard(pointer[0], pointer[1], users[user].getImage());			
			}
			//Haggis Bite tiles
			else if (type.equals("HaggisBite")) {
				System.out.println("You have been bitten by a Haggis(A mythical Scottish creature)!");
				System.out.println("You will miss your next turn");
				users[user].skipTurn(true);
				tempTurn = turn;
				board.updateBoard(userPos[0], userPos[1], users[user].getImage());
			}
			//Nessie's Revenege tiles
			else if (type.equals("NessieRevenge")) {
				System.out.println("You have landed on Nessie's Revenge");
				System.out.println("Nessie has gone on a rampage and all player will move randomly");
				System.out.println("However if you land on an obstacle it will not have any effect");
				//Randomly assigning postion to all players
				int x = 0;
				int y = 0;
				for (int i = 0; i < users.length; i++ ) {
					do {
						x = GenerateRandom.randomInt(9,0);
						y = GenerateRandom.randomInt(9,0);
					}
					while (y != 0 && x != 0);
					users[i].updatePostion(x, y);
					userPos = users[i].getPostion();
					board.updateBoard(userPos[0], userPos[1], users[i].getImage());
				}
			}
			//Bagpiper's delight tiles
			else if (type.equals("BagpiperDelight")) {
				System.out.println("You have landed on Bagpiper's Delight and will roll the dice again");
				rollDice(user);
				board.updateBoard(userPos[0], userPos[1], users[user].getImage());				
			}
			//Whisky boost tiles
			else if (type.equals("WhiskyBoost")) {
				users[user].move(5);
				userPos = users[user].getPostion();
				checkTile(user, userPos);
				board.updateBoard(userPos[0], userPos[1], users[user].getImage());
			}
		}
		else {
			//Updating the player postion/image on the board
			board.updateBoard(userPos[0], userPos[1], users[user].getImage());
		}
	}


	/**
	 * <p>Checks if the player has reached the final postion on the board or not</p>
	 * @param user the specific user doing the action e.g 0 for player1
	 */
	private void checkWin(int user) {
		int[] userPos;
		userPos = users[user].getPostion();
		if (userPos[0] == 0 && userPos[1] == 0) {
			appendLeaderBoard(user);
			System.out.println("\nCongradulations you have won!!!");
			System.out.println("Returning to main menu");
			exit = true;
		}
	}

	/**
	 * <p>Writes to the leader board and update it with a new score</p>
	 * @param user the index of the specific player in the array
	 */
	private void appendLeaderBoard(int user) {
		String newLine = users[user].getUserName() + " " + turn;
		FileAction.appendFile(lbPath, newLine);
	}
}

