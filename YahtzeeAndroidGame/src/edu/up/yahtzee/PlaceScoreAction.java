package edu.up.yahtzee;

import java.io.Serializable;

import edu.up.game.GameAction;

public class PlaceScoreAction extends GameAction implements Serializable {


private static final long serialVersionUID = 6673991771323096136L;
	// INSTANCE VARIABLES
	private int boxPosition;    // this variable is the position on a specific players board
				    // that goes from 0 - 12
				    // with 0 = the first box in the TOP SECTION
				    // with 6 = Three of A Kind box in the BOTTOM SECTION
				    // and 12 = the Chance box in the BOTTOM SECTION
				    // none of these boxes are a box where a final score is added up

	/*
	*
	* PlaceScoreAction
	* CONSTRUCTOR
	*
	* @param source - the player who made this action
	* @param boxPosition - the box to place the score in - see above for more in depth description
	*
	*/	
	public PlaceScoreAction(int source, int boxPosition)
	{
		super(source);
		this.boxPosition = boxPosition;
	}

	// Helps YahtzeeGame do its work correctly
	public int getboxPosition()
	{
		return boxPosition;
	}
}