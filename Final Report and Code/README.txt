CMSC 471 
TuTh 4:00-5:15 Section
Group 16 
Cassidy Crouse, Candace Campbell, Mathew Ferry, Chidi Ede
--------------------------------------------------------

Java Info:

JDK 1.8.0_191
JRE 1.8.0_191

Developed with:

Visual Studio Code
NetBeans 8.2

--------------------------------------------------------

Files and dependencies:

Initial Connect Four with naiive minimax function:
* this version plays connect four with a naiive minimax function that just looks to see if it could win
  in the next seven turns
* as this has no utility function to determine the score value of a terminal state (unless the terminal
  is a win or loss) the AI makes mostly random decisions unless it can easily win in two turns or so.
* Pros:
  - provides useful insight into the game space of connect four with our Tree structure
  - fast calculations
  - short circuits wins and losses in the next turn
* Cons:
  - no scoring function, makes mostly random decisions
  - can easily be beaten by even beginner players.

Board.java (contains the main function)
Node.java
Piece.java
Tree.java
gameState.java

Final Connect Four with utility minimax function:
* this version plays connect four with a minimax function that makes decisions based upon the utility (score)
  of the columns it explores up to the next seven turns (this is mostly hardware dependent, less powerful
  machines than those we tested on might have to drop MAX_DEPTH)
* utility function calculates at each and every permutation of gamespace, much more expensive than before
* if our code is logically correct, it should tend not to make random decisions because the scores should
  seldom be equal at all the permutations of the board
* Pros:
 - makes decisions based on score function, which in turn is calculated by weighted attributes of a connect
   four board
 - short circuits wins and losses in the next turn 
 - short circuits playing fatal columns (explained in final report as well as comments)
* Cons:
 - far more costly calculations (though due to expanded short circuiting tends to explore much less search space)
 - still not a perfect solution to connect four
 - weights are not as fine tuned as we would like them to be
 - still does not play as well as we would like

ConnectFour.java

--------------------------------------------------------

Compiling on Linux (Tested with Debian):

1. Get the latest JRE and JDK for linux

2. use "javac Board.java Node.java Piece.java Tree.java gameState.java" for the Easy AI

3. run with "java Board.java"

4. use "javac ConnectFour.java" for the Utility-based AI

5. run with "java ConnectFour.java"

--------------------------------------------------------

Directions:

Both programs run in two modes, test mode (where data is generated and evaluated) and regular mode (where only 
one game is played). There should be clear instructions on how to use each commented into Board.java and ConnectFour.java
respectively.

In order to change the look-ahead depth of our minimax AI, you need to change global variables in Tree.java for the Easy AI,
and global variables in ConnectFour.java for the Utility-based AI. The global variables in all of the files are located at the
top after the class definition.

We would like to think that we commented our code fairly well, and that any other questions you may have will be answered at
the code demo.

--------------------------------------------------------
