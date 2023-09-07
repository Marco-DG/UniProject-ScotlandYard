package sy.command.turn;

import sy.command.AbstractTurnCommand;
import sy.game.control.TurnControl;

public class DetectiveInformationsCommand extends AbstractTurnCommand
{
    //////////////////////////////////////////////
    //	    		  CONSTRUCTOR               //
    //////////////////////////////////////////////  

	public DetectiveInformationsCommand(TurnControl turn_control)
	{
		super(turn_control);
	}

    //////////////////////////////////////////////
    //			  ICOMMAND INTERFACE			//
    //////////////////////////////////////////////
	
	// ICommand interface implementation
	public void execute() 
	{
		_receiver.detectiveInformations(_access_key);
	}
}
