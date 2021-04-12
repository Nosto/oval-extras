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

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

public class VatIdCheckTest extends Assert {
    private static final String NO_VAT = null;
    private static final String INVALID_VAT = "123";
    private static final String VALID_FINNISH_VAT_CODE = "2418911-9";

    private VatId vatAnnotation = new VatId() {
        @Nullable
        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }

        @Override
        public String countryCodeField() {
            return "countryCode";
        }

        @Override
        public String ignoreValidationField() {
            return "";
        }
    };
    
    private VatId vatAnnotationIgnoreValidation = new VatId() {
        @Nullable
        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }

        @Override
        public String countryCodeField() {
            return "countryCode";
        }

        @Override
        public String ignoreValidationField() {
            return "ignoreValidation";
        }
    };

    @Test
    public void testWithNonEuCountry() {
        VatIdCheck vatCheck = new VatIdCheck();
        vatCheck.configure(vatAnnotation);
        assertTrue(vatCheck.isSatisfied(new TestEntity("US", true), NO_VAT, null, null));
        assertTrue(vatCheck.isSatisfied(new TestEntity("US", true), INVALID_VAT, null, null));
        assertTrue(vatCheck.isSatisfied(new TestEntity("US", true), VALID_FINNISH_VAT_CODE, null, null));
    }

    @Test
    public void testWithEuCountry() {
        VatIdCheck vatCheck = new VatIdCheck();
        vatCheck.configure(vatAnnotation);
        assertFalse(vatCheck.isSatisfied(new TestEntity("FI", true), NO_VAT, null, null));
        assertFalse(vatCheck.isSatisfied(new TestEntity("FI", true), INVALID_VAT, null, null));
        assertTrue(vatCheck.isSatisfied(new TestEntity("FI", true), VALID_FINNISH_VAT_CODE, null, null));
    }

    @Test
    public void testWithEuCountryVatValidationIgnored() {
        VatIdCheck vatCheck = new VatIdCheck();
        vatCheck.configure(vatAnnotationIgnoreValidation);
        assertTrue(vatCheck.isSatisfied(new TestEntity("FI", true), NO_VAT, null, null));
        assertTrue(vatCheck.isSatisfied(new TestEntity("FI", true), INVALID_VAT, null, null));
        assertTrue(vatCheck.isSatisfied(new TestEntity("FI", true), VALID_FINNISH_VAT_CODE, null, null));
    }

    private static class TestEntity {
        @SuppressWarnings("unused")
        private String countryCode;
        @SuppressWarnings("unused")
        private boolean ignoreValidation;

        private TestEntity(String countryCode, boolean ignoreValidation) {
            this.countryCode = countryCode;
            this.ignoreValidation = ignoreValidation;
        }
    }
}
