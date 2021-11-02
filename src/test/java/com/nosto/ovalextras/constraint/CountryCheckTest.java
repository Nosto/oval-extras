/*
 *  Copyright (c) 2021 Nosto Solutions Ltd All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of
 *  Nosto Solutions Ltd ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the agreement you entered into with
 *  Nosto Solutions Ltd.
 */
package com.nosto.ovalextras.constraint;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

public class CountryCheckTest extends AbstractContraintsTest {

    @Test
    public void testValidCountryCodes() {
        CountryCheck countryCheck = new CountryCheck();
        super.testCheck(countryCheck);

        assertTrue(countryCheck.isSatisfied(new Object(), new Locale("", "AL").getCountry(), null));
        assertTrue(countryCheck.isSatisfied(new Object(), new Locale("", "IT").getCountry(), null));
        assertTrue(countryCheck.isSatisfied(new Object(), new Locale("", "FI").getCountry(), null));
        assertTrue(countryCheck.isSatisfied(new Object(), new Locale("", "CH").getCountry(), null));
    }

    @Test
    public void testInvalidCountryCode() {
        CountryCheck countryCheck = new CountryCheck();
        super.testCheck(countryCheck);

        assertFalse(countryCheck.isSatisfied(new Object(), "NoWhere", null));
        assertFalse(countryCheck.isSatisfied(new Object(), "Somewhere", null));
        assertFalse(countryCheck.isSatisfied(new Object(), "", null));
        assertFalse(countryCheck.isSatisfied(new Object(), "ZZ", null));
    }
}
