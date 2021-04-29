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

import org.junit.Test;
import java.util.Locale;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CountryCheckTest extends AbstractContraintsTest {

    @Test
    public void testValidCountryCodes() {
        CountryCheck countryCheck = new CountryCheck();
        super.testCheck(countryCheck);

        assertTrue(countryCheck.isSatisfied(null, new Locale("", "AL").getCountry() , null));
        assertTrue(countryCheck.isSatisfied(null, new Locale("", "IT").getCountry() , null));
        assertTrue(countryCheck.isSatisfied(null, new Locale("", "FI").getCountry() , null));
        assertTrue(countryCheck.isSatisfied(null, new Locale("", "CH").getCountry() , null));
    }

    @Test
    public void testInvalidCountryCode() {
        CountryCheck countryCheck = new CountryCheck();
        super.testCheck(countryCheck);

        assertFalse(countryCheck.isSatisfied(null, "NoWhere" , null));
        assertFalse(countryCheck.isSatisfied(null, "Somewhere" , null));
        assertFalse(countryCheck.isSatisfied(null, "" , null));
        assertFalse(countryCheck.isSatisfied(null, "ZZ" , null));
    }
}
