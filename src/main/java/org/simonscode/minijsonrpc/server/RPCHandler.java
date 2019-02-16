package org.simonscode.minijsonrpc.server;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

class RPCHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        ResponseData responseData = new ResponseData();
        try {
            RequestData requestData = new Gson().fromJson(new InputStreamReader(t.getRequestBody()), RequestData.class);
            RequestHandler.handleRequest(requestData, responseData);
            t.sendResponseHeaders(200, responseData.length());
        } catch (JsonIOException | JsonSyntaxException e) {
            responseData.setError(e.getMessage());
            t.sendResponseHeaders(500, responseData.length());
        }
        OutputStream os = t.getResponseBody();
        os.write(responseData.getBytes());
        os.close();
    }
}
