package sy.game.view;

import java.util.LinkedList;
import java.util.List;

import sy.Settings;
import sy.game.model.Game;
import sy.utils.Dialog;

/*
 * 	This class should be instantiated only by GameControl
 * 	and its methods only be invoked by GameControl
 * 	Modifications may be applied due to the shared nature of the view.
 */
public class GameView
{
	//
	private final Game _game;
	private final Dialog _dialog;
	
	public GameView(Game game)
	{
		_game = game;
		_dialog = new Dialog();
	}
	
	//
	public void displayGameTitle()
	{
		_dialog.clearConsole();
		_dialog.say(Settings.SEPARATOR);
		_dialog.say(Settings.BEFORE_LINE + "Title         " + Settings.GAME_TITLE);
		_dialog.say(Settings.BEFORE_LINE + "Author        " + Settings.GAME_AUTHOR);
	}
	
	public Integer askForNumberOfPlayers()
	{
		displayGameTitle();

		_dialog.say(Settings.SEPARATOR);
		return _dialog.askForIntegerInRange(Settings.BEFORE_LINE + "Enter the number of players (from " + Settings.MIN_NUM_PLAYERS + " to " + Settings.MAX_NUM_PLAYERS + "): ", Settings.MIN_NUM_PLAYERS, Settings.MAX_NUM_PLAYERS);
	}
	
	public List<String> askForPlayersName(int num_players)
	{
		_dialog.say(Settings.SEPARATOR);
		var players_name = new LinkedList<String>();
		
		for (int i=0; i<num_players; i++)
		{
			players_name.add( _dialog.askForString(Settings.BEFORE_LINE + "Enter player name: "));
		}
		return players_name;
	}

	public void displayGameOver()
	{
		_dialog.say(Settings.SEPARATOR);
		_dialog.say(Settings.BEFORE_LINE + "Game is Over, " + _game.getWinner().toString() + " wins.");
		_dialog.say(Settings.SEPARATOR);
	}
}
