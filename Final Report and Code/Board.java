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

        int mode = 0;
        
        // test mode implies that both players are AI, and that they
        // will play a designated amount of games to populate the statistics
        // global arrays
        System.out.println("Test mode: Two AI play each other on X randomly generated boards.");
        System.out.println("Regular play: User designates which players are AI, plays the game with them.");
        System.out.print("Enter 0 for test mode, Enter 1 for regular play: ");
        mode = move.nextInt();

        while(mode < 0 || mode > 1) {
            System.out.print("Enter 0 for test mode, Enter 1 for regular play: ");
            mode = move.nextInt();
        }
        
        System.out.println();
        
        // data collection values
        int test_size = 100; // default 100
        long [] avg_times;
        int [] player_wins;
        
        if(mode == 0) {
            // run in testing mode
            
            System.out.println("Running in test mode, how many games should be played? (1-10000)");
            test_size = move.nextInt();
            
            while(test_size > 10000 || test_size < 0) {
                System.out.println("Values 1 - 10000 please");
                test_size = move.nextInt();
            } 
            
            
            avg_times = new long[test_size];
            player_wins = new int[test_size];
            
            int printing = 0; // enable or disable printing by altering this value
            
            System.out.println();
            
            System.out.println("Would you like to enable printing of the boards as they are played? Reccomend to disable for data collection");
            System.out.println("Enter 0: printing off, Enter 1: printing on ");
            printing = move.nextInt();
            
            while(printing > 1 || printing < 0) {
                System.out.println("Enter 0: printing off, Enter 1: printing on ");
                printing = move.nextInt();
            }
            
            System.out.println();
            
            // need for finding averages
            int turn = 1;
            
            for(int i = 0; i < test_size; i++) {
                gameState currentGame = new gameState();
                
                System.out.println("Playing game " + (i+1) + "...");

                // controls the state of the game, 1 while playing,
                // 0 when the game finishes
                int gameState = 1;

                // number of turns passed
                turn = 1;

                // number of player going, 0 for now but 1 and 2 later
                char player = ' ';

                // valid returned by check win
                boolean win = false;

                // plays the connect four game here
                while (gameState == 1) {
                    // show the game board
                    if(printing == 1)currentGame.printGameState();

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
                        if(printing == 1) System.out.println("Player " + player + ", which column (0-6) would you like to play?");

                        // testing mode, both players are AI
                        if(turn % 2 != 0) {
                            long begin = System.currentTimeMillis();
                            y = Tree.AILoop(currentGame); // get AI loop value
                            if(printing == 1) System.out.println(y);
                            long end = System.currentTimeMillis();
                            avg_times[i] += end-begin;
                        } else {
                            long begin = System.currentTimeMillis();
                            y = Tree.AILoop(currentGame); // get AI loop value
                            if(printing == 1) System.out.println(y);
                            long end = System.currentTimeMillis();
                            avg_times[i] += end-begin;
                        }

                        // validate user input
                        while(y < 0 || y > 6) {
                            if(printing == 1) System.out.println("Player " + player + ", enter a valid column (0-6): ");
                            System.out.println("AI returned an invalid column, something very bad happened");
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
                        if(printing == 1)currentGame.printGameState();
                        if(printing == 1)System.out.println("Game Over: Player " + player + " wins!");
                        gameState = 0;
                        if(player == 'x') {
                            player_wins[i] = 1;
                        } else {
                            player_wins[i] = 2;
                        }
                    }

                    // check draw condition
                    if(turn == 42 && win == false) {
                        if(printing == 1)currentGame.printGameState();
                        if(printing == 1)System.out.println("Game Over: Draw");
                        gameState = 0;
                        player_wins[i] = 0;
                    }
                }
                
                avg_times[i] /= turn;
            }
        
        double player1 = 0;
        double player2 = 0;
        double draw = 0;
        
        double avgTime = 0;
        
            
        // per game results, might get cut off because it's too large
        System.out.println("GAME RESULTS:");
        System.out.println("--------------------------------------------------------------");
        for(int i = 0; i < test_size; i++) {
            System.out.println("Game " + (i+1) + " outcome: " + player_wins[i]);
            System.out.println("Game " + (i+1) + " average search time: " + avg_times[i]);
            System.out.println("--");
            
            avgTime += avg_times[i];
            
            if(player_wins[i] == 1) {
                player1++;
            }
            
            if(player_wins[i] == 2) {
                player2++;
            }
            
            if(player_wins[i] == 0) {
                draw++;
            }
        }
        
        // calculate percentages and averages
        player1 /= test_size;
        player2 /= test_size;
        draw /= test_size;
        avgTime /= test_size;
        
        // print overall statistics
        System.out.println();
        System.out.println("OVERALL STATISTICS:");
        System.out.println("--------------------------------------------------------------");
        System.out.println("Player 1 win percentage: " + player1);
        System.out.println("Player 2 win percentage: " + player2);
        System.out.println("Draw percentage: " + draw);
        System.out.println("Average time per turn: " + avgTime);
            
        } else {
            // run in regular mode
            
            // ask game mode configuration:
            System.out.println("Game Mode Configurations:");
            System.out.println("0: Player 1 - Human, Player 2 - Human");
            System.out.println("1: Player 1 - Human, Player 2 - AI");
            System.out.println("2: Player 1 - AI,    Player 2 - Human");
            System.out.println("3: Player 1 - AI,    Player 2 - AI");
            System.out.print("Enter Preference: ");
            int config = move.nextInt();
            
            while(config < 0 || config > 3) {
                System.out.print("Enter Preference: ");
                config = move.nextInt();
            }
            
            System.out.println();
            
            int oneIsAI = 0;
            int twoIsAI = 0;
            
            if(config == 0) {
                oneIsAI = 0;
                twoIsAI = 0;
            }
            
            if(config == 1) {
                oneIsAI = 0;
                twoIsAI = 1;
            }
            
            if(config == 2) {
                oneIsAI = 1;
                twoIsAI = 0;
            }
            
            if(config == 3) {
                oneIsAI = 1;
                twoIsAI = 1;
            }
            
            gameState currentGame = new gameState();
            
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
                                if(oneIsAI == 1) {
                                    y = Tree.AILoop(currentGame); // get AI loop value
                                    System.out.println(y);
                                }
                            } else {
                                if(twoIsAI == 1) {
                                    y = Tree.AILoop(currentGame); // get AI loop value
                                    System.out.println(y);
                                }
                            }

                            // user input (if any user is playing)
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
        }
        // close the scanner
        move.close();
    }
}
