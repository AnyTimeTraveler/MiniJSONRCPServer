package org.simonscode.minijsonrpc.spec;

import com.google.gson.JsonObject;

/**
 * When a rpc call encounters an error, the Response Object MUST contain the error member with a value that is this Object.
 */
public class JSONRPC2ErrorObject {
    /**
     * A String specifying the version of the JSON-RPC protocol.
     * MUST be exactly "2.0".
     */
    public String jsonrpc = "2.0";

    /**
     * A Number that indicates the error type that occurred.
     * This MUST be an integer.
     */
    public int code;

    /**
     * A String providing a short description of the error.
     * The message SHOULD be limited to a concise single sentence.
     */
    public String message;

    /**
     * A Primitive or Structured value that contains additional information about the error.
     * This may be omitted.
     * The value of this member is defined by the Server (e.g. detailed error information, nested errors etc.).
     */
    public JsonObject data;
}
