import java.util.Random;

public class Tree{
    private Node head;
    final int treeDepth = 7; //NOTE:: CHANGE THIS TO ALTER HOW DEEP THE TREE IS


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
    public void addChild(Node parent,Node leaf, int spot){
        //leaf.piece.printPiece();
        parent.addChild(leaf, spot);
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

        //if this node contains a win, this function assigns the winner and cancels the tree
            // creation since we dont need to go any further
        if(passed.getData().board.checkWin()) {
            return;
        }

        //check for tree depth of 5, we dont want to go further than 5 deep
        if (passed.getHeight() < treeDepth) {

            //loop through every column
            for (int i = 0; i < 7; i++) {

                //check if there is room on that column
                if (passed.hasRoomOnColumn(i)) {
                    passed.removeChildAt(i);
                    //System.out.println("Creating child at spot " + i);

                    //create a new game board to represent that move
                    gameState tempBoard = new gameState(passed.getData().board);
                    char x;
                    if(passed.getTurn() == "AI")
                        x = 'x'; //parent was AI, child should be Player token
                    else
                        x = 'o';    //parent was Player, child should be AI token
                    tempBoard.insertIntoColumn(i, x);

                    //make a new temp Piece to to assign to the leaf
                    Piece newPiece = new Piece(0,0, "x","o", tempBoard, passed.getHeight()+1);

                    //Make a new node from this Piece
                    Node newNode = new Node(newPiece);

                    //initialize the leaf
                    passed.initializeLeaf(i);

                    //update the tree with the new node
                    passed.addChild(newNode, i);

                    //recursive call to fill the child
                    fillTree(newNode);
                    //PrintTree(newNode);
                }
            }
        }
        //dumpTreeHeight3(getHead());
    }
    //computes the weights of the tree
    private void weighTree(Node passed){
        int tieBreaker = 0;

        //else if the game leads to a Player One (token x) Losing against the AI (token o) and the game is not in wrapUp mode
        //NOTE::The AI will only pass this comparison if it represents a future move in which the AI wins
        if (passed.piece.board.getWinner() == 'o'){
            //assign high weight to this move
            passed.piece.setWeight(100);
        }
        //else if the game leads to Player One Winning
        //NOTE::The AI will only pass this comparison if it represents a future move that can make the player win
        else if(passed.piece.board.getWinner() == 'x'){
            passed.piece.setWeight(-100);
        }
        else { //Since this node is not representetive of a win or loss, its value must be determined by its subtrees
            if (!passed.hasNoChildren()) {//if this node has children

                int tempLWeight = 999; //holds lowest weight while calculating the parents weight
                boolean childLWeight[] = new boolean[7]; // hold the nodes which correspond to the a tied weight, it there is multiple game paths with the same weight
                int tempHWeight = -999; //holds highest weight while calculating the parents weight
                boolean childHWeight[] = new boolean[7];

                for (int i = 0; i < 7; i++) {  //loop through all children
                    //check if the passed node has children at that spot and compute their weights first if so
                    if (passed.getChildAt(i) != null) {
                        //recursive call to compute weights (to see if any children lead to an end state)
                        weighTree(passed.getChildAt(i));

                        if (passed.getChildAt(i).getTurn()=="AI") {//If passed represents the AI's move
                            //The AI will want to pick the next move with the highest weight
                            if (passed.getChildAt(i).getData().getWeight() > tempHWeight) {//If this child has the highest weight out of the other subtrees so far
                                tempHWeight = passed.getChildAt(i).getData().getWeight();
                                //clear the boolean vector that holds the nodes containing the same weights
                                for(int o = 0; o<7; o++){
                                    childHWeight[o] = false;
                                }
                                //assign the boolean vector the new child
                                childHWeight[i] = true;
                                tieBreaker = 1;
                            }
                            else if(passed.getChildAt(i).getData().getWeight() == tempHWeight){
                                childHWeight[i] = true;
                                tieBreaker++;
                            }
                        }
                        else {//It must be the players turn. Assume they will always pick a move with
                            //the highest weight to them
                            if (passed.getChildAt(i).getData().getWeight() < tempLWeight) {//If this child has the lowest weight out of the other subtrees so far
                                tempLWeight = passed.getChildAt(i).getData().getWeight();

                                //clear the boolean vector that holds the nodes containing the same weights
                                for(int o = 0; o<7; o++){
                                    childLWeight[o] = false;
                                }
                                //assign the boolean vector the new child
                                childLWeight[i] = true;
                                tieBreaker = 1;
                            }
                            else if(passed.getChildAt(i).getData().getWeight() == tempLWeight){
                                childLWeight[i] = true;
                                tieBreaker++;
                            }
                        }
                    }
                }
                //Now that we have computed all the weights of the Children and figured out which one has the lowest/highest respective weight

                //assign passed the new computed weight
                //(depending on weather this is a Player or AI turn)
                if(passed.getTurn()=="Player"){ //parent node means the AI is the next to play
                    if(tempHWeight < 0) //natural decrement back to 0
                        tempHWeight += 5;
                    else if(tempHWeight > 5)
                        tempHWeight -= 5;
                    passed.setWeight(tempHWeight);//Set the weight of this node to be the highest utility option, (highest weight for the AI)

                    //System.out.println("num ties: " + tieBreaker);
                    if(tieBreaker != 1){ //If there are multiple nodes with the same weight
                        Random random = new Random();
                        tieBreaker = random.nextInt(tieBreaker)+1; //assign a random number between 1 and the number of tied nodes
                        //System.out.println("random num assigned is : " + tieBreaker);
                        for(int i=0; i<7; i++){
                            if(childHWeight[i] != false) {  //if this node is a tied node
                                if (tieBreaker != 1)        //check if its the random one picked to be the next move for the game
                                    tieBreaker--;
                                else {
                                    passed.getData().setNextMove(i); //remember the move so the AI know which move that represents
                                    break;
                                }
                            }
                        }
                    }
                    else{ //there is no tie among nodes
                        for(int i=0; i<7; i++){
                            if(childHWeight[i] == true)
                                passed.getData().setNextMove(i);
                        }
                    }
                }
                else{ // node represents Ai just played, player has next choice
                    if(tempLWeight < 0) //natural decrement back to 0
                        tempLWeight += 5;
                    else if(tempLWeight > 5)
                        tempLWeight -= 5;

                    passed.setWeight(tempLWeight);//Set the weight of this node to be the lowest utility option to the AI,
                                                    // (which we should assume the player will pick)
                    if(tieBreaker != 1){ //If there are multiple nodes with the same weight
                        Random random = new Random();
                        tieBreaker = random.nextInt(tieBreaker)+1; //assign a random number between 1 and the number of tied nodes
                        for(int i=0; i<7; i++){
                            if(childLWeight[i] != false) {  //if this node is a tied node
                                if (tieBreaker != 1)        //check if its the random one picked to be the next move for the game
                                    tieBreaker--;
                                else {
                                    passed.getData().setNextMove(i);//remember the move so the AI know which move that represents
                                    break;
                                }
                            }
                        }
                    }
                    else{ //there is no tie among nodes
                        for(int i=0; i<7; i++){
                            if(childLWeight[i] == true)
                                passed.getData().setNextMove(i);
                        }
                    }
                }
            } else {//there are no children, ,this is not a game end state, and there is nothing to compute
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
        return head.getData().getNextMove();

    }

    public static int AILoop(gameState currentGame){
        int toReturn = -1;

        //This portion leads up to the end game state if we aren't already there
        //System.out.println("Creating Tree...");
        Tree decisionTree = new Tree(currentGame);

        //System.out.println("Filling Tree...");
        decisionTree.fillTree(decisionTree.getHead());

        //System.out.println("Calculating Weights of Tree...");
        decisionTree.weighTree(decisionTree.getHead());

        //System.out.println("Getting Next Optimal Move");
        toReturn = decisionTree.getNextMove(currentGame);
        //decisionTree.PrintTree(decisionTree.getHead());
        //decisionTree.printChildWeights(decisionTree.getHead());
        //decisionTree.printOptimalGamePath(decisionTree.getHead());
        //decisionTree.dumpTree(decisionTree.getHead());
        //decisionTree.dumpTreeHeight3(decisionTree.getHead());

        //System.out.println("Deleting Tree...");
        decisionTree.deleteTree();

        return toReturn;
    }

    private void printChildWeights(Node passed){
        for(int i = 0; i<7; i++){
            if (passed.getChildAt(i) != null) {
                System.out.println("Child " + i + " has weight " + passed.getChildAt(i).getData().getWeight());
            }
        }
    }

    public void dumpTree(Node passed){
        System.out.println("Head is:");
        passed.piece.printPiece();

        for(int i = 0; i < 7; i++){
            if(passed.getChildAt(i) != null){
                System.out.println("Child " + i + " is:");
                passed.getChildAt(i).piece.printPiece();
            }
            else {
                System.out.println("Child " + i + " is null");
            }
        }
    }

    public void dumpTreeHeight3(Node passed){
        System.out.println("Head is:");
        passed.piece.printPiece();

        for(int i = 0; i < 7; i++){
            if(head.getChildAt(i) != null){
                System.out.println("Child " + i + " is:");
                head.getChildAt(i).piece.printPiece();

                for(int j = 0; j < 7; j++){
                    if(head.getChildAt(i).getChildAt(j) != null){
                        System.out.println("Child of " + i + " has node " + j + " which is:");
                        head.getChildAt(i).getChildAt(j).piece.printPiece();
                    }
                    else {
                        System.out.println("Child of " + i + " has node " + j + " which is: null");
                    }
                }
            }
            else {
                System.out.println("Child " + i + " is null");
            }
        }
    }
}
