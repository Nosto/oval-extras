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

import org.junit.Before;
import org.junit.Test;

public class URLCheckTest extends AbstractContraintsTest {

    @Before
    public void setup() {
        System.setProperty(URLCheck.ALLOW_LOCAL, "true");
    }

    @Test
    public void isSatisfied() {
        URLCheck urlCheck = new URLCheck();
        super.testCheck(urlCheck);

        assertTrue(urlCheck.isSatisfied(new Object(), "//example.dev", null));
        assertFalse(urlCheck.isSatisfied(new Object(), "//example.test", null));
        assertFalse(urlCheck.isSatisfied(new Object(), "//example.local", null));
        assertFalse(urlCheck.isSatisfied(new Object(), "//example.invalid", null));
        assertFalse(urlCheck.isSatisfied(new Object(), "//example.example", null));
        assertTrue(urlCheck.isSatisfied(new Object(), "//example.shop", null));
        assertFalse(urlCheck.isSatisfied(new Object(), "example.com", null));
        assertTrue(urlCheck.isSatisfied(new Object(), "https://example.com", null));
        assertTrue(urlCheck.isSatisfied(new Object(), "https://example.shop", null));
    }

    @Test
    public void testWithLocalUrl() {

        URLCheck urlCheck = new URLCheck();
        super.testCheck(urlCheck);

        assertTrue(urlCheck.isSatisfied(new Object(), "http://localhost:8080", null));
    }

}
