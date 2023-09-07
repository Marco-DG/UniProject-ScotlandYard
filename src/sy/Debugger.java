package sy;
/*
 *  The Debugger class has privileged access to all the game components
 */
public class Debugger
{
    //////////////////////////////////////////////
    //				  ACCESS KEY				//
    //////////////////////////////////////////////

    // Friendship mechanism:  https://stackoverflow.com/a/18634125/8411453
    public static class MasterKey
    {
        private MasterKey() {}
    }
    public static final MasterKey master_key = new MasterKey();
}
