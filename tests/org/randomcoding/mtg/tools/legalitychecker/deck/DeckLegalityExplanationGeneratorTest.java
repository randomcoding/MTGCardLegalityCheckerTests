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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.randomcoding.mtg.tools.enumerations.MagicDeckFormat;
import org.randomcoding.mtg.tools.enumerations.MagicLegalityRestriction;

/**
 * @author Tym The Enchanter
 */
public class DeckLegalityExplanationGeneratorTest
{
	private static DeckLegalityExplanationGenerator explanationGenerator;

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
	private static final String WORLDGORGER_DRAGON_NAME = "Worldgorger Dragon"; // Banned in Legacy, legal in Vintage, not present in Standard and Extended
	private static final int WORLDGORGER_DRAGON_MULTIVERSE_ID = 35056;
	private static final String FACT_OR_FICTION_NAME = "Fact or Fiction"; // Restricted in Vintage, legal in Legacy
	private static final int FACT_OR_FICTION_MULTIVERSE_ID = 185819;
	private static final String GIFTS_UNGIVEN_NAME = "Gifts Ungiven"; // Restricted in Vintage, legal in Legacy
	private static final int GIFTS_UNGIVEN_MULTIVERSE_ID = 194971;

	@Before
	public void setupGenerator() throws Exception
	{
		explanationGenerator = new DeckLegalityExplanationGenerator();
	}

	@Test
	public void testGetExplanationForDeckWithMoreThanFourCopiesOfSingleCard() throws Exception
	{
		Map<MagicDeckFormat, Map<MtgCardData, Set<String>>> expectedExplanations = new HashMap<MagicDeckFormat, Map<MtgCardData, Set<String>>>();
		MtgCardData cardData = new MtgCardData(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID);
		for (MagicDeckFormat deckFormat : MagicDeckFormat.values())
		{
			Map<MtgCardData, Set<String>> cardExplanation = new HashMap<MtgCardData, Set<String>>();
			cardData.setFormatLegality(deckFormat, MagicLegalityRestriction.ILLEGAL);
			cardExplanation.put(cardData, Collections.singleton("There are more than four copies of " + ARCHITECTS_OF_WILL_NAME + " present in the deck."));
			expectedExplanations.put(deckFormat, cardExplanation);
		}
		assertEquals(expectedExplanations, explanationGenerator.getExplanationForDeckIllegality(getDeckLegalInAllFormatsExceptThatItHasFiveCopiesOfOneCard()));
	}

	@Test
	public void testGetExplanationForDeckWithMoreThanFourCopiesOfMultipleCards() throws Exception
	{
		Map<MagicDeckFormat, Map<MtgCardData, Set<String>>> expectedExplanations = new HashMap<MagicDeckFormat, Map<MtgCardData, Set<String>>>();
		MtgCardData aowCardData = new MtgCardData(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID);
		MtgCardData megrimCardData = new MtgCardData(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID);
		for (MagicDeckFormat deckFormat : MagicDeckFormat.values())
		{
			Map<MtgCardData, Set<String>> cardExplanation = new HashMap<MtgCardData, Set<String>>();
			aowCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.ILLEGAL);
			cardExplanation.put(aowCardData, Collections.singleton("There are more than four copies of " + ARCHITECTS_OF_WILL_NAME + " present in the deck."));
			megrimCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.ILLEGAL);
			cardExplanation.put(megrimCardData, Collections.singleton("There are more than four copies of " + MEGRIM_NAME + " present in the deck."));
			expectedExplanations.put(deckFormat, cardExplanation);
		}

		assertEquals(expectedExplanations, explanationGenerator.getExplanationForDeckIllegality(getDeckLegalInAllFormatsExceptThatItHasFiveCopiesOfTwoCards()));
	}

	@Test
	public void testGetExplanationForDeckWithSingleBannedCard() throws Exception
	{
		Map<MagicDeckFormat, Map<MtgCardData, Set<String>>> expectedExplanations = new HashMap<MagicDeckFormat, Map<MtgCardData, Set<String>>>();
		MtgCardData megrimCardData = new MtgCardData(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID);
		MtgCardData aowCardData = new MtgCardData(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID);
		MtgCardData bobCardData = new MtgCardData(BAZAAR_OF_BAGHDAD_NAME, BAZAR_OF_BAGHDAD_MULTIVERSE_ID);

		for (MagicDeckFormat deckFormat : MagicDeckFormat.values())
		{
			Map<MtgCardData, Set<String>> cardExplanation = new HashMap<MtgCardData, Set<String>>();
			megrimCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
			aowCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
			if (deckFormat.equals(MagicDeckFormat.STANDARD) || deckFormat.equals(MagicDeckFormat.EXTENDED))
			{
				bobCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.NOT_PRESENT);
				cardExplanation.put(bobCardData, Collections.singleton(getNotPresentExplanation(BAZAAR_OF_BAGHDAD_NAME, deckFormat)));
			}
			else if (deckFormat.equals(MagicDeckFormat.LEGACY))
			{
				bobCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.BANNED);
				cardExplanation.put(bobCardData, Collections.singleton(getBannedExplanation(BAZAAR_OF_BAGHDAD_NAME, deckFormat)));
			}
			else if (deckFormat.equals(MagicDeckFormat.VINTAGE))
			{
				bobCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
			}

			if (!cardExplanation.isEmpty())
			{
				expectedExplanations.put(deckFormat, cardExplanation);
			}
		}

		assertEquals(expectedExplanations, explanationGenerator.getExplanationForDeckIllegality(getDeckWithSingleBannedCard()));
	}

	@Test
	public void testGetExplanationForDeckWithMultipleSingleBannedCards() throws Exception
	{
		Map<MagicDeckFormat, Map<MtgCardData, Set<String>>> expectedExplanations = new HashMap<MagicDeckFormat, Map<MtgCardData, Set<String>>>();
		MtgCardData megrimCardData = new MtgCardData(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID);
		MtgCardData wdCardData = new MtgCardData(WORLDGORGER_DRAGON_NAME, WORLDGORGER_DRAGON_MULTIVERSE_ID);
		MtgCardData bobCardData = new MtgCardData(BAZAAR_OF_BAGHDAD_NAME, BAZAR_OF_BAGHDAD_MULTIVERSE_ID);

		for (MagicDeckFormat deckFormat : MagicDeckFormat.values())
		{
			Map<MtgCardData, Set<String>> cardExplanation = new HashMap<MtgCardData, Set<String>>();
			megrimCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
			if (deckFormat.equals(MagicDeckFormat.STANDARD) || deckFormat.equals(MagicDeckFormat.EXTENDED))
			{
				bobCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.NOT_PRESENT);
				cardExplanation.put(bobCardData, Collections.singleton(getNotPresentExplanation(BAZAAR_OF_BAGHDAD_NAME, deckFormat)));
				wdCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.NOT_PRESENT);
				cardExplanation.put(wdCardData, Collections.singleton(getNotPresentExplanation(WORLDGORGER_DRAGON_NAME, deckFormat)));
			}
			else if (deckFormat.equals(MagicDeckFormat.LEGACY))
			{
				bobCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.BANNED);
				cardExplanation.put(bobCardData, Collections.singleton(getBannedExplanation(BAZAAR_OF_BAGHDAD_NAME, deckFormat)));
				wdCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.BANNED);
				cardExplanation.put(wdCardData, Collections.singleton(getBannedExplanation(WORLDGORGER_DRAGON_NAME, deckFormat)));
			}
			else if (deckFormat.equals(MagicDeckFormat.VINTAGE))
			{
				bobCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
				wdCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
			}

			if (!cardExplanation.isEmpty())
			{
				expectedExplanations.put(deckFormat, cardExplanation);
			}
		}

		assertEquals(expectedExplanations, explanationGenerator.getExplanationForDeckIllegality(getDeckWithMultipleSingleBannedCards()));
	}

	@Test
	public void testGetExplanationForDeckWithMultipleCopiesOfSingleRestrictedCard() throws Exception
	{
		Map<MagicDeckFormat, Map<MtgCardData, Set<String>>> expectedExplanations = new HashMap<MagicDeckFormat, Map<MtgCardData, Set<String>>>();
		MtgCardData megrimCardData = new MtgCardData(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID);
		MtgCardData fofCardData = new MtgCardData(FACT_OR_FICTION_NAME, FACT_OR_FICTION_MULTIVERSE_ID);

		for (MagicDeckFormat deckFormat : MagicDeckFormat.values())
		{
			Map<MtgCardData, Set<String>> cardExplanation = new HashMap<MtgCardData, Set<String>>();
			megrimCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
			switch (deckFormat)
			{
				case STANDARD:
				case EXTENDED:
					fofCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.NOT_PRESENT);
					cardExplanation.put(fofCardData, Collections.singleton(getNotPresentExplanation(FACT_OR_FICTION_NAME, deckFormat)));
					break;
				case LEGACY:
					fofCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
					break;
				case VINTAGE:
					fofCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.RESTRICTED);
					cardExplanation.put(fofCardData, Collections.singleton(getMultipleCopiesOfRestrictedCardExplanation(FACT_OR_FICTION_NAME, deckFormat)));
					break;
			}

			if (!cardExplanation.isEmpty())
			{
				expectedExplanations.put(deckFormat, cardExplanation);
			}
		}

		assertEquals(expectedExplanations, explanationGenerator.getExplanationForDeckIllegality(getDeckWithMultipleCopiesOfSingleRestrictedCard()));
	}

	@Test
	public void testGetExplanationForDeckWithMultipleCopiesOfMultipleRestrictedCards() throws Exception
	{
		Map<MagicDeckFormat, Map<MtgCardData, Set<String>>> expectedExplanations = new HashMap<MagicDeckFormat, Map<MtgCardData, Set<String>>>();
		MtgCardData megrimCardData = new MtgCardData(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID);
		MtgCardData fofCardData = new MtgCardData(FACT_OR_FICTION_NAME, FACT_OR_FICTION_MULTIVERSE_ID);
		MtgCardData guCardData = new MtgCardData(GIFTS_UNGIVEN_NAME, GIFTS_UNGIVEN_MULTIVERSE_ID);

		for (MagicDeckFormat deckFormat : MagicDeckFormat.values())
		{
			Map<MtgCardData, Set<String>> cardExplanation = new HashMap<MtgCardData, Set<String>>();
			megrimCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
			switch (deckFormat)
			{
				case STANDARD:
				case EXTENDED:
					fofCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.NOT_PRESENT);
					cardExplanation.put(fofCardData, Collections.singleton(getNotPresentExplanation(FACT_OR_FICTION_NAME, deckFormat)));
					guCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.NOT_PRESENT);
					cardExplanation.put(guCardData, Collections.singleton(getNotPresentExplanation(GIFTS_UNGIVEN_NAME, deckFormat)));
					break;
				case LEGACY:
					fofCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
					guCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
					break;
				case VINTAGE:
					fofCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.RESTRICTED);
					cardExplanation.put(fofCardData, Collections.singleton(getMultipleCopiesOfRestrictedCardExplanation(FACT_OR_FICTION_NAME, deckFormat)));
					guCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.RESTRICTED);
					cardExplanation.put(guCardData, Collections.singleton(getMultipleCopiesOfRestrictedCardExplanation(GIFTS_UNGIVEN_NAME, deckFormat)));
					break;
			}

			if (!cardExplanation.isEmpty())
			{
				expectedExplanations.put(deckFormat, cardExplanation);
			}
		}

		assertEquals(expectedExplanations, explanationGenerator.getExplanationForDeckIllegality(getDeckWithMultipleCopiesOfMultipleRestrictedCards()));
	}

	@Test
	public void testGetExplanationForDeckThatIsLegalInAllFormatsExceptStandard() throws Exception
	{
		Map<MagicDeckFormat, Map<MtgCardData, Set<String>>> expectedExplanations = new HashMap<MagicDeckFormat, Map<MtgCardData, Set<String>>>();
		MtgCardData megrimCardData = new MtgCardData(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID);
		MtgCardData terrorCardData = new MtgCardData(TERROR_NAME, TERROR_MULTIVERSE_ID);
		MtgCardData aowCardData = new MtgCardData(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID);
		for (MagicDeckFormat deckFormat : MagicDeckFormat.values())
		{
			Map<MtgCardData, Set<String>> cardExplanation = new HashMap<MtgCardData, Set<String>>();
			megrimCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
			aowCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);

			if (deckFormat.equals(MagicDeckFormat.STANDARD))
			{
				terrorCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.NOT_PRESENT);
				cardExplanation.put(terrorCardData, Collections.singleton(getNotPresentExplanation(TERROR_NAME, deckFormat)));
			}
			else
			{
				terrorCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
			}
			if (!cardExplanation.isEmpty())
			{
				expectedExplanations.put(deckFormat, cardExplanation);
			}
		}

		assertEquals(expectedExplanations, explanationGenerator.getExplanationForDeckIllegality(getDeckLegalInAllFormatsExceptStandard()));
	}

	@Test
	public void testGetExplanationForDeckThatIsLegalInAllFormatsExceptStandardAndExtended() throws Exception
	{
		Map<MagicDeckFormat, Map<MtgCardData, Set<String>>> expectedExplanations = new HashMap<MagicDeckFormat, Map<MtgCardData, Set<String>>>();
		MtgCardData megrimCardData = new MtgCardData(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID);
		MtgCardData terrorCardData = new MtgCardData(TERROR_NAME, TERROR_MULTIVERSE_ID);
		MtgCardData aomCardData = new MtgCardData(ANKH_OF_MISHRA_NAME, ANKH_OF_MISHRA_MULTIVERSE_ID);
		MtgCardData aowCardData = new MtgCardData(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID);
		for (MagicDeckFormat deckFormat : MagicDeckFormat.values())
		{
			Map<MtgCardData, Set<String>> cardExplanation = new HashMap<MtgCardData, Set<String>>();
			megrimCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
			aowCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);

			if (deckFormat.equals(MagicDeckFormat.STANDARD))
			{
				terrorCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.NOT_PRESENT);
				aomCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.NOT_PRESENT);
				cardExplanation.put(terrorCardData, Collections.singleton(getNotPresentExplanation(TERROR_NAME, deckFormat)));
				cardExplanation.put(aomCardData, Collections.singleton(getNotPresentExplanation(ANKH_OF_MISHRA_NAME, deckFormat)));
			}
			else if (deckFormat.equals(MagicDeckFormat.EXTENDED))
			{
				terrorCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
				aomCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.NOT_PRESENT);
				cardExplanation.put(aomCardData, Collections.singleton(getNotPresentExplanation(ANKH_OF_MISHRA_NAME, deckFormat)));
			}
			else
			{
				terrorCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
				aomCardData.setFormatLegality(deckFormat, MagicLegalityRestriction.LEGAL);
			}
			if (!cardExplanation.isEmpty())
			{
				expectedExplanations.put(deckFormat, cardExplanation);
			}
		}

		assertEquals(expectedExplanations, explanationGenerator.getExplanationForDeckIllegality(getDeckLegalInAllFormatsExceptStandardAndExtended()));
	}

	private MtgDeck getDeckLegalInAllFormatsExceptThatItHasFiveCopiesOfOneCard()
	{
		MtgDeck deck = new MtgDeck("Deck Legal In All Formats");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 5);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.ILLEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.ILLEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.ILLEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.ILLEGAL);

		return deck;
	}

	private MtgDeck getDeckLegalInAllFormatsExceptThatItHasFiveCopiesOfTwoCards()
	{
		MtgDeck deck = new MtgDeck("Deck Legal In All Formats");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 5);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 5);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.ILLEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.ILLEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.ILLEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.ILLEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.ILLEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.ILLEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.ILLEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.ILLEGAL);

		return deck;
	}

	private MtgDeck getDeckLegalInAllFormatsExceptStandard()
	{
		MtgDeck deck = new MtgDeck("Deck Legal In All Formats Except Standard");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(TERROR_NAME, TERROR_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 4);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		deck.getCardData(TERROR_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(TERROR_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		deck.getCardData(TERROR_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(TERROR_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);

		return deck;
	}

	private MtgDeck getDeckLegalInAllFormatsExceptStandardAndExtended()
	{
		MtgDeck deck = new MtgDeck("Deck Legal In All Formats Except Standard and Extended");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(TERROR_NAME, TERROR_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 4);
		deck.add(ANKH_OF_MISHRA_NAME, ANKH_OF_MISHRA_MULTIVERSE_ID, 4);

		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		deck.getCardData(TERROR_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(TERROR_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		deck.getCardData(TERROR_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(TERROR_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ANKH_OF_MISHRA_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(ANKH_OF_MISHRA_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(ANKH_OF_MISHRA_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ANKH_OF_MISHRA_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);

		return deck;
	}

	private MtgDeck getDeckWithSingleBannedCard()
	{
		MtgDeck deck = new MtgDeck("Deck With Single Banned Card");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 4);
		deck.add(BAZAAR_OF_BAGHDAD_NAME, BAZAR_OF_BAGHDAD_MULTIVERSE_ID, 1);

		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(ARCHITECTS_OF_WILL_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		deck.getCardData(BAZAAR_OF_BAGHDAD_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(BAZAAR_OF_BAGHDAD_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(BAZAAR_OF_BAGHDAD_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(BAZAAR_OF_BAGHDAD_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.BANNED);

		return deck;
	}

	private MtgDeck getDeckWithMultipleCopiesOfSingleRestrictedCard()
	{
		MtgDeck deck = new MtgDeck("Deck With Multiple Copies of Single Restricted Card");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(FACT_OR_FICTION_NAME, FACT_OR_FICTION_MULTIVERSE_ID, 4);

		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		deck.getCardData(FACT_OR_FICTION_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(FACT_OR_FICTION_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(FACT_OR_FICTION_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.RESTRICTED);
		deck.getCardData(FACT_OR_FICTION_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);

		return deck;
	}

	private MtgDeck getDeckWithMultipleSingleBannedCards()
	{
		MtgDeck deck = new MtgDeck("Deck With Multiple Single Banned Cards");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(BAZAAR_OF_BAGHDAD_NAME, BAZAR_OF_BAGHDAD_MULTIVERSE_ID, 4);
		deck.add(WORLDGORGER_DRAGON_NAME, WORLDGORGER_DRAGON_MULTIVERSE_ID, 1);

		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		deck.getCardData(BAZAAR_OF_BAGHDAD_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(BAZAAR_OF_BAGHDAD_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(BAZAAR_OF_BAGHDAD_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(BAZAAR_OF_BAGHDAD_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.BANNED);
		deck.getCardData(WORLDGORGER_DRAGON_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(WORLDGORGER_DRAGON_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(WORLDGORGER_DRAGON_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(WORLDGORGER_DRAGON_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.BANNED);

		return deck;
	}

	private MtgDeck getDeckWithMultipleCopiesOfMultipleRestrictedCards()
	{
		MtgDeck deck = new MtgDeck("Deck With Single Restricted Cards");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(FACT_OR_FICTION_NAME, FACT_OR_FICTION_MULTIVERSE_ID, 2);
		deck.add(GIFTS_UNGIVEN_NAME, GIFTS_UNGIVEN_MULTIVERSE_ID, 3);

		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		deck.getCardData(MEGRIM_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		deck.getCardData(FACT_OR_FICTION_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(FACT_OR_FICTION_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(FACT_OR_FICTION_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.RESTRICTED);
		deck.getCardData(FACT_OR_FICTION_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		deck.getCardData(GIFTS_UNGIVEN_NAME).setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(GIFTS_UNGIVEN_NAME).setFormatLegality(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		deck.getCardData(GIFTS_UNGIVEN_NAME).setFormatLegality(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.RESTRICTED);
		deck.getCardData(GIFTS_UNGIVEN_NAME).setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);

		return deck;
	}

	private String getNotPresentExplanation(String cardName, MagicDeckFormat deckFormat)
	{
		return cardName + " is not in the legal sets for " + deckFormat.name();
	}

	private String getBannedExplanation(String cardName, MagicDeckFormat deckFormat)
	{
		return cardName + " is Banned in " + deckFormat.name();
	}

	private String getMultipleCopiesOfRestrictedCardExplanation(String cardName, MagicDeckFormat deckFormat)
	{
		return "Only one copy of " + cardName + " is permitted in " + deckFormat.name() + " as it is Restricted";
	}
}
