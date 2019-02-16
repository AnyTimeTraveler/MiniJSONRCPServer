package org.simonscode.minijsonrpc.spec;

/**
 * The error codes from and including -32768 to -32000 are reserved for pre-defined errors.
 * Any code within this range, but not defined explicitly below is reserved for future use.
 * The error codes are nearly the same as those suggested for XML-RPC at the following url:
 * http://xmlrpc-epi.sourceforge.net/specs/rfc.fault_codes.php
 *
 * -32000 to -32099 are reserved for implementation-defined server-errors.
 *
 * The remainder of the space is available for application defined errors.
 */
public class JSONRPCErrorCodes {
    /**
     * Parse error Invalid JSON was received by the server.
     * An error occurred on the server while parsing the JSON text.
     */
    public static final int PARSE_ERROR = -32700;

    /**
     * The JSON sent is not a valid Request object.
     */
    public static final int INVALID_REQUEST = -32600;

    /**
     * The method does not exist / is not available.
     */
    public static final int METHOD_NOT_FOUND = -32601;

    /**
     * Invalid method parameter(s).
     */
    public static final int INVALID_PARAMS = -32602;

    /**
     * Internal JSON-RPC error.
     */
    public static final int INTERNAL_ERROR = -32603;
}
