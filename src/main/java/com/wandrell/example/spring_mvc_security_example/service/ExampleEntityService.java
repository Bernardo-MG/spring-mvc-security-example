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

import com.wandrell.example.spring_mvc_security_example.model.persistence.DefaultExampleEntity;
import com.wandrell.example.spring_mvc_security_example.model.ExampleEntity;

/**
 * Service for the example entity domain.
 * <p>
 * This is a domain service just to allow the endpoints querying the entities
 * they are asked for.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public interface ExampleEntityService {

    /**
     * Persists an entity.
     * 
     * @param entity
     *            entity to persist
     * @return the persisted entity
     */
    public ExampleEntity add(final DefaultExampleEntity entity);

    /**
     * Returns an entity with the given id.
     * <p>
     * If no instance exists with that id then an entity with a negative id is
     * expected to be returned. Avoid returning nulls.
     *
     * @param identifier
     *            identifier of the entity to find
     * @return the entity for the given id
     */
    public ExampleEntity findById(final Integer identifier);

    /**
     * Returns all the entities from the DB.
     * 
     * @return the persisted entities
     */
    public Iterable<DefaultExampleEntity> getAllEntities();

    /**
     * Removes an entity from persistence.
     * 
     * @param entity
     *            entity to remove
     */
    public void remove(final DefaultExampleEntity entity);

}
