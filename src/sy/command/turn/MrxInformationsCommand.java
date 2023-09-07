package sy.command.turn;

import sy.command.AbstractTurnCommand;
import sy.game.control.TurnControl;

public class MrxInformationsCommand extends AbstractTurnCommand
{
    //////////////////////////////////////////////
    //	    		  CONSTRUCTOR               //
    //////////////////////////////////////////////

    public MrxInformationsCommand(TurnControl turn_control)
    {
		super(turn_control);
	}

	//////////////////////////////////////////////
    //			ABSTRACT IMPLEMENTATION			//
    //////////////////////////////////////////////
	
	// ICommand interface implementation
	public void execute() 
	{
		_receiver.knownMrxMoves(_access_key);
	}
}
