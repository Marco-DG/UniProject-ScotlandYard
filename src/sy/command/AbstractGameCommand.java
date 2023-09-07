package sy.command;

import sy.Debugger;
import sy.game.control.GameControl;

import java.util.Objects;

public abstract class AbstractGameCommand implements ICommand
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
    protected static final AccessKey _access_key = new AccessKey();
    
    //////////////////////////////////////////////
    //		 COMMAND PATTERN - RECEIVER			//
    //////////////////////////////////////////////
	
	protected final GameControl _receiver;

    //////////////////////////////////////////////
    //	    		  CONSTRUCTOR               //
    //////////////////////////////////////////////  

	public AbstractGameCommand(GameControl game_control)
	{
		_receiver = game_control;
	}

	abstract public void execute();
	
}
