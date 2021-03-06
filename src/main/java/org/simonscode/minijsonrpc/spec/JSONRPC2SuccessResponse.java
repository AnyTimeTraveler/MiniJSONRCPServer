package org.simonscode.minijsonrpc.spec;

import com.google.gson.JsonElement;
import lombok.Data;

/**
 * When a rpc call is made, the Server MUST reply with a Response, except for in the case of Notifications.
 * The Response is expressed as a single JSON Object.
 */
@Data
public class JSONRPC2SuccessResponse extends JSONRPC2Response {
    /**
     * A String specifying the version of the JSON-RPC protocol.
     * MUST be exactly "2.0".
     */
    public String jsonrpc = "2.0";
    /**
     * This member is REQUIRED on success.
     * This member MUST NOT exist if there was an error invoking the method.
     * The value of this member is determined by the method invoked on the Server.
     */
    public JsonElement result;
    /**
     * This member is REQUIRED.
     * It MUST be the same as the value of the id member in the Request Object.
     * If there was an error in detecting the id in the Request object (e.g. Parse error/Invalid Request), it MUST be Null.
     */
    public String id;

    public JSONRPC2SuccessResponse(JsonElement result, String id) {
        this.result = result;
        this.id = id;
    }
}
