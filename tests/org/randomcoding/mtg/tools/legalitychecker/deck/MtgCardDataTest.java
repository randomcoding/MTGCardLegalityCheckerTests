package org.randomcoding.mtg.tools.legalitychecker.deck;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;
import org.randomcoding.mtg.tools.enumerations.MagicDeckFormat;
import org.randomcoding.mtg.tools.enumerations.MagicLegalityRestriction;

/**
 * @author Tym The Enchanter
 */
public class MtgCardDataTest
{
	@Test
	public void testEquality() throws Exception
	{
		MtgCardData data = new MtgCardData("Name", 123);
		data.setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		MtgCardData equalData = new MtgCardData("Name", 123);
		equalData.setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		MtgCardData notEqualData1 = new MtgCardData("New Name", 321);
		notEqualData1.setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		MtgCardData notEqualData2 = new MtgCardData("Name", 321);
		notEqualData2.setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		MtgCardData notEqualData3 = new MtgCardData("Name", 123);
		notEqualData3.setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.RESTRICTED);

		assertEquals(data, equalData);
		assertEquals(equalData, data);
		assertEquals(data, data);

		assertFalse(data.equals(notEqualData1));
		assertFalse(notEqualData1.equals(data));
		assertFalse(data.equals(notEqualData2));
		assertFalse(notEqualData2.equals(data));
		assertFalse(data.equals(notEqualData3));
		assertFalse(notEqualData3.equals(data));
		assertFalse(data.equals(null));
		assertFalse(data.equals(new Object()));
	}

	@Test
	public void testToString() throws Exception
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Architects of Will, ");
		builder.append("Multiverse Ids: ");
		builder.append(179597);

		MtgCardData cardData = new MtgCardData("Architects of Will", 179597);
		assertEquals(builder.toString(), cardData.toString());

		cardData.add(179598);
		builder = new StringBuilder();
		builder.append("Architects of Will, ");
		builder.append("Multiverse Ids: ");
		builder.append("179598");
		builder.append(", 179597");
		assertEquals(builder.toString(), cardData.toString());
	}

	@Test
	public void testClearFormatLegality() throws Exception
	{
		MtgCardData cardData = new MtgCardData("Architects of Will", 179597);
		cardData.setFormatLegality(MagicDeckFormat.STANDARD, MagicLegalityRestriction.LEGAL);
		cardData.setFormatLegality(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL);
		cardData.clearFormatLegality(MagicDeckFormat.STANDARD);

		assertEquals(Collections.singletonMap(MagicDeckFormat.LEGACY, MagicLegalityRestriction.LEGAL), cardData.getCardLegality());
	}

	@Test
	public void testAddMultiverseId() throws Exception
	{
		MtgCardData cardData = new MtgCardData("Architects of Will", 179597);
		cardData.add(179598);

		assertEquals(new HashSet<Integer>(Arrays.asList(new Integer[] { 179597,
				179598 })), cardData.getMultiverseIds());
	}

	@Test
	public void testRemoveMultiverseId() throws Exception
	{
		MtgCardData cardData = new MtgCardData("Architects of Will", 179597);
		cardData.add(179598);
		cardData.remove(179597);

		assertEquals(new HashSet<Integer>(Arrays.asList(new Integer[] { 179598 })), cardData.getMultiverseIds());
	}
}
