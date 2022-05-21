/*
 * src/test/java/com/transroutownish/proto/bus/UrbanBusRoutingAppTests.java
 * ============================================================================
 * Urban bus routing microservice prototype. Version 0.20.1
 * ============================================================================
 * A Spring Boot-based application, designed and intended to be run
 * as a microservice, implementing a simple urban bus routing prototype.
 * ============================================================================
 * Copyright (C) 2021-2022 Radislav (Radicchio) Golubtsov
 *
 * (See the LICENSE file at the top of the source tree.)
 */

package com.transroutownish.proto.bus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static com.transroutownish.proto.bus.UrbanBusRoutingControllerHelper.*;

/**
 * The test class for the microservice.
 *
 * @version 0.20.1
 * @since   0.0.1
 */
@SpringBootTest
class UrbanBusRoutingAppTests {
    /** The SLF4J logger. */
    private static final Logger l = LoggerFactory.getLogger(
        MethodHandles.lookup().lookupClass()
    );

    /** Dummy for a while... Does nothing. */
    @Test
    void contextLoads() { l.debug(BRACES, BRACES); }
}

// vim:set nu et ts=4 sw=4:
