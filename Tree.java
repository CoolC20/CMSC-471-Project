import java.util.Random;

public class Tree implements Thing{
    private Node head;


    //default constructor and non-default constructor
    public Tree(){
        head = new Node();

    }

    public Tree(gameState currentGame){
        head = new Node();
        head.setBoard(currentGame);

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


    //deconstructs the tree from the passed node
    private void deconstructLeaf(Node toDelete){
        for(int i = 0; i<7; i++){
            if(toDelete.getChildAt(i) != null){
                deconstructLeaf(toDelete.getChildAt(i));//recursive call to delete children
                toDelete.removeChildAt(i); //delete the node
            }
        }
    }
    private void deleteTree(){
        deconstructLeaf(head);
        head = null;
    }

    //only able to print out head and 7 immediate children(no recursion/subtrees)
    public void PrintTree(Node start){
        Node current = start;
        if(current != null){
            System.out.println("The Node is ");
            current.getData().printPiece();
        }
        else{
            return;
        }
        /*
        if(current.hasNoChildren())
            System.out.println("This Node has No children");
        else {
            System.out.println("These are its' children:");
            for (int i = 0; i < 7; i++) {
                if (current.indexAt(i) != null) {
                    current.indexAt(i).getData().printPiece();
                }
            }
        }
        */
    }

    //fills the tree with every possible game state for a depth of 5
    private void fillTree(Node passed) {
        //check for tree depth of 5, we dont want to go further than 5 deep
        if (passed.getHeight() < 5) {

            //loop through every column
            for (int i = 0; i < 7; i++) {

                //check if there is room on that column
                if (passed.hasRoomOnColumn(i)) {

                    //create a new game board to represent that move
                    gameState tempBoard = new gameState(passed.getData().board);
                    char x;
                    if(passed.getHeight()%2 == 0)
                        x = 'x';
                    else
                        x = 'o';
                    tempBoard.insertIntoColumn(i, x);

                    //make a new temp Piece to to assign to the leaf
                    Piece newPiece = new Piece(0,0, "x","o", tempBoard, passed.getHeight()+1);

                    //Make a new node from this Piece
                    Node newNode = new Node(newPiece);

                    //initialize the leaf
                    passed.initializeLeaf(i);

                    //update the tree with the new node
                    this.addChild(passed,newNode);

                    //recursive call to fill the child
                    fillTree(newNode);
                    //this.PrintTree(newNode);

                }
            }
            //this.PrintTree(passed);
        }
    }

    //puts weights in the tree depending on the desireability of each node
    private void assignEndingsOfTree(Node passed, dataArray array){
        //check if passed's game board is an ending state
        int atSpot = array.isInArray(passed.getData().getBoard());

        if(atSpot != -1){
            //System.out.println("This is a game ending state:");
            //passed.getData().board.printGameState();
            //System.out.println("that leads to:" + array.endState(atSpot));
            //Set this node to have its associated end state
            passed.piece.setGameEnd(array.endState(atSpot));
            passed.piece.setIsGameEnd(true);

        }
        //check passed's children for ending state
        for(int i = 0; i< 7; i++){
            if(passed.getChildAt(i) != null && !passed.getData().getIsGameEnd()){
                assignEndingsOfTree(passed.getChildAt(i), array);
            }
        }

    }

    //computes the weights of the tree
    private void weighTree(Node passed){
        int nextMove = -1; //maintains which move the entity will make based off the computed weights

        //check if this game board leads to an end by itself
        if(passed.piece.getIsGameEnd()) {
            //if the game leads to a win and is the AIs turn OR the game leads to a loss and is the players turn
            if ((passed.piece.getGameEnd() == 'w' && passed.piece.getHeight() % 2 == 0) ||
                    (passed.piece.getGameEnd() == 'l' && passed.piece.getHeight() % 2 == 1)) {
                //assign high weight to this move
                passed.piece.setWeight(100);
            }
            //else if the game leads to a loss and is the AI's turn OR the game leads to a victory and is the players turn
            else{
                passed.piece.setWeight(-100);
            }
        }
        else{ //else we need to determine the weight from the subtrees
            if(!passed.hasNoChildren()) {//there are children
                int tempWeight = 0; //holds weight while calculating the parents weight
                //loop through all children
                for (int i = 0; i < 7; i++) {
                    //check if the passed node has children at that spot and compute their weights first if so
                    if (passed.getChildAt(i) != null) {
                        //recursive call to compute weights (to see if any children lead to an end state)
                        weighTree(passed.getChildAt(i));
                        if(passed.getHeight()%2 == 0) {//If passed represents the AI's move
                            //The AI will want to pick the next move with the highest positive weight
                            //and will never pick a subtree with a negative weight if it can help it
                            if (passed.getChildAt(i).getData().getWeight() > tempWeight) {
                                nextMove = i;
                                tempWeight = passed.getChildAt(i).getData().getWeight()/3;
                            }
                        }
                        else {//It must be the players turn. Assume they will always pick a move with
                            //the lowest weight to the AI
                            if(passed.getChildAt(i).getData().getWeight() > tempWeight) {
                                nextMove = i;
                                tempWeight = passed.getChildAt(i).getData().getWeight()/3;
                            }
                        }
                    }
                }
                //assign passed the new computed weight
                passed.setWeight(tempWeight);
                //remember the move so the AI know which move to make
                passed.getData().setNextMove(nextMove);
            }
            else {//there are no children, ,this is not a game end state, and there is nothing to compute
                //the weight will be left at 0
                //passed.piece.printPiece();
                return;
            }
        }
    }

    public void printOptimalGamePath(Node passed){
        passed.getData().printPiece();
        if(passed.getData().getNextMove() != -1){
            printOptimalGamePath(passed.getChildAt(passed.getData().getNextMove()));
        }
    }

    //returns the next optimal move, or a random move if there is no optimal move
    private int getNextMove(gameState currentGame){
        //check if there is an optimal move
        if(head.getData().getNextMove() != -1)
            return head.getData().getNextMove();
        //there is no optimal move, generate a random one
        else{
            Random rand = new Random();

            int n = rand.nextInt(7);
            while(!currentGame.hasRoomOnColumn(n)) {//find a random one to fill the spot
                n = rand.nextInt(7);
            }
            return n;
        }
    }

    public static int AILoop(gameState currentGame, dataArray gameStateArray){
        int toReturn = -1;

        //This portion leads up to the end game state if we aren't already there
        if(gameStateArray.isInArray(currentGame) != -1) { //if the current game isn't in an end game state
            System.out.println("Creating Tree...");
            Tree decisionTree = new Tree(currentGame);

            System.out.println("Filling Tree...");
            decisionTree.fillTree(decisionTree.getHead());

            System.out.println("Computing Game Endings of Tree...");
            decisionTree.assignEndingsOfTree(decisionTree.getHead(), gameStateArray);

            System.out.println("Calculating Weights of Tree...");
            decisionTree.weighTree(decisionTree.getHead());

            System.out.println("Getting Next Optimal Move");
            toReturn = decisionTree.getNextMove(currentGame);

            System.out.println("Deleting Tree...");
            decisionTree.deleteTree();
        }
        else{ //The current game is in an end game state

        }
        return toReturn;
    }
}
