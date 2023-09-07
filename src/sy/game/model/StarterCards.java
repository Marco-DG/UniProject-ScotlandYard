package sy.game.model;

import java.util.LinkedList;
import java.util.Random;

import sy.Settings;

public class StarterCards
{
	private final Integer [] 			_starter_cards;
	private final LinkedList<Integer> 	_picked_cards;
	private final Random 				_rnd_gen;
	
	public StarterCards()
	{
		_starter_cards = Settings.STARTER_CARDS;
		
		_picked_cards = new LinkedList<Integer>();
		
		// Initialize the random generator
		_rnd_gen = new Random();
	}
	
	public Integer pickRandom()
	{
		Integer picked_card;
		
		do
		{
			// Get random index for the array '_starter_cards'
			int rnd_ix = _rnd_gen.nextInt(_starter_cards.length);
			
			picked_card = _starter_cards[rnd_ix];	
		}
		// Loop until we get a card that was not previously picked
		while (_picked_cards.contains(picked_card));
		
		// Add the picked card to the list of picked cards
		_picked_cards.add(picked_card);

		return picked_card;
	}
	
	
}