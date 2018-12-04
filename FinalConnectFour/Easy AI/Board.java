import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;

public class Board implements ActionListener{

    private JFrame frame;
    int x = 7;
    int y = 6;
    Color player1 = Color.red;
    Color player2 = Color.blue;
    private JButton selection[];
    private JButton board1[][];
    public String board2[][];

    private JPanel panel1;
    private JPanel panel2;

    private JFrame frame2;
    private JPanel panel3;
    private JLabel draw;
    int turn = 0;

    //Creating the initial board UI
    Board(){

        frame = new JFrame("Connect Four");
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel1 = new JPanel();
        panel2 = new JPanel();

        selection = new JButton[x];
        for (int i = 0; i < selection.length; i ++){
            int num = i;
            String str = Integer.toString(num);
            //selection[i] = new JButton(str);
            selection[i] = new JButton();
            selection[i].setPreferredSize(new Dimension(85, 20));
            selection[i].setBackground(Color.lightGray);
            panel1.add(selection[i]);
            selection[i].addActionListener(this);
        }
        board1 = new JButton[y][x];
        board2 = new String[y][x];
        for(int i = 0; i < y; i ++){
            for (int j = 0; j < x; j++){
                int num1 = i;
                int num2 = j;
                String str1 = Integer.toString(num1);
                String str2 = Integer.toString(num2);
                board2[i][j] = "_";
                //board1[i][j] = new JButton(num1 + " " + num2);
                board1[i][j] = new JButton();
                board1[i][j].setPreferredSize(new Dimension(85,85));
                board1[i][j].setBackground(Color.GREEN);
                board1[i][j].setOpaque(true);
                panel2.add(board1[i][j]);
            }
        }
        frame.setBackground(Color.gray);
        panel1.setBackground(Color.gray);
        panel2.setBackground(Color.gray);
        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e){

        int placePiece = y-1;
        //Check which button was selected
        for (int i = 0 ; i < x; i++){
            if (e.getSource() == selection[i]){

                // ********* PLACE PIECE FROM AI *********

                //Check for the next available spot in column
                while (placePiece >= 0 && board1[placePiece][i].getBackground() != Color.GREEN){
                    placePiece --;
                }

                //When there are no spots in column invalid move message is displayed
                if (placePiece < 0){
                    System.out.println("Invalid move!");
                }

                //Place the piece in column
                else if (board1[placePiece][i].getBackground() == Color.GREEN ){

                    if (turn % 2 == 0){
                        board1[placePiece][i].setBackground(Color.red);
                        board2[placePiece][i] = "x";
                    }
                    else{
                        int n = computeAI(board2);
                        board1[n][i].setBackground(Color.blue);
                        board2[n][i] = "o";
                    }
                    turn ++;

                    //Check for winner
                    String player = board2[placePiece][i];
                    if (checkWin() == true){
                        for (int k = 0; k < selection.length; k ++){
                            selection[k].setEnabled(false);
                        }
                        winGame(player);
                        System.out.println("WINNER");
                    }

                    //Check for draw
                    if (turn >= 42){
                        for (int k = 0; k < selection.length; k ++){
                            selection[k].setEnabled(false);
                        }
                        System.out.println("DRAW");
                        drawGame();
                    }

                }
            }
        }
        // ********* PASS IN GAME STATE HERE *********
        getBoard(board2);
    }

    //Check for a win
    public Boolean checkWin(){
        for (int i = 0; i < y; i++){
            for (int j = 0; j < x; j++){
                //Check for horizontal win
                if (j < 4){
                    if (board2[i][j] != "_"){
                        if (board2[i][j] == board2[i][j+1] && board2[i][j] == board2[i][j+2] && board2[i][j] == board2[i][j+3]){
                            System.out.println("Horizontal Winner");
                            return true;
                        }
                    }
                }
                //Check for vertical win
                if (i < 3){
                    if(board2[i][j] != "_"){
                        if (board2[i][j] == board2[i+1][j] && board2[i][j] == board2[i+2][j] && board2[i][j] == board2[i+3][j]){
                            System.out.println("Vertical Winner");
                            return true;
                        }
                    }
                }
                //Check for left diagonal win
                if (i < 3 && j < 4){
                    if (board2[i][j] != "_"){
                        if (board2[i][j] == board2[i+1][j+1] && board2[i][j] == board2[i+2][j+2] && board2[i][j] == board2[i+3][j+3]){
                            System.out.println("Left Diagonal Winner");
                            return true;
                        }
                    }
                }
                //Check for right diagonal win
                if (i < 3 && j >= 4){
                    if (board2[i][j] != "_"){
                        if (board2[i][j] == board2[i+1][j-1] && board2[i][j] == board2[i+2][j-2] && board2[i][j] == board2[i+3][j-3]){
                            System.out.println("Right Diagonal Winner");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //GUI for if there is a winner
    public void winGame(String player){

        frame2 = new JFrame();
        frame2.setSize(300,400);
        panel3 = new JPanel();

        if (player == "x"){
            JLabel win = new JLabel("You win!");
            panel3.add(win);
        }
        else {
            JLabel lose = new JLabel("You lose!");
            panel3.add(lose);
        }
        frame2.add(panel3);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    //GUI for if there is a draw
    public void drawGame(){

        frame2 = new JFrame();
        frame2.setSize(300,400);

        panel3 = new JPanel();

        draw = new JLabel("DRAW!!");

        panel3.add(draw);

        frame2.add(panel3);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    //Get board state
    public String[][] getBoard(String board[][]){
        /*
        for (int i = 0; i < y; i ++){
            for (int j = 0; j < x; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        */
        return board;
    }

    // ********* Pass in integer for computers turn *********
    int computeAI(String[][] board2) {

        char[][] ary = new char[7][6];
        for(int i = 0; i<7; i++) {
            for(int j = 0; j<6; j++) {
                ary[i][j] = board2[j][i].charAt(0);
                if(ary[i][j] == '_'){
                    ary[i][j] = 'b';
                }
            }
        }
        gameState currentGame = new gameState();
        for(int i = 0; i<7; i++) {
            for(int j = 0; j<6; j++) {
                currentGame.gameBoard[i][j] = ary[i][j];
            }
        }

        return(Tree.AILoop(currentGame));
    }
    
    public static void main(String[] args) {
        Scanner move = new Scanner(System.in);
        gameState currentGame = new gameState();

        //AI LOOP
        //System.out.println(Tree.AILoop(currentGame));

        // controls the state of the game, 1 while playing,
        // 0 when the game finishes
        int gameState = 1;

        // number of turns passed
        int turn = 1;

        // number of player going, 0 for now but 1 and 2 later
        char player = ' ';

        // valid returned by check win
        boolean win = false;

        // plays the connect four game here
        while (gameState == 1) {
                // show the game board
                currentGame.printGameState();

                // player 1 goes on odd turns, player 2 goes on even
                if(turn % 2 != 0) {
                        player = 'x';
                } else {
                        player = 'o';
                }

                // for now, ask what move the player would like to
                // make with text
                boolean x = false;
                int y = -1;
                while(x == false) {
                        // ask player where they want to go
                        System.out.println("Player " + player + ", which column (0-6) would you like to play?");
                        // player x is AI, player o is AI
                        if(turn % 2 != 0) {
                            y = Tree.AILoop(currentGame); // get AI loop value
                            System.out.println(y);
                        } else {
                            y = Tree.AILoop(currentGame); // get AI loop value
                        }
                        

                        // validate user input
                        while(y < 0 || y > 6) {
                                System.out.println("Player " + player + ", enter a valid column (0-6): ");
                                y = move.nextInt();
                        }

                        // validate that the move is possible
                        x = currentGame.hasRoomOnColumn(y);
                }

                // move was validated, play the piece and increment turn
                currentGame.insertIntoColumn(y, player);
                turn++;

                // check win condition
                win = currentGame.checkWin();
                if(win) {
                        currentGame.printGameState();
                        System.out.println("Game Over: Player " + player + " wins!");
                        gameState = 0;
                }

                // check draw condition
                if(turn == 42 && win == false) {
                        currentGame.printGameState();
                        System.out.println("Game Over: Draw");
                        gameState = 0;
                }
        }

        // close the scanner
        move.close();
    }
}
