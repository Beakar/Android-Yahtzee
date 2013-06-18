package edu.up.yahtzee;

// Imports and Stuff
import edu.up.game.GameAction;
import edu.up.game.GameConfig;
import edu.up.game.LocalGame;

/*
 * YahtzeeLocalGame
 * 
 * @author Ben Rumptz
 * @version 12/5/2012
 * 
 * This class represents the GameState for a Game of Yahtzee.
 * This class knows how to play Yahtzee and can calculate moves
 * depending on the Actions it recieves.
 */
public class YahtzeeLocalGame extends LocalGame {

    private static final long serialVersionUID = 8111168212560467589L;

    // INSTANCE VARIABLES for YahtzeeGame
    private Die[] dice = new Die[5]; // Yahtzee always has 5 dice
    private int numPlayers; // Holds the number of players in this game
    private String[] playerNames;
    private int numRolls = 0; //Times the dice have been rolled
    private boolean isFirstRoll = true;  // check if the game was created
    private int aiHoldsDone = 0;

    // PRIVATE CONSTANTS for YahtzeeGame
    private static final int MAX_BOARD_SIZE = 13;
    private static final int UPPER_SECTION = 5;

    // STATIC PUBLIC CONSTANTS for use with a YahtzeeGame
    public static final int NUM_DICE = 5;
    public static final int EMPTY = -1;
    
    // THE BOARD
    private int[][] board; // Size is initialized in constructor

    // Constructor for YahtzeeGame class
    public YahtzeeLocalGame(GameConfig initConfig, int initTurn) {
	super(initConfig, initTurn); // Calls superclass Constructor
	numPlayers = this.getConfig().getNumPlayers(); // Gets the Number of
						       // players
	
	playerNames = new String[numPlayers];
	
	for(int i = 0; i < numPlayers; ++i){
	    playerNames[i] = this.getConfig().getSelNames()[i];
	}
	// Sets the board to the correct size
	board = new int[numPlayers][MAX_BOARD_SIZE];

	// initializes each scoring spot in the board to EMPTY
	// NOTE: The n spot in board should only be edited
	// by the player whose name is in playerNames[n]
	for (int i = 0; i < numPlayers; ++i) {
	    for (int j = 0; j < MAX_BOARD_SIZE; ++j) {
		board[i][j] = EMPTY;
	    }
	}

	// initialize each Die in the array
	for (int i = 0; i < NUM_DICE; ++i) {
	    dice[i] = new Die();
	}
    }

    @Override
    public LocalGame getPlayerState(int playerIndex) {
	return this;
    }

    @Override
    public void applyAction(GameAction action) {

	//////////////////////////////////////////
	//  THIS PART DEALS WITH ROLLING DICE 	//
	//////////////////////////////////////////
	if (action instanceof RollAction) {
	    if(numRolls == 3) return;
	    // ACTUAL CALCULATION GOING ON HERE
	    for (int i = 0; i < this.dice.length; ++i) {
		if (dice[i].getHighlightedStatus() == false) { // If the die is not
							 // highlighted, roll
							 // the die
		    dice[i].roll();
		}
	    }
	    numRolls++;
	    isFirstRoll = false;
	}

	//////////////////////////////////////////////////////////
	// THIS PART DEALS WITH HOLD ACTIONS FOR HUMAN PLAYERS	//
	//////////////////////////////////////////////////////////
	else if (action instanceof HoldAction) {
	    if(isFirstRoll) return; // if it is the First turn you cannot hold a Die 

	    HoldAction holdAction = (HoldAction) action;
	    int temp = holdAction.getDice();
	    dice[temp].changeHighlightedStatus();
	}

	///////////////////////////////////////////////////////
	// THIS PART DEALS WITH HOLD ACTIONS FOR AI PLAYERS  //
	///////////////////////////////////////////////////////
	else if (action instanceof AIHoldAction) {
	    AIHoldAction holdAction = (AIHoldAction) action;
	    Die[] temp = holdAction.getDice();
	    for(int i = 0; i < NUM_DICE; ++i){
		for(int j = 0; j < (temp.length-1); ++j){
		    if(dice[i] == temp[j])
			dice[i].changeHighlightedStatus();
		}
	    }
	    aiHoldsDone++;
	}
	
	//////////////////////////////////////////
	// THIS PART DEALS WITH PLACING SCORES 	//
	//////////////////////////////////////////
	// WHEN A PLAYER HAS GONE TO THIS PART 	//
	// IT BECOMES THE NEXT PLAYERS TURN	//
	//////////////////////////////////////////
	else if (action instanceof PlaceScoreAction) {
	    if(isFirstRoll) return;
	    PlaceScoreAction placeScoreAction = (PlaceScoreAction) action;
	    int temp = placeScoreAction.getboxPosition();

	    // the score to place in the box
	    int score = 0;

	    if (board[whoseTurn][temp] != EMPTY)
		return; // if the box is full do nothing
			// Does NOT change to the next person's turn

	    // TOP SECTION
	    if (temp <= UPPER_SECTION) { // Add up top Section correctly
		for (int i = 0; i < dice.length; ++i) { // add up the dice
		    if (dice[i].getFaceValue() == (temp + 1)) { // if the dice
								// has the
								// correct value
			score += dice[i].getFaceValue();
		    }
		}
	    }

	    // THREE OF A KIND / FOUR OF A KIND
	    else if (temp > UPPER_SECTION && temp <= UPPER_SECTION + 2) {
		boolean ThreeOfAKind = false;
		boolean FourOfAKind = false;

		for (int i = 1; i <= 6; i++) { // Run through the dice checking
					       // every possible value
		    int count = 0;
		    for (int j = 0; j < NUM_DICE; j++) { // Check all the dice
							 // against that value
			if (dice[j].getFaceValue() == i)
			    count++; // If we have a match, increment the number
				     // of matching dice

			if (count >= 4)
			    FourOfAKind = true; // If we have 4 or more matching
						// dice, it is a 4 of a kind
			else if (count >= 3)
			    ThreeOfAKind = true; // If we have 3 or more
						 // matching dice, it is a 3 of
						 // a kind
		    }
		}

		if (FourOfAKind) { // If we have a 4 of a kind, add it up
		    for (int k = 0; k < NUM_DICE; k++) {
			score += dice[k].getFaceValue();
		    }
		} else if (ThreeOfAKind) { // If we have a 3 of a kind, add it
					   // up
		    for (int k = 0; k < NUM_DICE; k++) {
			score += dice[k].getFaceValue();
		    }
		}
	    }

	    // FULL HOUSE
	    else if (temp == UPPER_SECTION + 3) { // if we are in the Full House
						  // box
		boolean isPair = false;
		boolean isTriple = false;

		for (int i = 1; i <= 6; i++) {
		    int count = 0;
		    for (int j = 0; j < NUM_DICE; j++) {
			if (dice[j].getFaceValue() == i)
			    count++;
		    }
		    if (count == 3)
			isTriple = true; // if count after this loop is 3, we
					 // have a three-of-a-kind
		    else if (count == 2)
			isPair = true; // if count after this loop is 2, we have
				       // a pair
		}

		if (isTriple && isPair)
		    score = 25; // if we have a three of a kind and a pair, it
				// is a full house
	    }

	    // SMALL STRAIGHT
	    else if (temp == UPPER_SECTION + 4) {
		boolean isZero = false;

		for (int i = 1; i <= 6; i++) { // Check the dice for the lowest
					       // possible values
		    int count = 0;
		    for (int j = 0; j < NUM_DICE; j++) {
			if (dice[j].getFaceValue() == i) {
			    count++;
			}
		    }
		    if (count > 2)
			isZero = true; // if we have more than 2 repeats of a
				       // number, this box should be zero
		}
		if (!isZero)
		    score = 30; // if it is a small Straight we add 30
	    }

	    // LARGE STRAIGHT
	    else if (temp == UPPER_SECTION + 5) {
		boolean isZero = false;

		for (int i = 1; i <= 6; i++) { // Check the dice for the number
					       // of repeats
		    int count = 0;
		    for (int j = 0; j < NUM_DICE; j++) {
			if (dice[j].getFaceValue() == i) {
			    count++;
			}
		    }
		    if (count > 1)
			isZero = true; // if we have any repeats, this box
				       // should be zero
		}

		if (!isZero)
		    score = 40; // if the box should not be zero, it should be
				// scored
	    }

	    // YAHTZEE
	    else if (temp == UPPER_SECTION + 6) {
		boolean isZero = true;

		for (int i = 1; i <= 6; i++) {
		    int count = 0;
		    for (int j = 0; j < NUM_DICE; j++) {
			if (dice[j].getFaceValue() == i) {
			    count++;
			}
		    }
		    if (count == 5)
			isZero = false; // if all dice are not the same, this box
				       	// should be zero
		}

		if (!isZero)
		    score = 50; // if there should be a score in here, we add 50
	    }

	    // CHANCE
	    else if (temp == 12) {
		for (int i = 0; i < dice.length; ++i) { // add up all the dice
		    score += dice[i].getFaceValue();
		}
	    }

	    board[whoseTurn][temp] = score; // places the score in the correct
					    // box
	    
	    
	    numRolls = 0;		// Reset the Number of rolls done by this player so far
	    isFirstRoll = true;		// it is now the first roll for the new player
	    aiHoldsDone = 0;		// Reset the number of HoldActions created by the AI
	    
	    for(int i = 0; i < NUM_DICE; i++){
		if(dice[i].getHighlightedStatus() == true)
		    dice[i].changeHighlightedStatus();
	    }	// Change all the dice to not held again
	    
	    //////////////////////////////////
	    // AFTER ALL THIS, CHANGE TURN  //
	    //////////////////////////////////
	    // THE IF STATEMENT IS TO CHECK //
	    // OUT OF BOUND ERRORS 	    //
	    //////////////////////////////////
	    if (whoseTurn < (numPlayers-1))
		this.whoseTurn++; // increment whoseTurn
	    else
		this.whoseTurn = 0;
	}
    }

    @Override
    /*
     * isGameOver
     * 
     * @return false if anything in the current player's column is empty
     * 
     * @return true if the current player's column is full
     */
    public boolean isGameOver() {
	for (int i = 0; i < MAX_BOARD_SIZE; i++) {
	    if (board[whoseTurn][i] == EMPTY)
		return false;
	}
	return true;
    }

    @Override
    /*
     * getWinnerId
     * 
     * pre-condition: isGameOver must have been called and return true
     * post-condition: -None-
     * 
     * @return the index of the winning player
     */
    public int getWinnerId() {
	boolean isDoingWork = true;
	int winner = 0;

	// do the comparisons and winner setting in here
	while (isDoingWork) {
	    int k = 1, i = 0, scoreA = 0, scoreB = 0; // init values needed for
						      // comparison - i, k are
						      // the players scoreA = i
						      // player scoreB = k
						      // player
	    scoreA = getTopScore(i) + getBottomScore(i); // get score for i
							 // player
	    scoreB = getTopScore(k) + getBottomScore(k); // get score for k
							 // player

	    // if comparing the same player, the i position will be incremented
	    // again
	    if (scoreA > scoreB) {
		k += 1; // if player i's score is greater, check it against k+1
			// player
		winner = i; // set the current winner to player i
	    } else {
		i += 1; // if player k's score is greater, check it against i+1
			// player
		winner = k; // set the current winner to player k
	    }

	    // if either holder will go out of bounds on next run through
	    if (i == (numPlayers) || k == (numPlayers)) {
		isDoingWork = false; // END THE LOOP
	    }
	}

	// return the winner
	return winner;
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    //////				-------------------					   //
    //////			GETTER FUNCTIONS ARE BELOW THIS BREAK				   //
    //////				-------------------					   //
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    
    /*
     * getDice
     * 
     * @return the dice in the gamestate
     */
    public Die[] getDice() {
	return dice;
    }

    /*
     * getTopScore
     * 
     * @param playerID - the player whose score we need
     * 
     * @return the score for the top with the Bonus added
     */
    public int getTopScore(int playerID) {
	if(playerID > (numPlayers - 1)) return 0;
	int topScore = 0;
	for (int i = 0; i <= UPPER_SECTION; ++i) {
	    if(board[playerID][i] != EMPTY)
		topScore += board[playerID][i];
	}
	if (topScore >= 63) {
	    topScore += 35;
	}
	return topScore;
    }

    /*
     * getBottomScore
     * 
     * @param playerID - the player whose score we need
     * 
     * @return the score for the bottom
     */
    public int getBottomScore(int playerID) {
	if(playerID > (numPlayers - 1)) return 0;
	int bottomScore = 0;
	for (int i = (UPPER_SECTION + 1); i < MAX_BOARD_SIZE; ++i) {
	    if(board[playerID][i] != EMPTY)
		bottomScore += board[playerID][i];
	}
	return bottomScore;
    }

    /*
     * getBoxScore
     * 
     * @param playerID the player who owns this section of the board
     * @param boxNum the box to get the score from
     * 
     * @return the score represented by boxNum
     */
    public int getBoxScore(int playerID, int boxNum) {
	if(playerID > (numPlayers - 1)) return 0;	// CHANGED THIS FOR THE NEW XML
	if(board[playerID][boxNum] == EMPTY)
		return 0;
	    return board[playerID][boxNum];
    }

    public int getScoreForPlayer(int playerID, int boxNum){
	return board[playerID][boxNum];
    }
    
    /*
     * NEW METHOD FOR OUR GAME
     * 
     * @param playerID - the player's id
     * @return the player's name
     */
    public String getPlayerName(int playerID){
	if(playerID > (numPlayers - 1)) return "";
	return playerNames[playerID];
    }
    
    /*
     * @return the number of times the dice have been rolled
     */
    public int getTimesRolled(){
    	return numRolls;
    }
    
    /*
     * @return the number of times the ai has sent a HoldAction to this class
     */
    public int getAIHolds(){
	return aiHoldsDone;
    }
    
    
    /*
     * @return the number of players in this game
     */
    public int getNumPlayers(){
	return numPlayers;
    }
}