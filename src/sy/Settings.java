package sy;

public class Settings
{
	//
	public static final String 	SEPARATOR 							= "***************************************************************";
	public static final String 	BEFORE_LINE 						= "*  ";
	
	public static final String 	GAME_TITLE 							= "Scotland Yard";
	public static final String 	GAME_AUTHOR 						= "Marco De Groskovskaja";
	
	public static final Integer MIN_NUM_PLAYERS 					= 3;//1
	public static final Integer MAX_NUM_PLAYERS 					= 6;
	
	public static final String	BOARD_GRAPH_FILEPATH				= "./rsc/Board.txt";
	
	public static final Integer [] STARTER_CARDS 					= { 13, 26, 29, 34, 50, 53, 91, 94, 103, 112, 117, 132, 138, 141, 155, 174, 197, 198 };
	//public static final Integer [] STARTER_CARDS 					= {141, 133}; // TODO: to remove
	
	public static final Integer [] MRX_SHARE_POSITION_AT_MOVE		= { 3, 8, 13, 18 };
	
	public static final String 	SAVEFILE_FILEPATH					= "./rsc/Savefile.txt";
	
	public static final Integer MRX_NUM_TAXI_TICKETS 				= 4;
	public static final Integer MRX_NUM_BUS_TICKETS 				= 3;
	public static final Integer MRX_NUM_UNDERGROUND_TICKETS 		= 3;
	public static final Integer MRX_NUM_DOUBLE_TICKETS 				= 2;
	
	public static final Integer DETECTIVE_NUM_TAXI_TICKETS 			= 10;
	public static final Integer DETECTIVE_NUM_BUS_TICKETS 			= 8;
	public static final Integer DETECTIVE_NUM_UNDERGROUND_TICKETS 	= 4;
	
	public static final Integer LAST_TURN							= 22;
	
}
