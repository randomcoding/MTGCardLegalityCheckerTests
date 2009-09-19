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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.randomcoding.mtg.tools.enumerations.MagicDeckFormat;
import org.randomcoding.mtg.tools.enumerations.MagicLegalityRestriction;

/**
 * @author Tym The Enchanter
 */
public class DeckLegalityCalculatorTest
{
	private DeckLegalityCalculator legalityCalculator;

	private static final String ARCHITECTS_OF_WILL_NAME = "Architects of Will"; // Legal in all formats (13/09/2009)
	private static final int ARCHITECTS_OF_WILL_MULTIVERSE_ID = 179597;
	private static final String MEGRIM_NAME = "Megrim"; // Legal in all formats (13/09/2009)
	private static final int MEGRIM_MULTIVERSE_ID = 189904;
	private static final String TERROR_NAME = "Terror"; // Legal in Extended, Vintage and Legacy
	private static final int TERROR_MULTIVERSE_ID = 135199;
	private static final String ANKH_OF_MISHRA_NAME = "Ankh of Mishra"; // Legal in Vintage and Legacy Only
	private static final int ANKH_OF_MISHRA_MULTIVERSE_ID = 1;
	private static final String BAZAAR_OF_BAGHDAD_NAME = "Bazaar of Baghdad"; // Legal in Vintage, Banned in Legacy, not present in Standard and Extended
	private static final int BAZAR_OF_BAGHDAD_MULTIVERSE_ID = 984;
	private static final String WORLDGORGER_DRAGON_NAME = "Worldgorger Dragon";
	private static final int WORLDGORGER_DRAGON_MULTIVERSE_ID = 35056;
	private static final String FACT_OR_FICTION_NAME = "Fact or Fiction"; // Restricted in Vintage, legal in Legacy
	private static final int FACT_OR_FICTION_MULTIVERSE_ID = 185819;
	private static final String GIFTS_UNGIVEN_NAME = "Gifts Ungiven"; // Restricted in Vintage, legal in Legacy
	private static final int GIFTS_UNGIVEN_MULTIVERSE_ID = 194971;

	@Test(expected = CloneNotSupportedException.class)
	public void testCloneThrowsCorrectException() throws Exception
	{
		DeckLegalityCalculator.getDeckLegalityCalculator().clone();
	}

	@Test
	public void testLegalityForDeckLegalInAllFormats() throws Exception
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> expectedLegality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();
		expectedLegality.put(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		expectedLegality.put(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		expectedLegality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		expectedLegality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		assertEquals(expectedLegality, getLegalityCalculator().checkDeckLegality(getDeckLegalInAllFormats()));
	}

	@Test
	public void testLegalityForDeckLegalInAllFormatsExceptStandard() throws Exception
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> expectedLegality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();
		expectedLegality.put(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		expectedLegality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		expectedLegality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		assertEquals(expectedLegality, getLegalityCalculator().checkDeckLegality(getDeckLegalInAllFormatsExceptStandard()));
	}

	@Test
	public void testLegalityForDeckLegalInAllFormatsExceptStandardAndExtended() throws Exception
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> expectedLegality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();
		expectedLegality.put(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		expectedLegality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		assertEquals(expectedLegality, getLegalityCalculator().checkDeckLegality(getDeckLegalInAllFormatsExceptStandardAndExtended()));
	}

	@Test
	public void testLegalityForDeckWithSingleBannedCard() throws Exception
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> expectedLegality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();
		expectedLegality.put(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.BANNED);
		expectedLegality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		assertEquals(expectedLegality, getLegalityCalculator().checkDeckLegality(getDeckWithSingleBannedCard()));
	}

	@Test
	public void testLegalityForDeckWithMultipleSingleBannedCards() throws Exception
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> expectedLegality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();
		expectedLegality.put(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.BANNED);
		expectedLegality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		assertEquals(expectedLegality, getLegalityCalculator().checkDeckLegality(getDeckWithMultipleSingleBannedCards()));
	}

	@Test
	public void testLegalityForDeckWithSingleRestrictedCard() throws Exception
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> expectedLegality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();
		expectedLegality.put(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		expectedLegality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		assertEquals(expectedLegality, getLegalityCalculator().checkDeckLegality(getDeckWithSingleRestrictedCard()));
	}

	@Test
	public void testLegalityForDeckWithMultipleCopiesOfSingleRestrictedCard() throws Exception
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> expectedLegality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();
		expectedLegality.put(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		expectedLegality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.RESTRICTED);
		assertEquals(expectedLegality, getLegalityCalculator().checkDeckLegality(getDeckWithMultipleCopiesOfSingleRestrictedCard()));
	}

	@Test
	public void testLegalityForDeckWithMultipleSinlgeRestrictedCards() throws Exception
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> expectedLegality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();
		expectedLegality.put(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		expectedLegality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		assertEquals(expectedLegality, getLegalityCalculator().checkDeckLegality(getDeckWithMultipleSingleRestrictedCards()));
	}

	@Test
	public void testGetLegalityForDeckWithMoreThanFourCopiesOfACard() throws Exception
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> expectedLegality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();
		expectedLegality.put(MagicDeckFormat.STANDARD, MagicLegalityRestriction.ILLEGAL);
		expectedLegality.put(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.ILLEGAL);
		expectedLegality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.ILLEGAL);
		expectedLegality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.ILLEGAL);
		assertEquals(expectedLegality, getLegalityCalculator().checkDeckLegality(getDeckLegalInAllFormatsExceptThatItHasFiveCopiesOfOneCard()));
	}

	private MtgDeck getDeckLegalInAllFormats()
	{
		MtgDeck deck = new MtgDeck("Deck Legal In All Formats");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 4);

		return deck;
	}

	private MtgDeck getDeckLegalInAllFormatsExceptThatItHasFiveCopiesOfOneCard()
	{
		MtgDeck deck = new MtgDeck("Deck Legal In All Formats");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 5);

		return deck;
	}

	private MtgDeck getDeckLegalInAllFormatsExceptStandard()
	{
		MtgDeck deck = new MtgDeck("Deck Legal In All Formats Except Standard");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(TERROR_NAME, TERROR_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 4);

		return deck;
	}

	private MtgDeck getDeckLegalInAllFormatsExceptStandardAndExtended()
	{
		MtgDeck deck = new MtgDeck("Deck Legal In All Formats Except Standard and Extended");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(TERROR_NAME, TERROR_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 4);
		deck.add(ANKH_OF_MISHRA_NAME, ANKH_OF_MISHRA_MULTIVERSE_ID, 4);

		return deck;
	}

	private MtgDeck getDeckWithSingleBannedCard()
	{
		MtgDeck deck = new MtgDeck("Deck With Single Banned Card");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(TERROR_NAME, TERROR_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 4);
		deck.add(ANKH_OF_MISHRA_NAME, ANKH_OF_MISHRA_MULTIVERSE_ID, 4);
		deck.add(BAZAAR_OF_BAGHDAD_NAME, BAZAR_OF_BAGHDAD_MULTIVERSE_ID, 1);

		return deck;
	}

	private MtgDeck getDeckWithSingleRestrictedCard()
	{
		MtgDeck deck = new MtgDeck("Deck With Single Restricted Card");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(TERROR_NAME, TERROR_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 4);
		deck.add(ANKH_OF_MISHRA_NAME, ANKH_OF_MISHRA_MULTIVERSE_ID, 4);
		deck.add(FACT_OR_FICTION_NAME, FACT_OR_FICTION_MULTIVERSE_ID, 1);

		return deck;
	}

	private MtgDeck getDeckWithMultipleCopiesOfSingleRestrictedCard()
	{
		MtgDeck deck = new MtgDeck("Deck With Multiple Copies of Single Restricted Card");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(TERROR_NAME, TERROR_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 4);
		deck.add(ANKH_OF_MISHRA_NAME, ANKH_OF_MISHRA_MULTIVERSE_ID, 4);
		deck.add(FACT_OR_FICTION_NAME, FACT_OR_FICTION_MULTIVERSE_ID, 4);

		return deck;
	}

	private MtgDeck getDeckWithMultipleSingleRestrictedCards()
	{
		MtgDeck deck = new MtgDeck("Deck With Single Restricted Cards");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(TERROR_NAME, TERROR_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 4);
		deck.add(ANKH_OF_MISHRA_NAME, ANKH_OF_MISHRA_MULTIVERSE_ID, 4);
		deck.add(FACT_OR_FICTION_NAME, FACT_OR_FICTION_MULTIVERSE_ID, 1);
		deck.add(GIFTS_UNGIVEN_NAME, GIFTS_UNGIVEN_MULTIVERSE_ID, 1);

		return deck;
	}

	private MtgDeck getDeckWithMultipleSingleBannedCards()
	{
		MtgDeck deck = new MtgDeck("Deck With Multiple Single Banned Cards");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(TERROR_NAME, TERROR_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 4);
		deck.add(ANKH_OF_MISHRA_NAME, ANKH_OF_MISHRA_MULTIVERSE_ID, 4);
		deck.add(BAZAAR_OF_BAGHDAD_NAME, BAZAR_OF_BAGHDAD_MULTIVERSE_ID, 1);
		deck.add(WORLDGORGER_DRAGON_NAME, WORLDGORGER_DRAGON_MULTIVERSE_ID, 1);

		return deck;
	}

	private DeckLegalityCalculator getLegalityCalculator()
	{
		if (legalityCalculator == null)
		{
			legalityCalculator = DeckLegalityCalculator.getDeckLegalityCalculator();
		}

		return legalityCalculator;
	}
}
