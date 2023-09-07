package sy.game.model;


public enum Ticket
{
	TAXI, BUS, UNDERGROUND, BLACK, DOUBLE;
	
	public boolean isTaxi()
	{
		return this == TAXI;
	}
	
	public boolean isBus()
	{
		return this == BUS;
	}
	
	public boolean isUnderground()
	{
		return this == UNDERGROUND;
	}
	
	public boolean isBlack()
	{
		return this == BLACK;
	}
	
	public boolean isDouble()
	{
		return this == DOUBLE;
	}
	
	public boolean isTransportTicket()
	{
		return isTaxi() || isBus() || isUnderground();
	}
}
