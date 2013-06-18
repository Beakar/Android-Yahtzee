package edu.up.yahtzee;

import java.io.Serializable;

import edu.up.game.GameAction;



public class HoldAction extends GameAction implements Serializable{

	
	private static final long serialVersionUID = -6395939107373440811L;
	// INSTANCE VARIABLES	
		private int die;

		/*
		*
		* HoldAction
		* CONSTRUCTOR
		*
		* @param source - the player who created this Action
		* @param die - the die's corresponding place in the Die[] that you want to place on hold
		*
		*/	
		public HoldAction(int source, int die){
			super(source);
			this.die = die;
		}

		// Helps out YahtzeeGame - DONT WORRY ABOUT THIS
		public int getDice() {
			return die;
		}
	}