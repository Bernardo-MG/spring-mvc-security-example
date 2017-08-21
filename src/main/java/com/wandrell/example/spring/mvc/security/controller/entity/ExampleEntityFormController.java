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

package com.wandrell.example.spring.mvc.security.controller.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wandrell.example.spring.mvc.security.controller.entity.bean.ExampleEntityForm;
import com.wandrell.example.spring.mvc.security.model.persistence.DefaultExampleEntity;
import com.wandrell.example.spring.mvc.security.service.ExampleEntityService;

/**
 * Controller for the example entities form view.
 * <p>
 * This serves as an adapter between the UI and the services layer.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Controller
@RequestMapping("/entity")
public class ExampleEntityFormController {

    /**
     * Example entity service.
     */
    private final ExampleEntityService exampleEntityService;

    /**
     * Constructs a controller with the specified dependencies.
     * 
     * @param service
     *            example entity service
     */
    @Autowired
    public ExampleEntityFormController(final ExampleEntityService service) {
        super();

        exampleEntityService = checkNotNull(service,
                "Received a null pointer as service");
    }

    /**
     * Returns the initial Sponsor form data.
     * 
     * @return the initial Sponsor form data
     */
    @ModelAttribute(ExampleEntityViewConstants.BEAN_FORM)
    public final ExampleEntityForm getEntityForm() {
        return new ExampleEntityForm();
    }

    /**
     * Persists an entity.
     * 
     * @param model
     *            model map
     * @param form
     *            form data
     * @param bindingResult
     *            binding result
     * @param session
     *            HTTP session
     * @return the next view to show
     */
    @PostMapping
    public final String saveEntity(final ModelMap model,
            @ModelAttribute(ExampleEntityViewConstants.BEAN_FORM) @Valid final ExampleEntityForm form,
            final BindingResult bindingResult, final HttpSession session) {
        final String path;
        final DefaultExampleEntity entity;

        if (bindingResult.hasErrors()) {
            // Invalid form data

            // Returns to the form view
            path = ExampleEntityViewConstants.VIEW_ENTITY_FORM;
            // TODO: Maybe it should return a bad request status?
        } else {

            entity = new DefaultExampleEntity();
            entity.setName(form.getName());

            getExampleEntityService().add(entity);

            // TODO: This flow decision shouldn't be handled by the controller
            // TODO: This should be a redirection to the list controller
            // Loads required data into the model and session
            loadViewModel(model);

            path = ExampleEntityViewConstants.VIEW_ENTITY_LIST;
        }

        return path;
    }

    /**
     * Shows the entity edition view.
     * <p>
     * Actually it just returns the name of the view. Spring will take care of
     * the rest.
     * 
     * @return the name for the entity edition view
     */
    @GetMapping(path = "/edit")
    public final String showEntityForm() {
        return ExampleEntityViewConstants.VIEW_ENTITY_FORM;
    }

    /**
     * Returns the example entity service.
     * 
     * @return the example entity service
     */
    private final ExampleEntityService getExampleEntityService() {
        return exampleEntityService;
    }

    /**
     * Loads the model data required for the entities listing view.
     * <p>
     * As the view will list all the entities, it requires these entities as one
     * of the parameters.
     * 
     * @param model
     *            model map
     */
    private final void loadViewModel(final ModelMap model) {
        model.put(ExampleEntityViewConstants.PARAM_ENTITIES,
                getExampleEntityService().getAllEntities());
    }

}
