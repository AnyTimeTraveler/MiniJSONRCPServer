package org.simonscode.minijsonrpc.spec;

/**
 * When a rpc call is made, the Server MUST reply with a Response, except for in the case of Notifications.
 * The Response is expressed as a single JSON Object.
 */
public class JSONRPC2ErrorResponse {
    /**
     * A String specifying the version of the JSON-RPC protocol.
     * MUST be exactly "2.0".
     */
    public String jsonrpc = "2.0";

    /**
     * This member is REQUIRED on error.
     * This member MUST NOT exist if there was no error triggered during invocation.
     * The value for this member MUST be an Object as defined in @code{JSONRPC2ErrorObject}.
     */
    public JSONRPC2ErrorObject error;

    /**
     * This member is REQUIRED.
     * It MUST be the same as the value of the id member in the Request Object.
     * If there was an error in detecting the id in the Request object (e.g. Parse error/Invalid Request), it MUST be Null.
     */
    public String id;
}