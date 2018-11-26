//a class to hold Game States
public class gameState {
    char gameBoard [][] = new char[7][6]; //Holds the game boards
    char end = 'n'; //char N stands for NULL, this holds the ending of the game

    //default constructor
    public gameState(){
        for(int i = 0; i<7; i++){
            for(int j = 0; j<6; j++){
                gameBoard[i][j] = 'b';
            }
        }
    }

    //constructor that takes care of annoying pointer copying issues
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
}
