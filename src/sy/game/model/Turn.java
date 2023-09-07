package sy.game.model;

import java.util.Objects;

import sy.Debugger;
import sy.game.control.TurnControl;

/*
 *	Access Privileges:
 * 	- Only a TurnControl can construct a Turn
 * 	- Only a Turn can invoke State alterations
 * 
 */
public class Turn //implements ITurn // TODO
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
    //	    		  ATTRIBUTES	            //
    //////////////////////////////////////////////  
 
	public final State			state;
	
    //////////////////////////////////////////////
    //		   TURNCONTROL ACCESS ONLY		 	//
    //////////////////////////////////////////////
	
	// Only a TurnControl can construct a Turn
	public Turn(TurnControl.AccessKey access_key, State  state)
	{
		Objects.requireNonNull(access_key);

		this.state		= state;

		state.setTurnNumber(_access_key, state.getTurnNumber() +1);
	}
	
	// Only a TurnControl can use a Ticket
    public void useTicket(TurnControl.AccessKey access_key, Ticket ticket_to_use)
    {
    	Objects.requireNonNull(access_key);
    	
    	state.turnPlayerUseTicket(_access_key, ticket_to_use);
    }
    
    // Only a TurnControl can make a move
    public void move(TurnControl.AccessKey access_key, Integer position_in_which_move)
    {
    	Objects.requireNonNull(access_key);
    	
    	state.turnPlayerMove(_access_key, position_in_which_move);
    }
    
    //////////////////////////////////////////////
    //				PUBLIC METHODS				//
    //////////////////////////////////////////////
    
    public int getNumber()
    {
    	return state.getTurnNumber();
    }
    
    public Player getPlayer() // TODO: return interface ?
    {
    	return state.getTurnPlayer(_access_key);
    }
    
}
