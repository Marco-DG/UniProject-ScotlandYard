package sy.menu.model;

import sy.command.ICommand;

public class Option
{
	//
	public final String 	name;
	public final ICommand 	command;
	
	public Option(String name, ICommand command)
	{
		this.name 		= name;
		this.command 	= command;
	}
}
