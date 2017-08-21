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

package com.wandrell.example.spring.mvc.security.unit.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wandrell.example.spring.mvc.security.controller.entity.bean.ExampleEntityForm;

/**
 * Unit tests for {@link ExampleEntityForm} bean validation.
 * <p>
 * These tests verify that the bean applies the correct Java validation
 * annotations.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class TestExampleEntityFormValidation {

    /**
     * Validator for validating the bean.
     */
    private Validator validator;

    /**
     * Default constructor.
     */
    public TestExampleEntityFormValidation() {
        super();
    }

    /**
     * Sets up the validator for the tests.
     */
    @BeforeTest
    public final void setUpValidator() {
        validator = createValidator();
    }

    /**
     * Verifies that if the name is empty this field is marked with an error.
     */
    @Test
    public final void testValidation_EmptyName_Error() {
        final ExampleEntityForm form; // Tested form
        final Set<ConstraintViolation<ExampleEntityForm>> errors;
        final ConstraintViolation<ExampleEntityForm> error;

        form = new ExampleEntityForm();

        form.setName("");

        errors = validator.validate(form);

        Assert.assertEquals(errors.size(), 1);

        error = errors.iterator().next();
        Assert.assertEquals(error.getPropertyPath().toString(), "name");
    }

    /**
     * Verifies that if the name is null this field is marked with an error.
     */
    @Test
    public final void testValidation_NullName_Error() {
        final ExampleEntityForm form; // Tested form
        final Set<ConstraintViolation<ExampleEntityForm>> errors;
        final ConstraintViolation<ExampleEntityForm> error;

        form = new ExampleEntityForm();

        // form.setName(null);

        errors = validator.validate(form);

        Assert.assertEquals(errors.size(), 1);

        error = errors.iterator().next();
        Assert.assertEquals(error.getPropertyPath().toString(), "name");
    }

    /**
     * Returns the validator to use in the tests.
     * 
     * @return the validator to use in the tests
     */
    private final Validator createValidator() {
        final LocalValidatorFactoryBean localValidatorFactoryBean;

        localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();

        return localValidatorFactoryBean;
    }

}
