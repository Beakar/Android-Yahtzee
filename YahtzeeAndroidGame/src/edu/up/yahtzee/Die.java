package edu.up.yahtzee;

import java.io.Serializable;
import java.util.Random;


public class Die implements Serializable {
	
	private static final long serialVersionUID = 2160189618030110666L;
	
	//Instance Variables
	private Random generator;
	private boolean isHighlighted = false; // A die is not highlighted by default
	private int pipValue = 1;
	
	//Constants for Die class
	private static final int MAX = 6;
	private static final int MIN = 1;
	
	
	// Constructor for Die Class
	// Initializes the random generator
	public Die(){
		generator = new Random();
	}
	
	// generates a random number from [1,6]
	public void roll(){
		pipValue = generator.nextInt(MAX - MIN + 1) + MIN;
	}
	
	// used to change a Die from highlighted to non-highlighted
	// and vice versa
	public void changeHighlightedStatus(){
		if(isHighlighted) isHighlighted = false;
		else isHighlighted = true;
	}
	
	public boolean getHighlightedStatus(){
		return isHighlighted;
	}
	
	public int getFaceValue(){
		return pipValue;
	}
}