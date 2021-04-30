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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VatIdCheckTest extends AbstractContraintsTest {
    private static final String NO_VAT = "";
    private static final String INVALID_VAT = "123";
    //ToDo double check if this should be public
    private static final String VALID_FINNISH_VAT_CODE = "24189119";

    @Test
    public void testWithNonEuCountry() {
        VatIdCheck vatCheck = new VatIdCheck();
        super.testCheck(vatCheck);

        assertTrue(vatCheck.isSatisfied(null , "US" + NO_VAT, null));
        assertTrue(vatCheck.isSatisfied(null , "US" + INVALID_VAT, null));
        assertTrue(vatCheck.isSatisfied(null , "US" + VALID_FINNISH_VAT_CODE, null));
    }

    @Test
    public void testWithEuCountry() {
        VatIdCheck vatCheck = new VatIdCheck();
        super.testCheck(vatCheck);

        assertFalse(vatCheck.isSatisfied(null , "FI" + NO_VAT, null));
        assertFalse(vatCheck.isSatisfied(null , "FI" + INVALID_VAT, null));
        assertTrue(vatCheck.isSatisfied(null , "FI" + VALID_FINNISH_VAT_CODE,  null));
    }

}
