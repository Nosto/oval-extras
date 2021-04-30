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

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;

public class PhoneCheck extends AbstractAnnotationCheck<Phone> {

    @Override
    public boolean isSatisfied(final Object validatedObject, final Object value, final ValidationCycle cycle) throws OValException {
        if (value == null) {
            return true;
        } else {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            try {
                Phonenumber.PhoneNumber number = phoneUtil.parse(value.toString(), "FI");
                return phoneUtil.isValidNumber(number);
            } catch (NumberParseException e) {
                return false;
            }
        }
    }
}
