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

package com.wandrell.example.spring_mvc_security_example.test.unit.controller.form;

import java.util.Collection;
import java.util.LinkedList;

import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wandrell.example.spring_mvc_security_example.controller.entity.ExampleEntityFormController;
import com.wandrell.example.spring_mvc_security_example.controller.entity.ExampleEntityViewConstants;
import com.wandrell.example.spring_mvc_security_example.model.persistence.DefaultExampleEntity;
import com.wandrell.example.spring_mvc_security_example.service.ExampleEntityService;
import com.wandrell.example.spring_mvc_security_example.test.config.UrlConfig;

/**
 * Unit tests for {@link ExampleEntityFormController}, checking the methods for
 * sending the form data.
 * <p>
 * These tests send data with missing values, to validate that the controller
 * handles binding error cases.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class TestExampleEntityFormControllerMissingData {

    /**
     * Mocked MVC context.
     */
    private MockMvc mockMvc;

    /**
     * Default constructor.
     */
    public TestExampleEntityFormControllerMissingData() {
        super();
    }

    /**
     * Sets up the mocked MVC context.
     * <p>
     * It expects all the responses to have the OK (200) HTTP code.
     */
    @BeforeTest
    public final void setUpMockContext() {
        mockMvc = MockMvcBuilders.standaloneSetup(getController())
                .alwaysExpect(MockMvcResultMatchers.status().isOk()).build();
    }

    /**
     * Verifies that after receiving form data missing the name, which is a
     * required field, this is marked as an error.
     */
    @Test
    public final void testSendFormData_NoName_ExpectedAttributeModel()
            throws Exception {
        final ResultActions result; // Request result

        result = mockMvc.perform(getFormRequest());

        // The response model contains the expected attributes
        result.andExpect(MockMvcResultMatchers.model()
                .attributeExists(ExampleEntityViewConstants.BEAN_FORM));

        // The response contains the expected errors
        result.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors(
                ExampleEntityViewConstants.BEAN_FORM, "name"));
    }

    /**
     * Verifies that after receiving form data missing the name, which is a
     * required field, the view is again the form view.
     */
    @Test
    public final void testSendFormData_NoName_NoViewChange() throws Exception {
        final ResultActions result; // Request result

        result = mockMvc.perform(getFormRequest());

        // The view is valid
        result.andExpect(MockMvcResultMatchers.view()
                .name(ExampleEntityViewConstants.VIEW_ENTITY_FORM));
    }

    /**
     * Returns a controller with mocked dependencies.
     * 
     * @return a mocked controller
     */
    private final ExampleEntityFormController getController() {
        final ExampleEntityService service; // Mocked service
        final Collection<DefaultExampleEntity> entities; // Mocked entities

        service = Mockito.mock(ExampleEntityService.class);

        entities = new LinkedList<>();

        Mockito.when(service.getAllEntities()).thenReturn(entities);

        return new ExampleEntityFormController(service);
    }

    /**
     * Returns a request builder for posting the form data.
     * <p>
     * This request is missing all the required request parameters.
     * <p>
     * There is only a single required parameter, the {@code name} parameter.
     * 
     * @return a request builder for posting the form data
     */
    private final RequestBuilder getFormRequest() {
        return MockMvcRequestBuilders.post(UrlConfig.URL_FORM_POST);
    }

}
