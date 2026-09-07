package com.dlsc.jfxcentral2.utils;

import one.jpro.platform.routing.Request;

/**
 * The part of a routing request that a page needs: the canonical path the route was registered
 * under and the decoded query parameters.
 *
 * <p>The path is deliberately not taken from the request. It is written back to the browser
 * address bar, so it has to be a value the application controls, such as a {@code PagePath}
 * constant or a constant plus an id that was validated against the repository.
 *
 * @param path   the canonical path of the page, for example {@code /videos}; {@code null} when the
 *               page was not created by a registered route, which disables the address bar sync
 * @param params the decoded query parameters
 */
public record PageRequest(String path, QueryParams params) {

    /**
     * A request without a canonical path or any parameter, used by callers that create a page
     * outside of the router.
     */
    public static final PageRequest EMPTY = new PageRequest(null, QueryParams.EMPTY);

    /**
     * Creates an instance from a routing request.
     *
     * @param request       the routing request, may be {@code null}
     * @param canonicalPath the path the route was registered under, never derived from the request
     * @return the page request, never {@code null}
     */
    public static PageRequest of(Request request, String canonicalPath) {
        if (request == null) {
            return EMPTY;
        }
        return new PageRequest(canonicalPath, QueryParams.of(request.getQueryParameters()));
    }
}
