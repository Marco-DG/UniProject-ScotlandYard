package sy.utils;

import java.util.List;

public class Dialog
{
	//
	private static CLI _cli = new CLI();
	private static StringParser _parser = new StringParser();
	
	public void say(String msg)
	{
		_cli.writeOutput(msg + '\n');
	}
	
	public Integer askForIntegerInRange(String msg, Integer range_from, Integer range_to)
	{
		String answer;
		do
		{
			_cli.writeOutput(msg);
			answer = _cli.readInput();
		}
		while(!_parser.isIntegerInRange(answer, range_from, range_to));
		
		return Integer.valueOf(answer);
	}
	
	public Integer askForIntegerInList(String msg, List<Integer> ints_list)
	{
		String answer;
		do
		{
			_cli.writeOutput(msg);
			answer = _cli.readInput();
		}
		while(!_parser.isIntegerInList(answer, ints_list));
		
		return Integer.valueOf(answer);
	}
	
	public String askForCharacterInList(String msg, List<String> chars_list)
	{
		String answer;
		do
		{
			_cli.writeOutput(msg);
			answer = _cli.readInput();
		}
		while(!_parser.isCharacterInList(answer, chars_list));
		
		return answer;
	}

	public String askForString(String msg)
	{
		_cli.writeOutput(msg);
		return _cli.readInput();
	}
	
	public void askForEnterKey(String msg)
	{
		_cli.writeOutput(msg);
		_cli.readEmpty();
	}
	
	public void clearConsole()
	{
		_cli.clearConsole();
	}
}
