
/**
 * Betty Vuong & Elianna Nghiem
 * ICS4U
 * 23/03/22
 * Program is a maze solver that takes a 2D char array that represents a maze, the program will solve the maze 
 * recursively and trace a path to exit the maze if there is an exit solution and display the traced maze 
 * recursively along with prompting the user to guess how many steps it takes to get to exit. If the maze is 
 * not solvable, the maze won't be displayed.
 */

import java.util.*;

public class ICS4U_RecursionCode_Betty_Vuong {
	public static void main(String[] args) {
		int row = 0, col = 0; // Declares + initializes row and column variables for recursive methods
		char[][] mazeArr = { //Declares + initializes 2D char maze array
				{ '*', '*', '*', '*', '*', '*', '*', '*', '*', '*' }, // 0
				{ '*', '#', '*', '*', '*', '*', '*', '*', '*', '*' }, // 1
				{ '*', ' ', '*', ' ', ' ', ' ', ' ', ' ', '*', '*' }, // 2
				{ '*', ' ', '*', ' ', ' ', ' ', '*', ' ', '*', '*' }, // 3
				{ '*', ' ', ' ', ' ', '*', ' ', '*', ' ', '*', '*' }, // 4
				{ '*', '*', '*', ' ', ' ', ' ', '*', ' ', '*', '*' }, // 5
				{ '*', '*', ' ', ' ', '*', ' ', '*', ' ', '*', '*' }, // 6
				{ '*', '*', ' ', ' ', '*', ' ', '*', ' ', ' ', '*' }, // 7
				{ '*', '*', '*', '*', '*', '*', '*', ' ', ' ', '*' }, // 8
				{ '*', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '$', '*' }, // 9
				{ '*', '*', '*', '*', '*', '*', '*', '*', '*', '*' } }; // 10
		
		System.out.println("Symbols for the maze:\n"
				+ "\n"
				+ "'*' = walls\n"
				+ "'#' = start\n"
				+ "'$' = end\n"
				+ "' ' = possible path/not a wall\n"
				+ "'@' = path\n------------------------------------------------------------------\n");

		System.out.println("Maze: 10 by 11"); // Displays maze dimensions
		
		displayMaze(mazeArr, row, col); // Calls on display method

		int step = 0; // Number of correct steps needed to solve the maze
		boolean isSolved = algorithm(mazeArr, step); // Calls on algorithm method; boolean variable indicates if
		                                             // there is a solution for the maze
		System.out.println("Maze Solution:");
		if (isSolved) { // If maze has a solution
			displayMaze(mazeArr, row, col); // Print solution through calling display method

		} else { // If maze has no solution
			System.out.println("No Solution"); // Print no solution
		}
	}

	// Recursively iterates through every column and row of the maze to display it
	// to the user
	// Pre: 2D Array for maze, row and column values that are updated after every
	// recursive call
	// Post: Does not return a value
	public static void displayMaze(char[][] mazeArr, int row, int col) {
		if (row < mazeArr.length) { // iterate through all (11) rows
			if (col < mazeArr[row].length) { // iterate through all (11) columns
				//displays maze without dead end indicators
				if (mazeArr[row][col] == '~') {
					mazeArr[row][col] = ' ';
				}
				System.out.print(mazeArr[row][col] + " "); // displays the current index in the array
				displayMaze(mazeArr, row, col + 1); // repeats this method by iterating through the columns first
				return;
			}

			System.out.println(); // enters the line after each row for display purposes

			displayMaze(mazeArr, row + 1, col = 0); // repeats this method by iterating through the rows
		}
	}

	// looks for starting point in the maze
	// pre: char 2D array mazeArr, int i
	// post: returns int value which is the combination of row/col
	public static int findStart(char[][] mazeArr, int i) {
		// look for starting point. i is combination of row/col
		int row = i / mazeArr.length; // value used to look at row compared to mazeArr length,
									  // if row is a valid row value, row should be less than
									  // mazeArr length
		if (row >= mazeArr.length) {
			return -1; // mazeArr has been searched and there's no starting point
		}

		int col = i % mazeArr[0].length; // value is used to look at the maze position b/c "col" is now given

		if (mazeArr[row][col] == '#') {
			return i; // found starting point
		}

		return findStart(mazeArr, i + 1); // increase "i" to continue traversing the maze recursively to possibly find a
									   	  // starting point
	}

	// indicates starting point for the maze to start solving and recursively calls
	// algorithm method that is overloaded
	// pre: char 2D array mazeArr, int step
	// post: returns a boolean value from the overloaded algorithm method
	public static boolean algorithm(char[][] mazeArr, int step) {
		int i = findStart(mazeArr, 0);

		if (i == -1) {
			return false; // No starting point found
		}

		int row = i / mazeArr.length;
		int col = i % mazeArr[0].length;

		return algorithm(mazeArr, row, col, step);
	}

	// solves the maze looking for a solution (if there is one), modifies the 2D
	// array accordingly. if there's a solution, user is able to input a guess to 
	// how many steps it takes to get to exit, user gets feedback accordingly
	// to the algorithm to trace the solution
	// pre: char 2D array mazeArr, int row, col, and step
	// post: returns boolean value depending on the algorithms solution
	public static boolean algorithm(char[][] mazeArr, int row, int col, int step) {
		Scanner input = new Scanner(System.in);

		if (mazeArr[row][col] == '$') { // found the target/exit
			
			// Lets user guess the number of steps needed to solve the maze
			System.out.print("\nGuess the number of steps taken to solve the maze: ");
			int guess = input.nextInt();
			if (guess == step) { // If guess is correct
				System.out.println("Correct! " + step + " steps was needed to solve the maze!\n");
			} else if (guess != step) { // If guess is not correct
				System.out.println("Sorry, that's incorrect! " + step + " steps was needed to solve the maze!\n");
			}
			
			return true; // maze is completely solved
		}

		if (mazeArr[row][col] == ' ') { // if there is a path
			mazeArr[row][col] = '@'; // mark as visited, block off path to not go back here again
			step++; //step taken to solve the maze
		} else if (mazeArr[row][col] != '#') { // not allowed
			return false;
		}

		// the core of the algorithm: try each direction until true is returned
		if (algorithm(mazeArr, row - 1, col, step) // north 
				|| algorithm(mazeArr, row + 1, col, step) // south 
				|| algorithm(mazeArr, row, col + 1, step) // east
				|| algorithm(mazeArr, row, col - 1, step) // west
		) {
			return true; // path found
		}

		if (mazeArr[row][col] == '@') { // mark backtracking
			mazeArr[row][col] = '~'; // indicates that this index is a dead end
		}

		return false;
	}
}

