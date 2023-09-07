package sy.utils;

import java.util.List;

public class StringParser
{
	//
	private static final String MAX_INT = "2147483647";
	
	public Boolean consistsOfOnlyNumbers(String str)
	{
		Boolean onlyNumbers = true;
		for (int i = 0; i < str.length() && onlyNumbers; i++)
		{
		    char c = str.charAt(i);        
		    if (!Character.isDigit(c))
		    {
		    	onlyNumbers = false;
		    }
		}
		
		return onlyNumbers;
	}
	
	public Boolean isInteger(String str)
	{
		if (str.length() > 10 || !consistsOfOnlyNumbers(str))
		{
			return false;
		}
		
		// if is length is 10, check if each digit is less then the MAX_INT digits
		if (str.length() == 10)
		{
			Boolean allLessThenMaxInt = true;
			for (int i = 0; i < str.length() && allLessThenMaxInt; i++)
			{
			    char c = str.charAt(i);        
			    char m = MAX_INT.charAt(i);
			    
			    int int_c = c - '0';
			    int int_m = m - '0';
			    
			    if (int_c > int_m)
			    {
			    	allLessThenMaxInt = false;
			    }
			}
			
			if (!allLessThenMaxInt)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public Boolean isIntegerInRange(String str, Integer range_from, Integer range_to)
	{
		if (isInteger(str))
		{
			Integer i = Integer.valueOf(str);
			
			if (i >= range_from && i <= range_to)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public Boolean isIntegerInList(String str, List<Integer> ints_list)
	{
		if (isInteger(str))
		{
			Integer i = Integer.valueOf(str);
			
			if (ints_list.contains(i))
			{
				return true;
			}
		}
		
		return false;
	}

	public Boolean isCharacterInList(String chr, List<String> chars_list)
	{
		// If it is a single character
		if (chr.length() == 1)
		{
			if (chars_list.contains(chr))
			{
				return true;
			}
		}
		return false;
	}
}
