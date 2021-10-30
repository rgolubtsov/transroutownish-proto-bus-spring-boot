/*
 * src/main/java/com/transroutownish/proto/bus/UrbanBusRoutingResponsePojo.java
 * ============================================================================
 * Urban bus routing microservice prototype. Version 0.11.2
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
 * The POJO representation, returning in the response.
 *
 * @version 0.11.2
 * @since   0.0.1
 */
public class UrbanBusRoutingResponsePojo {
    /** The starting bus stop point. */
    private final int from; // MAX_VALUE == 2,147,483,647.

    /** The ending bus stop point. */
    private final int to;   // MAX_VALUE == 2,147,483,647.

    /**
     * The indicator of the presence of a direct route
     * from <code>from</code> to <code>to</code>.
     */
    private final boolean direct;

    /**
     * The accessor method for the starting bus stop point.
     *
     * @return The starting bus stop point.
     */
    public int getFrom() {
        return from;
    }

    /**
     * The accessor method for the ending bus stop point.
     *
     * @return The ending bus stop point.
     */
    public int getTo() {
        return to;
    }

    /**
     * The accessor method for the indicator of the presence of a direct route.
     *
     * @return The indicator of the presence of a direct route.
     */
    public boolean isDirect() {
        return direct;
    }

    /**
     * The effective constructor.
     *
     * @param _from   The starting bus stop point.
     * @param _to     The ending   bus stop point.
     * @param _direct The indicator of the presence of a direct route.
     */
    public UrbanBusRoutingResponsePojo(final int     _from,
                                       final int     _to,
                                       final boolean _direct) {

        from   = _from;
        to     = _to;
        direct = _direct;
    }
}

// vim:set nu et ts=4 sw=4:
