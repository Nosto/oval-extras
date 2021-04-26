/*******************************************************************************
 * Copyright (c) 2018 Nosto Solutions Ltd All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Nosto Solutions Ltd ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the agreement you entered into with
 * Nosto Solutions Ltd.
 ******************************************************************************/
package com.nosto.ovalextras.constraint;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests to check that the validation class for Play that validates the 
 * list of email addresses
 *
 * @author mridang
 */
public class EmailAddressesCheckTest {

    /**
     * Simple test to check that the the checker handles null values
     */
    @Test
    public void testEmptyValues() {
        EmailsCheck addressChecker = new EmailsCheck();
        assertTrue(addressChecker.isSatisfied(null, null, null));
        assertTrue(addressChecker.isSatisfied(null, new HashSet<String>(), null));
    }

    /**
     * Simple test to check that the the checker handles values correctly
     */
    @Test
    public void testInvalidAddresses() {
        EmailsCheck addressChecker = new EmailsCheck();
        Set<String> ignoredAddresses = new HashSet<>();
        ignoredAddresses.add("test@example.com");
        assertTrue(addressChecker.isSatisfied(null, ignoredAddresses, null));
        ignoredAddresses.add("test+test@example.com");
        assertTrue(addressChecker.isSatisfied(null, ignoredAddresses, null));
        ignoredAddresses.add("test");
        assertFalse(addressChecker.isSatisfied(null, ignoredAddresses, null));
    }
}
