package com.dlsc.jfxcentral2.utils;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the decoding, normalization and URL building of {@link QueryParams}.
 */
public class QueryParamsTest {

    private static String normalizedValue(String rawValue) {
        return QueryParams.normalize(QueryParams.of(Map.of("type", rawValue)).get("type").orElse(""));
    }

    @Test
    public void testNormalizeDropsEverythingButLettersAndDigits() {
        assertEquals("jfxinaction", QueryParams.normalize("JFX In Action"));
        assertEquals("planningscheduling", QueryParams.normalize("Planning & Scheduling"));
        assertEquals("", QueryParams.normalize(null));
    }

    /**
     * The lenient matching only holds end to end, after the value has been decoded. Calling
     * normalize on a still encoded value would turn "%20" into "20".
     */
    @Test
    public void testAllSpellingsOfAValueMatchTheSameDataValue() {
        String expected = QueryParams.normalize("JFX In Action");
        assertEquals(expected, normalizedValue("jfxinaction"));
        assertEquals(expected, normalizedValue("jfx-in-action"));
        assertEquals(expected, normalizedValue("JFX%20In%20Action"));
        assertEquals(expected, normalizedValue("JFX+In+Action"));
    }

    @Test
    public void testToSlugProducesReadableValues() {
        assertEquals("jfx-in-action", QueryParams.toSlug("JFX In Action"));
        assertEquals("from-a-to-z", QueryParams.toSlug("From A to Z"));
        assertEquals("planning-scheduling", QueryParams.toSlug("Planning & Scheduling"));
        assertEquals("a-z", QueryParams.toSlug("A → Z"));
        assertEquals("", QueryParams.toSlug(null));
    }

    /**
     * The default locale must not influence the result: in Turkish the lower case of "I" is the
     * dotless "ı", which would make "IONICONS" normalize to something no data value matches.
     */
    @Test
    public void testNormalizeIsLocaleIndependent() {
        Locale previous = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr"));
            assertEquals("ionicons", QueryParams.normalize("IONICONS"));
            assertEquals("ionicons", QueryParams.toSlug("IONICONS"));
        } finally {
            Locale.setDefault(previous);
        }
    }

    @Test
    public void testParameterNamesAreCaseInsensitive() {
        QueryParams params = QueryParams.of(Map.of("TYPE", "library"));
        assertEquals("library", params.get("type").orElse(null));
        assertEquals("library", params.get("Type").orElse(null));
    }

    @Test
    public void testBlankValuesAreTreatedAsAbsent() {
        QueryParams params = QueryParams.of(Map.of("search", "   "));
        assertTrue(params.get("search").isEmpty());
    }

    /**
     * A value that cannot be decoded is dropped while the other parameters stay usable. Note that
     * such a value cannot reach this class through the router: the framework rejects the request
     * while validating the percent escapes of the URI. This only guards direct callers.
     */
    @Test
    public void testUndecodableValueIsDroppedWithoutAffectingTheOthers() {
        Map<String, String> raw = new LinkedHashMap<>();
        raw.put("search", "%");
        raw.put("type", "library");

        QueryParams params = QueryParams.of(raw);

        assertTrue(params.get("search").isEmpty());
        assertEquals("library", params.get("type").orElse(null));
    }

    @Test
    public void testBuildUrlOmitsBlankValues() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("search", "");
        params.put("type", "library");
        params.put("event", null);

        assertEquals("/videos?type=library", QueryParams.buildUrl("/videos", params));
    }

    @Test
    public void testBuildUrlWithoutParamsReturnsThePath() {
        assertEquals("/videos", QueryParams.buildUrl("/videos", null));
        assertEquals("/videos", QueryParams.buildUrl("/videos", new HashMap<>()));
    }

    @Test
    public void testBuildUrlEncodesReservedCharacters() {
        assertEquals("/videos?search=a%3Db", QueryParams.buildUrl("/videos", Map.of("search", "a=b")));
        assertEquals("/videos?search=a%26b", QueryParams.buildUrl("/videos", Map.of("search", "a&b")));
        assertEquals("/videos?search=a%20b", QueryParams.buildUrl("/videos", Map.of("search", "a b")));
        assertEquals("/videos?search=%E4%B8%AD%E6%96%87", QueryParams.buildUrl("/videos", Map.of("search", "中文")));
        // comma stays readable (legal query char) and round trips
        assertEquals("/icons?pack=a,b,c", QueryParams.buildUrl("/icons", Map.of("pack", "a,b,c")));
        assertEquals("a,b,c", QueryParams.of(Map.of("pack", "a,b,c")).get("pack").orElse(null));
    }

    /**
     * A generated URL has to survive URI parsing: a bare percent sign makes the URI constructor
     * reject the whole request, so it must come out encoded and decode back to the original.
     */
    @Test
    public void testGeneratedUrlSurvivesUriParsingAndDecodesBack() {
        String url = QueryParams.buildUrl("/videos", Map.of("search", "100%"));
        assertEquals("/videos?search=100%25", url);

        URI uri = URI.create(url);
        assertEquals("/videos", uri.getPath());
        assertEquals("100%", URLDecoder.decode(uri.getRawQuery().split("=")[1], StandardCharsets.UTF_8));
    }

    @Test
    public void testEmptyInstances() {
        assertTrue(QueryParams.EMPTY.isEmpty());
        assertTrue(QueryParams.of(null).isEmpty());
        assertTrue(QueryParams.of(new HashMap<>()).isEmpty());
        assertTrue(QueryParams.EMPTY.get("search").isEmpty());
    }
}
