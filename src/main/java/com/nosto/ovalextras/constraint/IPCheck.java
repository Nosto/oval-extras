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

import com.google.common.net.InetAddresses;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.OValException;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Custom validation class for Play that validates the list of ignored IP
 * addresses.
 *
 * @author mridang
 */
public class IPCheck extends AbstractAnnotationCheck<IP> {

    /**
     * Checks the all the addresses are valid IP addresses. It iterates over
     * each item in the set in uses the Google Commons validator to check them
     */
    @SuppressWarnings({"UnstableApiUsage", "unchecked"})
    public boolean isSatisfied(@Nullable Object object, @Nullable Object value) {
        if (value == null) {
            return true;
        } else {
            Set<String> ignoredAddresses = (HashSet<String>) value;
            for (String ignoredAddress : ignoredAddresses) {
                if (!InetAddresses.isInetAddress(ignoredAddress)) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public boolean isSatisfied(Object object, Object value, OValContext context, Validator validator) throws OValException {
        return isSatisfied(object, value);
    }
}
