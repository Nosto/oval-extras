/*******************************************************************************
 * Copyright (c) 2025 Nosto Solutions Ltd All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Nosto Solutions Ltd ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the agreement you entered into with
 * Nosto Solutions Ltd.
 ******************************************************************************/
package com.nosto.ovalextras;

import com.nosto.ovalextras.constraint.ValidateNestedPropertyCheck;
import net.sf.oval.AbstractCheck;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.localization.message.ResourceBundleMessageResolver;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.notNullValue;

public class ValidationMessagesTest {

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    /**
     * Checks that every constraint has a message in case of violation.
     */
    @Test
    public void constraintsHaveViolationMessages() {
        final ResourceBundleMessageResolver resolver = (ResourceBundleMessageResolver) Validator.getMessageResolver();
        resolver.addMessageBundle(ResourceBundle.getBundle("com/nosto/ovalextras/MessagesResolverTest", new Locale("en")));

        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("com.nosto")));
        Set<Class<? extends AbstractCheck>> annotations = reflections.getSubTypesOf(AbstractAnnotationCheck.class)
                .stream()
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .collect(Collectors.toSet());

        annotations.stream()
                // Nested property check is filtered out since it only delegates the checks
                .filter(aClass -> !ValidateNestedPropertyCheck.class.equals(aClass))
                .forEach(aClass -> {
                    try {
                        String key = aClass.getDeclaredConstructor().newInstance().getMessage();
                        String message = resolver.getMessage(key);
                        errorCollector.checkThat("No violation message for " + key, message, notNullValue());
                    } catch (Exception e) {
                        errorCollector.addError(e);
                    }
                });
    }

}
