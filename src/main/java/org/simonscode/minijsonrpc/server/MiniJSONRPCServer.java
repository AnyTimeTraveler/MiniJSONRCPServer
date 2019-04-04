package org.simonscode.minijsonrpc.server;

import com.sun.net.httpserver.HttpServer;
import org.simonscode.minijsonrpc.annotations.RemoteMethod;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class MiniJSONRPCServer {

    private final ExecutorService executorService;
    private final int executionTimeout;
    private final boolean returnExceptions;
    private List<Class<?>> classes;
    private boolean running;
    private HttpServer httpServer;


    public MiniJSONRPCServer(ExecutorService executorService, int executionTimeout, boolean returnExceptions, List<Class<?>> classes) {
        this.executorService = executorService;
        this.executionTimeout = executionTimeout;
        this.returnExceptions = returnExceptions;
        this.classes = classes;
        this.running = false;
    }

    public MiniJSONRPCServer() {
        this(new ForkJoinPool(), 10_000, false, new ArrayList<>());
    }

    public MiniJSONRPCServer addClass(Class<?> clazz) {
        if (running) {
            throw new IllegalStateException("Server already started");
        }
        classes.add(clazz);
        return this;
    }

    public MiniJSONRPCServer removeClass(Class<?> clazz) {
        if (running) {
            throw new IllegalStateException("Server already started");
        }
        classes.remove(clazz);
        return this;
    }

    public void start() throws IOException {
        running = true;
        httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
        httpServer.createContext("/", new RPCHandler(executorService, executionTimeout, returnExceptions, AnnotationFinder.getMethodsAnnotatedWith(classes, RemoteMethod.class)));
        httpServer.setExecutor(executorService);
        httpServer.start();
    }

    public void stop(int delay) {
        httpServer.stop(delay);
        running = false;
    }
}
