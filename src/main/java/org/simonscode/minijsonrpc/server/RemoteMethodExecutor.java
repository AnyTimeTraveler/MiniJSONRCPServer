package org.simonscode.minijsonrpc.server;

import com.google.gson.Gson;
import org.simonscode.minijsonrpc.errorinfo.InvalidParameterError;
import org.simonscode.minijsonrpc.spec.*;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;

public class RemoteMethodExecutor implements Callable<JSONRPC2Response> {

    private Map<String, Method> methods;
    private JSONRPC2Request request;

    public RemoteMethodExecutor(Map<String, Method> methods, JSONRPC2Request request) {
        this.methods = methods;
        this.request = request;
    }

    @Override
    public JSONRPC2Response call() {
        Method method = methods.get(request.getMethod());
        if (method == null) {
            return new JSONRPC2ErrorResponse(new JSONRPC2ErrorObject(JSONRPCErrorCodes.METHOD_NOT_FOUND, "Method not found", null), request.getId());
        }
        if (method.getParameterCount() != request.getParams().size()) {
            return new JSONRPC2ErrorResponse(
                    new JSONRPC2ErrorObject(
                            JSONRPCErrorCodes.INVALID_PARAMS,
                            "Invalid amount of parameters provided!",
                            new InvalidParameterError(method.getParameterCount(), request.getParams().size())
                    ),
                    request.getId()
            );
        }
        Object[] parameters = new Object[method.getParameterCount()];
        for (int i = 0; i < method.getParameterCount(); i++) {
            parameters[i] = new Gson().fromJson(request.getParams().get(i), method.getParameterTypes()[i]);
        }
        try {
            Object returnValue = method.invoke(parameters);
            return new JSONRPC2SuccessResponse(new Gson().toJsonTree(returnValue, method.getReturnType()), request.getId());
        } catch (Exception e) {
            return new JSONRPC2ErrorResponse(new JSONRPC2ErrorObject(JSONRPCErrorCodes.INTERNAL_ERROR, e.getMessage(), e), request.getId());
        }
    }
}
