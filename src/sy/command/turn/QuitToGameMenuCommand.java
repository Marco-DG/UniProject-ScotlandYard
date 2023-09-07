package sy.command.turn;

import sy.command.AbstractTurnCommand;
import sy.game.control.TurnControl;

public class QuitToGameMenuCommand extends AbstractTurnCommand
{
    //////////////////////////////////////////////
    //	    		  CONSTRUCTOR               //
    //////////////////////////////////////////////  

	public QuitToGameMenuCommand(TurnControl turn_control)
	{
		super(turn_control);
	}

    //////////////////////////////////////////////
    //			  ICOMMAND INTERFACE			//
    //////////////////////////////////////////////
	
	// ICommand interface implementation
	public void execute() 
	{
		_receiver.quitToGameMenu(_access_key);
	}
}
