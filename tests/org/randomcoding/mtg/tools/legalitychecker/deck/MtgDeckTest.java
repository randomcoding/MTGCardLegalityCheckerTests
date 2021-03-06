/*******************************************************************************
 * Copyright (c) 08/09/2009 Tym The Enchanter - tymtheenchanter@randomcoding.co.uk
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Tym The Enchanter - initial API and implementation
 *******************************************************************************/
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

	@Test
	public void testTryingToAddMoreThanFourCardsToDeckResultsInAdditionalCardsCardsBeingPresent() throws Exception
	{
		MtgDeck deck = getDeck();
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 5);
		assertEquals(5, deck.getCardCount(ARCHITECTS_OF_WILL_NAME));
	}

	@Test
	public void testIncrementingTheCountOfCardInADeckAboveFourDeckResultsInAdditionalCardsCardsBeingPresent() throws Exception
	{
		MtgDeck deck = getDeck();
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 3);
		assertEquals(3, deck.getCardCount(ARCHITECTS_OF_WILL_NAME));
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 1);
		assertEquals(4, deck.getCardCount(ARCHITECTS_OF_WILL_NAME));
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 1);
		assertEquals(5, deck.getCardCount(ARCHITECTS_OF_WILL_NAME));
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 1);
		assertEquals(6, deck.getCardCount(ARCHITECTS_OF_WILL_NAME));
	}

	@Test
	public void testGetCardCountForCardNotPresentInDeck() throws Exception
	{
		assertEquals(-1, getDeck().getCardCount("Megrim"));
	}

	@Test
	public void testGetDeckName() throws Exception
	{
		assertEquals("Deck", getDeck().getDeckName());
	}
}
