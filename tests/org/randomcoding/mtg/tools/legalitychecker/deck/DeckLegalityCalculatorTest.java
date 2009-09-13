package org.randomcoding.mtg.tools.legalitychecker.deck;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

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
	private static final String ARCHITECTS_OF_WILL_NAME = "Architects of Will"; // Legal in all formats (13/09/2009)
	private static final int ARCHITECTS_OF_WILL_MULTIVERSE_ID = 179597;
	private static final String MEGRIM_NAME = "Megrim"; // Legal in all formats (13/09/2009)
	private static final int MEGRIM_MULTIVERSE_ID = 189904;
	private static final String TERROR_NAME = "Terror"; // Legal in Extended, Vintage and Legacy
	private static final int TERROR_MULTIVERSE_ID = 135199;
	private static final String ANKH_OF_MISHRA_NAME = "Ankh of Mishra"; // Legal in Vintage and Legacy Only
	private static final int ANKH_OF_MISHRA_MULTIVERSE_ID = 1;

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
		assertEquals(expectedLegality, DeckLegalityCalculator.getDeckLegalityCalculator().checkDeckLegality(getDeckLegalInAllFormats()));
	}

	@Test
	public void testLegalityForDeckLegalInAllFormatsExceptStandard() throws Exception
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> expectedLegality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();
		expectedLegality.put(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		expectedLegality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		expectedLegality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		assertEquals(expectedLegality, DeckLegalityCalculator.getDeckLegalityCalculator().checkDeckLegality(getDeckLegalInAllFormatsExceptStandard()));
	}

	@Test
	public void testLegalityForDeckLegalInAllFormatsExceptStandardAndExtended() throws Exception
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> expectedLegality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();
		expectedLegality.put(MagicDeckFormat.STANDARD, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.NOT_PRESENT);
		expectedLegality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		expectedLegality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		assertEquals(expectedLegality, DeckLegalityCalculator.getDeckLegalityCalculator().checkDeckLegality(getDeckLegalInAllFormatsExceptStandardAndExtended()));
	}

	@Test
	public void testLegalityForDeckWithBannedCard() throws Exception
	{
		fail("Not Implemented Yet");
	}

	@Test
	public void testLegalityForDeckWithSingleRestrictedCard() throws Exception
	{
		fail("Not Implemented Yet");
	}

	@Test
	public void testLegalityForDeckWithMultipleCopiesOfRestrictedCard() throws Exception
	{
		fail("Not Implemented Yet");
	}

	@Test
	public void testLegalityForDeckWithMultipleSinlgeRestrictedCards() throws Exception
	{
		fail("Not Implemented Yet");
	}

	private MtgDeck getDeckLegalInAllFormats()
	{
		MtgDeck deck = new MtgDeck("Deck Legal In All Formats");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 4);

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
		MtgDeck deck = new MtgDeck("Deck Legal In All Formats Except Standard");

		deck.add(MEGRIM_NAME, MEGRIM_MULTIVERSE_ID, 4);
		deck.add(TERROR_NAME, TERROR_MULTIVERSE_ID, 4);
		deck.add(ARCHITECTS_OF_WILL_NAME, ARCHITECTS_OF_WILL_MULTIVERSE_ID, 4);
		deck.add(ANKH_OF_MISHRA_NAME, ANKH_OF_MISHRA_MULTIVERSE_ID, 4);

		return deck;
	}
}
