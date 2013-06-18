package edu.up.yahtzee;
import java.io.Serializable;

import edu.up.game.GameAction;


public class RollAction extends GameAction implements Serializable {

	private static final long serialVersionUID = -5362662747510492796L;
		
		/*
		*
		* RollAction
		* CONSTRUCTOR
		*
		* @param source - the player who created this Action
		*/	
		public RollAction(int source)
		{
			super(source);
		}
	}