package sy.game.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*	A Player have a name, a role, a position and a map of tickets
 * 
 * 	Access Privileges:
 * 	- Only a State can construct a Player and invoke Player alterations
 * 
 * 	# TODO: remove
 * 	#Interfaces implemented:
 * 	#- IPlayer: the public interface of a Player
 */
public class Player
{
	
    //////////////////////////////////////////////
    //	    		  ATTRIBUTES	            //
    //////////////////////////////////////////////
	
	public final String name;
	public final Role role;
	
	protected final Map<Ticket, Integer> _tickets;
	protected Integer _position;
	
	
    //////////////////////////////////////////////
    //	     		PUBLIC METHODS      	    //
    //////////////////////////////////////////////
	
	public Integer getPosition()
	{
		return _position;
	}
	
	public Integer getTicketCount(Ticket ticket)
	{
		return _tickets.get(ticket);
	}
	
	public boolean hasTicket(Ticket ticket)
	{
		return (getTicketCount(ticket) > 0) ? true : false;
	}
	
	public boolean isMrx()
	{
		return role == Role.MRX ? true : false;
	}
	
	public boolean isDetective()
	{
		return role == Role.DETECTIVE ? true : false;
	}

    //////////////////////////////////////////////
    //		      STATE ACCESS ONLY		        //
    //////////////////////////////////////////////
	
	// Only a State can construct a Player
	public Player(State.AccessKey access_key, String name, Role role)
	{
		Objects.requireNonNull(access_key);
		
		this.name = name;
		this.role = role;
		
		_tickets = new HashMap<Ticket, Integer>();
		_position = null;
	}
	
	// Only a State can set a Player's position
	public void setPosition(State.AccessKey access_key, Integer position)
	{
		Objects.requireNonNull(access_key);
		
		_position = position;
	}
	
	// Only a State can set a Player's tickets
	public void setTicketCount(State.AccessKey access_key, Ticket ticket, Integer count)
	{
		Objects.requireNonNull(access_key);
		
		_tickets.put(ticket, count);
	}
}
