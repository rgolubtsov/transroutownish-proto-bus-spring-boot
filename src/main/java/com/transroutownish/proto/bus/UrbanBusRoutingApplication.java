/*
 * src/main/java/com/transroutownish/proto/bus/UrbanBusRoutingApplication.java
 * ============================================================================
 * Urban bus routing microservice prototype. Version 0.0.1
 * ============================================================================
 * A Spring Boot-based application, designed and intended to be run
 * as a microservice, implementing a simple urban bus routing prototype.
 * ============================================================================
 * Written by Radislav (Radicchio) Golubtsov, 2021
 *
 * (See the LICENSE file at the top of the source tree.)
 */

package com.transroutownish.proto.bus;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

/**
 * The startup class of the microservice.
 *
 * @version 0.0.1
 * @since   0.0.1
 */
@SpringBootApplication
public class UrbanBusRoutingApplication {
    /**
     * The microservice entry point.
     *
     * @param args The array of command-line arguments.
     */
    public static void main(final String[] args) {
        // Starting up the app.
        SpringApplication.run(UrbanBusRoutingApplication.class, args);
    }
}

// vim:set nu et ts=4 sw=4:
