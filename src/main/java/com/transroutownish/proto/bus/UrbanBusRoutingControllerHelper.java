/*
 * src/main/java/com/transroutownish/proto/bus/
 * UrbanBusRoutingControllerHelper.java
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

import java.io.InputStream;

import java.util.Properties;

/**
 * The helper class for the controller and the related ones.
 *
 * @version 0.9.9
 * @since   0.0.1
 */
public class UrbanBusRoutingControllerHelper {
    // Helper constants.
    public static final String EMPTY_STRING =   "";
    public static final String SLASH        =  "/";
    public static final String EQUALS       =  "=";
    public static final String BRACES       = "{}";
    public static final String SPACE        =  " ";
    public static final String V_BAR        =  "|";

    // Common error messages.
    public static final String ERR_REQ_PARAMS_MUST_BE_POSITIVE_INTS
        = "Request parameters must take positive integer values, "
        + "in the range 1 .. 2,147,483,647. Please check your inputs.";

    /** The application properties filename. */
    private static final String APP_PROPS = "application.properties";

    // Application properties keys of the routes data store.
    private static final String PATH_PREFIX = "routes.datastore.path.prefix";
    private static final String PATH_DIR    = "routes.datastore.path.dir";
    private static final String FILENAME    = "routes.datastore.filename";

    /**
     * Retrieves the path and filename of the routes data store
     * from application properties.
     *
     * @return The path and filename of the routes data store
     *         or <code>null</code>, if they are not defined.
     */
    public static String get_routes_datastore() {
        String datastore = EMPTY_STRING;

        Properties props = new Properties();

        ClassLoader loader
            = UrbanBusRoutingControllerHelper.class.getClassLoader();

        InputStream data = loader.getResourceAsStream(APP_PROPS);

        try {
            props.load(data);
            data.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        String path_prefix = props.getProperty(PATH_PREFIX);
        String path_dir    = props.getProperty(PATH_DIR   );
        String filename    = props.getProperty(FILENAME   );

        if (path_prefix != null) { datastore += path_prefix; }
        if (path_dir    != null) { datastore += path_dir;    }
        if (filename    != null) { datastore += filename;    }

        if (datastore.isEmpty()) { return null; }

        return datastore;
    }
}

// vim:set nu et ts=4 sw=4:
