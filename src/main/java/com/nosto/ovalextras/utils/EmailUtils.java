/*******************************************************************************
 * Copyright (c) 2018 Nosto Solutions Ltd All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Nosto Solutions Ltd ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the agreement you entered into with
 * Nosto Solutions Ltd.
 ******************************************************************************/
package com.nosto.ovalextras.utils;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Email utility class which contains a number of different
 * small convenience utility functions for mail sending.
 *
 * <p>
 * There are also ErrorEmailUtils which is closely related to this
 * class.
 * </p>
 */
public class EmailUtils {


    private EmailUtils() {}

    /**
     * Splits emails by new line
     * @param emails new line separated list of emails
     * @return set of emails
     */
    public static Set<String> splitByNewLine(@Nullable String emails) {
        if (StringUtils.isBlank(emails)) {
            return Collections.emptySet();
        }

        return Arrays.stream(emails.split("\n")).map(String::trim).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
    }

}
