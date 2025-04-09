/*******************************************************************************
 * Copyright (c) 2025 Nosto Solutions Ltd All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Nosto Solutions Ltd ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the agreement you entered into with
 * Nosto Solutions Ltd.
 ******************************************************************************/
package com.nosto.ovalextras.constraint;

import net.sf.oval.constraint.Size;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.List;

import static org.junit.Assert.assertTrue;

@SuppressWarnings({"unused", "NullAway"})
public class ValidNestedPropertyCheckTest extends AbstractContraintsTest {

    @Test
    public void validateNestedProperty() {
        ValidNestedPropertyCheck check = new ValidNestedPropertyCheck();
        super.testCheck(check);

        NestedClass nestedObj = new NestedClass(null, "Valid String", List.of("one", "two"), List.of("1", "2"));
        MainClass mainObj = new MainClass(nestedObj, List.of());
        assertTrue(validator.validate(nestedObj).isEmpty());
        assertTrue(validator.validate(mainObj).isEmpty());
    }

    @Test
    public void validateInvalidNestedProperty() {
        NestedClass nestedObj = new NestedClass(null, null, List.of("two", "three"), List.of("1", "2", "3"));
        MainClass mainObj = new MainClass(nestedObj, List.of());
        assertNestedViolations(
                List.of(
                        "com.nosto.ovalextras.constraint.Required",
                        "com.nosto.ovalextras.constraint.Contains",
                        "net.sf.oval.constraint.Size"),
                mainObj,
                nestedObj
        );
    }

    @Test
    public void validateNestedList() {
        List<NestedClass> nestedList = List.of(
                new NestedClass(null, "Valid String", List.of("one", "two"), List.of("1", "2")),
                new NestedClass(null, "Another valid String", List.of("one", "two", "three"), List.of("2", "3")));
        MainClass mainObj = new MainClass(null, nestedList);
        assertTrue(validator.validate(mainObj).isEmpty());
    }

    @Test
    public void validateInvalidNestedList() {
        List<NestedClass> nestedList = List.of(
                new NestedClass(null, null, List.of("one", "two"), List.of("1", "2")),
                new NestedClass(null, "Valid String", List.of("two", "three"), List.of("1", "2")),
                new NestedClass(null, "Valid String", List.of("one", "two"), List.of("1", "2", "3")));
        MainClass mainObj = new MainClass(null, nestedList);
        assertViolations(
                List.of(
                        "com.nosto.ovalextras.constraint.Required",
                        "com.nosto.ovalextras.constraint.Contains",
                        "net.sf.oval.constraint.Size"),
                validator.validate(mainObj));
    }

    private void assertNestedViolations(List<String> expectedViolations, MainClass mainObj, NestedClass nestedObj) {
        assertViolations(expectedViolations, validator.validate(nestedObj));
        assertViolations(expectedViolations, validator.validate(mainObj));
    }

    @SuppressWarnings("FieldCanBeLocal")
    private static class MainClass {
        @ValidNestedProperty
        private final NestedClass nested;
        @ValidNestedProperty
        private final List<NestedClass> nestedList;

        public MainClass(NestedClass nested, List<NestedClass> nestedList) {
            this.nested = nested;
            this.nestedList = nestedList;
        }
    }

    @SuppressWarnings("FieldCanBeLocal")
    private static class NestedClass {
        @Nullable
        private final String notRequiredStr;
        @Required
        private final String requiredStr;
        @Required
        @Contains(values = {"one", "two"})
        private final List<String> itemsToContain;
        @Size(min = 1, max = 2)
        private final List<String> itemsOfSize;

        public NestedClass(@Nullable String notRequiredStr, String requiredStr, List<String> itemsToContain, List<String> itemsOfSize) {
            this.notRequiredStr = notRequiredStr;
            this.requiredStr = requiredStr;
            this.itemsToContain = itemsToContain;
            this.itemsOfSize = itemsOfSize;
        }
    }
}