/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017-2018 the original author or authors.
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

package com.bernardomg.example.spring.mvc.security.controller.secured;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bernardomg.example.spring.mvc.security.service.RoleSecuredService;

/**
 * Secured controller.
 * <p>
 * It makes use of role-based security.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Controller
@RequestMapping("/secured")
public final class RoleSecuredController {

    /**
     * Secured service.
     */
    private final RoleSecuredService service;

    /**
     * Constructs a login controller.
     * 
     * @param securedService
     *            secured service
     */
    @Autowired
    public RoleSecuredController(final RoleSecuredService securedService) {
        super();

        service = checkNotNull(securedService,
                "Received a null pointer as secured service");
    }

    /**
     * Shows a page only accessible to the admin.
     * 
     * @param model
     *            data model
     * @return the admin view
     */
    @GetMapping("/admin")
    public final String showAdminPage(final Model model) {
        getService().adminMethod();

        return SecuredViews.ADMIN;
    }

    /**
     * Returns the secured service.
     * 
     * @return the secured service
     */
    private final RoleSecuredService getService() {
        return service;
    }

}
