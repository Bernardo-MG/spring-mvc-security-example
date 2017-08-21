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

package com.wandrell.example.spring.mvc.security.controller.report;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wandrell.example.spring.mvc.security.service.ExampleEntityReportService;
import com.wandrell.example.spring.mvc.security.service.ExampleEntityService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Controller for generating reports.
 * <p>
 * This serves as an adapter between the UI and the services layer.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Controller
@RequestMapping("/entity")
public class ReportController {

    /**
     * Default report file name.
     */
    private static final String              FILENAME = "EntityReport";

    /**
     * Example entity report service.
     */
    private final ExampleEntityReportService exampleEntityReportService;

    /**
     * Example entity service.
     */
    private final ExampleEntityService       exampleEntityService;

    /**
     * Constructs a controller with the specified dependencies.
     * 
     * @param entityService
     *            example entity service
     * @param reportService
     *            report service
     */
    @Autowired
    public ReportController(final ExampleEntityService entityService,
            final ExampleEntityReportService reportService) {
        super();

        exampleEntityService = checkNotNull(entityService,
                "Received a null pointer as service");
        exampleEntityReportService = checkNotNull(reportService,
                "Received a null pointer as report service");
    }

    /**
     * Generates a PDF report and returns it in the response.
     * 
     * @param model
     *            model
     * @param request
     *            HTTP request
     * @param response
     *            HTTP response
     * @throws JRException
     *             if there is a problem during the report generation
     * @throws IOException
     *             if there is a problem when streaming into the response
     */
    @GetMapping(path = "/pdf")
    public final void getPdfReport(final Model model,
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws JRException, IOException {
        final JasperPrint jasperPrint;

        jasperPrint = getExampleEntityReportService()
                .getReport(getExampleEntityService().getAllEntities());

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition",
                String.format("inline; filename=%s.pdf", FILENAME));

        JasperExportManager.exportReportToPdfStream(jasperPrint,
                response.getOutputStream());
    }

    /**
     * Returns the report service.
     * 
     * @return the report service
     */
    private final ExampleEntityReportService getExampleEntityReportService() {
        return exampleEntityReportService;
    }

    /**
     * Returns the example entity service.
     * 
     * @return the example entity service
     */
    private final ExampleEntityService getExampleEntityService() {
        return exampleEntityService;
    }

}
