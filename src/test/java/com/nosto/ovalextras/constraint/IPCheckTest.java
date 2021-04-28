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
 * list of ignored IP addresses.
 *
 * @author mridang
 */
public class IPCheckTest extends AbstractContraintsTest {

    /**
     * Simple test to check that the the checker handles null values
     */
    @Test
    public void testEmptyValues() {
        IPCheck addressChecker = new IPCheck();
        super.testCheck(addressChecker);

        assertTrue(addressChecker.isSatisfied(null, null));
        assertTrue(addressChecker.isSatisfied(null, new HashSet<String>()));
    }

    /**
     * Simple test to check that the the checker handles values correctly
     */
    @Test
    public void testInvalidAddresses() {
        IPCheck addressChecker = new IPCheck();
        super.testCheck(addressChecker);

        Set<String> ignoredAddresses = new HashSet<>();
        ignoredAddresses.add("0.0.0.0");
        assertTrue(addressChecker.isSatisfied(null, ignoredAddresses));
        ignoredAddresses.add("127.0.0.1");
        assertTrue(addressChecker.isSatisfied(null, ignoredAddresses));
        ignoredAddresses.add("255.255.255.255");
        assertTrue(addressChecker.isSatisfied(null, ignoredAddresses));
        ignoredAddresses.add("256.256.256.256.256");
        assertFalse(addressChecker.isSatisfied(null, ignoredAddresses));
    }
}
