package edu.up.yahtzee;

import edu.up.game.*;

/*
 * EasyAIPlayer
 * 
 * This is an AI player that is knows how to play Yahtzee
 * This player is EASY - It will roll 1 time, and then place the
 * highest possible score from that roll.
 */
public class EasyAIPlayer extends GameComputerPlayer{

    // INSTANCE VARIABLES
    private Die[] dice = new Die[YahtzeeLocalGame.NUM_DICE];	// An array of Die that is init to the number of dice used in Yahtzee
    private YahtzeeLocalGame myGame;	// this is the copy of YahtzeeLocalGame we are given.
    
    @Override
    protected GameAction calculateMove() {
	myGame = (YahtzeeLocalGame) game;
	if(myGame.getTimesRolled() < 1){
	    RollAction rollOne = new RollAction(game.whoseTurn());
	    return rollOne;
	}
	
	// After the above if statement is done, this AI player will have rolled exactly 1 time.
	// This means that we have to now calculate which box can be the best possible score.
	dice = myGame.getDice();
	
	
	// The below section counts all of the values shown on each of the dice
	int ones = 0;
	int twos = 0;
	int threes = 0;
	int fours = 0;
	int fives = 0;
	int sixes = 0;
	
	for(int i = 1; i < 6; ++i){
	    for(int j = 0; j < YahtzeeLocalGame.NUM_DICE; ++j){
		if(dice[j].getFaceValue() == i){
		    if(i == 1) ones++;
		    else if(i == 2) twos++;
		    else if(i == 3) threes++;
		    else if(i == 4) fours++;
		    else if(i == 5) fives++;
		    else if(i == 6) sixes++;
		}
	    }
	}
	
	// This statement returns a yahtzee ScoreAction if it is possible
	// Will only fill EMPTY boxes
	if(myGame.getScoreForPlayer(game.whoseTurn(), 11) == YahtzeeLocalGame.EMPTY && 
		(ones == 5 || twos ==5 || threes ==5 || fours==5 || fives==5 || sixes==5)){
	    PlaceScoreAction yahtzee = new PlaceScoreAction(game.whoseTurn(), 11);
	    return yahtzee;
	}
	
	// If we have 5 consecutive values, then we have a Large Straight
	// Will only fill EMPTY boxes
	if(myGame.getScoreForPlayer(game.whoseTurn(), 10) == YahtzeeLocalGame.EMPTY &&
		(ones == 1 && twos == 1 && threes == 1 && fours == 1 && fives == 1) || 
		(twos ==1 && threes == 1 && fours == 1 && fives == 1 && sixes == 1)){
	    PlaceScoreAction largeStr = new PlaceScoreAction(game.whoseTurn(), 10);
	    return largeStr;
	}
	
	// If we have 4 consecutive values then we have a Small Straight
	// Will only fill EMPTY boxes
	if(myGame.getScoreForPlayer(game.whoseTurn(), 9) == YahtzeeLocalGame.EMPTY &&
		(ones < 3 && twos < 3 && threes < 3 && fours < 3) ||
		(twos < 3 && threes < 3 && fours < 3 && fives < 3) ||
		(threes < 3 && fours < 3 && fives < 3 && sixes < 3)){
	    PlaceScoreAction smallStr = new PlaceScoreAction(game.whoseTurn(), 9);
	    return smallStr;
	}
	
	//The next few if statements are used as helpers to calculate a FULL HOUSE
	boolean isThreeOfAKind = false;
	boolean isPair = false;
	if(ones == 3 || twos == 3 || threes == 3 || fours == 3 || fives == 3 || sixes == 3){
	    isThreeOfAKind = true;
	}
	if(ones == 2 || twos == 2 || threes == 2 || fours == 2 || fives == 2 || sixes == 2){
	    isPair = true;
	}
	
	// We have a FULL HOUSE if the above statements both pass through
	// First make sure that the box is EMPTY though
	if(myGame.getScoreForPlayer(game.whoseTurn(), 8) == YahtzeeLocalGame.EMPTY &&
		isThreeOfAKind && isPair){
	    PlaceScoreAction fullHouse = new PlaceScoreAction(game.whoseTurn(), 8);
	    return fullHouse;
	}
	
	// Checks if there is a FOUR OF A KIND
	// Will only fill in EMPTY spaces
	if(myGame.getScoreForPlayer(game.whoseTurn(), 7) == YahtzeeLocalGame.EMPTY &&
		ones == 4 || twos == 4 || threes == 4 || fours == 4 || fives == 4 || sixes == 4){
	    PlaceScoreAction fourOfAKind = new PlaceScoreAction(game.whoseTurn(), 7);
	    return fourOfAKind;
	}
	
	// Checks if there is a THREE OF A KIND
	// Will only fill in EMPTY spaces
	if(myGame.getScoreForPlayer(game.whoseTurn(), 6) == YahtzeeLocalGame.EMPTY &&
		isThreeOfAKind ||
		ones == 4 || twos == 4 || threes == 4 || fours == 4 || fives == 4 || sixes == 4){
	    PlaceScoreAction threeOfAKind = new PlaceScoreAction(game.whoseTurn(), 6);
	    return threeOfAKind;
	}
	
	// Checks if the we need to fill in the UPPER SECTION
	// Will only fill in EMPTY spaces
	if(myGame.getScoreForPlayer(game.whoseTurn(), 5) == YahtzeeLocalGame.EMPTY &&
		sixes > fives && sixes > fours && sixes > threes && sixes > twos && sixes > ones){
	    PlaceScoreAction six = new PlaceScoreAction(game.whoseTurn(), 5);
	    return six;
	}
	else if(myGame.getScoreForPlayer(game.whoseTurn(), 4) == YahtzeeLocalGame.EMPTY &&
		fives > fours && fives > threes && fives > twos && fives > ones){
	    PlaceScoreAction five = new PlaceScoreAction(game.whoseTurn(), 4);
	    return five;
	}
	else if(myGame.getScoreForPlayer(game.whoseTurn(), 3) == YahtzeeLocalGame.EMPTY &&
		fours > threes && fours > twos && fours > ones){
	    PlaceScoreAction four = new PlaceScoreAction(game.whoseTurn(), 3);
	    return four;
	}
	else if(myGame.getScoreForPlayer(game.whoseTurn(), 2) == YahtzeeLocalGame.EMPTY &&
		threes > twos && threes > ones){
	    PlaceScoreAction three = new PlaceScoreAction(game.whoseTurn(), 2);
	    return three;
	}
	else if(myGame.getScoreForPlayer(game.whoseTurn(), 1) == YahtzeeLocalGame.EMPTY &&
		twos > ones){
	    PlaceScoreAction two = new PlaceScoreAction(game.whoseTurn(), 1);
	    return two;
	}
	else if(myGame.getScoreForPlayer(game.whoseTurn(), 0) == YahtzeeLocalGame.EMPTY){
	    PlaceScoreAction one = new PlaceScoreAction(game.whoseTurn(), 0);
	    return one;
	}
	else{}

	// If CHANCE is NOT EMPTY
	// place a score of 0 in the board starting from YAHTZEE all the way down to ONE
	//
	// NOTE: This loop will terminate if CHANCE is EMPTY
	    for(int i = 12; i >= 0; --i){
		if(myGame.getScoreForPlayer(game.whoseTurn(), i) != YahtzeeLocalGame.EMPTY){
		    PlaceScoreAction placeZero = new PlaceScoreAction(game.whoseTurn(), i);
		    return placeZero;
		}
		// The below else if is to exit the loop if CHANCE is actually EMPTY
		else if(myGame.getScoreForPlayer(game.whoseTurn(), 12) == YahtzeeLocalGame.EMPTY){
		    break;
		}
	    }
	    
	// If there is no other choice AND CHANCE is EMPTY
	// pick CHANCE
	    PlaceScoreAction chance = new PlaceScoreAction(game.whoseTurn(), 12);
	    return chance;
    }
}
