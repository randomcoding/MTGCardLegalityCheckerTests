package org.randomcoding.mtg.tools.legalitychecker.deck;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.randomcoding.mtg.tools.enumerations.MagicDeckFormat;
import org.randomcoding.mtg.tools.enumerations.MagicLegalityRestriction;

/**
 * @author Tym The Enchanter
 */
public class DeckLegalityCalculatorTest
{
	@Test
	public void testLegalityForDeckLegalInAllFormats() throws Exception
	{
		Map<MagicLegalityRestriction, Set<MagicDeckFormat>> expectedLegality = new HashMap<MagicLegalityRestriction, Set<MagicDeckFormat>>();
		expectedLegality.put(MagicLegalityRestriction.LEGAL, new HashSet<MagicDeckFormat>(Arrays.asList(MagicDeckFormat.values())));
		assertEquals(expectedLegality, DeckLegalityCalculator.getDeckLegalityCalculator().checkDeckLegality(getDeckLegalInAllFormats()));
	}

	@Test
	public void testLegalityForDeckLegalInAllFormatsExceptStandard() throws Exception
	{
		fail("Not Implemented Yet");
	}

	@Test
	public void testLegalityForDeckLegalInAllFormatsExceptStandardAndExtended() throws Exception
	{
		fail("Not Implemented Yet");
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

		deck.add("Megrim", 0, 0);
		deck.add("Architects of Will", 179597, 4);

		return deck;
	}
}
