//A class to hold the data from the data set
class dataArray{

    final int numElements = 67557;
    //All possible game endings
    gameState boardArray [] = new gameState[numElements];

    //constructor initializes the array
    public dataArray(){
        for (int i = 0; i<numElements; i++){
            boardArray[i] = new gameState();
            for(int j = 0; j < 7; j++){
                for(int k = 0; k < 6; k++){
                    boardArray[i].gameBoard[j][k] = 'b';
                    //System.out.println("Filled spot [" + j + "][" + k + "] with 'b'");
                }
            }
        }
    }

    //fills a spot given the parameters:
    public void fillSpot(int arraySpot, int rowNum, int columnNum, char charAtSpot){
        boardArray[arraySpot].gameBoard[rowNum][columnNum] = charAtSpot;
    }

    //sets the ending of the game
    public void setEnd(int i, char c){
        boardArray[i].end = c;
    }

    //Fills the data vector with the comparison data
    public void fillDataArray(dataArray theArray){

        //Create file from file path
		
	////////////////////////////////////////////////////////////////////////////////////////
	//CHANGE THE FILE PATH WHEN YOU USE THIS, MINE WILL BE DIFFERENT THAN YOURS
	////////////////////////////////////////////////////////////////////////////////////////
	
        File f = new File("C:\\Users\\mathe\\OneDrive\\Desktop\\Code Stuff\\AI HW\\Project\\connect-4.data");
        int i = 0;
        //read file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line; //holds each line of the file
            while ((line = br.readLine()) != null) {    //iterate through each line

                //Line processing of the file
                for(int j=0; j<7; j++) {
                    for(int k=5; k>=0; k--) { //NOTE:: FLOPPED BECAUSE OF THE WAY THE DATA FILE PARSES THE DATA
                        char c = line.charAt(0); //the character to be filled

                        line = line.substring(2); //update the line from the file to throw out that char

                        theArray.fillSpot(i,j,k,c); //update the game end state array with the char
                    }
                }
                theArray.setEnd(i,line.charAt(0)); //set the conclusion from the game state
                //System.out.println(line.charAt(0));
                i++; //line counter for the file
            }
            //theArray.dumpArray();
        }
        catch (Exception e){
            System.out.println("File Not Found- connect-4.data");
            System.exit(1);
        }
        //System.out.println(i);
    }

    //used for debugging, dumps the contents of the array
    public void dumpArray(){
        for(int k = 0; k < 1; k ++) { //NOTE: change the k<1 to a different number to dump different objects
            for (int i = 0; i < 6; i++) {
                for (int j =0 ; j < 7; j++) {
                    System.out.print(boardArray[k].gameBoard[j][i] + " ");
                }
                System.out.println(' ');
            }
            System.out.println("Game ends in: " + boardArray[k].end);
        }
    }
}
