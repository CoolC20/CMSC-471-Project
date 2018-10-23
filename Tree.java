import java.util.ArrayList

public class Tree implements Thing{
private Node* head;

public Tree(){
	head = new Node*();
	
}

public Tree(Piece now){
	head = new Node*(now);
}

public void addChild(Node* leaf){
	boolean in = true;
	Node* current = head;
	Node* temp;
	Node* temp1;
	while(in){
	for(int i = 0; i < 7; i++){
		if(current -> indexAt(i) == null){
		    current -> indexAt(i) = leaf;
			in = false;
	}
	}
	current = current -> m_next;
	if(current 
}
}
 
public void removeChild(Node* leaf){
	boolean in = true;
	Node* current = head;
	while(in){
	for(int i = 0; i < 7; i++){
		if(current -> indexAt(i) == leaf){
		    current -> indexAt(i) = ;
			in = false;
	}
	}
	current = current -> indexAt(0);
}
}
}
   



}