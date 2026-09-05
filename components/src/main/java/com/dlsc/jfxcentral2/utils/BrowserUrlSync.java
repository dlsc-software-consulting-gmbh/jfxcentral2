package com.dlsc.jfxcentral2.utils;

import com.google.gson.Gson;
import com.jpro.webapi.WebAPI;
import javafx.scene.Node;
import one.jpro.platform.routing.SessionManagerContext;
import one.jpro.platform.routing.sessionmanager.SessionManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Writes the URL of the current filter state into the browser address bar without reloading the
 * page, and keeps the router's own URL bookkeeping in sync with it.
 */
public final class BrowserUrlSync {

    private static final Logger LOGGER = Logger.getLogger(BrowserUrlSync.class.getName());
    private static final Gson GSON = new Gson();
    private static final Pattern SEGMENT = Pattern.compile("[A-Za-z0-9._~-]+");
    private static final Pattern PAIR = Pattern.compile("[^=&]+=[^=&]+");

    private BrowserUrlSync() {
    }

    /**
     * Replaces the URL shown by the browser and updates the router's bookkeeping to match.
     *
     * <p>The result only states that the Java side preconditions held, the script was submitted and
     * the router state was updated. Whether the browser executed the script is not confirmed: the
     * two updates are not atomic.
     *
     * @param node the node the calling view belongs to
     * @param url  an absolute-path reference within the application, such as {@code /videos?type=library}
     * @return whether the URL was submitted, so that the caller only records what it submitted
     */
    public static boolean replace(Node node, String url) {
        if (!WebAPI.isBrowser() || node == null || node.getScene() == null) {
            return false;
        }
        if (!isSafeUrl(url)) {
            LOGGER.warning("Refusing to write an unexpected URL to the address bar: " + url);
            return false;
        }
        if (!(SessionManagerContext.getContext(node) instanceof SessionManager sessionManager)
                || sessionManager.getURL() == null) {
            return false;
        }
        // The router keeps an absolute URL, so the path has to be resolved against it.
        String absoluteUrl = SessionManager.mergeURLs(sessionManager.getURL(), url);
        WebAPIUtil.executeScript(node, buildReplaceScript(url));
        // Scala setter of the router's url var, there is no public replaceURL API.
        sessionManager.url_$eq(absoluteUrl);
        return true;
    }

    /**
     * Builds the script that replaces the URL. Extracted so that a test can assert the exact
     * script text instead of only the escaping of its argument.
     */
    static String buildReplaceScript(String url) {
        return "history.replaceState(history.state, '', " + GSON.toJson(url) + ");";
    }

    /**
     * Accepts only an absolute-path reference within this application: no scheme, no authority,
     * no fragment, no empty or dot segments, only unreserved characters in the path, and a query
     * string made of {@code name=value} pairs that the router's own parser accepts.
     */
    static boolean isSafeUrl(String url) {
        if (url == null) {
            return false;
        }
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException ex) {
            return false;
        }
        if (uri.getScheme() != null || uri.getRawAuthority() != null || uri.getRawFragment() != null) {
            return false;
        }
        return isSafePath(uri.getRawPath()) && isSafeQuery(uri.getRawQuery());
    }

    private static boolean isSafePath(String path) {
        if (path == null || !path.startsWith("/") || path.startsWith("//")) {
            return false;
        }
        if (path.equals("/")) {
            return true;
        }
        for (String segment : path.substring(1).split("/", -1)) {
            if (segment.equals(".") || segment.equals("..") || !SEGMENT.matcher(segment).matches()) {
                return false;
            }
        }
        return true;
    }

    /**
     * The router splits the query on "&" and then expects exactly one "=" per pair, so anything
     * else would make the page fail to load after a refresh or a back navigation.
     */
    private static boolean isSafeQuery(String rawQuery) {
        if (rawQuery == null) {
            return true;
        }
        if (rawQuery.isEmpty()) {
            return false;
        }
        for (String pair : rawQuery.split("&", -1)) {
            if (!PAIR.matcher(pair).matches()) {
                return false;
            }
        }
        return true;
    }
}
