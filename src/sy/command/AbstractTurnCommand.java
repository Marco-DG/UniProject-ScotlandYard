package sy.command;

import sy.Debugger;
import sy.game.control.TurnControl;

import java.util.Objects;

public abstract class AbstractTurnCommand implements ICommand
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
	
	protected final TurnControl _receiver;

    //////////////////////////////////////////////
    //	    		  CONSTRUCTOR               //
    //////////////////////////////////////////////  

	public AbstractTurnCommand(TurnControl turn_control)
	{
		_receiver = turn_control;
	}

	abstract public void execute();
	
}
