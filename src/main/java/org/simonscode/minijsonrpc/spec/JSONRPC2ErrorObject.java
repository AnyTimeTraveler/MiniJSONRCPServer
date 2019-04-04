package org.simonscode.minijsonrpc.spec;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * When a rpc call encounters an error, the Response Object MUST contain the error member with a value that is this Object.
 */
@Data
@AllArgsConstructor
public class JSONRPC2ErrorObject {

    /**
     * A Number that indicates the error type that occurred.
     * This MUST be an integer.
     */
    private int code;

    /**
     * A String providing a short description of the error.
     * The message SHOULD be limited to a concise single sentence.
     */
    private String message;

    /**
     * A Primitive or Structured value that contains additional information about the error.
     * This may be omitted.
     * The value of this member is defined by the Server (e.g. detailed error information, nested errors etc.).
     */
    private Object data;
}
