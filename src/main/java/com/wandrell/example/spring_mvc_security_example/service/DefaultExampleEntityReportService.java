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

package com.wandrell.example.spring_mvc_security_example.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import com.wandrell.example.spring_mvc_security_example.model.persistence.DefaultExampleEntity;

/**
 * Default implementation of the report service.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
public final class DefaultExampleEntityReportService
        implements ExampleEntityReportService {

    /**
     * Default constructor.
     */
    public DefaultExampleEntityReportService() {
        super();
    }

    @Override
    public final JasperPrint
            getReport(final Iterable<DefaultExampleEntity> data) {
        final File reportFile;
        final JasperReport jasperReport;
        final JasperPrint jasperPrint;
        final Map<String, Object> parameters;

        // TODO: The file should be received as a configuration value
        try {
            reportFile = new ClassPathResource("/report/entities.jasper")
                    .getFile();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        if (!reportFile.exists()) {
            // TODO: Compile report
        }

        try {
            jasperReport = (JasperReport) JRLoader
                    .loadObjectFromFile(reportFile.getPath());
        } catch (final JRException e) {
            throw new RuntimeException(e);
        }

        parameters = new HashMap<>();
        // TODO: Internationalize
        parameters.put("ReportTitle", "Report");

        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                    new JRBeanCollectionDataSource(
                            IteratorUtils.toList(data.iterator())));
        } catch (final JRException e) {
            throw new RuntimeException(e);
        }

        return jasperPrint;
    }

}
