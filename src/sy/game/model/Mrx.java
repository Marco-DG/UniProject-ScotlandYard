package sy.game.model;

/*	A Mrx is an extension of a Player //TODO: Check if necessary
 * 	
 */
public class Mrx extends Player
{
    //////////////////////////////////////////////
    //		      STATE ACCESS ONLY		        //
    //////////////////////////////////////////////

    // Only a State can construct a Mrx
	public Mrx(State.AccessKey access_key, String name)
	{
		super(access_key, name, Role.MRX);
	}
}
