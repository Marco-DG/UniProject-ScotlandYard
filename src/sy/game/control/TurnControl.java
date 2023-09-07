package sy.game.control;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

import sy.Debugger;
import sy.command.turn.AdjacentNodesCommand;
import sy.command.turn.AvailableTicketsCommand;
import sy.command.turn.DetectiveInformationsCommand;
import sy.command.turn.MrxInformationsCommand;
import sy.command.turn.PlayTicketCommand;
import sy.command.turn.PossibleMovesCommand;
import sy.command.turn.QuitToGameMenuCommand;
import sy.game.model.Ticket;
import sy.game.model.Turn;
import sy.game.view.TurnView;
import sy.menu.control.TurnMenuControl;

/*
 *	Access Privileges:
 *	- Only a GameControl can construct a TurnControl and invoke TurnControl alterations
 * 	- Only a TurnControl can construct a Turn and invoke Turn alterations
 *  - A TurnControl can require non privileged access to the GameControl's underlying game model
 */
public class TurnControl
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
    //				  MVC PATTERN				//
    //////////////////////////////////////////////
    
	private Turn _turn; // model
	private final TurnView _view;

    //////////////////////////////////////////////
    //				  GAMECONTROL				//
    //////////////////////////////////////////////
    
	//private final
	//IGameControl_TurnControl _game_control; 	// reference to interfaced GameControl

	private final GameControl _game_control;	// TODO
	
    //////////////////////////////////////////////
    //	    		      MENU                	//
    ////////////////////////////////////////////// 
	
	private final TurnMenuControl _menu;
	
    //////////////////////////////////////////////
    //	    	    TURNMENUCONTROL             //
	//				  ACCESS ONLY				//
    //////////////////////////////////////////////  
	
	public Turn getTurn(TurnMenuControl.AccessKey access_key)
	{
		Objects.requireNonNull(access_key);
		
		return _turn;
	}
	
    //////////////////////////////////////////////
	//		   GAMECONTROL ACCESS ONLY          //
	//////////////////////////////////////////////
	
	// Only a GameControl can construct a TurnControl
	public TurnControl(GameControl.AccessKey access_key, GameControl game_control) // IGameControl_TurnControl game_control) TODO
	{
		Objects.requireNonNull(access_key);
		
		_turn = null; 							// instantiated trough TurnControl.new_turn()
		_view = new TurnView();
		
		_game_control = game_control;
		
		_menu = new TurnMenuControl(this);
	}
	
	public void new_turn(GameControl.AccessKey access_key)
	{
		Objects.requireNonNull(access_key);
		
		final var state = _game_control.getGameState(_access_key);

		_turn = new Turn(_access_key, state);
		_view.referenceTurn(_turn);
		_menu.late_configuration(); // we configure the Menu only after we have referenced the Turn
	}
	
	public void info(GameControl.AccessKey access_key)
	{
		Objects.requireNonNull(access_key);
		
		_info();
	}
	
	public void menu(GameControl.AccessKey access_key)
	{
		Objects.requireNonNull(access_key);

		_menu();
	}
	
    //////////////////////////////////////////////
    //		 COMMAND PATTERN - COMMANDS			//
    //////////////////////////////////////////////
	
	// Only a PlayTicketCommand can invoke the execution of the turn logic
	public void playTicket(PlayTicketCommand.AccessKey access_key)
	{
		Objects.requireNonNull(access_key);
		
		//final var state = _turn.state;//getState(_access_key); TODO
		
		_game_control.title();
		_info();

		if (_turn.state.canPlayerMove(_turn.getPlayer()))
		{
			var tickets_player_can_use = _turn.state.getTicketsPlayerCanUse(_turn.getPlayer(), true);
			
			if (tickets_player_can_use != null)
			{
				
				Ticket ticket_to_use = _view.askForTicketToUse(tickets_player_can_use); // The first ticket can be a DOUBLE
				
				if (ticket_to_use != null) // Cannot use any ticket
				{
					Integer current_position 		= _turn.getPlayer().getPosition();
					_askForPositionInWhichMove(current_position, ticket_to_use); // Play Ticket Logic
					
					//_turn.useTicket(_access_key, ticket_to_use); // TODO: move to _askForPositionInWhichMove
					//_turn.move(_access_key, position_in_which_move); TODO: move to _askForPositionInWhichMove
				}

			}
			else { _view.sayPlayerTicketsHaveTerminated(); }
		}
		else { _view.sayPlayerCannotMove(); }
	}
	
	// Only a AvailableTicketsCommand can invoke the display of the possible moves
	public void possibleMoves(PossibleMovesCommand.AccessKey access_key)
	{
		Objects.requireNonNull(access_key);
	
		_game_control.title();
		_info();
		_view.displayPossibleMoves();
		_waitEnter();
		_game_control.title();
		_info();
		_menu();
	}
	
	// Only a AvailableTicketsCommand can invoke the display of the available tickets
	public void availableTickets(AvailableTicketsCommand.AccessKey access_key)
	{
		Objects.requireNonNull(access_key);

		_game_control.title();
		_info();
		_view.displayAvailableTickets();
		_waitEnter();
		_game_control.title();
		_info();
		_menu();
	}
	
	// Only a DetectivesInformationCommand can invoke the display of the detectives info
	public void detectiveInformations(DetectiveInformationsCommand.AccessKey access_key)
	{
		Objects.requireNonNull(access_key);

		_game_control.title();
		_info();
		_view.displayDetectiveInformations();
		_waitEnter();
		_game_control.title();
		_info();
		_menu();
	}
	
	// Only a AvailableTicketsCommand can invoke the display of the adjacent nodes
	public void adjacentNodes(AdjacentNodesCommand.AccessKey access_key)
	{
		Objects.requireNonNull(access_key);
		
		_game_control.title();
		_info();
		_view.displayAdjacentNodes();
		_waitEnter();
		_game_control.title();
		_info();
		_menu();
	}
	
	public void knownMrxMoves(MrxInformationsCommand.AccessKey access_key)
	{
		Objects.requireNonNull(access_key);
		
		_game_control.title();
		_info();
		_view.displayKnownMrxMoves();
		_waitEnter();
		_game_control.title();
		_info();
		_menu();
		
	}

	
	// Only a QuitToGameMenuCommand can invoke a quit to game menu
	public void quitToGameMenu(QuitToGameMenuCommand.AccessKey access_key)
	{
		Objects.requireNonNull(access_key);
	
		_game_control.menu();
	}
	
    //////////////////////////////////////////////
    //			   PRIVATE METHODS				//
    //////////////////////////////////////////////
	
	private void _waitEnter()
	{
		_view.pressEnterToMenu();
	}

	private void _info()
	{
		_view.displayTurnInfo();
	}
	
	private void _menu()
	{
		_menu.interaction();
	}
	
	private Integer _askForPositionInWhichMove(Integer current_position, Ticket ticket_to_use)
	{
		Integer position_in_which_move;
		
		//final var state = _turn.getState(_access_key); TODO

		if (ticket_to_use.isTransportTicket())
		{
			Set<Integer> possible_moves = _turn.state.getAvailableAdjacentPositionsByTransportTicket(current_position, ticket_to_use);
			
			//Convert Set to List
			var adj_lst = new LinkedList<Integer>(possible_moves);
			
			position_in_which_move = _view.askForPositionInWhichMove(adj_lst);

			_turn.useTicket(_access_key, ticket_to_use); // Use Ticket
			_turn.move(_access_key, position_in_which_move); // Move
		}
		else if(ticket_to_use == Ticket.BLACK)
		{
			// Join possible adj positions
			var all_possible_moves = new LinkedList<Integer>();
			
			for (var t : Arrays.asList(Ticket.TAXI, Ticket.BUS, Ticket.UNDERGROUND))
			{
				var possible_moves = _turn.state.getAvailableAdjacentPositionsByTransportTicket(current_position, t);
				if (possible_moves != null)
				{
					all_possible_moves.addAll(possible_moves);
				}
			}

			position_in_which_move = _view.askForPositionInWhichMove(all_possible_moves);
			
			_turn.useTicket(_access_key, ticket_to_use); // Use Ticket
			_turn.move(_access_key, position_in_which_move); // Move
		}
		else if (ticket_to_use == Ticket.DOUBLE)
		{
			_turn.useTicket(_access_key, ticket_to_use);

			// Ticket 1
			var tickets_player_can_use = _turn.state.getTicketsPlayerCanUse(_turn.getPlayer(), false); // This ticket cannot be a double
			Ticket first_ticket = _view.askForTicketToUse(tickets_player_can_use); // Ask for the first position to which move
			Integer first_position = _askForPositionInWhichMove(current_position, first_ticket); // Ask for the first position to which move
			
			// Ticket 2
			tickets_player_can_use = _turn.state.getTicketsPlayerCanUse(_turn.getPlayer(), false); // This ticket cannot be a double
			Ticket second_ticket = _view.askForTicketToUse(tickets_player_can_use); // Ask for the second ticket to use 
			Integer second_position = _askForPositionInWhichMove(first_position, second_ticket); // Ask for the second position to which move
			
			position_in_which_move = second_position;
		}
		else { position_in_which_move = null; /* BAD: This should never happen */ }

		return position_in_which_move;
	}

}
