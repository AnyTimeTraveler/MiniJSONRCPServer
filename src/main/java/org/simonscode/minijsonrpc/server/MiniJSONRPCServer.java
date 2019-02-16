package org.simonscode.minijsonrpc.server;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class MiniJSONRPCServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new RPCHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }


}
