public class Piece{
    private int alpha;
	private int beta;
	private String color1;
	private String color2;
	public Piece(){
		alpha = 0;
		beta = 0;
		color1 = " ";
		color2 = " ";
	}
	public Piece(int al, int bet, String col1, String col2){
		alpha = al;
		beta = bet;
		color1 = col1;
		color2 = col2;
	}
	public int getAlpha(){
		return alpha;
	}
	public int getBeta(){
		return beta;
	}
	public String getFirstColor(){
		return color1;
	}
	public String getSecondColor(){
		return color2;
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
}