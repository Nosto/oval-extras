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

public class URLCheckTest extends AbstractContraintsTest {

    @Test
    public void isSatisfied() {
        URLCheck urlCheck = new URLCheck();
        super.testCheck(urlCheck);

        assertTrue(urlCheck.isSatisfied(null, "//example.dev", null));
        assertFalse(urlCheck.isSatisfied(null, "//example.test", null));
        assertFalse(urlCheck.isSatisfied(null, "//example.local", null));
        assertFalse(urlCheck.isSatisfied(null, "//example.invalid", null));
        assertFalse(urlCheck.isSatisfied(null, "//example.example", null));
        assertTrue(urlCheck.isSatisfied(null, "//example.shop", null));
        assertFalse(urlCheck.isSatisfied(null, "example.com", null));
        assertTrue(urlCheck.isSatisfied(null, "https://example.com", null));
        assertTrue(urlCheck.isSatisfied(null, "https://example.shop", null));
    }

}
