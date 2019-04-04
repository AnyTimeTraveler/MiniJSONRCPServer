package org.simonscode.minijsonrpc.spec;

import com.google.gson.JsonArray;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JSONRPC2Request {
    /**
     * A String specifying the version of the JSON-RPC protocol.
     * MUST be exactly "2.0".
     */
    private final String jsonrpc = "2.0";

    /**
     * A String containing the name of the method to be invoked.
     * Method names that begin with the word rpc followed by a period character (U+002E or ASCII 46) are reserved for rpc-internal methods and extensions and MUST NOT be used for anything else.
     */
    private String method;

    /**
     * A Structured value that holds the parameter values to be used during the invocation of the method.
     * This member MAY be omitted.
     */
    private JsonArray params;

    /**
     * An identifier established by the Client that MUST contain a String, Number, or NULL value if included.
     * If it is not included it is assumed to be a notification.
     * The value SHOULD normally not be Null and Numbers SHOULD NOT contain fractional parts.
     */
    private String id;
}
