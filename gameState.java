//a class to hold Game States
class gameState{
    char gameBoard [][] = new char[7][6]; //Holds the game boards
    char end = 'N'; //char N stands for NULL, this holds the ending of the game

    public void printGameState(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(this.gameBoard[j][i] + " ");
            }
            System.out.println(' ');
        }
    }

    //Prints game states of the connect four board
    public boolean compareGameStates(gameState gameState2){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (this.gameBoard[j][i] != gameState2.gameBoard[j][i])
                    return false;
            }
        }
        return true;
    }
}