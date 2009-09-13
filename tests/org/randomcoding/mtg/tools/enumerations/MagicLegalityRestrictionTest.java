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
		assertTrue(MagicLegalityRestriction.RESTRICTED.isMoreRestrictiveThan(MagicLegalityRestriction.LEGAL));
		assertTrue(MagicLegalityRestriction.BANNED.isMoreRestrictiveThan(MagicLegalityRestriction.LEGAL));
		assertTrue(MagicLegalityRestriction.BANNED.isMoreRestrictiveThan(MagicLegalityRestriction.RESTRICTED));
		assertTrue(MagicLegalityRestriction.NOT_PRESENT.isMoreRestrictiveThan(MagicLegalityRestriction.LEGAL));
		assertTrue(MagicLegalityRestriction.NOT_PRESENT.isMoreRestrictiveThan(MagicLegalityRestriction.RESTRICTED));
		assertTrue(MagicLegalityRestriction.NOT_PRESENT.isMoreRestrictiveThan(MagicLegalityRestriction.BANNED));

		assertFalse(MagicLegalityRestriction.LEGAL.isMoreRestrictiveThan(MagicLegalityRestriction.RESTRICTED));
		assertFalse(MagicLegalityRestriction.LEGAL.isMoreRestrictiveThan(MagicLegalityRestriction.BANNED));
		assertFalse(MagicLegalityRestriction.LEGAL.isMoreRestrictiveThan(MagicLegalityRestriction.NOT_PRESENT));
		assertFalse(MagicLegalityRestriction.RESTRICTED.isMoreRestrictiveThan(MagicLegalityRestriction.BANNED));
		assertFalse(MagicLegalityRestriction.RESTRICTED.isMoreRestrictiveThan(MagicLegalityRestriction.NOT_PRESENT));
		assertFalse(MagicLegalityRestriction.BANNED.isMoreRestrictiveThan(MagicLegalityRestriction.NOT_PRESENT));
	}
}
