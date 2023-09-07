package sy.game.view;

import java.util.List;

import sy.Settings;
import sy.game.model.Board;
import sy.game.model.Ticket;
import sy.game.model.Turn;
import sy.utils.Dialog;

public class TurnView
{
	//
	private Turn _turn; // model
	private final Dialog _dialog;
	
	public TurnView()
	{
		_turn = null;
		
		_dialog = new Dialog();
	}
	
	public void referenceTurn(Turn turn)
	{
		_turn = turn;
	}

	//
	public void displayTurnInfo()
	{	
		_dialog.say(Settings.SEPARATOR);
		_dialog.say(Settings.BEFORE_LINE + "Turn          " + _turn.getNumber());
		_dialog.say(Settings.BEFORE_LINE + "Player        " + _turn.getPlayer().name);
		_dialog.say(Settings.BEFORE_LINE + "Role          " + _turn.getPlayer().role);
		_dialog.say(Settings.BEFORE_LINE + "Position      " + _turn.getPlayer().getPosition());
	}
	
	public void displayAvailableTickets()
	{
		_dialog.say(Settings.SEPARATOR);
		_dialog.say(Settings.BEFORE_LINE + "Tickets:      " );
		_dialog.say(Settings.BEFORE_LINE + "\t- TAXI:         " + _turn.getPlayer().getTicketCount(Ticket.TAXI));
		_dialog.say(Settings.BEFORE_LINE + "\t- BUS:          " + _turn.getPlayer().getTicketCount(Ticket.BUS));
		_dialog.say(Settings.BEFORE_LINE + "\t- UNDERGROUND:  " + _turn.getPlayer().getTicketCount(Ticket.UNDERGROUND));
		if (_turn.getPlayer().isMrx())
		{
			_dialog.say(Settings.BEFORE_LINE + "\t- BLACK:        " + _turn.getPlayer().getTicketCount(Ticket.BLACK));
			_dialog.say(Settings.BEFORE_LINE + "\t- DOUBLE:       " + _turn.getPlayer().getTicketCount(Ticket.DOUBLE));
		}
	}

	public void displayAdjacentNodes()
	{
		final var adj_map = Board.ISTANCE.getAdjacentMap(_turn.getPlayer().getPosition());
		final var adj_map_keys = adj_map.keySet();
		
		_dialog.say(Settings.SEPARATOR);
		_dialog.say(Settings.BEFORE_LINE + "Adjacent Nodes:");
		if (adj_map_keys.contains(Ticket.TAXI))
		{
			_dialog.say(Settings.BEFORE_LINE + "\t- TAXI:         " + adj_map.get(Ticket.TAXI).toString());
		}
		if (adj_map_keys.contains(Ticket.BUS))
		{
			_dialog.say(Settings.BEFORE_LINE + "\t- BUS:          " + adj_map.get(Ticket.BUS).toString());
		}
		if (adj_map_keys.contains(Ticket.UNDERGROUND))
		{
			_dialog.say(Settings.BEFORE_LINE + "\t- UNDERGROUND:  " + adj_map.get(Ticket.UNDERGROUND).toString());
		}
	}
	
	public void displayPossibleMoves()
	{
		final var adj_map 		= Board.ISTANCE.getAdjacentMap(_turn.getPlayer().getPosition());
		final var adj_map_keys 	= adj_map.keySet();
		
		_dialog.say(Settings.SEPARATOR);
		_dialog.say(Settings.BEFORE_LINE + "Possible moves:");
		if (adj_map_keys.contains(Ticket.TAXI))
		{
			_dialog.say(Settings.BEFORE_LINE + "\t- TAXI:         " + _turn.state.getAvailableAdjacentPositionsByTransportTicket(_turn.getPlayer().getPosition(), Ticket.TAXI).toString());
		}
		if (adj_map_keys.contains(Ticket.BUS))
		{
			_dialog.say(Settings.BEFORE_LINE + "\t- BUS:          " + _turn.state.getAvailableAdjacentPositionsByTransportTicket(_turn.getPlayer().getPosition(), Ticket.BUS).toString());
		}
		if (adj_map_keys.contains(Ticket.UNDERGROUND))
		{
			_dialog.say(Settings.BEFORE_LINE + "\t- UNDERGROUND:  " + _turn.state.getAvailableAdjacentPositionsByTransportTicket(_turn.getPlayer().getPosition(), Ticket.UNDERGROUND).toString());
		}
	}
	
	public void displayDetectiveInformations()
	{
		_dialog.say(Settings.SEPARATOR);
		_dialog.say(Settings.BEFORE_LINE + "Detectives:");
		final var detectives = _turn.state.getDetectives();
		for (var d : detectives)
		{
			_dialog.say(Settings.BEFORE_LINE);
			_dialog.say(Settings.BEFORE_LINE + "\t------------------------------");
			_dialog.say(Settings.BEFORE_LINE + "\t" 					+ d.name );
			_dialog.say(Settings.BEFORE_LINE + "\t------------------------------");
			_dialog.say(Settings.BEFORE_LINE + "\tposition: " 			+ d.getPosition() );
			_dialog.say(Settings.BEFORE_LINE);
			_dialog.say(Settings.BEFORE_LINE + "\ttickets\t:");
			_dialog.say(Settings.BEFORE_LINE + "\t\tTAXI\t\t: " 		+ d.getTicketCount(Ticket.TAXI));
			_dialog.say(Settings.BEFORE_LINE + "\t\tBUS\t\t: " 			+ d.getTicketCount(Ticket.BUS));
			_dialog.say(Settings.BEFORE_LINE + "\t\tUNDERGROUND\t: " 	+ d.getTicketCount(Ticket.UNDERGROUND));
			_dialog.say(Settings.BEFORE_LINE);
		}
	}
	
	public void displayKnownMrxMoves()
	{
		_dialog.say(Settings.SEPARATOR);
		
		final var mrx_moves_position = _turn.state.getMrxMovesSharedPosition();
		final var mrx_moves_ticket = _turn.state.getMrxMovesTicket();
		
		if (mrx_moves_position.size() > 0)
		{
			_dialog.say(Settings.BEFORE_LINE + "Mrx last known positions:");
			
			for (int i=0; i< mrx_moves_position.size(); i++)
			{
				_dialog.say(Settings.BEFORE_LINE + "\tMove: " + Settings.MRX_SHARE_POSITION_AT_MOVE[i] + "\t\tPosition: " + mrx_moves_position.get(i).toString());
			}
			
			_dialog.say(Settings.BEFORE_LINE);	
		}

		_dialog.say(Settings.BEFORE_LINE + "Mrx moves:");
		for (int i=0; i< mrx_moves_ticket.size(); i++)
		{
			_dialog.say(Settings.BEFORE_LINE + "\tMove: " + (i+1) + "\t\tTicket: " + mrx_moves_ticket.get(i).toString());
		}

	}
	
	public Ticket askForTicketToUse(List<Ticket> tickets_player_can_use)
	{
		_dialog.say(Settings.SEPARATOR);

		// ### Message to display
		String msg = "Enter the ticket to use (";
		int i = 1;
		for (Ticket ticket_type : tickets_player_can_use)
		{
			msg += i + ": " + ticket_type.name() +", ";
			i++;
		}
		msg = msg.substring(0, msg.length() - 2); // Remove last ', '
		msg += "): ";
		
		// ### Ask for ticket to use
		Integer ticket_i = _dialog.askForIntegerInRange(Settings.BEFORE_LINE + msg, 1, tickets_player_can_use.size());
		Ticket ticket_to_use = tickets_player_can_use.get(ticket_i -1);

		//
		return ticket_to_use;
	}
	
	public Integer askForPositionInWhichMove(List<Integer> adj_list)
	{	
		return _dialog.askForIntegerInList(Settings.BEFORE_LINE + "Enter the node to which move " + adj_list.toString() + ": ", adj_list);
	}
	
	public void sayPlayerCannotMove()
	{
		_dialog.say(Settings.BEFORE_LINE + "Player cannot move. Skip turn.");
	}
	
	public void sayPlayerTicketsHaveTerminated()
	{
		_dialog.say(Settings.BEFORE_LINE + "Tickets have terminated.");
	}
	
	public void pressEnterToMenu()
	{
		_dialog.say(Settings.BEFORE_LINE);
		_dialog.askForEnterKey(Settings.BEFORE_LINE + "Press ENTER to return the menu");
	}

}