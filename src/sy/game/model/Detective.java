package sy.game.model;

/*	A Detective is an extension of a Player //TODO: Check if necessary
 * 	
 */
public class Detective extends Player
{
    //////////////////////////////////////////////
    //		      STATE ACCESS ONLY		        //
    //////////////////////////////////////////////

    // Only a State can construct a Detective
	public Detective(State.AccessKey access_key, String name)
	{
		super(access_key, name, Role.DETECTIVE);
	}
}
