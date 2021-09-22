/*
 * src/main/java/com/transroutownish/proto/bus/UrbanBusRoutingResponsePojo.java
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
 * The POJO representation, returning in the response.
 *
 * @version 0.0.1
 * @since   0.0.1
 */
public class UrbanBusRoutingResponsePojo {
    private final long    from;
    private final long    to;
    private final boolean direct;

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public boolean isDirect() {
        return direct;
    }

    public UrbanBusRoutingResponsePojo(final long    _from,
                                       final long    _to,
                                       final boolean _direct) {

        from   = _from;
        to     = _to;
        direct = _direct;
    }
}

// vim:set nu et ts=4 sw=4:
