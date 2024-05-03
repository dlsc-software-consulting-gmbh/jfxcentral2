package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral.data.RSSManager;
import com.jpro.webapi.server.ServerAPI;
import com.jpro.webapi.server.Request;
import com.jpro.webapi.server.Response;

public class JFXCentral2Server {
    public static void main(String[] args) {
        ServerAPI.getServerAPI().addRequestHandler(request -> {
            if (request.getPath().equals("/lotw/rss.xml")) {
                return Response.of(RSSManager.createRSS().getBytes(), "application/rss+xml");
            }
            return Response.empty();
        });
    }
}
