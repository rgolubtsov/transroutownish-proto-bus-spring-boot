/*
 * src/main/java/com/transroutownish/proto/bus/UrbanBusRoutingApp.java
 * ============================================================================
 * Urban bus routing microservice prototype. Version 0.15.1
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

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

import java.io.File;

import java.util.Properties;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import static com.transroutownish.proto.bus.UrbanBusRoutingControllerHelper.*;

/**
 * The startup class of the microservice.
 *
 * @version 0.15.1
 * @since   0.0.1
 */
@SpringBootApplication
public class UrbanBusRoutingApp {
    /** The path and filename of the sample routes data store. */
    private static final String SAMPLE_ROUTES = "./data/routes.txt";

    /**
     * The regex pattern for the element to be excluded
     * from a bus stops sequence: it is an arbitrary identifier
     * of a route, which is not used in the routes processing anyhow.
     */
    private static final String ROUTE_ID_REGEX = "^\\d+";

    /** The SLF4J logger. */
    private static final Logger l = LoggerFactory.getLogger(
        MethodHandles.lookup().lookupClass()
    );

    /** The application properties object. */
    public static Properties props;

    /** The list, containing all available routes. */
    public static List<String> routes_list;

    /** The debug logging enabler. */
    public static boolean debug_log_enabled;

    /**
     * The microservice entry point.
     *
     * @param args The array of command-line arguments.
     */
    public static void main(final String[] args) {
        Scanner routes = null;

        // Getting the application properties object.
        props = _get_props();

        // Getting the path and filename of the routes data store
        // from application properties.
        String datastore = get_routes_datastore();

        if (datastore == null) { datastore = SAMPLE_ROUTES; }

        File data = new File(datastore);

        try {
            routes = new Scanner(data);
        } catch (java.io.FileNotFoundException e) {
            l.error(ERR_DATASTORE_NOT_FOUND);
        }

        routes_list = new ArrayList();

        while (routes.hasNextLine()) {
            routes_list.add(routes.nextLine()
                .replaceFirst(ROUTE_ID_REGEX, EMPTY_STRING) + SPACE);
        }

        routes.close();

        // Identifying whether debug logging is enabled.
        debug_log_enabled = is_debug_log_enabled();

        // Starting up the app.
        SpringApplication.run(UrbanBusRoutingApp.class, args);
    }
}

// vim:set nu et ts=4 sw=4:
