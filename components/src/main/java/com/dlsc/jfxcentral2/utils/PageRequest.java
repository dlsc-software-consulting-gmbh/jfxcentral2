package com.dlsc.jfxcentral2.utils;

import one.jpro.platform.routing.Request;

/**
 * The part of a routing request that a page needs: the path it was reached by and its decoded
 * query parameters.
 *
 * @param path   the request path without the query string, for example {@code /videos}
 * @param params the decoded query parameters
 */
public record PageRequest(String path, QueryParams params) {

    /**
     * A request without any parameter, used by callers that create a page outside of the router.
     */
    public static final PageRequest EMPTY = new PageRequest("/", QueryParams.EMPTY);

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
        return new PageRequest(request.getPath(), QueryParams.of(request.getQueryParameters()));
    }
}
