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

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.OValException;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Custom validation class for Play that validates the list of reportee email
 * addresses.
 *
 * @author mridang
 */
public class EmailsCheck extends AbstractAnnotationCheck<Emails> {

    private static final Pattern PATTERN = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[a-zA-Z0-9](?:[\\w-]*[\\w])?");

    @Override
    public void configure(Emails url) {
        super.configure(url);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isSatisfied(@Nullable Object object, @Nullable Object value, @Nullable OValContext context, @Nullable Validator validator)
            throws OValException {
        if (value == null) {
            return true;
        }

        if (value instanceof String) {
            Set<String> splitEmails = splitByNewLine((String) value);
            return checkEmails(splitEmails);
        } else if (value instanceof Collection) {
            return checkEmails((Collection) value);
        } else {
            return false;
        }
    }

    public static boolean checkEmails(Collection emails) {
        for (Object emailAddress : emails) {
            if (StringUtils.isNotBlank(emailAddress.toString()) && !PATTERN.matcher(emailAddress.toString()).matches()) {
                return false;
            }
        }
        return true;
    }

    private static Set<String> splitByNewLine(@Nullable String emails) {
        if (StringUtils.isBlank(emails)) {
            return Collections.emptySet();
        }

        return Arrays.stream(emails.split("\n")).map(String::trim).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
    }
}
