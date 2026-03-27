package com.dlsc.jfxcentral2.utils;

import javafx.application.Platform;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.LongConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility for asynchronously fetching the GitHub star count of a repository.
 * The result callback is always invoked on the JavaFX Application Thread.
 */
public class GitHubStarsService {

    private static final Logger LOG = Logger.getLogger(GitHubStarsService.class.getName());
    private static final String API_URL = "https://api.github.com/repos/%s/%s";
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

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
        String url = String.format(API_URL, owner, repo);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/vnd.github.v3+json")
                .GET()
                .build();

        HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        long stars = parseStargazersCount(response.body());
                        if (stars >= 0) {
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
