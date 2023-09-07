package sy.menu.control;

import sy.command.game.NewGameCommand;
import sy.command.game.QuitGameCommand;
import sy.game.control.GameControl;
import sy.menu.model.Menu;
import sy.menu.view.MenuView;

/*
 * 
 */
public class GameMenuControl
{
    //////////////////////////////////////////////
    //				  MVC PATTERN				//
    //////////////////////////////////////////////

	private final Menu _menu; // model
	private final MenuView _view;
	
    //////////////////////////////////////////////
    //		 		COMMAND PATTERN		        //
    //////////////////////////////////////////////
	
	private final GameControl _receiver; // Command Pattern
	
    //////////////////////////////////////////////
    //	    		  CONSTRUCTOR               //
    //////////////////////////////////////////////
	
	public GameMenuControl(GameControl game_control) // TODO: Access Only key by GameControl
	{
		_menu = new Menu();
		_view = new MenuView(_menu); // TODO: Move to a static/global enviroment
	
		_receiver = game_control;
		
		_configuration();
	}

	private void _configuration()
	{
		_menu.setName("Game");
		_menu.addOption("New Game", 	new NewGameCommand(_receiver));
		//_menu.addOption("Load Game", 	null); //TODO
		//_menu.addOption("Settings", 	null);	//TODO
		_menu.addOption("Quit", 		new QuitGameCommand(_receiver));
	}

    //////////////////////////////////////////////
    //				PUBLIC METHODS				//
    //////////////////////////////////////////////
	
	public void interaction() // TODO: Access Only key by GameControl
	{
		_view.displayMenu();
		
		Integer option_n = _view.askForMenuOption();
		_menu.executeOption(option_n);
	}

}
