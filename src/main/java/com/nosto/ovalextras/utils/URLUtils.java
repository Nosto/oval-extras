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

import okhttp3.HttpUrl;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.regex.Pattern;

public class URLUtils {

    private static final UrlValidator VALIDATOR = new UrlValidator();
    private static final Pattern DOUBLE_SLASH = Pattern.compile("([^:])//");

    private URLUtils() {}

    public static boolean isValid(String url) {
        try {
            if (url.startsWith("//")) {
                url = "http:" + url;
            } else if (url.startsWith("://")) {
                url = "http" + url;
            }

            HttpUrl httpUrl = HttpUrl.parse(url);
            return httpUrl != null && VALIDATOR.isValid(removeDoubleSlashesFromPath(httpUrl.uri().toString()));
        } catch (Exception e) {
            return false;
        }
    }

    private static String removeDoubleSlashesFromPath(String url) {
        return DOUBLE_SLASH.matcher(url).replaceAll("$1/");
    }
}
