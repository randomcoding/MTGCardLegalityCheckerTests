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
package org.randomcoding.mtg.tools.enumerations;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

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
