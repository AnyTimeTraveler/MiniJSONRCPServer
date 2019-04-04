package org.simonscode.minijsonrpc.tests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import org.simonscode.minijsonrpc.TestClass;
import org.simonscode.minijsonrpc.server.MiniJSONRPCServer;
import org.simonscode.minijsonrpc.spec.JSONRPC2Request;
import org.simonscode.minijsonrpc.spec.JSONRPC2Response;
import org.simonscode.minijsonrpc.spec.JSONRPC2SuccessResponse;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static org.testng.Assert.assertEquals;

public class AnnotationTest {
    @Test
    public void test() throws IOException {
        MiniJSONRPCServer server = new MiniJSONRPCServer().addClass(TestClass.class);
        server.start();

        URL url = new URL("http://127.0.0.1:8000/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.connect();
        JsonArray params = new JsonArray(1);
        params.add("test");
        String reverse = new Gson().toJson(new JSONRPC2Request("reverse", params, "123"));
        System.out.println(reverse);
        con.getOutputStream().write(reverse.getBytes());
        con.getOutputStream().flush();
        con.getOutputStream().close();
        Scanner sc = new java.util.Scanner(con.getInputStream()).useDelimiter("\\A");
        assertEquals(new Gson().fromJson(sc.next(), JSONRPC2Response.class), new JSONRPC2SuccessResponse(new JsonPrimitive("tset"), "123"));
        server.stop(5);
    }
}
