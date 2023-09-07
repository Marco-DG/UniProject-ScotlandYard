package sy.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import sy.game.model.Board;
import sy.game.model.Ticket;

public class BoardLoader
{
    //////////////////////////////////////////////
    //				  ACCESS KEY				//
    //////////////////////////////////////////////
	
	// Friendship mechanism:  https://stackoverflow.com/a/18634125/8411453
    public static final class AccessKey
    {
    	private AccessKey() {}
    }
    private static final AccessKey access_key = new AccessKey();
    
    //////////////////////////////////////////////
    //				PUBLIC METHODS				//
    //////////////////////////////////////////////
    
	public static void load(Board board, String filepath)
	{
		try
		{	
			final var text = Files.readAllLines(Paths.get(filepath));					// Text-file -> List of strings
			
			for (int i = 0; i < text.size(); i++)										// For each row in the text-file
			{
				String [] args = text.get(i).split(" ");								// Split row and get "type", 'from_node' and 'to_node'
				
				String 	str_type 	= args[0];
				Integer from_node 	= Integer.valueOf(args[1]);
				Integer to_node 	= Integer.valueOf(args[2]);

				Ticket ticket;
				if 		(str_type.equals("T"))		ticket = Ticket.TAXI;
				else if (str_type.equals("B")) 		ticket = Ticket.BUS;
				else if (str_type.equals("U"))  	ticket = Ticket.UNDERGROUND;
				else 								throw new IOException();
				
				board.setLink(access_key, from_node, to_node, ticket);
			}
		}
		catch (Exception e)
		{
			System.out.println("BoardLoader - Error: cannot load the file '" + filepath +"'");
			System.exit(0);
		}
	}
}