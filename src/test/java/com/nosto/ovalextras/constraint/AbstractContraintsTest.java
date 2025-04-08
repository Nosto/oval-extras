/*
 * Copyright 2005-2021 by Sebastian Thomschke and contributors.
 * SPDX-License-Identifier: EPL-2.0
 */
package com.nosto.ovalextras.constraint;

import net.sf.oval.Check;
import net.sf.oval.Validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public abstract class AbstractContraintsTest {
    protected final Validator validator = new Validator();

    /**
     * Performs basic tests of the check implementation.
     */
    protected void testCheck(final Check check) {
        check.setMessage("XYZ");
        assertEquals("XYZ", check.getMessage());

        check.setProfiles("p1");
        assertNotNull(check.getProfiles());
        assertEquals(1, check.getProfiles().length);
        assertEquals("p1", check.getProfiles()[0]);

        check.setProfiles((String[]) null);
        assertTrue(check.getProfiles() == null || check.getProfiles().length == 0);
    }
}