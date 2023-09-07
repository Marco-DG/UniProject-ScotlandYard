package sy.command.turn;

import sy.command.AbstractTurnCommand;
import sy.game.control.TurnControl;

public class PossibleMovesCommand extends AbstractTurnCommand
{
    //////////////////////////////////////////////
    //	    		  CONSTRUCTOR               //
    //////////////////////////////////////////////  

	public PossibleMovesCommand(TurnControl turn_control)
	{
		super(turn_control);
	}

    //////////////////////////////////////////////
    //			  ICOMMAND INTERFACE			//
    //////////////////////////////////////////////
	
	// ICommand interface implementation
	public void execute() 
	{
		_receiver.possibleMoves(_access_key);
	}
}
