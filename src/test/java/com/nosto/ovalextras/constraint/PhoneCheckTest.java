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

import org.junit.Test;

public class PhoneCheckTest extends AbstractContraintsTest {

    @Test
    public void testValidPhoneNumbers() {
        PhoneCheck phoneCheck = new PhoneCheck();
        super.testCheck(phoneCheck);

        assertTrue(phoneCheck.isSatisfied(new Object(), "+1 4155552671", null));
        assertTrue(phoneCheck.isSatisfied(new Object(), "+358 4353454364", null));
        assertTrue(phoneCheck.isSatisfied(new Object(), "+3584353454364", null));
        assertTrue(phoneCheck.isSatisfied(new Object(), "00358 4353454364", null));
    }

    @Test
    public void testInvalidPhoneNumbers() {
        PhoneCheck phoneCheck = new PhoneCheck();
        super.testCheck(phoneCheck);

        assertFalse(phoneCheck.isSatisfied(new Object(), "abcd", null));
        assertFalse(phoneCheck.isSatisfied(new Object(), "1234", null));
        assertFalse(phoneCheck.isSatisfied(new Object(), "", null));
    }
}
