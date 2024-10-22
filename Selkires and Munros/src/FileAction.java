import java.io.*;

/**
 *FileAction Class
 *<p>Handles the reading and loading of fiels, aswell as creating them for the game</p>
 * @author Dylan Mwakasekele
 * @version 01/12/23
 */
public class FileAction {

	/**
	 * <p>Saves a Game object to a specified folder</P>
	 * @param fName the name of the file to save the object to 
	 * @param game the Game object to save
	 */
	public static void save(String fName, Game game) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(fName);
			oos = new ObjectOutputStream(fos);
			//writing object data to file
			oos.writeObject(game);
		}
		catch (IOException e) {
			System.out.println("\nError saving the game");
		}
		finally {
            if (oos != null) {
                try { 
                	//closing file
                    oos.close();
                    fos.close();
                }
                catch (IOException e) {
                    System.out.println("\nError closing the file");
                }
            }
        }
	}

	/**
	 * <p>Loads a Game object to a specified folder</P>
	 * @param fName the name of the file to load the object from 
	 * @return the value of read object
	 */
	public static Game load(String fName) {
		Game game = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(fName);
			ois = new ObjectInputStream(fis);
			//reading object data from file
			game = (Game)ois.readObject();
		}
		catch (IOException e) {
			System.out.println("\nError loading the game.");
		}
		catch (ClassNotFoundException e) {
			System.out.println("\nClass not found. Error loading game.");
		}
		finally {
            if (ois != null) {
             	try {
             		//closing file
                    ois.close();
                    fis.close();
                }
                catch (IOException e) {
                    System.out.println("\nError closing the file");
                }
        	}
		}
		return game;
	}

	/**
	 * <p>Checks if a directory exist or not and creates one if not</p>
	 * @param dPath the path to the directory
	 */
	public static void checkDirExists(String dPath) {
		File dir = new File(dPath);
		//Checking if a new dir needs to be created
		if (dir.mkdir()) {
			System.out.println("\nSave folder created");
		}
	}

	/**
	 * <p>Creates a files from a specified file path</p>
	 * @param fPath the path of the file 
	 */
	public static void checkFile(String fPath) {
		try {
			File file = new File(fPath);
			//checking if a new file needs to be created
			if (file.createNewFile()) {
				System.out.println("\nFile created");
			}
		}
		catch (IOException e) {
			System.out.println("\nAn error occured");
		}
	}

	/**
	 * <p>Appends a string of text to a file</p>
	 * @param fPath path fo the file
	 * @param newLine the new line to be added to the file
	 * 
	 */
	public static void appendFile(String fPath, String newLine) {
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			fos = new FileOutputStream(fPath, true);
			pw = new PrintWriter(fos);
			//writing to file
			pw.println(newLine);
		}
		catch (IOException e) {
			System.out.println("Error writing to file");
		}
		finally {
			if (pw != null){
				try {
					//closing file
					pw.close();
					fos.close();
				}
				catch (IOException e) {
					System.out.println("Error closing file");
				}
			}
		}
	}


	/**
	 * <p>Reads a file designed to be turned into a 2d array</p>
	 * @param attributes the number of elements one index in the array has
	 * @param fPath the path to the file
	 * @return a 2d array containg attribues from each line
	 */
	public static String[][] readFile(int attributes, String fPath) {
		FileReader fr = null;
		BufferedReader br = null;
		//Setting vasriables
		String[][] arrOfStr = null;
		String[] tempArr;
		String nextLine;
		int line = 0;
		try {
			fr = new FileReader(fPath);
			br = new BufferedReader(fr);
			br.mark(1000);
			nextLine = br.readLine();
			//Getting length of array
			while (nextLine != null) {
				line ++;
				nextLine = br.readLine();
			}
			br.reset();
			//Reading the values of the fields for 2dArray
			arrOfStr = new String[line][attributes];
			for (int i = 0; i < arrOfStr.length; i++ ) {
				nextLine = br.readLine();
				//Splittign the line ' ' to access each attribute
				tempArr = nextLine.split(" ");
				for (int j = 0; j < arrOfStr[i].length; j++) {
					arrOfStr[i][j] = tempArr[j];
				}
			}
		}
		catch (IOException e) {
			System.out.println("Error reading from file");
		}
		finally {
			if (br != null) {
				try {
					//closting file
					br.close();
					fr.close();
				}
				catch (IOException e) {
					System.out.println("Error closing file");
				}
			}
		}
		return arrOfStr;
	}
}	

