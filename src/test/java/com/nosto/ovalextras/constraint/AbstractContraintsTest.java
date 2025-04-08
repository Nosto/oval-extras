/*
 * Copyright 2005-2021 by Sebastian Thomschke and contributors.
 * SPDX-License-Identifier: EPL-2.0
 */
package com.nosto.ovalextras.constraint;

import net.sf.oval.Check;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * Checks that given constraint violations correspond to expected.
     */
    protected void assertViolations(List<String> expectedViolationCodes, List<ConstraintViolation> violations) {
        assertEquals(expectedViolationCodes.size(), violations.size());
        Set<String> actualErrorCodes = violations.stream()
                .map(ConstraintViolation::getErrorCode)
                .collect(Collectors.toUnmodifiableSet());

        expectedViolationCodes.forEach(errorCode -> assertTrue("Violation of " + errorCode + " is expected", actualErrorCodes.contains(errorCode)));
    }
}