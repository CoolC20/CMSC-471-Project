import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Board extends ConnectFour implements ActionListener{

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
    private JFrame invalidFrame;

    private JFrame frame2;
    private JPanel panel3;
    private JLabel draw;
    int turn = 0;

    private JLabel invalid;
    private JPanel panel4;

    //Creating the initial board UI
    Board(){
          
        frame = new JFrame("Connect Four");
        frame.setResizable(false);
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
            // Check which button was selected
            for (int i = 0 ; i < x; i++){
                if (e.getSource() == selection[i]){

                    // Check for the next available spot in column
                    while (placePiece >= 0 && board1[placePiece][i].getBackground() != Color.GREEN){
                            placePiece --;
                    }

                    // When there are no spots in column invalid move message is displayed
                    if (placePiece < 0){
                        System.out.println("Invalid move!");
                        invalidMove();
                        }
                        
                    // Place the piece in column
                    else if (board1[placePiece][i].getBackground() == Color.GREEN ){
                        
                        if (turn % 2 == 0){
                            board1[placePiece][i].setBackground(Color.red);
                            board2[placePiece][i] = "x";
                        }
                        
                    turn ++;
                    }
                    //Check for player win
                    String player = board2[placePiece][i];
                    if (checkWin()){
                        for (int k = 0; k < selection.length; k ++){
                            selection[k].setEnabled(false);
                        }
                        winGame(player);
                        System.out.println("WINNER");
                    }

                    // Check for draw on player turn
                    if (turn >= 42){
                        for (int k = 0; k < selection.length; k ++){
                            selection[k].setEnabled(false);
                        }
                        System.out.println("DRAW");
                        drawGame();
                    }
                
            }
        }           
        
        getBoard(board2);

        // Updating board with AI move
        if (!checkWin() && turn < 42){
            int aiMove = computeAI(board2);
            updateBoard(aiMove);
        }

    }

        public void updateBoard(int ai){

            int placePiece = y-1;

            while (placePiece >= 0 && board1[placePiece][ai].getBackground() != Color.GREEN){
                placePiece --;
            }
 
            board1[placePiece][ai].setBackground(Color.blue);
            board2[placePiece][ai] = "o";
            
            // Check for AI win
            if (checkWin()){
                winGame(board2[placePiece][ai]);
                for (int k = 0; k < selection.length; k ++){
                    selection[k].setEnabled(false);
                }
             }
             
            // Check for draw on AI turn
            if (turn >= 42){
                for (int k = 0; k < selection.length; k ++){
                    selection[k].setEnabled(false);
                }
                System.out.println("DRAW");
                drawGame();
            }

            turn ++;

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
        for (int i = 0; i < y; i ++){
            for (int j = 0; j < x; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        return board;
    }

    public void invalidMove(){

        invalidFrame = new JFrame();
        invalidFrame.setSize(300,400);
        panel4 = new JPanel();
        invalid = new JLabel("Invalid Move!");

        panel4.add(invalid);
        invalidFrame.add(panel4);
        invalidFrame.setVisible(true);

    }

    public int computeAI(String[][] board2) {
    
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

}
