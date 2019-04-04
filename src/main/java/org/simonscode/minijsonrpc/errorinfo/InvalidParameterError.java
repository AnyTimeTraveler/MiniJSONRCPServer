package org.simonscode.minijsonrpc.errorinfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidParameterError {
    private int expected;
    private int supplied;
}
