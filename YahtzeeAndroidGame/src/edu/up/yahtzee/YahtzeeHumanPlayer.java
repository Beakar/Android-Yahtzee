package edu.up.yahtzee;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import edu.up.game.GameHumanPlayer;
import edu.up.game.R;

public class YahtzeeHumanPlayer extends GameHumanPlayer implements
	OnClickListener {

    // all my important GUI controls
    Button restartButton;
    Button helpButton;
    Button quitButton;
    Button rollButton;

    // score buttons
    Button acesScoreButton;
    Button twosScoreButton;
    Button threesScoreButton;
    Button foursScoreButton;
    Button fivesScoreButton;
    Button sixesScoreButton;
    Button threeOfAKindScoreButton;
    Button fourOfAKindScoreButton;
    Button fullHouseScoreButton;
    Button smallStraightScoreButton;
    Button largeStraightScoreButton;
    Button yahtzeeScoreButton;
    Button chanceScoreButton;

    // score TextViews
    TextView acesScoreTextView;
    TextView twosScoreTextView;
    TextView threesScoreTextView;
    TextView foursScoreTextView;
    TextView fivesScoreTextView;
    TextView sixesScoreTextView;
    TextView threeOfAKindScoreTextView;
    TextView fourOfAKindScoreTextView;
    TextView fullHouseScoreTextView;
    TextView smallStraightScoreTextView;
    TextView largeStraightScoreTextView;
    TextView yahtzeeScoreTextView;
    TextView chanceScoreTextView;
    TextView upperTotalScoreTextView;
    TextView lowerTotalScoreTextView;
    TextView grandTotalScoreTextView;
    TextView playersTurnTextView;

    ImageView[] diceImages = new ImageView[5];

    // my copy of the current game state
    YahtzeeLocalGame myGame = null;

    // the die images used in the display
    Drawable[] myFacesOne = new Drawable[6];

    /** display the yahtzee gui and begin listening for button clicks from it */
    @Override
    protected void initializeGUI() {
	setContentView(R.layout.yahtzee_human_player);

	myGame = (YahtzeeLocalGame) game;

	// init the GUI control instance variables
	restartButton = (Button) findViewById(R.id.restartButton);
	helpButton = (Button) findViewById(R.id.helpButton);
	quitButton = (Button) findViewById(R.id.quitButton);
	rollButton = (Button) findViewById(R.id.rollButton);

	// score buttons
	acesScoreButton = (Button) findViewById(R.id.selectAces);
	twosScoreButton = (Button) findViewById(R.id.selectTwos);
	threesScoreButton = (Button) findViewById(R.id.selectThrees);
	foursScoreButton = (Button) findViewById(R.id.selectFours);
	fivesScoreButton = (Button) findViewById(R.id.selectFives);
	sixesScoreButton = (Button) findViewById(R.id.selectSixes);
	threeOfAKindScoreButton = (Button) findViewById(R.id.selectThreeOfAKinds);
	fourOfAKindScoreButton = (Button) findViewById(R.id.selectFourOfAKinds);
	fullHouseScoreButton = (Button) findViewById(R.id.selectFullHouses);
	smallStraightScoreButton = (Button) findViewById(R.id.selectSmallStraight);
	largeStraightScoreButton = (Button) findViewById(R.id.selectLargeStraight);
	yahtzeeScoreButton = (Button) findViewById(R.id.selectYahtzee);
	chanceScoreButton = (Button) findViewById(R.id.selectChance);

	// score text views
	acesScoreTextView = (TextView) findViewById(R.id.onesScoreTextView);
	twosScoreTextView = (TextView) findViewById(R.id.twosScoreTextView);
	threesScoreTextView = (TextView) findViewById(R.id.threesScoreTextView);
	foursScoreTextView = (TextView) findViewById(R.id.foursScoreTextView);
	fivesScoreTextView = (TextView) findViewById(R.id.fivesScoreTextView);
	sixesScoreTextView = (TextView) findViewById(R.id.sixesScoreTextView);
	threeOfAKindScoreTextView = (TextView) findViewById(R.id.threeOfAKindScoreTextView);
	fourOfAKindScoreTextView = (TextView) findViewById(R.id.fourOfAKindScoreTextView);
	fullHouseScoreTextView = (TextView) findViewById(R.id.fullHouseScoreTextView);
	smallStraightScoreTextView = (TextView) findViewById(R.id.smallStraightScoreTextView);
	largeStraightScoreTextView = (TextView) findViewById(R.id.largeStraightScoreTextView);
	
	yahtzeeScoreTextView = (TextView) findViewById(R.id.yahtzeeScoreTextView);
	chanceScoreTextView = (TextView) findViewById(R.id.chanceScoreTextView);
	upperTotalScoreTextView = (TextView) findViewById(R.id.upperTotalScoreTextView);
	lowerTotalScoreTextView = (TextView) findViewById(R.id.lowerTotalScoreTextView);
	grandTotalScoreTextView = (TextView) findViewById(R.id.grandTotalScoreTextView);

	// Player's turn text view
	playersTurnTextView = (TextView) findViewById(R.id.playersTurnTextView);

	diceImages[0] = (ImageView) findViewById(R.id.dice1);
	diceImages[1] = (ImageView) findViewById(R.id.dice2);
	diceImages[2] = (ImageView) findViewById(R.id.dice3);
	diceImages[3] = (ImageView) findViewById(R.id.dice4);
	diceImages[4] = (ImageView) findViewById(R.id.dice5);

	// Dice listeners
	for(int i = 0; i < 5; i++){
	    diceImages[i].setOnClickListener(this);
	}
	
	// setup listeners
	restartButton.setOnClickListener(this);
	helpButton.setOnClickListener(this);
	quitButton.setOnClickListener(this);
	rollButton.setOnClickListener(this);

	// score listeners
	acesScoreButton.setOnClickListener(this);
	twosScoreButton.setOnClickListener(this);
	threesScoreButton.setOnClickListener(this);
	foursScoreButton.setOnClickListener(this);
	fivesScoreButton.setOnClickListener(this);
	sixesScoreButton.setOnClickListener(this);
	threeOfAKindScoreButton.setOnClickListener(this);
	fourOfAKindScoreButton.setOnClickListener(this);
	fullHouseScoreButton.setOnClickListener(this);
	smallStraightScoreButton.setOnClickListener(this);
	largeStraightScoreButton.setOnClickListener(this);
	yahtzeeScoreButton.setOnClickListener(this);
	chanceScoreButton.setOnClickListener(this);

	// load the images into the myFaces array
	myFacesOne[0] = getResources().getDrawable(R.drawable.yahtzee_dice0_1);
	myFacesOne[1] = getResources().getDrawable(R.drawable.yahtzee_dice0_2);
	myFacesOne[2] = getResources().getDrawable(R.drawable.yahtzee_dice0_3);
	myFacesOne[3] = getResources().getDrawable(R.drawable.yahtzee_dice0_4);
	myFacesOne[4] = getResources().getDrawable(R.drawable.yahtzee_dice0_5);
	myFacesOne[5] = getResources().getDrawable(R.drawable.yahtzee_dice0_6);

	updateDisplay();

    }// initializeGUI

    @Override
    protected void updateDisplay() {

	// these data will be useful below TODO: Ask Kevin about the value of
	// whoseTurn()
	int myId = myGame.whoseTurn();
	playersTurnTextView.setText(myGame.getPlayerName(myId) + "'s Turn");

	// displaying the proper image for each die value
	for (int i = 0; i < myGame.getDice().length; ++i) {
	    int value = myGame.getDice()[i].getFaceValue();
	    
	    if(myGame.getDice()[i].getHighlightedStatus() == true){
		diceImages[i].setColorFilter(Color.argb(60,255,0,0));
	    }
	    else{
		diceImages[i].setColorFilter(Color.argb(0,0,0,0));
	    }
		diceImages[i].setImageDrawable(myFacesOne[value - 1].getConstantState().newDrawable());
	}// for
	
	acesScoreTextView.setText("" + myGame.getBoxScore(myId, 0));
	twosScoreTextView.setText("" + myGame.getBoxScore(myId, 1));
	threesScoreTextView.setText("" + myGame.getBoxScore(myId, 2));
	foursScoreTextView.setText("" + myGame.getBoxScore(myId, 3));
	fivesScoreTextView.setText("" + myGame.getBoxScore(myId, 4));
	sixesScoreTextView.setText("" + myGame.getBoxScore(myId, 5));
	threeOfAKindScoreTextView.setText("" + myGame.getBoxScore(myId, 6));
	fourOfAKindScoreTextView.setText("" + myGame.getBoxScore(myId, 7));
	fullHouseScoreTextView.setText("" + myGame.getBoxScore(myId, 8));
	smallStraightScoreTextView.setText("" + myGame.getBoxScore(myId, 9));
	largeStraightScoreTextView.setText("" + myGame.getBoxScore(myId, 10));
	yahtzeeScoreTextView.setText("" + myGame.getBoxScore(myId, 11));
	chanceScoreTextView.setText("" + myGame.getBoxScore(myId, 12));
	
	upperTotalScoreTextView.setText("" + myGame.getTopScore(myId));
	lowerTotalScoreTextView.setText("" + myGame.getBottomScore(myId));
	grandTotalScoreTextView.setText("" + (myGame.getBottomScore(myId) + myGame.getTopScore(myId)));
    }// updateDisplay

    // The button click handler just generates actions
    public void onClick(View v) {

	int myId = this.game.whoseTurn();

	// //Create the action associated with each button
	// if (v == restartButton){
	// RestartAction restartAction = new RestartAction(myId, myGame);
	// myGame.applyAction(restartAction);
	// }
	// else if (v == helpButton){
	// HelpAction helpAction = new HelpAction(myId);
	// myGame.applyAction(helpAction);
	// }
	// else if (v == quitButton){
	// QuitAction quitAction = new QuitAction(myId, myGame);
	// myGame.applyAction(quitAction);
	// }

	if (v == rollButton) {
	    RollAction rollAction = new RollAction(myId);
	    takeAction(rollAction);
	    updateDisplay();
	}
	else if (v == acesScoreButton) {
	    PlaceScoreAction placeScoreAction = new PlaceScoreAction(myId,0);
	    takeAction(placeScoreAction);
	}

	else if (v == twosScoreButton) {
	    PlaceScoreAction placeScoreAction = new PlaceScoreAction(myId,1);
	    takeAction(placeScoreAction);
	}

	else if (v == threesScoreButton) {
	    PlaceScoreAction placeScoreAction = new PlaceScoreAction(myId,2);
	    takeAction(placeScoreAction);
	}

	else if (v == foursScoreButton) {
	    PlaceScoreAction placeScoreAction = new PlaceScoreAction(myId,3);
	    takeAction(placeScoreAction);
	}

	else if (v == fivesScoreButton) {
	    PlaceScoreAction placeScoreAction = new PlaceScoreAction(myId,4);
	    takeAction(placeScoreAction);
	}

	else if (v == sixesScoreButton) {
	    PlaceScoreAction placeScoreAction = new PlaceScoreAction(myId,5);
	    takeAction(placeScoreAction);
	}

	else if (v == threeOfAKindScoreButton) {
	    PlaceScoreAction placeScoreAction = new PlaceScoreAction(myId,6);
	    takeAction(placeScoreAction);
	}

	else if (v == fourOfAKindScoreButton) {
	    PlaceScoreAction placeScoreAction = new PlaceScoreAction(myId,7);
	    takeAction(placeScoreAction);
	}

	else if (v == fullHouseScoreButton) {
	    PlaceScoreAction placeScoreAction = new PlaceScoreAction(myId,8);
	    takeAction(placeScoreAction);
	}

	else if (v == smallStraightScoreButton) {
	    PlaceScoreAction placeScoreAction = new PlaceScoreAction(myId,9);
	    takeAction(placeScoreAction);
	}

	else if (v == largeStraightScoreButton) {
	    PlaceScoreAction placeScoreAction = new PlaceScoreAction(myId,10);
	    takeAction(placeScoreAction);
	}

	else if (v == yahtzeeScoreButton) {
	    PlaceScoreAction placeScoreAction = new PlaceScoreAction(myId,11);
	    takeAction(placeScoreAction);
	}

	else if (v == chanceScoreButton) {
	    PlaceScoreAction placeScoreAction = new PlaceScoreAction(myId,12);
	    takeAction(placeScoreAction);
	}
	else if(v == diceImages[0]){
	    HoldAction hold = new HoldAction(myId,0);
	    takeAction(hold);
	}
	else if(v == diceImages[1]){
	    HoldAction hold = new HoldAction(myId,1);
	    takeAction(hold);
	}
	else if(v == diceImages[2]){
	    HoldAction hold = new HoldAction(myId,2);
	    takeAction(hold);
	}
	else if(v == diceImages[3]){
	    HoldAction hold = new HoldAction(myId,3);
	    takeAction(hold);
	}
	else if(v == diceImages[0]){
	    HoldAction hold = new HoldAction(myId,4);
	    takeAction(hold);
	}

    }// onClick
}// class YahtzeeHumanPlayer