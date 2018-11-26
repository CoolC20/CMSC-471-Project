import java.io.*;

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
        System.out.println("Creating Tree...");
        Tree decisionTree = new Tree(currentGame);
        System.out.println("Filling Tree...");
        decisionTree.fillTree(decisionTree.getHead());
        System.out.println("Computing Game Endings of Tree...");
        decisionTree.assignEndingsOfTree(decisionTree.getHead(), gameStateArray);
        System.out.println("Calculating Weights of Tree...");
        decisionTree.weighTree(decisionTree.getHead());
        decisionTree.printOptimalGamePath(decisionTree.getHead());
        System.out.println("Program end");

    }
}
