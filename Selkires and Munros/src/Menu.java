import java.util.Scanner;

/**
 * Menu Class
 * <p>Displays an interactive menu to the user, and excutes the user's choices</P>
 * @author Dylan Mwakasekele
 * @version 16/11/23
 */

public class Menu {
	
	//fileds

	//If the Menu should close or not
	private Boolean menuExit;
	//Game object 
	private Game game;
	//2d array containg the leader board
	private String[][] leaderBoard;
	//path to save slot 1
	private final String save1;
	//path to save slot 2
	private final String save2;
	//path to save slot 3
	private final String save3;
	//path to save data dir
	private final String dir;
	//path to leader board
	private final String lbPath;

	//Constructor
	/**
     * <p>Initialises the values of a Menu object</p>
     */
	public Menu() {
		menuExit = false;
		save1 = "../data/save1.ser";
		save2 = "../data/save2.ser";
		save3 = "../data/save3.ser";
		dir = "../data";
		lbPath = "../data/LeaderBoard.txt";
		Game game = null;
		leaderBoard = null;
	}

	//Main
	/**
	 *<p>This is the main method of the program</p>. 
	 *<p>It displays the menu to the user, and allows them to interact with it</p>
	 * @param args input arguments
	 */
	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.runMenu();
	}

    /**
     * <p>Updates the screen with menu</p>
     */
	public void runMenu() {
		while(menuExit == false) {
			int choice = 0;
			drawTitel();
			drawMenu();
		 	while(choice < 1 || choice > 5) {
		 		try {
		 			System.out.print("\nEnter your choice(1-5): ");
		 			choice = Integer.parseInt(getInput());
		 		}
		 		catch(NumberFormatException e) {
		 			System.out.println("Invalid option. Please try again.");
		 		}
		 	}
			action(choice);
		}
	}

	 /**
     * <p>Displays the titel of the program</p>
     */
	private void drawTitel() {
		System.out.println("+-----------------------------------------+");
		System.out.println("|           Selkies and Munrows           |");
		System.out.println("|                                         |");
		System.out.println("+-----------------------------------------+");

	}

	/**
     * <p>Displays the choices to the user</p>
     */
	private void drawMenu() {
		System.out.println("\n(1) Start Game");
		System.out.println("(2) Load Game");
		System.out.println("(3) Help/Insructions");
		System.out.println("(4) Display Leader Board");
		System.out.println("(5) Exit Menu");
	}


	/**
     * <p>gets a String input from the user. No validation</p>
     * @return strInput the String input from user 
     */
	private String getInput() {
		Scanner s = new Scanner(System.in);
		String userInput;
		userInput = s.nextLine();
		return userInput;
	}

	/**
     * <p>Triggers an action based on user choice</p>
     * @param choice user menu action choice
     */
	private void action(int choice) {
		switch(choice) {
			case 1:
				//Creating new game
				game = new Game();
				game.intialiseGame();
				runGame();
				break;
			case 2:
				//loading game
				loadGame();
				//checking if save file is empty
				if (game != null) {
					System.out.println("\nGame loaded");
					game.setExitStatus(false);
					game.intialiseLoad();
					runGame();
				}
				else {
					System.out.println("Save slot is empty please try another option");
				}
				break;
			case 3:
				help();
				break;
			case 4:
				//displaying leader board
				updateLeaderBoard();
				displayLeaderBoard();
				break;
			case 5:
				//exiting the program
				System.out.println("Thanks for using the program");
				menuExit = true;
				break;
			default:
				System.out.println("UnknownError has occured");
		} 
	}

	/**
	 * <p>Runs the game</p>
	 */
	private void runGame() {
		int arrlength = game.getUserslenght();
		int choice;
		int turn = game.getTurn();
		while (!game.getExitStatus()) {
			System.out.println("Turn: " + turn + "\n");
			for (int i = 0; i < arrlength ; i++ ) {
				System.out.println(game.getUser(i) + " Select from the following options:");
				while (game.getUsersTurn(i)) {
					choice = gameChoices();
					gameAction(choice, i);
				}
				game.setUsersTurn(true, i);
				if (game.getExitStatus() == true) {
					break;
				}
			}
			turn++;
			game.setTurn(turn);
		}
	} 

	/**
	 *<p>Gets the choice from user on whithc action they want to perform</p> 
	 *@return user's choice
	 */
	private int gameChoices() {
		gameOptions();
		int choice = 0;
		//getting user input
		while(choice < 1 || choice > 3) {
		 	try {
		 		System.out.print("\nEnter your choice(1-3): ");
		 		choice = Integer.parseInt(getInput());
		 	}
		 	catch(NumberFormatException e) {
		 		System.out.println("Invalid option. Please try again.");
		 	}
		}
		return choice;
	}

	/**
	 * <p>Diplays the Game options to the user</p>
	 */
	private void gameOptions() {
		System.out.println("\n(1) Roll Dice");
		System.out.println("(2) Save Game");
		System.out.println("(3) Exit");
	}

	/**
	 * <p>Excutes an action the game</p>
	 * @param choice which action the player wants to perform
	 * @param user the index of the player
	 */
	public void gameAction(int choice, int user) {
		switch(choice) {
			case 1:
				//Rolling the dice and updating the board
				game.update(user);
				game.setUsersTurn(false, user);
				break;
			case 2:
				//saving the game
				saveGame();
				game.setUsersTurn(true, user);
				break;
			case 3:
				//Exiting to main menu
				System.out.println("Returning to main menu, all unsaved progress will be lost");
				game.setUsersTurn(false, user);
				game.setExitStatus(true);
				break;
			default:
				System.out.println("UnknownError has occured");
		}
	}


	/**
	 * <p>Saves a Game object to a specified file</p>
	 */
	private void saveGame() {
		System.out.println("\nSave game selected");
		//Making sure save files exist
		checkSaveFiles();
		//save options
		int choice = saveChoices();
		//Selectingt the correct save
		switch(choice) {
			case 1:
				FileAction.save(save1, game);
				break;
			case 2:
				FileAction.save(save2, game);
				break;
			case 3:
				FileAction.save(save3, game);
				break;
		}
		System.out.println("\nYour turn again :)");
	}

	/**
	 * <p>Loads a Game object from a file</p>
	 */
	private void loadGame() {
		System.out.println("\nLoad game selected");
		checkSaveFiles();
		//save options
		int choice = saveChoices();
		//Selecting correct save
		switch(choice) {
			case 1:
				game = FileAction.load(save1);
				break;
			case 2:
				game = FileAction.load(save2);
				break;
			case 3:
				game = FileAction.load(save3);
				break;
		}
	}
	/**
	 * <p>Checks the save files to make sure they exist</p>
	 */
	private void checkSaveFiles() {
		//checking directory
		FileAction.checkDirExists(dir);
		//chceking files
		FileAction.checkFile(save1);
		FileAction.checkFile(save2);
		FileAction.checkFile(save3);
	}

	/**
	 * <p>Gets the users input to select a save slot</p>
	 * @return the user choice for which save to user
	 */
	private int saveChoices() {
		int choice = -1;
		//Displaying choices
		System.out.println("\n(1) Save slot 1");
		System.out.println("(2) Save slot 2");
		System.out.println("(3) Save slot 3");
		//getting user input
		while(choice < 1 || choice > 3) {
		 	try {
		 		System.out.print("\nSelect a save slot(1-3): ");
		 		choice = Integer.parseInt(getInput());
		 	}
		 	catch(NumberFormatException e) {
		 		System.out.println("Invalid option. Please try again.");
		 	}
		}
		return choice;
	}

	/**
	 * <p>Updates the leader board on the screen</p>
	 */
	private void updateLeaderBoard() {
		//Checking if dir exist
		FileAction.checkDirExists(dir);
		//checking if leader board file exists
		FileAction.checkFile(lbPath);
		leaderBoard = FileAction.readFile(2, lbPath);
		sortleaderBoard();
	}

	/**
	 * <p>Sorts the leader board by lowestr turn, using bubble sort</p>
	 */
	private void sortleaderBoard() {
		//Bubble sort
		String[] temp;
		//Looping through the array
		for (int i = 0; i < (leaderBoard.length - 1); i++) {
			for (int j = 0; j < (leaderBoard.length - i - 1); j++) {
				//comparing the two index values
				if (Integer.parseInt(leaderBoard[j][1]) > Integer.parseInt(leaderBoard[j + 1][1])) {
					//swaping indexes
					temp = leaderBoard[j];
					leaderBoard[j] = leaderBoard[j +  1];
					leaderBoard[j + 1] = temp;
				}
			}
		}
	}

	/**
	 * <p>Displays the top 15 players to the users</p>
	 */
	private void displayLeaderBoard() {
		//usersename of the player
		String userName;
		//how many tunrns it took them to finsh
		String turnNum;
		//Creating leader board
		System.out.println("-------------------------------------------");
		for (int i = 0; i < leaderBoard.length; i++) {
			//Checking if the max has been reacehed
			if (i > 14){
				break;
			}
			userName = leaderBoard[i][0];
			turnNum = leaderBoard[i][1];
			System.out.println("Number(" + (i + 1) + ") User: " + userName + " Tunrs: " + turnNum);			
		}
		System.out.println("-------------------------------------------");
	}
	

	/**
	 * <p>Draws the help Menu/Instuctiions to the screen</p>
	 */
	private void help() {
		System.out.println("---------------------------------------------");
		System.out.println("              Help/ Instuctions              ");
		System.out.println("Game instructions:                           ");
		System.out.println("The aim is to reach the top tile '100        ");
		System.out.println("Each turn you will can roll a 6 sided dice   ");
		System.out.println("This will determine how far you move         ");
		System.out.println("Landing on non-tile will alter how you move  ");
		System.out.println("                                             ");
		System.out.println("To roll the dice press '1' once in the game  ");
		System.out.println("Then your player will move e.g P1 moves + 5  ");
		System.out.println("                                             ");
		System.out.println("You can save by pressing '2'                 ");
		System.out.println("However any saved data will be overwitten    ");
		System.out.println("                                             ");
		System.out.println("You can exit the game by pressing '3'        ");
		System.out.println("All unsaved progress will be lost            ");
		System.out.println("                                             ");
		System.out.println("Game Symbols:                                ");
		System.out.println("'#P1' symbol is the player                   ");
		System.out.println("'^' is a Munro, you will move upwards        ");
		System.out.println("'!' is a Selkie, you will move downwards     ");
		System.out.println("                                             ");
		System.out.println("Activty spaces:                              ");
		System.out.println("Activity spaces are hidden from the player   ");
		System.out.println("Haggis Bite - miss next turn                 ");
		System.out.println("Nessies's Revenge - All players move randomly");
		System.out.println("Bagpiper's Delight - roll dice again         ");
		System.out.println("Whisky Boost - Move an additional 5 spaces   ");
		System.out.println("---------------------------------------------");
	}
}
