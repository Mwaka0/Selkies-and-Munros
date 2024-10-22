import java.util.Random;

/**
 * GenerateRandom Class
 * <p>Generates varies random numbers</p>
 * @author Dylan Mwakasekele
 * @version 29/11/23
 */

public class GenerateRandom {

	/**
	 * <p>Creates a random postion on the board</p>
	 * @return an array containg a postion(x,y)
	 */
	public static int[] genRandomPos() {
		int max = 9;
		int min = 0;
		int randX = randomInt(max, min);
		int randY = randomInt(max, min);
		int[] pos = {randX, randY};
		return pos;
	}

	/**
	 * <p>Generates a random interger value</p>
	 * @param max the maximum value of the int 
	 * @param min the minimum value of the int
	 * @return a random number withing a specif range
	 */
	public static int randomInt(int max, int min) {
		Random r = new Random();
		int num = r.nextInt(max - min + 1) + min;
		return num;
	}
}