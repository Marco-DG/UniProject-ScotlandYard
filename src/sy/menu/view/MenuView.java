package sy.menu.view;

import sy.Settings;
import sy.menu.model.Menu;
import sy.utils.Dialog;

public class MenuView
{
	//
	private final Menu _menu; // model
	private final Dialog _dialog;
	
	public MenuView(Menu menu)
	{
		_menu = menu;

		_dialog = new Dialog();
	}

	//
	public void displayMenu()
	{
		_dialog.say(Settings.SEPARATOR);
		_dialog.say(Settings.BEFORE_LINE + "### " + _menu.getName() + " Menu ###");
		_dialog.say(Settings.BEFORE_LINE);
		
		var menu_options = 	_menu.getOptions();
		int option_n = 0;
		for (var option : menu_options)
		{
			_dialog.say(Settings.BEFORE_LINE + ++option_n + ": " + option.name);
		}	
	}

	public Integer askForMenuOption()
	{
		_dialog.say(Settings.BEFORE_LINE);
		return _dialog.askForIntegerInRange(Settings.BEFORE_LINE + "Enter the menu option: ", 1, _menu.getNumOptions()) -1;
	}
}
