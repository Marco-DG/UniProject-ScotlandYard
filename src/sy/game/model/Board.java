package sy.game.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import sy.Settings;
import sy.utils.BoardLoader;

// Joshua Bloch' Singleton approach: http://www.youtube.com/watch?v=pi_I7oD_uGI#t=28m50s
public enum Board
{
	//
	ISTANCE;
	
	private final Map<Integer, Map<Ticket, Set<Integer>>> _board;
	
    //////////////////////////////////////////////
    //			 PRIVATE CONSTRUCTOR			//
    //////////////////////////////////////////////
	
	private Board()
	{
		_board = new HashMap<Integer, Map<Ticket, Set<Integer>>>();
		BoardLoader.load(this, Settings.BOARD_GRAPH_FILEPATH);
	}
	
    //////////////////////////////////////////////
    //				PUBLIC METHODS				//
    //////////////////////////////////////////////

	public Map<Ticket, Set<Integer>> getAdjacentMap(Integer node)
	{
		return Collections.unmodifiableMap(_board.get(node));
	}
	
    //////////////////////////////////////////////
    //		  BOARD BUILDER ACCESS ONLY		    //
    //////////////////////////////////////////////
	
	// Only a BoardBuilder can modify the board
	public void setLink(BoardLoader.AccessKey access_key, Integer from_node, Integer to_node, Ticket ticket)
	{
		Objects.requireNonNull(access_key);
		
		_setLink(from_node, to_node, ticket);
		_setLink(to_node, from_node, ticket);
	}
	
    //////////////////////////////////////////////
    //				PRIVATE METHODS				//
    //////////////////////////////////////////////

    public void _setLink(Integer from_node, Integer to_node, Ticket ticket)
    {	
    	var inner_map = _board.get(from_node);
    	if (inner_map == null)
    	{
    		_board.put(from_node, new HashMap<Ticket, Set<Integer>>());
    		inner_map = _board.get(from_node);
    	}
    	
    	var inner_set = inner_map.get(ticket);
    	if (inner_set == null)
    	{
    		inner_map.put(ticket, new HashSet<Integer>());
    		inner_set = inner_map.get(ticket);
    	}
    	
    	inner_set.add(to_node);
    }
}
