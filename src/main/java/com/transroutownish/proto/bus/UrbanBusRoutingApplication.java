/*
 * src/main/java/com/transroutownish/proto/bus/UrbanBusRoutingApplication.java
 * ============================================================================
 * Urban bus routing microservice prototype. Version 0.9.9
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

import java.io.File;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import static com.transroutownish.proto.bus.UrbanBusRoutingControllerHelper.*;

/**
 * The startup class of the microservice.
 *
 * @version 0.9.9
 * @since   0.0.1
 */
@SpringBootApplication
public class UrbanBusRoutingApplication {
    /** The path and filename of the sample routes data store. */
    private static final String SAMPLE_ROUTES = "./data/routes.txt";

    /**
     * The regex pattern for the element to be excluded
     * from a bus stops sequence: it is an arbitrary identifier
     * of a route, which is not used in the routes processing anyhow.
     */
    private static final String ROUTE_ID_REGEX = "^\\d+";

    /** The list, containing all available routes. */
    public static List<String> routes_list;

    /**
     * The microservice entry point.
     *
     * @param args The array of command-line arguments.
     */
    public static void main(final String[] args) {
        Scanner routes = null;

        // Getting the path and filename of the routes data store
        // from application properties.
        String datastore = get_routes_datastore();

        if (datastore == null) { datastore = SAMPLE_ROUTES; }

        File data = new File(datastore);

        try {
            routes = new Scanner(data);
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }

        routes_list = new ArrayList();

        while (routes.hasNextLine()) {
            routes_list.add(routes.nextLine()
                .replaceFirst(ROUTE_ID_REGEX, EMPTY_STRING) + SPACE);
        }

        routes.close();

        // Starting up the app.
        SpringApplication.run(UrbanBusRoutingApplication.class, args);
    }
}

// vim:set nu et ts=4 sw=4:
