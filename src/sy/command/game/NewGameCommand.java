package sy.command.game;

import sy.command.AbstractGameCommand;
import sy.game.control.GameControl;

public class NewGameCommand extends AbstractGameCommand
{
    //////////////////////////////////////////////
    //	    		  CONSTRUCTOR               //
    //////////////////////////////////////////////  
	
	public NewGameCommand(GameControl game_control)
	{
		super(game_control);
	}

    //////////////////////////////////////////////
    //			  ICOMMAND INTERFACE			//
    //////////////////////////////////////////////
	
	// ICommand interface implementation
	public void execute() 
	{
		_receiver.newGame(_access_key);
	}
}
