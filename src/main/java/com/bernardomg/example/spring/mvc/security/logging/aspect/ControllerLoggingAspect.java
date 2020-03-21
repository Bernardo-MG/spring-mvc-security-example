/**
 * Copyright 2019 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bernardomg.example.spring.mvc.security.logging.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Logging aspect for controllers. Will log arguments and returned values.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Component
@Aspect
public class ControllerLoggingAspect {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ControllerLoggingAspect.class);

    public ControllerLoggingAspect() {
        super();
    }

    /**
     * Logs the returned value after the method is called.
     * 
     * @param joinPoint
     *            point where the aspect is applied
     * @param returnValue
     *            returned value
     */
    @AfterReturning(
            value = "execution(* com.bernardomg.example..*Controller*.*(..))",
            returning = "returnValue")
    public void afterCall(final JoinPoint joinPoint, final Object returnValue) {
        LOGGER.debug("Called {} and returning {}",
                joinPoint.getSignature().toShortString(), returnValue);
    }

    /**
     * Logs the received arguments before the method is called.
     * 
     * @param joinPoint
     *            point where the aspect is applied
     */
    @Before(value = "execution(* com.bernardomg.tabletop.painting..*Controller*.*(..))",
            argNames = "joinPoint")
    public void beforeCall(final JoinPoint joinPoint) {
        LOGGER.debug("Calling {} with arguments {}",
                joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

}
