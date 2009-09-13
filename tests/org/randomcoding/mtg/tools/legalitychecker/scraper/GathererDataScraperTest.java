package org.randomcoding.mtg.tools.legalitychecker.scraper;

import static junit.framework.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.randomcoding.mtg.tools.enumerations.MagicDeckFormat;
import org.randomcoding.mtg.tools.enumerations.MagicLegalityRestriction;

/**
 * @author Tym The Enchanter
 */
public class GathererDataScraperTest
{
	private static GathererDataScraper scraper;

	@BeforeClass
	public static void setupScraper() throws Exception
	{
		scraper = new GathererDataScraper();
	}

	@Test
	public void testGetMultiverseIdWithSimpleName() throws Exception
	{
		assertEquals(135199, scraper.getMultiverseId("Terror"));
	}

	/**
	 * Terror is currently legal in Extended, Vintage an dLegacy, but not in Standard. (13/09/2009)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetLegalityReturnsCorrectLegalitySetForTerror() throws Exception
	{
		assertEquals(getExpectedLegalityForTerror(), scraper.getLegality("Terror"));
	}

	/**
	 *Megrim is currently legal in all four formats. (13/09/2009)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetLegalityReturnsCorrectLegalitySetForMegrim() throws Exception
	{
		assertEquals(getExpectedLegalityForMegrim(), scraper.getLegality("Megrim"));
	}

	@Test
	public void testGetMultiverseIdForCardWithSpacesInName() throws Exception
	{
		String cardName = "Defiler of Souls";
		int expectedId = 189646;

		assertEquals(expectedId, scraper.getMultiverseId(cardName));
	}

	@Test
	public void testGetMultiverseIdForCardWithApostropheInName() throws Exception
	{
		String cardName = "Necromancer's Covenant";
		int expectedId = 183011;

		assertEquals(expectedId, scraper.getMultiverseId(cardName));
	}

	@Test
	public void testGetMultiverseIdForCardWithCommaInName() throws Exception
	{
		String cardName = "Jenara, Asura of War";
		int expectedId = 180605;

		assertEquals(expectedId, scraper.getMultiverseId(cardName));
	}

	/**
	 * Windfall is currently Banned in Vintage and Restricted in Legacy. (13/09/2009)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetLegalityReturnsCorrectLegalitySetForWindfall() throws Exception
	{
		assertEquals(getExpectedLegalityForWindfall(), scraper.getLegality("Windfall"));
	}

	private Map<MagicDeckFormat, MagicLegalityRestriction> getExpectedLegalityForTerror()
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> legality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();
		legality.put(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		legality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		legality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);

		return legality;
	}

	private Map<MagicDeckFormat, MagicLegalityRestriction> getExpectedLegalityForMegrim()
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> legality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();
		legality.put(MagicDeckFormat.EXTENDED, MagicLegalityRestriction.LEGAL);
		legality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		legality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.LEGAL);
		legality.put(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);

		return legality;
	}

	private Map<MagicDeckFormat, MagicLegalityRestriction> getExpectedLegalityForWindfall()
	{
		Map<MagicDeckFormat, MagicLegalityRestriction> legality = new HashMap<MagicDeckFormat, MagicLegalityRestriction>();

		legality.put(MagicDeckFormat.VINTAGE, MagicLegalityRestriction.RESTRICTED);
		legality.put(MagicDeckFormat.LEGACY, MagicLegalityRestriction.BANNED);
		return legality;
	}
}
