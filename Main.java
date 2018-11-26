public class Main{

    public static void main(String[] args) {
        boolean gameOver = false;
        //create and fill the data array
        dataArray gameStateArray = new dataArray();
        gameState currentGame = new gameState();

        currentGame.gameBoard[6][1] = 'x';
        currentGame.gameBoard[6][2] = 'o';
        currentGame.gameBoard[6][3] = 'x';
        currentGame.gameBoard[6][4] = 'o';
        currentGame.gameBoard[6][5] = 'x';

        //gameStateArray.dumpArray();

        //AI LOOP
        System.out.println(Tree.AILoop(currentGame, gameStateArray));
        System.out.println("Program end");
    }
}
