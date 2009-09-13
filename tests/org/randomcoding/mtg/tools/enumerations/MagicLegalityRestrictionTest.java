package org.randomcoding.mtg.tools.enumerations;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;
import org.randomcoding.mtg.tools.enumerations.MagicLegalityRestriction;

/**
 * @author Tym The Enchanter
 */
public class MagicLegalityRestrictionTest
{
	@Test
	public void testIsMoreRestrictive() throws Exception
	{
		assertTrue(MagicLegalityRestriction.RESTRICTED.isNewRestrictionMoreRestrictive(MagicLegalityRestriction.LEGAL));
		assertTrue(MagicLegalityRestriction.BANNED.isNewRestrictionMoreRestrictive(MagicLegalityRestriction.LEGAL));
		assertTrue(MagicLegalityRestriction.BANNED.isNewRestrictionMoreRestrictive(MagicLegalityRestriction.RESTRICTED));
		assertTrue(MagicLegalityRestriction.NOT_PRESENT.isNewRestrictionMoreRestrictive(MagicLegalityRestriction.LEGAL));
		assertTrue(MagicLegalityRestriction.NOT_PRESENT.isNewRestrictionMoreRestrictive(MagicLegalityRestriction.RESTRICTED));
		assertTrue(MagicLegalityRestriction.NOT_PRESENT.isNewRestrictionMoreRestrictive(MagicLegalityRestriction.BANNED));

		assertFalse(MagicLegalityRestriction.LEGAL.isNewRestrictionMoreRestrictive(MagicLegalityRestriction.RESTRICTED));
		assertFalse(MagicLegalityRestriction.LEGAL.isNewRestrictionMoreRestrictive(MagicLegalityRestriction.BANNED));
		assertFalse(MagicLegalityRestriction.LEGAL.isNewRestrictionMoreRestrictive(MagicLegalityRestriction.NOT_PRESENT));
		assertFalse(MagicLegalityRestriction.RESTRICTED.isNewRestrictionMoreRestrictive(MagicLegalityRestriction.BANNED));
		assertFalse(MagicLegalityRestriction.RESTRICTED.isNewRestrictionMoreRestrictive(MagicLegalityRestriction.NOT_PRESENT));
		assertFalse(MagicLegalityRestriction.BANNED.isNewRestrictionMoreRestrictive(MagicLegalityRestriction.NOT_PRESENT));
	}
}
