package com.dlsc.jfxcentral2.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * An immutable, decoded view of the query parameters of a page request.
 *
 * <p>Parameter names are lower-cased and values are URL-decoded on construction. Matching a
 * parameter value against a display name is done via {@link #normalize(String)}, which is lenient:
 * {@code jfxinaction}, {@code jfx-in-action} and {@code JFX%20In%20Action} all match the data value
 * {@code "JFX In Action"}. Links are generated with the readable form produced by
 * {@link #toSlug(String)}.
 */
public final class QueryParams {

    /**
     * An instance without any parameter.
     */
    public static final QueryParams EMPTY = new QueryParams(Collections.emptyMap());

    private final Map<String, String> params;

    private QueryParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * Creates an instance from the raw, still encoded parameters of a request.
     *
     * <p>A value that cannot be decoded is dropped and the remaining parameters are kept. Values
     * arriving through the router have already passed the percent-escape validation of
     * {@code java.net.URI}, so this is a safeguard for direct callers rather than a fix for a
     * reachable failure.
     *
     * @param raw the raw parameters, may be {@code null}
     * @return an instance holding the decoded parameters, never {@code null}
     */
    public static QueryParams of(Map<String, String> raw) {
        if (raw == null || raw.isEmpty()) {
            return EMPTY;
        }

        Map<String, String> decoded = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : raw.entrySet()) {
            String name = entry.getKey();
            if (StringUtils.isBlank(name)) {
                continue;
            }
            try {
                decoded.put(name.toLowerCase(Locale.ROOT), decode(entry.getValue()));
            } catch (IllegalArgumentException ex) {
                LOGGER.warn("Ignoring query parameter with an undecodable value: ", name);
            }
        }

        return decoded.isEmpty() ? EMPTY : new QueryParams(Collections.unmodifiableMap(decoded));
    }

    private static String decode(String value) {
        return value == null ? "" : URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    /**
     * Returns the decoded value of the given parameter.
     *
     * @param name the parameter name, case-insensitive
     * @return the value, or an empty optional when the parameter is absent or blank
     */
    public Optional<String> get(String name) {
        if (StringUtils.isBlank(name)) {
            return Optional.empty();
        }
        String value = params.get(name.toLowerCase(Locale.ROOT));
        return StringUtils.isBlank(value) ? Optional.empty() : Optional.of(value);
    }

    /**
     * Returns whether this instance holds no parameter at all.
     *
     * @return true if there is no parameter
     */
    public boolean isEmpty() {
        return params.isEmpty();
    }

    /**
     * Reduces a value to its comparable form by lower-casing it and dropping everything that is
     * neither a letter nor a digit. Used to match a parameter value against a display name.
     *
     * @param value the value to normalize, may be {@code null}
     * @return the normalized value, never {@code null}
     */
    public static String normalize(String value) {
        if (value == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder(value.length());
        for (char c : value.toLowerCase(Locale.ROOT).toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * Converts a display name into the readable form used when generating links, for example
     * {@code "JFX In Action"} becomes {@code "jfx-in-action"}.
     *
     * @param displayName the display name, may be {@code null}
     * @return the slug, never {@code null}
     */
    public static String toSlug(String displayName) {
        if (displayName == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder(displayName.length());
        boolean pendingSeparator = false;
        for (char c : displayName.toLowerCase(Locale.ROOT).toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                if (pendingSeparator && !builder.isEmpty()) {
                    builder.append('-');
                }
                builder.append(c);
                pendingSeparator = false;
            } else {
                pendingSeparator = true;
            }
        }
        return builder.toString();
    }

    /**
     * Builds a URL from a path and a set of parameters.
     *
     * <p>Parameters with a blank value are omitted and values are percent-encoded, which keeps the
     * result parsable by the router: an empty value or an unencoded {@code =} makes the framework
     * reject the whole request.
     *
     * @param path   the page path, for example {@code /videos}
     * @param params the parameters to append, may be {@code null}
     * @return the URL, never {@code null}
     */
    public static String buildUrl(String path, Map<String, String> params) {
        String base = path == null ? "" : path;
        if (params == null || params.isEmpty()) {
            return base;
        }

        StringBuilder builder = new StringBuilder(base);
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isBlank(name) || StringUtils.isBlank(value)) {
                continue;
            }
            builder.append(first ? '?' : '&');
            builder.append(encode(name)).append('=').append(encode(value));
            first = false;
        }
        return builder.toString();
    }

    private static String encode(String value) {
        // URLEncoder targets form encoding, where a space becomes "+". Inside a query string "%20"
        // is the safer form because it survives readers that do not apply form decoding. The comma
        // is left as is: it is a legal query character and keeps multi-value links such as a pack
        // list readable, and "%2C" is only ever the encoding of a comma so undoing it is exact.
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20").replace("%2C", ",");
    }
}
