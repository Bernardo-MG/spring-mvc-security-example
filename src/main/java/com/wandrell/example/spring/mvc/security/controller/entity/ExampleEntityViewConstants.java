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

/**
 * Constants for the example entity view controllers.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class ExampleEntityViewConstants {

    /**
     * Form bean parameter name.
     */
    public static final String BEAN_FORM        = "form";

    /**
     * Entities parameter name.
     */
    public static final String PARAM_ENTITIES   = "entities";

    /**
     * Name for the entity form.
     */
    public static final String VIEW_ENTITY_FORM = "entity/form";

    /**
     * Name for the entities view.
     */
    public static final String VIEW_ENTITY_LIST = "entity/list";

    /**
     * Private constructor to avoid initialization.
     */
    private ExampleEntityViewConstants() {
        super();
    }

}
