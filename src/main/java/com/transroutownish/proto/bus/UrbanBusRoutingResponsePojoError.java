/*
 * src/main/java/com/transroutownish/proto/bus/
 * UrbanBusRoutingResponsePojoError.java
 * ============================================================================
 * Urban bus routing microservice prototype. Version 0.12.0
 * ============================================================================
 * A Spring Boot-based application, designed and intended to be run
 * as a microservice, implementing a simple urban bus routing prototype.
 * ============================================================================
 * Copyright (C) 2021 Radislav (Radicchio) Golubtsov
 *
 * (See the LICENSE file at the top of the source tree.)
 */

package com.transroutownish.proto.bus;

/**
 * The POJO representation, returning in the response
 * when erroneous behavior is occurred.
 *
 * @version 0.12.0
 * @since   0.0.1
 */
public class UrbanBusRoutingResponsePojoError {
    /** The error message. */
    private final String error;

    /**
     * The accessor method for the error message.
     *
     * @return The error message.
     */
    public String getError() {
        return error;
    }

    /**
     * The effective constructor.
     *
     * @param _error The error message.
     */
    public UrbanBusRoutingResponsePojoError(final String _error) {
        error = _error;
    }
}

// vim:set nu et ts=4 sw=4:
