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

import java.util.Currency;

import org.junit.Test;

public class CurrencyCheckTest extends AbstractContraintsTest {

    @Test
    public void testValidCurrencies() {
        CurrencyCheck currencyCheck = new CurrencyCheck();
        super.testCheck(currencyCheck);

        assertTrue(currencyCheck.isSatisfied(new Object(), Currency.getInstance("ALL").getCurrencyCode(), null));
        assertTrue(currencyCheck.isSatisfied(new Object(), Currency.getInstance("EUR").getCurrencyCode(), null));
        assertTrue(currencyCheck.isSatisfied(new Object(), Currency.getInstance("USD").getCurrencyCode(), null));
        assertTrue(currencyCheck.isSatisfied(new Object(), Currency.getInstance("JPY").getCurrencyCode(), null));
    }

    @Test
    public void testInvalidCurrencies() {
        CurrencyCheck currencyCheck = new CurrencyCheck();
        super.testCheck(currencyCheck);

        assertFalse(currencyCheck.isSatisfied(new Object(), "euro", null));
        assertFalse(currencyCheck.isSatisfied(new Object(), "dollar", null));
        assertFalse(currencyCheck.isSatisfied(new Object(), "$", null));
    }
}
