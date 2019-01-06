package org.simonscode.minijsonrpc.server;

import lombok.Data;

@Data
public class ResponseData {
    private String result;
    private String error;

    public long length() {
        return 0;
    }

    public byte[] getBytes() {
        return new byte[3];
    }
}
