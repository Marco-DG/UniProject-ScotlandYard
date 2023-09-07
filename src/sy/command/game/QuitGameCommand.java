package sy.command.game;

import sy.command.AbstractGameCommand;
import sy.game.control.GameControl;

public class QuitGameCommand extends AbstractGameCommand
{
    //////////////////////////////////////////////
    //	    		  CONSTRUCTOR               //
    //////////////////////////////////////////////  

	public QuitGameCommand(GameControl game_control)
	{
		super(game_control);
	}

    //////////////////////////////////////////////
    //			  ICOMMAND INTERFACE			//
    //////////////////////////////////////////////
	
	// ICommand interface implementation
	public void execute() 
	{
		_receiver.quitGame(_access_key);
	}
}
