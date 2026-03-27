package com.dlsc.jfxcentral2.utils;

import javafx.application.Platform;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.LongConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility for asynchronously fetching the GitHub star count of a repository.
 * The result callback is always invoked on the JavaFX Application Thread.
 *
 * <p>Authentication is picked up automatically from the system property
 * {@code github.token} (highest priority) or the environment variable
 * {@code GITHUB_TOKEN}. Without a token requests are unauthenticated and
 * subject to GitHub's low rate limit (60 req/h per IP).
 */
public class GitHubStarsService {

    private static final Logger LOG = Logger.getLogger(GitHubStarsService.class.getName());
    private static final String API_URL = "https://api.github.com/repos/%s/%s";
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final ConcurrentHashMap<String, Long> STAR_CACHE = new ConcurrentHashMap<>();

    /** Resolved once at class-load time; {@code null} when no token is configured. */
    private static final String AUTH_TOKEN = resolveToken();

    private static String resolveToken() {
        String token = System.getProperty("github.token");
        if (token == null || token.isBlank()) {
            token = System.getenv("GITHUB_TOKEN");
        }
        if (token == null || token.isBlank()) {
            LOG.warning("No GitHub token configured (system property 'github.token' or env var 'GITHUB_TOKEN'). " +
                    "Unauthenticated requests are limited to 60/hour.");
            return null;
        }
        return token;
    }

    private GitHubStarsService() {
    }

    /**
     * Fetches the star count for the given GitHub repository asynchronously.
     * The {@code onSuccess} callback is invoked on the JavaFX Application Thread
     * when the count is available.
     *
     * @param owner     the GitHub account or organisation name
     * @param repo      the GitHub repository name
     * @param onSuccess called with the star count on success
     */
    public static void fetchStarCount(String owner, String repo, LongConsumer onSuccess) {
        String cacheKey = owner + "/" + repo;
        Long cached = STAR_CACHE.get(cacheKey);
        if (cached != null) {
            Platform.runLater(() -> onSuccess.accept(cached));
            return;
        }

        String url = String.format(API_URL, owner, repo);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/vnd.github.v3+json");
        if (AUTH_TOKEN != null) {
            requestBuilder.header("Authorization", "Bearer " + AUTH_TOKEN);
        }
        HttpRequest request = requestBuilder.GET().build();

        HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        long stars = parseStargazersCount(response.body());
                        if (stars >= 0) {
                            STAR_CACHE.put(cacheKey, stars);
                            Platform.runLater(() -> onSuccess.accept(stars));
                        }
                    } else {
                        LOG.warning("GitHub API returned status " + response.statusCode() + " for " + owner + "/" + repo);
                    }
                })
                .exceptionally(ex -> {
                    LOG.log(Level.WARNING, "Failed to fetch GitHub star count for " + owner + "/" + repo, ex);
                    return null;
                });
    }

    /**
     * Formats a raw GitHub star count into a compact human-readable string,
     * e.g. {@code 1200} → {@code "1.2k"}, {@code 1500000} → {@code "1.5M"}.
     *
     * @param stars the raw star count
     * @return formatted string
     */
    public static String formatStarCount(long stars) {
        if (stars >= 1_000_000) {
            return String.format("%.1fM", stars / 1_000_000.0);
        } else if (stars >= 1_000) {
            return String.format("%.1fk", stars / 1_000.0);
        }
        return String.valueOf(stars);
    }

    private static long parseStargazersCount(String json) {
        String key = "\"stargazers_count\":";
        int idx = json.indexOf(key);
        if (idx < 0) {
            return -1;
        }
        String after = json.substring(idx + key.length()).trim();
        int end = 0;
        while (end < after.length() && Character.isDigit(after.charAt(end))) {
            end++;
        }
        if (end == 0) {
            return -1;
        }
        try {
            return Long.parseLong(after.substring(0, end));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
