import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

//A class to hold the data from the data set
//and compare gameStates to it

public class dataArray {
    final int numElements = 67557;

    //All possible game endings
    gameState boardArray[] = new gameState[numElements];

    //constructor initializes the array
    public dataArray() {
        for (int i = 0; i < numElements; i++) {
            boardArray[i] = new gameState();
            for (int j = 0; j < 7; j++) {
                for (int k = 0; k < 6; k++) {
                    boardArray[i].gameBoard[j][k] = 'b';
                    //System.out.println("Filled spot [" + j + "][" + k + "] with 'b'");
                }
            }
        }

        fillDataArray();
    }

    //fills a spot given the parameters:
    private void fillSpot(int arraySpot, int rowNum, int columnNum, char charAtSpot) {
        boardArray[arraySpot].gameBoard[rowNum][columnNum] = charAtSpot;
    }

    //sets the ending of the game
    private void setEnd(int i, char c) {
        boardArray[i].end = c;
    }

    //Fills the data vector with the comparison data
    private void fillDataArray() {

        //Create file from file path
        File f = new File("C:\\Users\\mathe\\OneDrive\\Desktop\\Code Stuff\\AI HW\\Project\\connect-4.data");
        int i = 0;
        //read file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line; //holds each line of the file
            while ((line = br.readLine()) != null) {    //iterate through each line

                //Line processing of the file
                for (int j = 0; j < 7; j++) {
                    for (int k = 5; k >= 0; k--) { //NOTE:: FLOPPED BECAUSE OF THE WAY THE DATA FILE PARSES THE DATA
                        char c = line.charAt(0); //the character to be filled

                        line = line.substring(2); //update the line from the file to throw out that char

                        this.fillSpot(i, j, k, c); //update the game end state array with the char
                    }
                }
                this.setEnd(i, line.charAt(0)); //set the conclusion from the game state
                //System.out.println(line.charAt(0));
                i++; //line counter for the file
            }
            //theArray.dumpArray();
        } catch (Exception e) {
            System.out.println("File Not Found- connect-4.data");
            System.exit(1);
        }
        //System.out.println(i);
    }

    //Checks if a given gameState is contained in the array anywhere
    public int isInArray(gameState passed){
        for(int i=0; i<numElements; i++){
            if(passed.isEqualTo(boardArray[i])) {
                return i;
            }
        }
        return -1;
    }

    //returns the end condition given a spot in the array
    public char endState(int i){
        return boardArray[i].end;
    }

    //used for debugging, dumps the contents of the array
    public void dumpArray() {
        for (int k = 0; k < 1; k++) { //NOTE: change the k<1 to a different number to dump different objects
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    System.out.print(boardArray[k].gameBoard[j][i] + " ");
                }
                System.out.println(' ');
            }
            System.out.println("Game ends in: " + boardArray[k].end);
        }
    }
}
