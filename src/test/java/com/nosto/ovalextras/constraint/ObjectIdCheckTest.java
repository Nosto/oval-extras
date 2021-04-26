/*
 *  Copyright (c) 2021 Nosto Solutions Ltd All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of
 *  Nosto Solutions Ltd ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the agreement you entered into with
 *  Nosto Solutions Ltd.
 */

package com.nosto.ovalextras.constraint;

import org.bson.types.ObjectId;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ObjectIdCheckTest {

    @Test
    public void testValidObjectId() {
        ObjectIdCheck objectIdCheck = new ObjectIdCheck();
        assertTrue(objectIdCheck.isSatisfied(null, ObjectId.get(), null));
    }

    @Test
    public void testInvalidObjectId() {
        ObjectIdCheck objectIdCheck = new ObjectIdCheck();
        assertFalse(objectIdCheck.isSatisfied(null, "12324", null));
        assertFalse(objectIdCheck.isSatisfied(null, "", null));
        assertFalse(objectIdCheck.isSatisfied(null, "someObjectId", null));
    }
}
