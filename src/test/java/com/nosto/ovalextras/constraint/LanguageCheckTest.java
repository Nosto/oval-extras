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
import java.util.Locale;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LanguageCheckTest {

    @Test
    public void testValidLanguage() {
        LanguageCheck languageCheck = new LanguageCheck();
        assertTrue(languageCheck.isSatisfied(null, new Locale("en").getLanguage(), null));
        assertTrue(languageCheck.isSatisfied(null, new Locale("fi").getLanguage(), null));
        assertTrue(languageCheck.isSatisfied(null, new Locale("it").getLanguage(), null));
    }

    @Test
    public void testInvalidLanguage() {
        LanguageCheck languageCheck = new LanguageCheck();
        assertFalse(languageCheck.isSatisfied(null, "english", null));
        assertFalse(languageCheck.isSatisfied(null, "", null));
    }
}
