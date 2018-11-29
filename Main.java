public class Main{

    public static void main(String[] args) {
        gameState currentGame = new gameState();

        currentGame.insertIntoColumn(0,'x');
        currentGame.insertIntoColumn(0,'o');
        currentGame.insertIntoColumn(1,'x');
        currentGame.insertIntoColumn(1,'o');
        currentGame.insertIntoColumn(2,'x');

        //AI LOOP
        System.out.println(Tree.AILoop(currentGame));

        //System.out.println("Program end");

        //Board board = new Board();
    }
}

