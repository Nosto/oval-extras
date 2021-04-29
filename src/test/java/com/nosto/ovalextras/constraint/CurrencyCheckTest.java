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

import java.util.Currency;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CurrencyCheckTest extends AbstractContraintsTest {

    @Test
    public void testValidCurrencies() {
        CurrencyCheck currencyCheck = new CurrencyCheck();
        super.testCheck(currencyCheck);

        assertTrue(currencyCheck.isSatisfied(null, Currency.getInstance("ALL").getCurrencyCode(), null));
        assertTrue(currencyCheck.isSatisfied(null, Currency.getInstance("EUR").getCurrencyCode(), null));
        assertTrue(currencyCheck.isSatisfied(null, Currency.getInstance("USD").getCurrencyCode(), null));
        assertTrue(currencyCheck.isSatisfied(null, Currency.getInstance("JPY").getCurrencyCode(), null));
    }

    @Test
    public void testInvalidCurrencies() {
        CurrencyCheck currencyCheck = new CurrencyCheck();
        super.testCheck(currencyCheck);

        assertFalse(currencyCheck.isSatisfied(null, "euro", null));
        assertFalse(currencyCheck.isSatisfied(null, "dollar", null));
        assertFalse(currencyCheck.isSatisfied(null, "$", null));
    }
}
