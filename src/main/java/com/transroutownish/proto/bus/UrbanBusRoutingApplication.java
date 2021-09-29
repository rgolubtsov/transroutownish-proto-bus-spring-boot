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

import java.io.File;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * The startup class of the microservice.
 *
 * @version 0.0.1
 * @since   0.0.1
 */
@SpringBootApplication
public class UrbanBusRoutingApplication {
    /** The filename of the sample routes data store. */
    private static final String SAMPLE_ROUTES = "./data/routes.txt";

    /** The list, containing all available routes. */
    public static List<String> routes_list;

    /**
     * The microservice entry point.
     *
     * @param args The array of command-line arguments.
     */
    public static void main(final String[] args) {
        Scanner routes = null;

        File data = new File(SAMPLE_ROUTES);

        try {
            routes = new Scanner(data);
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }

        routes_list = new ArrayList();

        while (routes.hasNext()) {
            routes_list.add(routes.nextLine());
        }

        routes.close();

        // Starting up the app.
        SpringApplication.run(UrbanBusRoutingApplication.class, args);
    }
}

// vim:set nu et ts=4 sw=4:
