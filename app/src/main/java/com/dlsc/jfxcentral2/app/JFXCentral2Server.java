package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral.data.RSSManager;
import com.dlsc.jfxcentral.data.util.SitemapGenerator;
import com.jpro.webapi.server.ServerAPI;
import com.jpro.webapi.server.Response;

import java.nio.charset.StandardCharsets;

public class JFXCentral2Server {
    public static void main(String[] args) {
        ServerAPI.getServerAPI().addRequestHandler(request -> {
            if (request.getPath().equals("/lotw/rss.xml")) {
                return Response.of(RSSManager.createRSS().getBytes(StandardCharsets.UTF_8), "application/rss+xml");
            }
            return Response.empty();
        });
        ServerAPI.getServerAPI().addRequestHandler(request -> {
            if (request.getPath().equals("/sitemap.xml")) {
                return Response.of(SitemapGenerator.generate().getBytes(StandardCharsets.UTF_8), "application/xml");
            }
            return Response.empty();
        });
        ServerAPI.getServerAPI().addRequestHandler(request -> {
            if (request.getPath().equals("/robots.txt")) {
                return Response.of("User-agent: *\nAllow: /\n\nSitemap: https://www.jfx-central.com/sitemap.xml".getBytes(StandardCharsets.UTF_8), "text/plain; charset=UTF-8");
            }
            return Response.empty();
        });
    }
}
