package com.dlsc.jfxcentral2.utils;

import one.jpro.platform.routing.Request;

/**
 * The decoded query parameters a page needs, carried from the routing request to the page.
 *
 * @param params the decoded query parameters
 */
public record PageRequest(QueryParams params) {

    /**
     * A request without any parameter, used by callers that create a page outside of the router.
     */
    public static final PageRequest EMPTY = new PageRequest(QueryParams.EMPTY);

    /**
     * Creates an instance from a routing request.
     *
     * @param request the routing request, may be {@code null}
     * @return the page request, never {@code null}
     */
    public static PageRequest of(Request request) {
        if (request == null) {
            return EMPTY;
        }
        return new PageRequest(QueryParams.of(request.getQueryParameters()));
    }
}
