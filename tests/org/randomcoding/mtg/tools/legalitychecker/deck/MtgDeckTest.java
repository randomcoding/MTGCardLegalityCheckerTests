package org.randomcoding.mtg.tools.legalitychecker.deck;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Tym The Enchanter
 */
public class MtgDeckTest
{
	private static final String ARCHITECTS_OF_WILL_NAME = "Architects of Will";
	private static final int ARCHITECTS_OF_WILL_MULTIVERSE_ID = 179597;

	@Test
	public void testAddtionOfCardWithoutMultiverseIdAddsTheCorrectId() throws Exception
	{
		MtgDeck deck = getDeck();
		deck.add(ARCHITECTS_OF_WILL_NAME, 4);
		assertEquals(179597, deck.getCardData().iterator().next().getMultiverseIds().iterator().next().intValue());
	}

	private MtgDeck getDeck()
	{
		MtgDeck deck = new MtgDeck("Deck");
		return deck;
	}

	@Test
	public void testAdditionOfCardWithZeroCountDoesNotAddCard() throws Exception
	{
		MtgDeck deck = getDeck();
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 0);
		assertTrue(deck.getCardData().isEmpty());
	}

	@Test
	public void testAdditionOfCardWithInvalidMultiverseIdDoesNotAddCard() throws Exception
	{
		MtgDeck deck = getDeck();
		deck.add(ARCHITECTS_OF_WILL_NAME, 0, 4);
		assertTrue(deck.getCardData().isEmpty());
		deck.add(ARCHITECTS_OF_WILL_NAME, -1, 4);
		assertTrue(deck.getCardData().isEmpty());
	}
}
