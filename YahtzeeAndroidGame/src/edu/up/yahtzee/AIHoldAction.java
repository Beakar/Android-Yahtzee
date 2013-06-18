package edu.up.yahtzee;

import edu.up.game.GameAction;

/*
 * DO NOT CREATE THIS CLASS BEFORE THE FIRST ROLL HAS OCCURED
 * THIS WILL CAUSE THE AI TO "CHEAT"
 */
public class AIHoldAction extends GameAction{
    
    // INSTANCE VARIABLES for this ACTION
    private Die[] dice;
    
    // SERIAL ID
    private static final long serialVersionUID = -2593281989253188635L;

    /*
     * Represents a HoldAction
     * 
     * ONLY AI CLASSES SHOULD USE THIS
     * 
     * @param source - the player who created this Action
     * @param dice - the array of dice to be put on hold
     */
    public AIHoldAction(int source, Die[] dice) {
	super(source);
	this.dice = dice;
    }
    
    /*
     * getDice
     * 
     * @return the dice given to this Action
     */
    public Die[] getDice(){
	return dice;
    }
}
