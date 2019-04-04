package org.simonscode.minijsonrpc.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.simonscode.minijsonrpc.spec.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

class RPCHandler implements HttpHandler {

    private Gson gson;
    private ExecutorService executorService;
    private int executionTimeout;
    private boolean returnExceptions;
    private Map<String, Method> methods;

    RPCHandler(ExecutorService executorService, int executionTimeout, boolean returnExceptions, Map<String, Method> methods) {
        this.executionTimeout = executionTimeout;
        this.returnExceptions = returnExceptions;
        this.methods = methods;
        gson = new GsonBuilder()
                .registerTypeAdapter(JSONRPC2RequestList.class, new AlwaysListTypeAdapter(new Gson().getAdapter(JSONRPC2Request.class)))
                .create();
        this.executorService = executorService;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        String responseJson;
        int responseCode = 200;
        try {
            JSONRPC2RequestList requests = gson.fromJson(new InputStreamReader(t.getRequestBody()), JSONRPC2RequestList.class);
            List<Future<JSONRPC2Response>> futures = executorService.invokeAll(
                    requests.stream()
                            .map(request -> new RemoteMethodExecutor(methods, request))
                            .collect(Collectors.toList()),
                    executionTimeout,
                    TimeUnit.MILLISECONDS);
            if (futures.size() == 1) {
                responseJson = gson.toJson(futures.get(0).get());
            } else {
                ArrayList<JSONRPC2Response> responses = new ArrayList<>();
                for (Future<JSONRPC2Response> future : futures) {
                    responses.add(future.get());
                }
                responseJson = gson.toJson(responses);
            }
        } catch (JsonIOException | JsonSyntaxException e) {
            responseJson = gson.toJson(new JSONRPC2ErrorResponse(new JSONRPC2ErrorObject(JSONRPCErrorCodes.PARSE_ERROR, e.getMessage(), returnExceptions ? e : null), null));
        } catch (InterruptedException e) {
            responseCode = 500;
            responseJson = gson.toJson(new JSONRPC2ErrorResponse(new JSONRPC2ErrorObject(JSONRPCErrorCodes.INTERNAL_ERROR, e.getMessage(), returnExceptions ? e : null), null));
        } catch (ExecutionException e) {
            // this should never happen
            e.printStackTrace();
            responseCode = 500;
            responseJson = gson.toJson(new JSONRPC2ErrorResponse(new JSONRPC2ErrorObject(JSONRPCErrorCodes.INTERNAL_ERROR, e.getMessage(), returnExceptions ? e : null), null));
        }
        t.sendResponseHeaders(responseCode, responseJson.length());
        OutputStream os = t.getResponseBody();
        os.write(responseJson.getBytes());
        os.close();
    }
}
