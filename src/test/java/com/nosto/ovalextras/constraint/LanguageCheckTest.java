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

import java.util.Locale;

import org.junit.Test;

public class LanguageCheckTest extends AbstractContraintsTest {

    @Test
    public void testValidLanguage() {
        LanguageCheck languageCheck = new LanguageCheck();
        super.testCheck(languageCheck);

        assertTrue(languageCheck.isSatisfied(new Object(), new Locale("en").getLanguage(), null));
        assertTrue(languageCheck.isSatisfied(new Object(), new Locale("fi").getLanguage(), null));
        assertTrue(languageCheck.isSatisfied(new Object(), new Locale("it").getLanguage(), null));
    }

    @Test
    public void testInvalidLanguage() {
        LanguageCheck languageCheck = new LanguageCheck();
        super.testCheck(languageCheck);

        assertFalse(languageCheck.isSatisfied(new Object(), "english", null));
        assertFalse(languageCheck.isSatisfied(new Object(), "", null));
    }
}
