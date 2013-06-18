package edu.up.yahtzee;

import java.util.Vector;

import edu.up.game.GameAction;
import edu.up.game.GameComputerPlayer;

import android.R.integer;
import android.os.Bundle;

/**
 * 
 * @author John Edward McCormick
 * @version 11.19.2012
 *
 * GameComputerPlayer
 *
 * Objects of this class represent individual AI players playing a particular instance of Yahtzee.
 * The core of the class is the "calculateMove" method, which is inherited from the "GameComputerPlayer"
 * in the game framework.
 */

// Fix this so it runs faster / does the Calculate Move correctly / Other stuff we talked about
// GOOD LUCK - BEN RUMPTZ
public class easyYahtzeeComputerPlayer extends GameComputerPlayer {

	//	Constants used to simplify typing the AI's decision making algorithm.
	//	Constants used in scoring the "Upper Section"
	public static final int ACES = 0;
	public static final int DOUBLES = 1;
	public static final int TRIPLES = 2;
	public static final int QUADS = 3;
	public static final int PENTS = 4;
	public static final int SEXES = 5;

	//	Constants used in scoring the "Lower Section"
	public static final int THREE_OF_A_KIND = 6;
	public static final int FOUR_OF_A_KIND = 7;
	public static final int FULL_HOUSE = 8;
	public static final int SMALL_STRAIGHT = 9;
	public static final int LARGE_STRAIGHT = 10;
	public static final int YAHTZEE = 11;
	public static final int CHANCE = 12;

	//	Instance variables including: the number of turns the given YahtzeeComputerPlayer has taken in the game thus far,
	//	the index of the AI Player in the current game's vector of players, etc.
	public int localComputerPlayerId;
	public Die[] localComputerDie;
	public int[] possibleHands;
	public boolean hasSavedDice;
	public YahtzeeLocalGame personalizedGameState;
	public int bestPossibleHand;
	public int actionsMade;
	public boolean computerPlayerIsHard;

	//	The action the given AI player intends to take when it has been called upon by the game.
	public GameAction placeScoreForBestHand;

	/**
	 * The ONLY constructor of this class. It is only one of two parts
	 * that provide any kind of explicit functionality in the "YahtzeeComputerPlayer" Class.
	 */
	public easyYahtzeeComputerPlayer() {
		localComputerPlayerId = 0;
		possibleHands = new int[14];
		actionsMade = 0;
		computerPlayerIsHard = false;
	}

	/**
	 * Method that invokes the AI's decision-making algorithm on the current GameState.
	 * 
	 * Note: The AI Player does NOT interact with the "main" GameState that controls the game.
	 * Rather, it is passed an individually customized copy of the GameState so as to prevent cheating.
	 * It then returns a newly generated GameAction to the "main" GameState. This GameAction contains the
	 * information that reflects how the player would like to use his/her/its turn. Once it is handed
	 * to the main "GameState," the GameState invokes its "ApplyAction" method on it for further processing.
	 * 
	 * @return    The copy of the GameState that is passed to the YahtzeeComputer 
	 */
	@Override
	protected GameAction calculateMove() {

		//		If the game is over, don't do anything,
		//		if the game is still in progress, prepare
		//		the AI Player to make a move.

		if(game.isGameOver()){
			return null;
		} else {
			initAI();

			//				Checks for the presence of all possible hands.
			checkHands();

			bestPossibleHand = findBestHand(possibleHands);
			placeScoreForBestHand = new PlaceScoreAction(localComputerPlayerId, bestPossibleHand);

			if(!computerPlayerIsHard){
				game.applyAction(placeScoreForBestHand);
			}
			
			actionsMade++;
			
			return null;
		}
	}
	/**
	 * initAI()
	 * 
	 * The GameState is retrieved for use in the decision-making algorithm, then customized to exclude
	 * information that might give the AI an unfair advantage. In the case of Yahtzee, there IS no
	 * information that needs to be hidden from individual players.
	 */
	protected void initAI(){
		
		//		retrieves the appropriate version of the GameState
		localComputerPlayerId = game.whoseTurn();
		YahtzeeLocalGame personalizedGameState = (YahtzeeLocalGame) game.getPlayerState(localComputerPlayerId);

		//		Retrieves the current set of die from the YahtzeeGame
		localComputerDie = personalizedGameState.getDice();

		//		Fourteenth space is init to 0 to prevent "OutOfBounds" Exceptions
		possibleHands[13] = 0;
		
		game.applyAction(new RollAction(localComputerPlayerId));
	}

	protected void checkHands(){

		//		Checks for all types of hands

		checkAllExceptStrights();
		checkStraights();

	}
	protected void checkAllExceptStrights(){

		for(Die die: localComputerDie){
			for(Die die2: localComputerDie){

				if((!die.equals(die2)) && (die2.getFaceValue() == die.getFaceValue())){

					checkUpperSection(die2, 2);

					for(Die die3: localComputerDie){

						if(!(die3.equals(die) || die3.equals(die2)) && (die3.getFaceValue() == die2.getFaceValue())){

							checkUpperSection(die3, 3);

							//							We can get three of a kind! Three of a kind nets you points
							//							equal to the sum face values of all your current dice.

							possibleHands[THREE_OF_A_KIND] = die.getFaceValue() +
									die2.getFaceValue() + die3.getFaceValue();

							//							Because we already know that the AI player has 3/5 of a full house,
							//							so now is the perfect time to check and see if it has a full house :)
							checkForFullHouse(die, die2, die3);

							for(Die die4: localComputerDie){

								if(!(die4.equals(die) || die4.equals(die2) || die4.equals(die3))
										&& (die4.getFaceValue() == die3.getFaceValue())){

									checkUpperSection(die4, 4);

									//									We can get four of a kind! Four of a kind nets you points
									//									equal to the sum face values of all your current dice.

									possibleHands[FOUR_OF_A_KIND] = die.getFaceValue() + die2.getFaceValue()
											+ die3.getFaceValue() + die4.getFaceValue();

									for(Die die5: localComputerDie){
										if(!(die5.equals(die) || die5.equals(die2) || die5.equals(die3) ||
												die5.equals(die4)) && (die5.getFaceValue() == die4.getFaceValue())){

											checkUpperSection(die5, 5);

											//											We can get a Yahtzee!!! Yahtzees are worth 50 points :D
											possibleHands[YAHTZEE] = 50;

											//											If the AI Player has not yet scored a "Chance," add it to the list of possible hands.
											possibleHands[CHANCE] = die.getFaceValue() + die2.getFaceValue()
													+ die3.getFaceValue() + die4.getFaceValue() + die5.getFaceValue();
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	protected void checkForFullHouse(Die die, Die die2, Die die3){

		for(Die houseDie: localComputerDie){
			if(!(houseDie.equals(die) || houseDie.equals(die2) || houseDie.equals(die3))){

				for(Die houseDie2: localComputerDie){
					if(!(houseDie2.equals(die) || houseDie2.equals(die2)
							|| houseDie2.equals(die3) || houseDie2.equals(houseDie))){

						if(houseDie.getFaceValue() == houseDie2.getFaceValue()){

							//							We can get a full house! Full houses are 25 points :)
							possibleHands[FULL_HOUSE] = 25;
						}
					}
				}
			}
		}
	}
	protected void checkStraights(){

		for(Die straightsDie: localComputerDie){
			for(Die straightsDie2: localComputerDie){

				if((!straightsDie.equals(straightsDie2)) &&
						(straightsDie2.getFaceValue() == (straightsDie.getFaceValue()+1))){
					for(Die straightsDie3: localComputerDie){

						if(!(straightsDie3.equals(straightsDie) || straightsDie3.equals(straightsDie2))
								&& (straightsDie2.getFaceValue() == (straightsDie.getFaceValue()+2)));
						for(Die straightsDie4: localComputerDie){

							if(!(straightsDie4.equals(straightsDie) || straightsDie4.equals(straightsDie2)
									|| straightsDie4.equals(straightsDie3)) && (straightsDie4.getFaceValue()) ==
									(straightsDie.getFaceValue()+3)){

								//								We got a small straight! Small straights are worth 30 points.
								possibleHands[SMALL_STRAIGHT] = 30;

								for(Die straightsDie5: localComputerDie){

									if(!(straightsDie5.equals(straightsDie) || straightsDie5.equals(straightsDie2) ||
											straightsDie5.equals(straightsDie3) || straightsDie5.equals(straightsDie4))
											&& (straightsDie5.getFaceValue() == (straightsDie.getFaceValue()+4))){

										//										We got a large straight!! Large straights are worth 40 points :O
										possibleHands[LARGE_STRAIGHT] = 40;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	protected void checkUpperSection(Die dieToBeChecked, int multiplier){
		switch(dieToBeChecked.getFaceValue()){

		default:
			break;
		case 1:
			possibleHands[ACES] = multiplier*dieToBeChecked.getFaceValue();
			break;
		case 2:
			possibleHands[DOUBLES] = multiplier*dieToBeChecked.getFaceValue();
			break;
		case 3:
			possibleHands[TRIPLES] = multiplier*dieToBeChecked.getFaceValue();
			break;
		case 4:
			possibleHands[QUADS] = multiplier*dieToBeChecked.getFaceValue();
			break;
		case 5:
			possibleHands[PENTS] = multiplier*dieToBeChecked.getFaceValue();
			break;
		case 6:
			possibleHands[SEXES] = multiplier*dieToBeChecked.getFaceValue();
		}
	}
	protected int findBestHand(int[] handsToBeChecked){

		int currentBestHand = 0;

		for(int i = 0; i < handsToBeChecked.length; i++){
			if(handsToBeChecked[currentBestHand]<handsToBeChecked[i]){
				currentBestHand = i;
			}
		}

		return currentBestHand;
	}
	protected int getBestHand(){
		return bestPossibleHand;
	}
}