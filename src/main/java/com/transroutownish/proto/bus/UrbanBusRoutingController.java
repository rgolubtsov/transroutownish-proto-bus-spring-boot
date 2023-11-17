/*
 * src/main/java/com/transroutownish/proto/bus/UrbanBusRoutingController.java
 * ============================================================================
 * Urban bus routing microservice prototype. Version 0.30.5
 * ============================================================================
 * A Spring Boot-based application, designed and intended to be run
 * as a microservice, implementing a simple urban bus routing prototype.
 * ============================================================================
 * Copyright (C) 2021-2023 Radislav (Radicchio) Golubtsov
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
 * @version 0.30.5
 * @since   0.0.1
 */
@RestController
@SuppressWarnings("unchecked")
public class UrbanBusRoutingController {
    // Helper constants.
    private static final String REST_PREFIX = "route";
    private static final String REST_DIRECT = "direct";

    private static final String FROM = "from";
    private static final String TO   = "to";

    // Extra helper constants.
    private static final String ZERO = "0";

    /**
     * The regex pattern for the leading part of a bus stops sequence,
     * before the matching element.
     */
    private static final String SEQ1_REGEX = ".*\\s";

    /**
     * The regex pattern for the trailing part of a bus stops sequence,
     * after the matching element.
     */
    private static final String SEQ2_REGEX = "\\s.*";

    /** The SLF4J logger. */
    private static final Logger l = LoggerFactory.getLogger(
        MethodHandles.lookup().lookupClass()
    );

    /**
     * The &quot;<code>/route/direct</code>&quot; <b>GET</b> endpoint.
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
     * @return The ResponseEntity object, containing the response body
     *         in JSON representation, along with a specific HTTP status code
     *         provided.
     */
    @GetMapping(REST_PREFIX + SLASH + REST_DIRECT)
    public ResponseEntity get_direct_route(
        @RequestParam(name=FROM, defaultValue=ZERO) final String from,
        @RequestParam(name=TO,   defaultValue=ZERO) final String to) {

        int _from = 0;
        int _to   = 0;

        if (UrbanBusRoutingApp.debug_log_enabled) {
            l.debug(FROM + EQUALS + BRACES + SPACE + V_BAR + SPACE
                  + TO   + EQUALS + BRACES,
                    from,
                    to);

            UrbanBusRoutingApp.
            s.debug(FROM + EQUALS + from   + SPACE + V_BAR + SPACE
                  + TO   + EQUALS + to);
        }

        // --------------------------------------------------------------------
        // --- Parsing and validating request params - Begin ------------------
        // --------------------------------------------------------------------
        boolean is_request_malformed = false;

        try {
            _from = Integer.parseInt(from);
            _to   = Integer.parseInt(to  );

            if ((_from < 1) || (_to < 1)) {
                is_request_malformed = true;
            }
        } catch (NumberFormatException e) {
            is_request_malformed = true;
        }

        if (is_request_malformed) {
            UrbanBusRoutingResponsePojoError resp_body_err
                = new UrbanBusRoutingResponsePojoError(
                    ERR_REQ_PARAMS_MUST_BE_POSITIVE_INTS);

            return new ResponseEntity(resp_body_err, HttpStatus.BAD_REQUEST);
        }
        // --------------------------------------------------------------------
        // --- Parsing and validating request params - End --------------------
        // --------------------------------------------------------------------

        // Performing the routes processing to find out the direct route.
        boolean direct = find_direct_route(String.valueOf(_from),
                                           String.valueOf(_to  ));

        UrbanBusRoutingResponsePojo resp_body
            = new UrbanBusRoutingResponsePojo(_from, _to, direct);

        return new ResponseEntity(resp_body, HttpStatus.OK);
    }

    /**
     * Performs the routes processing (onto bus stops sequences) to identify
     * and return whether a particular interval between two bus stop points
     * given is direct (i.e. contains in any of the routes), or not.
     *
     * @param from The starting bus stop point.
     * @param to   The ending   bus stop point.
     *
     * @return <code>true</code> if the direct route is found,
     *         <code>false</code> otherwise.
     */
    private boolean find_direct_route(final String from, final String to) {
        boolean direct = false;

        // Two bus stop points in a route cannot point up to the same value.
        if (from.compareTo(to) == 0) { return direct; }

        String route = EMPTY_STRING, route_from = EMPTY_STRING;

        int routes_count = UrbanBusRoutingApp.routes_list.size();

        for (int i = 0; i < routes_count; i++) {
            route = UrbanBusRoutingApp.routes_list.get(i);

            if (UrbanBusRoutingApp.debug_log_enabled) {
                l.debug((i + 1) + SPACE + EQUALS + SPACE + BRACES, route);
            }

            if (route.matches(SEQ1_REGEX + from + SEQ2_REGEX)) {
                // Pinning in the starting bus stop point, if it's found.
                // Next, searching for the ending bus stop point
                // on the current route, beginning at the pinned point.
                route_from = route.substring(route.indexOf(from));

                if (UrbanBusRoutingApp.debug_log_enabled) {
                    l.debug(BRACES + SPACE + V_BAR + SPACE + BRACES,
                            from, route_from);
                }

                if (route_from.matches(SEQ1_REGEX + to + SEQ2_REGEX)) {
                    direct = true; break;
                }
            }
        }

        return direct;
    }
}

// vim:set nu et ts=4 sw=4:
