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

    int turn = 0;

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
            selection[i] = new JButton(str);
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
                board1[i][j] = new JButton(num1 + " " + num2);
                board1[i][j].setPreferredSize(new Dimension(85,85));
                board1[i][j].setBackground(Color.GREEN);
                board1[i][j].setOpaque(true);
                panel2.add(board1[i][j]);
            }            
        }

        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.setVisible(true);

    }
    
        public void actionPerformed(ActionEvent e){
            int placePiece = y-1;

            for (int i = 0 ; i < x; i++){
                if (e.getSource() == selection[i]){
                    
                    while (placePiece >= 0 && board1[placePiece][i].getBackground() != Color.GREEN){
                            placePiece --;
                        }
                        
                    if (placePiece < 0){
                        System.out.println("Invalid move!");
                        }
                        
                    else if (board1[placePiece][i].getBackground() == Color.GREEN ){
                        if (turn % 2 == 0){
                            board1[placePiece][i].setBackground(Color.red);
                            board2[placePiece][i] = "x";
                        }
                        else{
                            board1[placePiece][i].setBackground(Color.blue);
                            board2[placePiece][i] = "o";
                        }
                        turn ++;
                    }
                }          
                
            }
                getBoard(board2);
        }

        public String[][] getBoard(String board[][]){
            for (int i = 0; i < y; i ++){
                for (int j = 0; j < x; j++){
                    System.out.print(board[i][j]);
                }
                System.out.println();
            }
            return board;
        }
    
    }
