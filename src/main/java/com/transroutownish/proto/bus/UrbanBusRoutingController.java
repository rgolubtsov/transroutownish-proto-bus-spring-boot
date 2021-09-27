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

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    private static final String SLASH = "/";

    /**
     * The &quot;<code>/api/direct</code>&quot; <b>GET</b> endpoint.
     * <br />
     * <br />Returns .
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

//      l.debug(FROM + EQUALS + BRACES + SPACE + V_BAR + SPACE
//            + TO   + EQUALS + BRACES,
//              from,
//              to);

        return new ResponseEntity(HttpStatus.OK);
    }
}

// vim:set nu et ts=4 sw=4:
