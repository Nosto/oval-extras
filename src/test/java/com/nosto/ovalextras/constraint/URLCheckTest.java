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

/**
 * @author oskar
 */
public class URLCheckTest {

    @Test
    public void isSatisfied() {
        URLCheck urlCheck = new URLCheck();
        assertTrue(urlCheck.isSatisfied(null, "//example.dev", null, null));
        assertFalse(urlCheck.isSatisfied(null, "//example.test", null, null));
        assertFalse(urlCheck.isSatisfied(null, "//example.local", null, null));
        assertFalse(urlCheck.isSatisfied(null, "//example.invalid", null, null));
        assertFalse(urlCheck.isSatisfied(null, "//example.example", null, null));
        assertTrue(urlCheck.isSatisfied(null, "//example.shop", null, null));
        assertFalse(urlCheck.isSatisfied(null, "example.com", null, null));
        assertTrue(urlCheck.isSatisfied(null, "https://example.com", null, null));
        assertTrue(urlCheck.isSatisfied(null, "https://example.shop", null, null));
    }

}
