package org.simonscode.minijsonrpc.server;

import java.io.File;
import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException {
        // get configuration
        ServerConfiguration sc = ServerConfiguration.fromFile(new File("config.json"));
        // find annotated methods
//        AnnotationFinder.getMethodsAnnotatedWith(sc.)
        // start server
        // ...
        // profit?
    }
}
