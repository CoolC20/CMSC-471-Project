//a class to hold Game States
public class gameState {
    char gameBoard [][] = new char[7][6]; //Holds the game boards
    private char winner = 'n';

    //default constructor
    public gameState(){
        for(int i = 0; i<7; i++){
            for(int j = 0; j<6; j++){
                gameBoard[i][j] = 'b';
            }
        }
    }

    //copy constructor that takes care of annoying pointer copying issues
    public gameState(gameState other){
        for(int i = 0; i<7;i++){
            for(int j = 0; j < 6; j++){
                this.gameBoard[i][j] =  other.gameBoard[i][j];
            }
        }
    }

    //Prints game states of the connect four board
    public void printGameState(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(this.gameBoard[j][i] + " ");
            }
            System.out.println(' ');
        }
    }

    //Checks if the game State is equal to the passed game state
    public boolean isEqualTo(gameState gameState2){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (this.gameBoard[j][i] != gameState2.gameBoard[j][i])
                    return false;
            }
        }
        return true;
    }

    //Checks if the gameBoard is empty
    public boolean gameIsEmpty(){
        for(int i = 0; i<7; i++){
            for(int j = 0; j<6; j++){
                if(gameBoard[i][j] != 'b')
                    return false;
            }
        }
        return true;
    }

    //Check if there is room on column for another piece by checking the top of the column
    public boolean hasRoomOnColumn(int i){
        if(gameBoard[i][0] != 'b'){
            //System.out.println("There is no room on column: " + i);
            return false;
        }
        else
            return true;
    }

    //Inserts another piece into the column
    public void insertIntoColumn(int i, char c){
        for(int j =5;j>=0;j--){
            if(gameBoard[i][j] == 'b'){
                gameBoard[i][j] = c;
                return;
            }
        }
    }

    //Check for a win
    public Boolean checkWin(){
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 6; j++){
                //Check for horizontal win
                try {
                    if (i < 4) {
                        if (this.gameBoard[i][j] != 'b') {
                            if (this.gameBoard[i][j] == this.gameBoard[i + 1][j] && this.gameBoard[i][j] == this.gameBoard[i + 2][j] && this.gameBoard[i][j] == this.gameBoard[i + 3][j]) {
                                //System.out.println("Horizontal Winner");
                                if (gameBoard[i][j] == 'x') {
                                    winner = 'x';
                                } else {
                                    winner = 'o';
                                }
                                return true;
                            }
                        }
                    }
                }
                catch (Exception e){
                    System.out.println("Problem in Horizontal Win Check");
                }
                //Check for vertical win
                try {
                    if (j < 3) {
                        if (this.gameBoard[i][j] != 'b') {
                            if (this.gameBoard[i][j] == this.gameBoard[i][j + 1] && this.gameBoard[i][j] == this.gameBoard[i][j + 2] && this.gameBoard[i][j] == this.gameBoard[i][j + 3]) {
                                //System.out.println("" + "Verticle Winner");
                                if (gameBoard[i][j] == 'x') {
                                    winner = 'x';
                                } else {
                                    winner = 'o';
                                }
                                return true;
                            }
                        }
                    }
                }
                catch(Exception e){
                    System.out.println("Problem in Verticle Win Check");
                }
                //Check for left diagonal win
                try {
                    if (i < 4 && j < 3) {
                        if (this.gameBoard[i][j] != 'b') {
                            if (this.gameBoard[i][j] == this.gameBoard[i + 1][j + 1] && this.gameBoard[i][j] == this.gameBoard[i + 2][j + 2] && this.gameBoard[i][j] == this.gameBoard[i + 3][j + 3]) {
                                //System.out.println("Left Diagonal Winner");
                                if (gameBoard[i][j] == 'x') {
                                    winner = 'x';
                                } else {
                                    winner = 'o';
                                }
                                return true;
                            }
                        }
                    }
                }
                catch (Exception e){
                    System.out.println("Problem in Left Diagonal Win Check");
                }
                //Check for right diagonal win
                try {
                    if (i < 4 && j >= 3) {
                        if (this.gameBoard[i][j] != 'b') {
                            if (this.gameBoard[i][j] == this.gameBoard[i + 1][j - 1] && this.gameBoard[i][j] == this.gameBoard[i + 2][j - 2] && this.gameBoard[i][j] == this.gameBoard[i + 3][j - 3]) {
                                //System.out.println("Right Diagonal Winner");
                                if (gameBoard[i][j] == 'x') {
                                    winner = 'x';
                                } else {
                                    winner = 'o';
                                }
                                return true;
                            }
                        }
                    }
                }
                catch (Exception e){
                    System.out.println("Problem in Right Diagonal Win Check");
                }
            }
        }
        return false;
    }

    public char getWinner(){
        return this.winner;
    }
}
