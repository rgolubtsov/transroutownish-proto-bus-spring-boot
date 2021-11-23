/*
 * src/main/java/com/transroutownish/proto/bus/
 * UrbanBusRoutingControllerHelper.java
 * ============================================================================
 * Urban bus routing microservice prototype. Version 0.12.4
 * ============================================================================
 * A Spring Boot-based application, designed and intended to be run
 * as a microservice, implementing a simple urban bus routing prototype.
 * ============================================================================
 * Copyright (C) 2021 Radislav (Radicchio) Golubtsov
 *
 * (See the LICENSE file at the top of the source tree.)
 */

package com.transroutownish.proto.bus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import java.io.InputStream;

import java.util.Properties;

/**
 * The helper class for the controller and the related ones.
 *
 * @version 0.12.4
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

    // Extra helper constants.
    private static final String YES = "yes";

    // Common error messages.
    public static final String ERR_DEBUG_LOG_UNABLE_TO_ACTIVATE
        = "Unable to activate debug log.";
    public static final String ERR_DATASTORE_NOT_LOADED_USE_DEFAULT
        = "Data store specified could not be loaded. "
        + "Using default data store.";
    public static final String ERR_DATASTORE_NOT_FOUND
        = "FATAL: Data store file not found. Quitting...";
    public static final String ERR_REQ_PARAMS_MUST_BE_POSITIVE_INTS
        = "Request parameters must take positive integer values, "
        + "in the range 1 .. 2,147,483,647. Please check your inputs.";

    /** The application properties filename. */
    private static final String APP_PROPS = "application.properties";

    // Application properties keys of the logger.
    private static final String DEBUG_LOG_ENABLED = "logger.debug.enabled";

    // Application properties keys of the routes data store.
    private static final String PATH_PREFIX = "routes.datastore.path.prefix";
    private static final String PATH_DIR    = "routes.datastore.path.dir";
    private static final String FILENAME    = "routes.datastore.filename";

    /** The SLF4J logger. */
    private static final Logger l = LoggerFactory.getLogger(
        MethodHandles.lookup().lookupClass()
    );

    /**
     * Retrieves the path and filename of the routes data store
     * from application properties.
     *
     * @return The path and filename of the routes data store
     *         or <code>null</code>, if they are not defined.
     */
    public static String get_routes_datastore() {
        String datastore = EMPTY_STRING;

        Properties props = _get_props(ERR_DATASTORE_NOT_LOADED_USE_DEFAULT);

        String path_prefix = props.getProperty(PATH_PREFIX);
        String path_dir    = props.getProperty(PATH_DIR   );
        String filename    = props.getProperty(FILENAME   );

        if (path_prefix != null) { datastore += path_prefix; }
        if (path_dir    != null) { datastore += path_dir;    }
        if (filename    != null) { datastore += filename;    }

        if (datastore.isEmpty()) { return null; }

        return datastore;
    }

    /**
     * Identifies whether debug logging is enabled by retrieving
     * the corresponding setting from application properties.
     *
     * @return <code>true</code> if debug logging is enabled,
     *         <code>false</code> otherwise.
     */
    public static boolean is_debug_log_enabled() {
        Properties props = _get_props(ERR_DEBUG_LOG_UNABLE_TO_ACTIVATE);

        String debug_log_enabled = props.getProperty(DEBUG_LOG_ENABLED);

        if ((debug_log_enabled != null)
            && (debug_log_enabled.compareTo(YES) == 0)) { return true; }

        return false;
    }

    // Helper method. Used to get the application properties object.
    private static final Properties _get_props(final String error_msg) {
        Properties props = new Properties();

        ClassLoader loader
            = UrbanBusRoutingControllerHelper.class.getClassLoader();

        InputStream data = loader.getResourceAsStream(APP_PROPS);

        try {
            props.load(data);
            data.close();
        } catch (java.io.IOException e) {
            l.error(error_msg);
        }

        return props;
    }
}

// vim:set nu et ts=4 sw=4:
