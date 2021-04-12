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

import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.localization.message.MessageResolver;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.notNullValue;

public class ValidationMessagesTest extends Assert {

    private static MessageResolver messageResolver;
    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void testMessageHandling() {

        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("com.nosto")));
        Set<Class<? extends AbstractAnnotationCheck>> annotations = reflections.getSubTypesOf(AbstractAnnotationCheck.class)
                .stream()
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .collect(Collectors.toSet());

        annotations.forEach(aClass -> {
            try {
                String key = aClass.newInstance().getMessage();
                String message = messageResolver.getMessage(key);
                errorCollector.checkThat("No localisation for " + key, message, notNullValue());
            } catch (Exception e) {
                errorCollector.addError(e);
            }
        });

    }

}
