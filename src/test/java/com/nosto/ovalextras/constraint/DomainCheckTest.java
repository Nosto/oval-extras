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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DomainCheckTest {

    @Test
    public void testValidDomain() {
        DomainCheck domainCheck = new DomainCheck();
        assertTrue(domainCheck.isSatisfied(null, "domain.com", null));
        assertTrue(domainCheck.isSatisfied(null, "domain.com/", null));
    }

    @Test
    public void testInvalidDomain() {
        DomainCheck domainCheck = new DomainCheck();
        assertFalse(domainCheck.isSatisfied(null, "domain", null));
        assertFalse(domainCheck.isSatisfied(null, "domain@com", null));
    }
}
