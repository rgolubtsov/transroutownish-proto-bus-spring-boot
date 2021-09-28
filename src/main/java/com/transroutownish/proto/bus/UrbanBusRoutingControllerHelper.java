/*
 * src/main/java/com/transroutownish/proto/bus/
 * UrbanBusRoutingControllerHelper.java
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

/**
 * The helper class for the controller and the related ones.
 *
 * @version 0.0.1
 * @since   0.0.1
 */
public class UrbanBusRoutingControllerHelper {
    // Helper constants.
    public static final String EQUALS =  "=";
    public static final String BRACES = "{}";
    public static final String SPACE  =  " ";
    public static final String V_BAR  =  "|";

    // Common error messages.
    public static final String ERR_REQ_PARAMS_MUST_BE_POSITIVE_INTS = "Request parameters must be "
                                                                    + "positive integer values.";
    public static final String ERR_REQ_MALFORMED_CHECK_INPUTS       = "Request is malformed. "
                                                                    + "Please check input values.";
}

// vim:set nu et ts=4 sw=4:
