package sy.game.control;

import java.util.Objects;

import sy.Debugger;
import sy.command.game.NewGameCommand;
import sy.command.game.QuitGameCommand;
import sy.game.model.Game;
import sy.game.model.State;
import sy.game.view.GameView;
import sy.menu.control.GameMenuControl;

/*	GameControl is the main controller
 * 
  *	Access Privileges:
 *	- Everyone can construct a GameControl
 * 	- Everyone can invoke IGameControl methods
 * 	- Only a GameControl can construct a Game, a GameView , a GameMenuControl and a TurnControl and invoke alterations on all of those apart on Game
 *  - Only the respective command class can invoke the respective command method
 *  - A TurnControl can require non privileged access to the underlying game model
 */
public class GameControl
{
    //////////////////////////////////////////////
    //				  ACCESS KEY				//
    //////////////////////////////////////////////

	// Friendship mechanism:  https://stackoverflow.com/a/18634125/8411453
	public static final class AccessKey
	{
		private AccessKey() {}
		public AccessKey(Debugger.MasterKey master_key) { Objects.requireNonNull(master_key); }
	}
	private static final GameControl.AccessKey _access_key = new GameControl.AccessKey();
    
    //////////////////////////////////////////////
    //				  MVC PATTERN				//
    //////////////////////////////////////////////
    
	private final Game _game; // model
	private final GameView _view;
	
    //////////////////////////////////////////////
    //				 TURN CONTROL				//
    //////////////////////////////////////////////
	
	private final TurnControl _turn_control;
	
    //////////////////////////////////////////////
    //	    		    MENU                	//
    //////////////////////////////////////////////  
    
    private final GameMenuControl _menu;
    
    //////////////////////////////////////////////
    //	    	   PUBLIC METHODS               //
    //////////////////////////////////////////////
	
	public GameControl()
	{
		_game = new Game(_access_key);
		_view = new GameView(_game);
		
		_turn_control = new TurnControl(_access_key, this);
		
		_menu = new GameMenuControl(this);
	}

	public void title()
	{
		_view.displayGameTitle();
	}
	
	public void menu()
	{
		title();
		_menu.interaction();
	}

    //////////////////////////////////////////////
    //		 COMMAND PATTERN - COMMANDS			//
    //////////////////////////////////////////////

	// Only a NewGameCommand can invoke a new game
	public void newGame(NewGameCommand.AccessKey access_key)
	{
		title();
		
		Objects.requireNonNull(access_key);
	
		final var num_players = _view.askForNumberOfPlayers();
		final var players_name = _view.askForPlayersName(num_players);
		
		_game.init(_access_key, num_players, players_name);
		
		_start();
	}
	
	
	// Only a QuitGameCommand can invoke a game quit
	public void quitGame(QuitGameCommand.AccessKey access_key)
	{
		Objects.requireNonNull(access_key);
		
		System.exit(0);
	}
	
    //////////////////////////////////////////////
	//		   TURNCONTROL ACCESS ONLY          //
	//	 IGAMECONTROL_TURNCONTROL INTERFACE	    //
	//////////////////////////////////////////////
	
	// A TurnControl can require non privileged access to the overlying game state // TODO
	public State getGameState(TurnControl.AccessKey access_key)
	{
		Objects.requireNonNull(access_key);
		
		return _game.getState(_access_key);
	}

    //////////////////////////////////////////////
    //				PRIVATE METHODS				//
    //////////////////////////////////////////////

	private void _start()
	{
		while (!_game.isGameOver())
		{
			_view.displayGameTitle();
			_turn_control.new_turn(_access_key);
			_turn_control.info(_access_key);
			_turn_control.menu(_access_key);
		}

		_view.displayGameOver();
	}

	//////////////////////////////////////////////
	//			DEBUGGER ACCESS ONLY			//
	//////////////////////////////////////////////

	public Game getGame(Debugger.MasterKey master_key)
	{
		Objects.requireNonNull(master_key);
		return _game;
	}
}