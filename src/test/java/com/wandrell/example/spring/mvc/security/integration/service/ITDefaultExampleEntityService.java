/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.wandrell.example.spring.mvc.security.integration.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.wandrell.example.spring.mvc.security.model.ExampleEntity;
import com.wandrell.example.spring.mvc.security.model.persistence.DefaultExampleEntity;
import com.wandrell.example.spring.mvc.security.service.ExampleEntityService;

import org.testng.Assert;

/**
 * Integration tests for the {@link ExampleEntityService}.
 * <p>
 * As this service doesn't contain any actual business logic, and it just wraps
 * the example entities repository, these tests are for verifying everything is
 * set up correctly and working.
 */
@ContextConfiguration(locations = { "classpath:context/service.xml",
        "classpath:context/persistence.xml",
        "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties",
        "classpath:config/service.properties" })
public final class ITDefaultExampleEntityService
        extends AbstractTransactionalTestNGSpringContextTests {

    /**
     * Service being tested.
     */
    @Autowired
    private ExampleEntityService service;

    /**
     * Default constructor.
     */
    public ITDefaultExampleEntityService() {
        super();
    }

    /**
     * Verifies that the service adds entities into persistence.
     */
    @Test
    public final void testAdd_NotExisting_Added() {
        final DefaultExampleEntity entity; // Entity to add
        final Integer entitiesCount;       // Original number of entities
        final Integer finalEntitiesCount;  // Final number of entities

        entitiesCount = ((Collection<DefaultExampleEntity>) service
                .getAllEntities()).size();

        entity = new DefaultExampleEntity();
        entity.setName("ABC");

        service.add(entity);

        finalEntitiesCount = ((Collection<DefaultExampleEntity>) service
                .getAllEntities()).size();

        Assert.assertEquals(finalEntitiesCount, new Integer(entitiesCount + 1));
    }

    /**
     * Verifies that searching an existing entity by id returns the expected
     * entity.
     */
    @Test
    public final void testFindById_Existing_Valid() {
        final ExampleEntity entity; // Found entity

        entity = service.findById(1);

        Assert.assertEquals(entity.getId(), new Integer(1));
    }

    /**
     * Verifies that searching for a not existing entity by id returns an empty
     * entity.
     */
    @Test
    public final void testFindById_NotExisting_Invalid() {
        final ExampleEntity entity; // Found entity

        entity = service.findById(100);

        Assert.assertEquals(entity.getId(), new Integer(-1));
    }

}
