package sy.menu.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import sy.command.ICommand;

public class Menu
{
	//
	private String _name;
	private List<Option> _options;
	
	public Menu()
	{
		_name = "";
		_options = new LinkedList<Option>();
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public void executeOption(Integer number)
	{
		_options.get(number).command.execute();
	}
	
	public String getName()
	{
		return _name;
	}

	public void addOption(String name, ICommand command)
	{
		_options.add(new Option(name, command));
	}
	
	
	public List<Option> getOptions()
	{
		return Collections.unmodifiableList(_options);
	}
	
	public Integer getNumOptions()
	{
		return _options.size();
	}
	
	public void clearOptions()
	{
		_options = new LinkedList<Option>();
	}
}
