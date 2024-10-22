import java.io.Serializable;

/**
 *Board Class
 * <p>This class contians the grid with makes up the boar for the game</P>
 * <p>The gird is a 2D Array of objectes made up of Tile objects and it's sub classes, in random postions</p>
 * @author Dylan Mwakasekele
 * @version 17/11/23
 */


public class Board implements java.io.Serializable{

	//fields
	private int rows;
	private int columns;
	private Tile grid[][];
	
	//Constructor
	/**
     * <P>Initialises the values of a Player object</P>
     */
	public Board() {
		rows =  10;
		columns = 10;
		grid = new Tile[rows][columns];
		int counter = 100;
		//Adding the tiles to the grid
		for (int i = 0; i < rows; i++ ) {
			//Checking which direction to go through the array
			if (i % 2 == 0) {
				for (int j = 0; j < columns ; j++ ) {
					grid[i][j] = new Tile("Tile", String.valueOf(counter));
					counter--;
				}
			}
			else {
				for (int j = 9; j > -1 ; j-- ) {
					grid[i][j] = new Tile("Tile", String.valueOf(counter));
					counter--;
				}
			}	
		}
		//Adding obstacles
		String image; 
		int x1;
		int y1;
		int x2;
		int y2;
		int[] pointer;
		int radnInt = GenerateRandom.randomInt(12, 8);
		int chance;
		//Genetating Munros and Selkies
		for (int l = 0; l < radnInt; l++) {
			//50% chance to create a munor or selkie on the board
			chance = GenerateRandom.randomInt(1,0);
			//Choosing Munro or Selki
			if (chance == 0) {
				//generating random coordinates
				do {
					x1 = GenerateRandom.randomInt(9, 0);
					y1 = GenerateRandom.randomInt(9, 0);
				}
				while (grid[y1][x1].getType() != "Tile" || y1 == 0 || y1 == 9 && x1 == 0);
				do {
					x2 = GenerateRandom.randomInt(9, 0);
					y2 = GenerateRandom.randomInt(9, 0);
				}
				while (grid[y2][x2].getType() != "Tile" || y2 >= y1);
				//Updating the grid with a Munro tile
				image = grid[y2][x2].getImage();
				image = "^(" + image + ")";
				pointer = new int[]{x2, y2};
				grid[y1][x1] = new Obstacle("Munro", image, pointer);
			}
			else {
				//generating random coordinates
				do {
					x1 = GenerateRandom.randomInt(9, 0);
					y1 = GenerateRandom.randomInt(9, 0);
				}
				while (grid[y1][x1].getType() != "Tile" || y1 == 0 && x1 == 0 || y1 == 9);
				do {
					x2 = GenerateRandom.randomInt(9, 0);
					y2 = GenerateRandom.randomInt(9, 0);
				}
				while (grid[y2][x2].getType() != "Tile" || y2 <= y1);
				//Updating the grid with a Selkie tile
				image = grid[y2][x2].getImage();
				image = "!(" + image + ")";
				pointer = new int[]{x2, y2};
				grid[y1][x1] = new Obstacle("Selkie", image, pointer);				
			}
		}
		//Generating Activity spaces
		radnInt = GenerateRandom.randomInt(15, 8);
		for (int i = 0; i < radnInt; i ++) {
			do {
				x1 = GenerateRandom.randomInt(9, 0);
				y1 = GenerateRandom.randomInt(9, 0);
			}
			while (grid[y1][x1].getType() != "Tile" || y1 == 0 && x1 == 0 || y1 == 9 && x1 == 0);
			image = grid[y1][x1].getImage();
			chance = GenerateRandom.randomInt(3,1);
			//33% chance for Haggis, Bagpipe and Whisky
			switch(chance) {
			 	case 1:
			 		grid[y1][x1] = new Tile("HaggisBite", image);
			 		break;
			 	case 2:
			 		grid[y1][x1] = new Tile("BagpiperDelight", image);
			 		break;
			 	case 3:
			 		grid[y1][x1] = new Tile("WhiskyBoost", image);
			 		break;
			 	default:
			 		System.out.println("UnknownError");
			 } 
		}
		//Only one Nessie activty space
		do {
			x1 = GenerateRandom.randomInt(9, 0);
			y1 = GenerateRandom.randomInt(9, 0);
		}
		while (grid[y1][x1].getType() != "Tile" || y1 == 0 && x1 == 0 || y1 == 9 && x1 == 0);
		image = grid[y1][x1].getImage();
		grid[y1][x1] = new Tile("NessieRevenge", image);

	}

	//Methods

	/**
     * <P>Draws the board to the screen</P>
     */
	public void draw() {
		printBoarder();
		//printing out the 10x10 grid
		System.out.println();
		for (int i = 0; i < grid.length; i++ ) {
			for (int j = 0; j < grid[i].length ; j++ ) {
				System.out.print(grid[i][j].getImage()+ "\t");
			}
			System.out.println();
			System.out.println();
		}
		printBoarder();
		//resettign the grid to it's default state
		clearBoard();
	}

	/**
	 * <p>Sets the postion of the object provided on the board</p>
	 * @param newX postion of the board to update
	 * @param newY postion of the board to update
	 * @param image the new updated image on the board
	 */
	public void updateBoard(int newX, int newY, String image) {
		int x = newX;
		int y = newY;
		grid[y][x].setImage(image);
	}

	/**
	 * <p>Checks the tile type on the grid</p>
	 * @param uPos the postion of the player releative to the board
	 * @return the type of tile the user is on
	 */
	public String checkType(int[] uPos) {
		return grid[uPos[1]][uPos[0]].getType();
	}

	/**
	 * <p>Gets the pointer of the obstacle Object of where the player will move to</p>
	 * @param uPos the postion of the player releative to the board
	 * @return the new postion of the user
	 */
	public int[] checkPointer(int[] uPos) {
		Obstacle x = (Obstacle)grid[uPos[1]][uPos[0]];
		return x.getPointer();
	}

	/**
	 * <p>Returns the image of a given postion in the grid</p>
	 * @param pos the postion on the grid to check
	 * @return the image of the object
	 */
	public String checkImage(int[] pos) {
		return grid[pos[1]][pos[0]].getImage();
	}

	/**
	 * <p>Returns the default image of a given postion in the grid</p>
	 * @param pos the postion on the grid to check
	 * @return the default image of the object
	 */
	public String checkDefaultImage(int[] pos) {
		return grid[pos[1]][pos[0]].getDefaultImage();
	}


	/**
	 * <p>Clears the board, resets the images to its oringal values</p> 
	 */
	private void clearBoard() {
		for (int i = 0; i < rows; i++ ) {
			for (int j = 0; j < columns ; j++ ) {
				grid[i][j].reset();
			}		
		}
	}

	/**
	 * <P>Prints a line boarder</P>
	 */
	private void printBoarder() {
		String line = "---------------------------------------------------------------------------";
		System.out.println(line);
	}




}