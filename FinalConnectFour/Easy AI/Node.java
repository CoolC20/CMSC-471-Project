//each node contains info about a piece and is used to have the piece in the tree class.
public class Node{
    public Piece piece;
    private Node children[];
    private boolean empty;


    //default and non-default constructor
    public Node(){
        this.piece = new Piece();
        this.children = new Node[7];

    }

    public Node(Piece pie){
        children = new Node[7];
        for(int i = 0; i < 7; i++){
            this.children[i] = null;
        }
        this.piece = pie;
    }

    //getter and setter
    public void setData(Piece pie){
        this.piece = pie;
    }
    public Piece getData(){
        return this.piece;
    }


    public Node indexAt(int index){
        return children[index];
    }

    //if current node has no data(may have useful subtree)
    public boolean isEmptyBoard(){
        if(piece.checkIfEmpty() == false)
            return true;
        else
            return false;
    }

    public boolean hasNoChildren(){
        for(int i = 0; i<7; i++){
            if(children[i] != null){
                return false;
            }
        }
        return true;
    }

    //adds child to node, returns true if successful, false otherwise
    public boolean addChild(Node item, int spot){
        this.children[spot] = item;
        //children[spot].piece.printPiece();
        return true;
    }
    public void removeChildAt(int i){
        children[i] = null;
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
                children[i] = null; //in Java, making a pointer null will delete the object the pointer was pointing to
                //empty = true;
                return true;
            }
        }
        return false;
    }

    public boolean hasRoomOnColumn(int i){
        return piece.board.hasRoomOnColumn(i);
    }

    public void setBoard(gameState passed){
        this.piece.setBoard(passed);
    }

    public int getHeight(){
        return piece.getHeight();
    }

    public void initializeLeaf(int i){
        if(this.children[i] == null){
            this.children[i] = new Node();
        }
    }

    public String getTurn(){
        if(this.piece.getHeight()%2 == 0){
            return "AI";
        }
        else{
            return "Player";
        }
    }

    public Node getChildAt(int i){
        return children[i];
    }

    public void setWeight(int newWeight){
        piece.setWeight(newWeight);
    }
}
