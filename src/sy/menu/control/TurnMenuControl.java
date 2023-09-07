package sy.menu.control;

import sy.Debugger;
import sy.command.turn.AdjacentNodesCommand;
import sy.command.turn.AvailableTicketsCommand;
import sy.command.turn.DetectiveInformationsCommand;
import sy.command.turn.MrxInformationsCommand;
import sy.command.turn.PlayTicketCommand;
import sy.command.turn.PossibleMovesCommand;
import sy.command.turn.QuitToGameMenuCommand;
import sy.game.control.TurnControl;
import sy.menu.model.Menu;
import sy.menu.view.MenuView;

import java.util.Objects;

public class TurnMenuControl
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
    private static final AccessKey _access_key = new AccessKey();
    
    //////////////////////////////////////////////
    //				  MVC PATTERN				//
    //////////////////////////////////////////////

	private final Menu _menu; // model
	private final MenuView _view;
	
    //////////////////////////////////////////////
    //		 		COMMAND PATTERN		        //
    //////////////////////////////////////////////
	
	private final TurnControl _receiver; // Command Pattern

    //////////////////////////////////////////////
    //	    		  CONSTRUCTOR               //
    //////////////////////////////////////////////
	
	public TurnMenuControl(TurnControl turn_control)
	{
		_menu = new Menu();
		_view = new MenuView(_menu); // TODO: Move to a static/global enviroment

		_receiver = turn_control;
	}

	public void late_configuration()
	{
		_menu.clearOptions(); // reset the menu
		
		_menu.setName("Turn");
		_menu.addOption("Play Ticket", 				new PlayTicketCommand(_receiver));
		_menu.addOption("Available Tickets", 		new AvailableTicketsCommand(_receiver));
		_menu.addOption("Detective Informations", 	new DetectiveInformationsCommand(_receiver));

		if (_receiver.getTurn(_access_key).getPlayer().isDetective())
		{
			_menu.addOption("Mrx Informations", 		new MrxInformationsCommand(_receiver));
		}
		
		_menu.addOption("Possible Moves", 			new PossibleMovesCommand(_receiver));
		_menu.addOption("Adjacent Nodes", 			new AdjacentNodesCommand(_receiver));
		_menu.addOption("Quit to Game Menu", 		new QuitToGameMenuCommand(_receiver));
		
	}

    //////////////////////////////////////////////
    //				PUBLIC METHODS				//
    //////////////////////////////////////////////
	
	public void interaction()
	{
		_view.displayMenu();

		Integer option_n = _view.askForMenuOption();
		_menu.executeOption(option_n);
	}

}
