package org.simonscode.minijsonrpc.server;

import com.google.gson.Gson;
import lombok.Data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Data
public class ServerConfiguration {
    private List<Class<?>> annotatedClasses;
    private String address;
    private int port;

    public static ServerConfiguration fromFile(File configFile) throws IOException {
        return new Gson().fromJson(new FileReader(configFile), ServerConfiguration.class);
    }

    public void toFile(File configFile) throws IOException {
        new Gson().toJson(this, new FileWriter(configFile));
    }
}
