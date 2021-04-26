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

import static org.junit.Assert.assertTrue;

public class PhoneCheckTest {

    @Test
    public void testValidPhoneNumbers() {
        PhoneCheck phoneCheck = new PhoneCheck();
        assertTrue(phoneCheck.isSatisfied(null, "+1 4155552671", null));
        assertTrue(phoneCheck.isSatisfied(null, "+358 4353454364", null));
        assertTrue(phoneCheck.isSatisfied(null, "00358 4353454364", null));
    }
}
