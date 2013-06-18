package edu.up.yahtzee;

import edu.up.game.GameConfig;
import edu.up.game.GameMainActivity;
import edu.up.game.GamePlayerType;
import edu.up.game.LocalGame;
import edu.up.game.ProxyGame;
import edu.up.game.ProxyPlayer;

public class YahtzeeGameMainActivity extends GameMainActivity {

    private GamePlayerType[] YahtzeePlayerTypes;

    @Override
    public GameConfig createDefaultConfig() {
	YahtzeePlayerTypes = new GamePlayerType[4];

	YahtzeePlayerTypes[0] = new GamePlayerType("Human Player", false,
		"edu.up.yahtzee.YahtzeeHumanPlayer");
	YahtzeePlayerTypes[1] = new GamePlayerType("Easy AI Player", false,
		"edu.up.yahtzee.EasyAIPlayer");
	YahtzeePlayerTypes[2] = new GamePlayerType("Medium AI Player", false,
		"edu.up.yahtzee.MediumAIPlayer");
	YahtzeePlayerTypes[3] = new GamePlayerType("Hard AI Player", false,
		"edu.up.yahtzee.HardAIPlayer");

	GameConfig defaultYahtzeeGameConfig = new GameConfig(YahtzeePlayerTypes, 1, 4,
		"Yahtzee");

	// Makes it so that one player is always there
	defaultYahtzeeGameConfig.addPlayer("Player One", 0);

	defaultYahtzeeGameConfig.addPlayer("Player Two", 0);

	return defaultYahtzeeGameConfig;
    }

    @Override
    public LocalGame createLocalGame(GameConfig config) {
	YahtzeeLocalGame newYahtzeeLocalGame = new YahtzeeLocalGame(
		config, 0);
	return newYahtzeeLocalGame;
    }

    @Override
    public ProxyPlayer createRemotePlayer() {
	return null;
    }

    @Override
    public ProxyGame createRemoteGame(String hostName) {
	return null;
    }

}
