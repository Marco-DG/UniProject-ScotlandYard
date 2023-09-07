package test;

import org.junit.jupiter.api.Test;
import sy.Debugger;
import sy.command.game.NewGameCommand;
import sy.game.control.GameControl;
import sy.game.control.TurnControl;
import sy.game.model.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StateTest
{
    /*
     *  Vengono testati i metodi:
     *
     *  	State:
     *          Set<Integer> getAvailableAdjacentPositionsByTransportTicket(Integer position, Ticket ticket)
     *  		List<Ticket> getTicketsPlayerCanUse(Player player, boolean canUseDouble)
     *           boolean canPlayerUseTicket(Player player, Ticket ticket)
     *
     * 		Board:
     * 			Map<Ticket, Set<Integer>> getAdjacentMap(Integer node)
     *
     * 		Player:
     * 			boolean hasTicket(Ticket ticket)
     *
     *
     * 		e una serie di altri metodi triviali.
     *
     */
    //////////////////////////////////////////////
    //			  GLOBAL VARs/CONSTs			//
    //////////////////////////////////////////////
    final Board board = Board.ISTANCE;
    final int num_nodes = 199;                  // assume the graph to be composed by 199 nodes from 1 to 199
    final Random rnd_gen = new Random();

    final GameControl game_control;
    final Game game;
    final State state;

    //////////////////////////////////////////////
    //				CONSTRUCTOR				    //
    //////////////////////////////////////////////
    StateTest()
    {
        // Create a game control
        game_control = new GameControl();

        // Use the Debugger class to gain access to the game state
        game  = game_control.getGame(Debugger.master_key);
        state = game.getState(new GameControl.AccessKey(Debugger.master_key));
    }
    //////////////////////////////////////////////
    //				UTILITY CODE				//
    //////////////////////////////////////////////

    int _getRandomNode()
    {
        return rnd_gen.nextInt(num_nodes) + 1;
    }


    //////////////////////////////////////////////
    //				    TESTS				    //
    //////////////////////////////////////////////
    /*
        Test 01: mrx's adjacent nodes are all occupied by detectives
    */
    @Test
    void Test01()
    {
        // Get adjacent map of random node
        int random_node = _getRandomNode();
        var adjacent_map = board.getAdjacentMap(random_node);

        var adjacent_nodes = new HashSet<Integer>();
        for (var ticket : adjacent_map.keySet())
        {
            adjacent_nodes.addAll( adjacent_map.get(ticket) );
        }

        // Initialize a game with a number of players equal to the number of adjacent nodes +1
        int num_adj_nodes = adjacent_nodes.size();
        int num_players = num_adj_nodes +1;

        List<String> players_name = new LinkedList<String>();
        for (int i=0; i<num_players; i++) players_name.add("");

        game.init(new GameControl.AccessKey(Debugger.master_key), num_players, players_name);

        // Assert that we have the same number of adjacent nodes and players
        var players_in_game = state.getPlayers(Debugger.master_key);
        assertEquals(num_players, players_in_game.size());

        // Put mrx at the random node, and the detectives ar the adjacent nodes
        var mrx = players_in_game.get(0);
        // Assert that players[0] is mrx
        assertTrue(mrx.isMrx());

        mrx.setPosition(new State.AccessKey(Debugger.master_key), random_node);

        var it = adjacent_nodes.iterator();
        for (int i=1; i<num_players; i++)
        {
            var p = players_in_game.get(i);
            // Assert that all the remaining players are detectives
            assertTrue(p.isDetective());

            p.setPosition(new State.AccessKey((Debugger.master_key)), it.next());
        }

        // Assert that there are no available adjacent positions from the random node
        assertNull(
                state.getAvailableAdjacentPositionsByTransportTicket(mrx.getPosition(), Ticket.TAXI)
        );

        assertNull(
                state.getAvailableAdjacentPositionsByTransportTicket(mrx.getPosition(), Ticket.BUS)
        );

        assertNull(
                state.getAvailableAdjacentPositionsByTransportTicket(mrx.getPosition(), Ticket.UNDERGROUND)
        );

        // Assert that mrx cannot use any tickets
        assertNull(
                state.getTicketsPlayerCanUse(mrx, false)
        );
    }

}