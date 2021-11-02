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

public class DomainCheckTest extends AbstractContraintsTest {

    @Test
    public void testValidDomain() {
        DomainCheck domainCheck = new DomainCheck();
        super.testCheck(domainCheck);

        assertTrue(domainCheck.isSatisfied(new Object(), "domain.com", null));
        assertTrue(domainCheck.isSatisfied(new Object(), "domain.com/", null));
    }

    @Test
    public void testInvalidDomain() {
        DomainCheck domainCheck = new DomainCheck();
        super.testCheck(domainCheck);

        assertFalse(domainCheck.isSatisfied(new Object(), "domain", null));
        assertFalse(domainCheck.isSatisfied(new Object(), "domain@com", null));
    }
}
