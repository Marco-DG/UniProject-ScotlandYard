package sy.game.model;

import java.util.List;
import java.util.Objects;

import sy.Debugger;
import sy.Settings;
import sy.game.control.GameControl;

/*	A Game is defined by is State
 * 	
 *	Access Privileges:
 *	- Only a GameControl can construct (and initialize) a Game and invoke Game alterations
 * 	- Only a Game can construct (and initialize) a State
 * 
 * 	Interfaces implemented:
 * 	- IGame: the public interface of a Game
 */
public class Game
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
    
    private final State 		_state;
    private Role 				_winner;

    //////////////////////////////////////////////
	//		   GAMECONTROL ACCESS ONLY          //
	//////////////////////////////////////////////
    
    // Only a GameControl can construct a Game
    public Game(GameControl.AccessKey access_key)
    {
    	Objects.requireNonNull(access_key);
    	
    	_state = new State(_access_key);
    }
    
    // Only a GameControl can initialize a Game
    public void init(GameControl.AccessKey access_key, Integer num_players, List<String> players_name)
    {
    	Objects.requireNonNull(access_key);

    	_state.init(_access_key, num_players, players_name);
    }
    
    // Only a GameControl can ask for a State reference
    public State getState(GameControl.AccessKey access_key)
    {
    	Objects.requireNonNull(access_key);
    	
    	return _state;
    }
    
    //////////////////////////////////////////////
    //				PUBLIC METHODS				//
    //////////////////////////////////////////////

    public Role getWinner()
    {
    	return _winner;
    }

    public boolean isGameOver()
    {
    	// Ask the State if Mrx has been caught or the detectives have terminated the tickets
    	if (_state.hasMrxBeenCaught())
    	{
    		_winner = Role.DETECTIVE;
    		return true;
    	}
    	else if (!_state.canDetectivesMove() || _state.getTurnNumber() == Settings.LAST_TURN)
    	{
    		_winner = Role.MRX;
    		return true;
    	}
    	else return false;
    		
    }
}