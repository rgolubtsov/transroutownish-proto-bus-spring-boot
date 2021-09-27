/*
 * src/main/java/com/transroutownish/proto/bus/UrbanBusRoutingController.java
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.transroutownish.proto.bus.UrbanBusRoutingControllerHelper.*;

/**
 * The controller class of the microservice.
 *
 * @version 0.0.1
 * @since   0.0.1
 */
@RestController
@SuppressWarnings("unchecked")
public class UrbanBusRoutingController {
    // Helper constants.
    private static final String REST_PREFIX = "api";
    private static final String REST_DIRECT = "direct";

    private static final String FROM = "from";
    private static final String TO   = "to";

    private static final String ZERO = "0";

    // Extra helper constants.
    private static final String SLASH = "/";

    /** The SLF4J logger. */
    private static final Logger l = LoggerFactory.getLogger(
        MethodHandles.lookup().lookupClass()
    );

    /**
     * The &quot;<code>/api/direct</code>&quot; <b>GET</b> endpoint.
     * <br />
     * <br />Returns the response object in the JSON representation,
     * containing the following properties:
     * <ul>
     * <li><strong>from</strong>   &mdash; The starting bus stop point.</li>
     * <li><strong>to</strong>     &mdash; The ending   bus stop point.</li>
     * <li><strong>direct</strong> &mdash; The logical indicator
     * of the presence of a direct route from <code>from</code>
     * to <code>to</code>.</li>
     * </ul>
     *
     * @param from The starting bus stop point.
     * @param to   The ending   bus stop point.
     *
     * @return The ResponseEntity object with a specific
     *         HTTP status code provided.
     */
    @GetMapping(REST_PREFIX + SLASH + REST_DIRECT)
    public ResponseEntity get_direct_route(
        @RequestParam(name=FROM, defaultValue=ZERO) final String from,
        @RequestParam(name=TO,   defaultValue=ZERO) final String to) {

        l.debug(FROM + EQUALS + BRACES + SPACE + V_BAR + SPACE
              + TO   + EQUALS + BRACES,
                from,
                to);

        boolean is_request_malformed = false;

        if ((from.compareTo(ZERO) == 0) || (to.compareTo(ZERO) == 0)) {
            is_request_malformed = true;
        }

        if (   (new Integer(from).intValue() < 0)
            || (new Integer(to  ).intValue() < 0)) {

            is_request_malformed = true;
        }

        if (is_request_malformed) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}

// vim:set nu et ts=4 sw=4:
