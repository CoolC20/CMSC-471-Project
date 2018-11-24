//each node contains info about a piece and is used to have the piece in the tree class.
public class Node implements Thing{
	 private Piece piece;
	 private Node children[];
	 private boolean empty;
	 
	 
	//default and non-default constructor
	public Node(){
		piece = new Piece();
		children = new Node[7];
		
	}
	
	public Node(Piece pie){
		children = new Node[7];
		for(int i = 0; i < 7; i++){
			children[i] = null;
		}
		piece = pie;
	} 
	
	//getter and setter
	public void setData(Piece pie){
		piece = pie;
	}
	public Piece getData(){
		return piece;
	}
	
	
	public Node indexAt(int index){
		return children[index];
	}
	
	//if current node has no data(may have useful subtree)
	public boolean isEmpty(){
		return empty;
	}
	
	//adds child to node, returns true if sucessful, false otherwise
	public boolean addChild(Node item){
		for(int i = 0; i < 7; i++){
			if (children[i] == null || children[i].isEmpty()){
				children[i] = item;
				empty = false;
				return true;
			}
	}
	 return false;
	}
	
	//tests to see if all children have data(not the entire subtree that each child may possess) 
	public boolean isFull(){
		for(int i = 0; i < 7; i++){
			if (children[i] == null){
				return false;
			}
	}
	 return true;
	}

	//removes child from node, returns true if sucessful, false otherwise
	public boolean removeChild(Node item){
		for(int i = 0; i < 7; i++){
			if (children[i] == item){
				children[i] = new Node();
				empty = true;
				return true;
			}
	}
	return false;
	}
		
}