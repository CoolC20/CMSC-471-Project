public class Tree implements Thing{
private Node head;


//default constructor and non-default constructor
public Tree(){
	head = new Node();
	
}

public Tree(Piece now){
	head = new Node(now);
}

//returns the root
public Node getHead(){
	return head;
}


//adds child to tree
public void addChild(Node parent,Node leaf){
	Node current = parent;
	boolean in = false;
	while(!in){
		//if tree has an empty child, the leaf inserted into that place 
		in = current.addChild(leaf);
		if(in == false){
			//if not, searches for subtree that does
			int start = 0;
			current = current.indexAt(start);
			//if current subtree is full, moves to next one on same level
			while(current.isFull() == true){
				start++;
				//if all 7 children of the current tree have full subtrees, moves down one level
				if(start == 8){
					 start = 0;
					 current = current.indexAt(start);
					 addChild(current, leaf);
					 return;
				}
				current = current.indexAt(start);
			}
			//adds child to subtree
			addChild(current,leaf);
			break;
		}
	}
}

 
//technically doesn't remove node, but replaces the values within the node with empty ones.
public void removeChild(Node parent, Node leaf){
	Node current = parent;
	boolean in = false;
	while(!in){
		in = current.removeChild(leaf);
		if(in == false){
			int start = 0;	
			current = current.indexAt(start);
			while(current.isFull() == true){
				start++;
				if(start == 8){
					 removeChild(current.indexAt(0), leaf);
					 return;
				}
				current = current.indexAt(start);
			}
			if(current.isFull() == false){
				System.out.println("This leaf is not in the tree.");
				return;
			}		
		}			
	}
}

//only able to print out head and 7 immediate children(no recursion/subtrees)
public void PrintTree(Node start){
	Node current = start;
	if(current != null){
		System.out.println("The head is ");
		current.getData().printPiece(); 
	}
	else{
		return;
	}
	System.out.println("And the children are");
	for(int i = 0; i < 7; i++){
		if(current.indexAt(i) != null){
			current.indexAt(i).getData().printPiece();
		}
	}
}
}