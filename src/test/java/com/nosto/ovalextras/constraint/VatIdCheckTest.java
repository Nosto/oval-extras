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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.Test;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VatIdCheckTest {
    private static final String NO_VAT = null;
    private static final String INVALID_VAT = "123";
    //ToDo double check if this should be public
    private static final String VALID_FINNISH_VAT_CODE = "24189119";

    private final VatId vatAnnotation = new VatId() {
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

        @Override
        public boolean equals(Object o) {
            return EqualsBuilder.reflectionEquals(this, o);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
    };
    
    private final VatId vatAnnotationIgnoreValidation = new VatId() {
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

        @Override
        public boolean equals(Object o) {
            return EqualsBuilder.reflectionEquals(this, o);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
    };

    @Test
    public void testWithNonEuCountry() {
        VatIdCheck vatCheck = new VatIdCheck();
        vatCheck.configure(vatAnnotation);
        assertTrue(vatCheck.isSatisfied(new TestEntity("US", true), NO_VAT, null));
        assertTrue(vatCheck.isSatisfied(new TestEntity("US", true), INVALID_VAT, null));
        assertTrue(vatCheck.isSatisfied(new TestEntity("US", true), VALID_FINNISH_VAT_CODE, null));
    }

    @Test
    public void testWithEuCountry() {
        VatIdCheck vatCheck = new VatIdCheck();
        vatCheck.configure(vatAnnotation);
        assertFalse(vatCheck.isSatisfied(new TestEntity("FI", true), NO_VAT, null));
        assertFalse(vatCheck.isSatisfied(new TestEntity("FI", true), INVALID_VAT, null));
        assertTrue(vatCheck.isSatisfied(new TestEntity("FI", true), VALID_FINNISH_VAT_CODE,  null));
    }

    @Test
    public void testWithEuCountryVatValidationIgnored() {
        VatIdCheck vatCheck = new VatIdCheck();
        vatCheck.configure(vatAnnotationIgnoreValidation);
        assertTrue(vatCheck.isSatisfied(new TestEntity("FI", true), NO_VAT, null));
        assertTrue(vatCheck.isSatisfied(new TestEntity("FI", true), INVALID_VAT, null));
        assertTrue(vatCheck.isSatisfied(new TestEntity("FI", true), VALID_FINNISH_VAT_CODE, null, null));
    }

    private static class TestEntity {
        @SuppressWarnings({"unused", "FieldCanBeLocal"})
        private final String countryCode;
        @SuppressWarnings({"unused", "FieldCanBeLocal"})
        private final boolean ignoreValidation;

        private TestEntity(String countryCode, boolean ignoreValidation) {
            this.countryCode = countryCode;
            this.ignoreValidation = ignoreValidation;
        }
    }
}
