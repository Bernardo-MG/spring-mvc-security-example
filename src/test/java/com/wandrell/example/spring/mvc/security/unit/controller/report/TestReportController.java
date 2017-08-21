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

package com.wandrell.example.spring.mvc.security.unit.controller.report;

import java.util.ArrayList;

import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wandrell.example.spring.mvc.security.controller.report.ReportController;
import com.wandrell.example.spring.mvc.security.model.persistence.DefaultExampleEntity;
import com.wandrell.example.spring.mvc.security.service.DefaultExampleEntityReportService;
import com.wandrell.example.spring.mvc.security.service.ExampleEntityReportService;
import com.wandrell.example.spring.mvc.security.service.ExampleEntityService;

/**
 * Unit tests for {@link ReportController}, checking the methods for generating
 * reports.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class TestReportController {

    /**
     * PDF view URL.
     */
    private static final String URL_PDF = "/entity/pdf";

    /**
     * Mocked MVC context.
     */
    private MockMvc             mockMvc;

    /**
     * Default constructor.
     */
    public TestReportController() {
        super();
    }

    /**
     * Sets up the mocked MVC context.
     */
    @BeforeTest
    public final void setUpMockContext() {
        mockMvc = MockMvcBuilders.standaloneSetup(getController()).build();
    }

    /**
     * Verifies that the PDF view sets the expected attributes.
     */
    @Test
    public final void testPdf_ExpectedAttributeModel() throws Exception {
        final ResultActions result; // Request result

        result = mockMvc.perform(getRequest());

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status().isOk());

        // The response indicates it is a PDF
        result.andExpect(MockMvcResultMatchers.content()
                .contentType(MediaType.APPLICATION_PDF));
    }

    /**
     * Returns a controller with mocked dependencies.
     * 
     * @return a mocked controller
     */
    private final ReportController getController() {
        final ExampleEntityService service; // Mocked unit codex
        final ExampleEntityReportService reportService; // Mocked unit codex
        final Iterable<DefaultExampleEntity> entities;

        entities = new ArrayList<DefaultExampleEntity>();

        service = Mockito.mock(ExampleEntityService.class);
        Mockito.when(service.getAllEntities()).thenReturn(entities);

        reportService = new DefaultExampleEntityReportService();

        return new ReportController(service, reportService);
    }

    /**
     * Returns a request builder for getting the PDF view.
     * 
     * @return a request builder for the PDF view
     */
    private final RequestBuilder getRequest() {
        return MockMvcRequestBuilders.get(URL_PDF);
    }

}
