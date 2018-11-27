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
        //check for tree depth of 5, we dont want to go further than 5 deep
        if (passed.getHeight() < 4) {

            //loop through every column
            for (int i = 0; i < 7; i++) {

                //check if there is room on that column
                if (passed.hasRoomOnColumn(i)) {
                    //System.out.println("Creating child at sopt " + i);

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
                    this.addChild(passed,newNode, i);

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

            for(int i = 0; i < 7; i++){//delete leafs, we already know how this game turns out
                if(passed.getChildAt(i) != null){
                    deconstructLeaf(passed.getChildAt(i));
                    passed.removeChild(passed.getChildAt(i));
                }

            }

        }
        //check passed's children for ending state
        else{
            for(int i = 0; i< 7; i++) {
                if (passed.getChildAt(i) != null && !passed.getData().getIsGameEnd()) {
                    assignEndingsOfTree(passed.getChildAt(i), array);
                }

            }
        }

    }

    //computes the weights of the tree
    private void weighTree(Node passed){
        int nextMove = -1; //maintains which move the entity will make based off the computed weights

        //check if this game board leads to a win AND the game is  in wrap up mode
        //NOTE:: Wrap up mode means the AI has already reached a winning game state and just needs to finish it off
        //NOTE:: Perfect play from player One will never get the game out of wrap up mode
        if (passed.piece.getGameEnd() == 'l' && passed.piece.board.wrapUpGame){
            //assign high weight to this move
            //passed.piece.setWeight(100);
        }
        //else if the game leads to a Player One (token x) Losing against the AI (token o) and the game is not in wrapUp mode
        //NOTE::The AI will only pass this comparison if it represents a future move that can make the player lose
        if (passed.piece.getGameEnd() == 'l'){
            //assign high weight to this move
            passed.piece.setWeight(100);
        }
        //else if the game leads to Player One Winning
        //NOTE::The AI will only pass this comparison if it represents a future move that can make the player win
        else if(passed.piece.getGameEnd() == 'w'){
            passed.piece.setWeight(-100);
        }
        //else the game ends in a tie and is not being wrapped up
        //NOTE::The AI will only pass this comparison if it represents a future move that can make the player draw
        else if(passed.piece.getGameEnd() == 'd') {
            passed.piece.setWeight(0);
        }
        else { //Since the game is not being wrapped up and the game state being tested is not an end game state, we need to delve further
            //into subtrees to determine what moves from here will lead to possible game victories
            if (!passed.hasNoChildren()) {//if this node has children

                int tempLWeight = 999; //holds lowest weight while calculating the parents weight
                int tempHWeight = -999; //holds highest weight while calculating the parents weight

                for (int i = 0; i < 7; i++) {  //loop through all children
                    //check if the passed node has children at that spot and compute their weights first if so
                    if (passed.getChildAt(i) != null) {
                        //recursive call to compute weights (to see if any children lead to an end state)
                        weighTree(passed.getChildAt(i));
                        if (passed.getTurn()=="AI") {//If passed represents the AI's move
                            //The AI will want to pick the next move with the lowest weight
                            if (passed.getChildAt(i).getData().getWeight() < tempLWeight) {//If this child has the lowest weight out of the other subtrees so far
                                nextMove = i;
                                tempLWeight = passed.getChildAt(i).getData().getWeight() / 3;
                            }
                        }
                        else {//It must be the players turn. Assume they will always pick a move with
                            //the highest weight to them
                            if (passed.getChildAt(i).getData().getWeight() > tempHWeight) {//If this child has the highest weight out of the other subtrees so far
                                nextMove = i;
                                tempHWeight = passed.getChildAt(i).getData().getWeight() / 3;
                            }
                        }
                    }
                }
                //Now that we have computed all the weights of the Children and figured out which one has the lowest/highest respective weight
                //(depending on weather this is a Player or AI turn)

                //assign passed the new computed weight
                if(passed.getTurn()=="AI"){
                    passed.setWeight(tempLWeight);//Set the weight of this node to be the highest utility option, (lowest weight for the player)
                }
                else{
                    passed.setWeight(tempHWeight);//Set the weight of this node to be the lowest utility option,
                                                    // (highest weight, which you should assume the player will pick)
                }
                //remember the move so the AI know which move that represents
                passed.getData().setNextMove(nextMove);
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

        //If this game is and end game and the AI is winning
        if(gameStateArray.isInArray(currentGame) != -1 && gameStateArray.endState(gameStateArray.isInArray(currentGame)) == 'l'){
            currentGame.wrapUpGame = true;
        }

        //This portion leads up to the end game state if we aren't already there
        if(!currentGame.wrapUpGame) { //if the current game isn't in an end game state
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
            //decisionTree.PrintTree(decisionTree.getHead());
            //decisionTree.printChildWeights(decisionTree.getHead());
            decisionTree.printOptimalGamePath(decisionTree.getHead());
            //decisionTree.dumpTree(decisionTree.getHead());


            System.out.println("Deleting Tree...");
            //decisionTree.deleteTree();
        }
        else{ //The current game is in an end game state with us winning, lets bring home the bacon
            System.out.println("Game is in end state " + gameStateArray.boardArray[gameStateArray.isInArray(currentGame)].end);
        }

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
            else
                System.out.println("Child " + i + " is null");
        }
    }
