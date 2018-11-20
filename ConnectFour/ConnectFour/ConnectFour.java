import java.util.Scanner;

public class ConnectFour {
	
	// stores the board of the game in a two dimen. array
	// for now its fine if this is global
	int [][] gameBoard = new int[6][7];
	
	/*
	 * playMove attempts to play a move at the target column
	 * returns the row the piece would be located at if valid
	 * returns -1 if not valid
	*/
	public int playMove(int player, int col) {
		// travel up the column looking for a place the piece
		// can go, keep in mind the top of the column is 0 and
		// the bottom is 5.
		for(int i = 5; i > -1; i--) {
			if(gameBoard[i][col] == 0) {
				return i;
			}
		}
		
		// for loop never found a spot
		return -1;
	}
	
	/*
	 * checks if the last move given with (x,y) made by the
	 * given player is a win.
	 * returns 0 if the move was a win
	 * returns -1 if the move was not
	*/
	public int checkWin(int player, int x, int y) {
		// counters for all the possible ways a player could
		// win
		int leftDiag = 0; // diagonal going from top left to bottom right
		int rightDiag = 0; // diagonal going from bottom left to top right
		int horiz = 0; // horizontally
		int verti = 0; // vertically

		// run this loop seven times checking seven move
		// stripes along all the possible ways a player could
		// win
		for(int i = -3; i < 4; i++) {
			// keep in mind that x is a row from 0 to 5 and
			// y is a column from 0 to 6, they arent cartesian
			// coordinates.
			
			// boundary check for left diagonal
			if( ((x + i) >= 0 && (x + i) <= 5) && ((y + i) >= 0 && (y + i) <= 6) ) {
				// check if we have found another piece in a row
				if(gameBoard[x + i][y + i] == player) {
					// increment counter if the piece is the players
					leftDiag++;
				} else {
					// reset the counter if the piece is not
					leftDiag = 0;
				}
			}
			
			// boundary check for right diagonal
			if( ((x - i) >= 0 && (x - i) <= 5) && ((y + i) >= 0 && (y + i) <= 6)  ) {
				if(gameBoard[x - i][y + i] == player) {
					rightDiag++;
				} else {
					rightDiag = 0;
				}
			}
				
			// boundary check for horizontal
			if( (y + i) >= 0 && (y + i) <= 6 ) {
				if(gameBoard[x][y + i] == player) {
					horiz++;
				} else {
					horiz = 0;
				}
			}
			
			// boundary check for vertical
			if( (x + i) >= 0 && (x + i) <= 5 ) {
				if(gameBoard[x + i][y] == player) {
					verti++;
				} else {
					verti = 0;
				}
			}
			
			// if any of the counters have reached four
			// there was a win
			if(leftDiag == 4 || rightDiag == 4 || horiz == 4 || verti == 4) {
				return 0;
			}
		}
		
		// if the for loop completes and this function
		// hasn't yet returned 0, there were no wins
		return -1;
	}
	
	/*
	 * prints a human readable string for the gameboard
	 */
	public void printBoard() {
		System.out.println();
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				System.out.print(gameBoard[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		// used later for reading input moves with text
		Scanner move = new Scanner(System.in);
		ConnectFour game = new ConnectFour();
		
		// empty board
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				game.gameBoard[i][j] = 0;
			}
		}
		
		// controls the state of the game, 1 while playing,
		// 0 when the game finishes
		int gameState = 1;
		
		// number of turns passed
		int turn = 1;
		
		// number of player going, 0 for now but 1 and 2 later
		int player = 0;
		
		// valid returned by check win
		int win = 0;
		
		// plays the connect four game here
		while (gameState == 1) {
			// show the game board
			game.printBoard();
			
			// player 1 goes on odd turns, player 2 goes on even
			if(turn % 2 != 0) {
				player = 1;
			} else {
				player = 2;
			}
			
			// for now, ask what move the player would like to
			// make with text
			int x = -1;
			int y = -1;
			while(x < 0) {
				// ask player where they want to go
				System.out.println("Player " + player + ", which column (0-6) would you like to play?");
				y = move.nextInt();
				
				// validate user input
				while(y < 0 || y > 6) {
					System.out.println("Player " + player + ", enter a valid column (0-6): ");
					y = move.nextInt();
				}
				
				// validate that the move is possible
				x = game.playMove(player, y);
			}
			
			// move was validated, play the piece and increment turn
			game.gameBoard[x][y] = player;
			turn++;
			
			// check win condition
			win = game.checkWin(player, x, y);
			if(win == 0) {
				game.printBoard();
				System.out.println("Game Over: Player " + player + " wins!");
				gameState = 0;
			}
			
			// check draw condition
			if(turn == 42 && win == -1) {
				game.printBoard();
				System.out.println("Game Over: Draw");
				gameState = 0;
			}
		}

		// close the scanner
		move.close();
	}

}
