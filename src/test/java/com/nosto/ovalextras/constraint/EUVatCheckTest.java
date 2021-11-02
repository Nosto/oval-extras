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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EUVatCheckTest extends AbstractContraintsTest {
    private static final String NO_VAT = "";
    private static final String INVALID_VAT = "123";
    //ToDo double check if this should be public
    private static final String VALID_FINNISH_VAT_CODE = "24189119";

    @Test
    public void testWithNonEuCountry() {
        EUVatCheck vatCheck = new EUVatCheck();
        super.testCheck(vatCheck);

        assertTrue(vatCheck.isSatisfied(new Object(), "US" + NO_VAT, null));
        assertTrue(vatCheck.isSatisfied(new Object(), "US" + INVALID_VAT, null));
        assertTrue(vatCheck.isSatisfied(new Object(), "US" + VALID_FINNISH_VAT_CODE, null));
    }

    @Test
    public void testWithEuCountry() {
        EUVatCheck vatCheck = new EUVatCheck();
        super.testCheck(vatCheck);

        assertFalse(vatCheck.isSatisfied(new Object(), "FI" + NO_VAT, null));
        assertFalse(vatCheck.isSatisfied(new Object(), "FI" + INVALID_VAT, null));
        assertTrue(vatCheck.isSatisfied(new Object(), "FI" + VALID_FINNISH_VAT_CODE, null));
    }

}
