public class Piece implements Thing{
    private int alpha;
    private int beta;
    private String color1;
    private String color2;
    public gameState board;
    private int height, weight;
    private boolean isGameEnd;
    private char gameEnd;
    private int nextMove = -1;

    public Piece(){

        alpha = 0;
        beta = 0;
        color1 = " ";
        color2 = " ";
        board = new gameState();
        height = 1;
        weight = 0;
        isGameEnd = false;
        gameEnd = 'n';
    }
    public Piece(int al, int bet, String col1, String col2, gameState newBoard, int newHeight){
        alpha = al;
        beta = bet;
        color1 = col1;
        color2 = col2;
        board = newBoard;
        height = newHeight;
        isGameEnd = false;
        gameEnd = 'n';
    }
    public int getAlpha(){
        return alpha;
    }
    public int getBeta(){
        return beta;
    }
    public int getHeight(){
        return height;
    }
    public int getWeight(){
        return weight;
    }
    public boolean getIsGameEnd(){
        return isGameEnd;
    }
    public char getGameEnd(){
        return gameEnd;
    }
    public String getFirstColor(){
        return color1;
    }
    public String getSecondColor(){
        return color2;
    }
    public gameState getBoard(){
        return board;
    }
    public int getNextMove(){
        return nextMove;
    }

    public void setAlpha(int al){
        alpha = al;
    }
    public void setBeta(int bet){
        beta = bet;
    }
    public void setFirstColor(String col){
        color1 = col;
    }
    public void setSecondColor(String col){
        color2 = col;
    }
    public void setHeight(int newHeight){
        height = newHeight;
    }
    public void setWeight(int wet){
        weight = wet;
    }
    public void setIsGameEnd(boolean f){
        isGameEnd = f;
    }
    public void setGameEnd(char end){
        gameEnd = end;
    }
    public void setBoard(gameState game){
        board = game;
    }
    public void setNextMove(int next){
        nextMove = next;
    }

    public boolean checkIfEmpty(){
        if( board.gameIsEmpty()){
            return true;
        }
        else return false;
    }
    public void printPiece(){
        System.out.format("%d is alpha, %d is beta, %s is color1, %s is color2, %d is height, %d is weight, ", alpha, beta, color1, color2, height, weight, nextMove);

        System.out.print(nextMove + " is the next move the ");
        if(height%2 == 0){//Node represents AI's move
            System.out.print("of the Player");
        }
        else //Node represent the next move of the player
            System.out.print("of the AI");
        System.out.println();
        System.out.println("Game Board is:");
        board.printGameState();
    }
}
