package sy.game.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import sy.Debugger;
import sy.Settings;

/*	The state of the game, described by a turn number and a list of players;
 * 	each player, as described in the Player class, have a name, a role, a position
 * 	and a map of tickets.
 * 	
 *	Access Privileges:
 * 	- Only a Game can construct a State
 * 	- Only a Turn can invoke State alterations (Only a TurnControl can invoke State alterations trough a Turn)
 * 	- Only a State can construct a Player and invoke Player alterations
 * 
 */
public class State
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
    
    private int							_turn_number;
    private final List<Player> 			_players;
    private final List<Ticket>			_mrx_moves_ticket;
    private final List<Integer>			_mrx_moves_position;
    
    //////////////////////////////////////////////
    //		      GAME ACCESS ONLY		        //
    //////////////////////////////////////////////
	
    // Only a Game can construct a State
    public State(Game.AccessKey access_key)
    {
    	Objects.requireNonNull(access_key);
    	
    	_turn_number 			= 0;
    	_players 				= new LinkedList<Player>();
    	_mrx_moves_ticket 		= new LinkedList<>();
    	_mrx_moves_position 	= new LinkedList<>();
    }

    // Only a Game can initialize a State
	public void init(Game.AccessKey access_key, Integer num_players, List<String> players_name)
	{
		Objects.requireNonNull(access_key);
		
    	_turn_number = 0;
    	
    	_players.add(new Mrx(_access_key, players_name.get(0)));
    	
    	for (int i = 1; i < num_players; i++)
    	{
    		_players.add(new Detective(_access_key, players_name.get(i)));
    	}
    	
    	final var starter_cards = new StarterCards();

    	_players.get(0).setPosition(_access_key, starter_cards.pickRandom());
    	_players.get(0).setTicketCount(_access_key, Ticket.TAXI, 			Settings.MRX_NUM_TAXI_TICKETS);
    	_players.get(0).setTicketCount(_access_key, Ticket.BUS, 			Settings.MRX_NUM_BUS_TICKETS);
    	_players.get(0).setTicketCount(_access_key, Ticket.UNDERGROUND, 	Settings.MRX_NUM_UNDERGROUND_TICKETS);
    	_players.get(0).setTicketCount(_access_key, Ticket.DOUBLE, 			Settings.MRX_NUM_DOUBLE_TICKETS);
    	_players.get(0).setTicketCount(_access_key, Ticket.BLACK, 			_players.size() -1);
    	
    	for (int i = 1; i < _players.size(); i++)
    	{
        	_players.get(i).setPosition(_access_key, starter_cards.pickRandom());
        	_players.get(i).setTicketCount(_access_key, Ticket.TAXI, 			Settings.DETECTIVE_NUM_TAXI_TICKETS);
        	_players.get(i).setTicketCount(_access_key, Ticket.BUS, 			Settings.DETECTIVE_NUM_BUS_TICKETS);
        	_players.get(i).setTicketCount(_access_key, Ticket.UNDERGROUND, 	Settings.DETECTIVE_NUM_UNDERGROUND_TICKETS);
    	}		
	}
	
    //////////////////////////////////////////////
    //		  	   TURN ACCESS ONLY		 	    //
    //////////////////////////////////////////////
	
	// Only Turn can invoke State alterations
	public void turnPlayerUseTicket(Turn.AccessKey access_key, Ticket ticket)
	{
		Objects.requireNonNull(access_key);
		
		final var player 	= getTurnPlayer(access_key);
		final var mrx 		= _getMrx();
		
		if (player.isDetective())
		{
			player.setTicketCount(_access_key, ticket, player.getTicketCount(ticket) -1);
			mrx.setTicketCount(_access_key, ticket, mrx.getTicketCount(ticket) +1);
		}
		else if (player.isMrx())
		{
			mrx.setTicketCount(_access_key, ticket, mrx.getTicketCount(ticket) -1);
			
			if (ticket != Ticket.DOUBLE) _mrx_moves_ticket.add(ticket);
		}
	}
	
	// Only Turn can invoke State alterations
	public void turnPlayerMove(Turn.AccessKey access_key, Integer position)
	{
		Objects.requireNonNull(access_key);

		final var player = getTurnPlayer(access_key);

		player.setPosition(_access_key, position);
		
		if (player.isMrx())
		{
			_mrx_moves_position.add(position);
		}
	}
	
	
	// Only Turn can alter a State
	public void setTurnNumber(Turn.AccessKey access_key, int turn_number)
	{
		Objects.requireNonNull(access_key);

		_turn_number = turn_number;
	}
	
    // get interfaced Turn Player // TODO
    public Player getTurnPlayer(Turn.AccessKey access_key)
    {
    	Objects.requireNonNull(access_key);

    	return _players.get((_turn_number -1) % _players.size());
    }

    //////////////////////////////////////////////
    //				PUBLIC METHODS				//
    //////////////////////////////////////////////
    
    public int getTurnNumber()
    {
    	return _turn_number;
    }
    
    public List<Player> getDetectives() // TODO interface?
    {
    	return Collections.unmodifiableList(_players.subList(1, _players.size()));
    }
    
    public boolean hasMrxBeenCaught()
    {
    	for (var d :  getDetectives())
    	{
    		if (d.getPosition().intValue() == _getMrx().getPosition().intValue())
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean canDetectivesMove()
    {
    	boolean ret = false;
    	for (var d : getDetectives())
    	{
    		if (canPlayerMove(d))
    			ret = true;
    	}
    	return ret;
    }

	public boolean canPlayerMove(Player player)
	{
		return canPlayerUseTicket(player, Ticket.TAXI) || canPlayerUseTicket(player, Ticket.BUS) || canPlayerUseTicket(player, Ticket.UNDERGROUND);
	}
	
	public List<Ticket> getTicketsPlayerCanUse(Player player, boolean canUseDouble)
	{
		var tickets_lst = new LinkedList<Ticket>();
		for (Ticket t : Ticket.values())
		{
			if (canPlayerUseTicket(player, t)) tickets_lst.add(t);
		}
		
		if (!canUseDouble) tickets_lst.remove(Ticket.DOUBLE);

		if (tickets_lst.isEmpty())
			return null;
		else return tickets_lst;
	}
	
    public Set<Integer> getAvailableAdjacentPositionsByTransportTicket(Integer position, Ticket ticket)
    {	
    	if (!ticket.isTransportTicket()) return null;
    	
    	var adj_map = Board.ISTANCE.getAdjacentMap(position);

    	var inner_set = adj_map.get(ticket);
		if (inner_set == null) return null;

		var rem_lst = new LinkedList<Integer>();

		for(var p : getDetectives())
		{
			var pos = p.getPosition();
			rem_lst.add(pos);
		}

		rem_lst.forEach(inner_set::remove);

		if (inner_set.isEmpty())
			return null;
		else return inner_set;
    }

    public List<Ticket> getMrxMovesTicket()
    {
    	return Collections.unmodifiableList(_mrx_moves_ticket);
    }
    
    public List<Integer> getMrxMovesSharedPosition()
    {
    	final var mrx_shared_positions = new LinkedList<Integer>();
    	
    	for (int i=0; i < _mrx_moves_position.size(); i++)
    		for (var e : Settings.MRX_SHARE_POSITION_AT_MOVE)
    			if ( i+1 == e )
    				mrx_shared_positions.add(_mrx_moves_position.get(i));
		
    	return mrx_shared_positions;
    }

    //////////////////////////////////////////////
    //				PRIVATE METHODS				//
    //////////////////////////////////////////////

    private Player _getMrx()
    {
    	return _players.get(0);
    }

	private boolean canPlayerUseTicket(Player player, Ticket ticket)
	{
		if (ticket.isTransportTicket() && player.hasTicket(ticket) && getAvailableAdjacentPositionsByTransportTicket(player.getPosition(), ticket) != null) return true;
		else if (ticket.isDouble() && player.isMrx())
		{
			// For each transportation ticket type
			for (var t : Arrays.asList(Ticket.TAXI, Ticket.BUS, Ticket.UNDERGROUND))
			{
				// If I have such ticket
				if (player.hasTicket(t))
				{
					// Get all possible moves with that ticket
					var possible_moves = getAvailableAdjacentPositionsByTransportTicket(player.getPosition(), t);
					if (possible_moves == null) return false;

					// For each possible move
					for (var m : possible_moves)
					{
						// Again for each transportation ticket type
						for (var t_2 : Arrays.asList(Ticket.TAXI, Ticket.BUS, Ticket.UNDERGROUND))
						{
							// Again if I have such ticket
							// note: when checking the same ticket type as the prior for-loop,
							//       we do not check if we have one, but if we have two.
							if (player.hasTicket(t_2) && t != t_2 || player.getTicketCount(t) > 2)
							{
								// Again get all possible moves with that ticket
								var possible_moves_2 = getAvailableAdjacentPositionsByTransportTicket(m, t_2);

								// If there is again a possible move: we can use the DOUBLE ticket.
								if (possible_moves_2 != null) return true;
							}
						}
					}
				}
			}

			return false;
		}
		else if (ticket.isBlack() && player.isMrx())
		{
			// For each transportation ticket type
			for (var t : Arrays.asList(Ticket.TAXI, Ticket.BUS, Ticket.UNDERGROUND))
			{
				// If I have such ticket
				if (player.hasTicket(t))
				{
					// Get all possible moves with that ticket
					var possible_moves = getAvailableAdjacentPositionsByTransportTicket(player.getPosition(), t);

					// If there is a possible move: we can use the BLACK ticket.
					if (possible_moves != null) return true;
				}
			}
			return false;
		}
		else
			return false;
	}


	//////////////////////////////////////////////
	//			DEBUGGER ACCESS ONLY			//
	//////////////////////////////////////////////

	public List<Player> getPlayers(Debugger.MasterKey master_key)
	{
		Objects.requireNonNull(master_key);
		return _players;
	}


}
