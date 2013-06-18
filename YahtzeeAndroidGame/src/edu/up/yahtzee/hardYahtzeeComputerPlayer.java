package edu.up.yahtzee;

import edu.up.game.GameAction;

/**
 * 
 * @author John Edward McCormick
 * @version 11.19.2012
 * 
 * This subclass of easyYahtzeeAIPlayer, "hardYahtzeeAIPlayer
 *
 */

// DO THE SAME THING AS POSTED ON THE EASY COMPUTER PLAYER FILE
public class hardYahtzeeComputerPlayer extends easyYahtzeeComputerPlayer {
	
	/**
	 * Serializable Mark I (For "Test-to-Fail")
	 */
	
	Die[] dieToKeep;

	public hardYahtzeeComputerPlayer() {
		super();
		computerPlayerIsHard = true;
	}
	
	@Override
	protected GameAction calculateMove(){
		
		super.calculateMove();
		
		if(bestPossibleHand < 25){
			for(int i=0; i<localComputerDie.length; i++){
				if(localComputerDie[i].getFaceValue() > 3){
					dieToKeep[i] = localComputerDie[i];
				}
			}
			
			// Changed the below line so that it works with our updated HoldAction
			// the placeholder "0" is there just so that it compiles.
			
			// Fix this so it works correctly
			HoldAction keepTheseDicePlease = new HoldAction(localComputerPlayerId, 0);
			game.applyAction(keepTheseDicePlease);
			
			super.calculateMove();
			
			game.applyAction(placeScoreForBestHand);
		} else {
			game.applyAction(placeScoreForBestHand);
		}
		
		return null;
	}

}
