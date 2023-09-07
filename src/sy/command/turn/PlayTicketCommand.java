package sy.command.turn;

import sy.command.AbstractTurnCommand;
import sy.game.control.TurnControl;

public class PlayTicketCommand extends AbstractTurnCommand
{
    //////////////////////////////////////////////
    //	    		  CONSTRUCTOR               //
    //////////////////////////////////////////////  

	public PlayTicketCommand(TurnControl turn_control)
	{
		super(turn_control);
	}

    //////////////////////////////////////////////
    //			  ICOMMAND INTERFACE			//
    //////////////////////////////////////////////
	
	// ICommand interface implementation
	public void execute() 
	{
		_receiver.playTicket(_access_key);
	}
}
