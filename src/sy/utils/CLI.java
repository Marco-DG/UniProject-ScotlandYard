package sy.utils;

import java.util.Scanner;

public class CLI
{
	//
	private final Scanner _scanner;
	
	public CLI()
	{
		_scanner = new Scanner(System.in);
	}
	
	public void writeOutput(String str)
	{
		System.out.print(str);
	}
	
	public String readInput()
	{
		String string_read;
		
		do
		{
			string_read = _scanner.nextLine();
		}
		while (string_read == "");
 
		return string_read;
	}
	
	public void readEmpty()
	{
		_scanner.nextLine();
	}
	
	public void clearConsole()
	{
	    try
	    {
	        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	    }
	    catch (final Exception e)
	    {
	        System.out.println(e.toString());
	    }
	}
}
