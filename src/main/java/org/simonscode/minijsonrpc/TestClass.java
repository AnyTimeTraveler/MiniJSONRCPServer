package org.simonscode.minijsonrpc;

import org.simonscode.minijsonrpc.annotations.RemoteMethod;

public class TestClass {

    @RemoteMethod
    public static void justPrintStuff() {
        System.out.println("TestClass.justPrintStuff");
    }

    @RemoteMethod
    public static void printSomething(String string) {
        System.out.println("TestClass.printSomething");
        System.out.println("string = [" + string + "]");
    }

    @RemoteMethod
    public static String reverse(String string) {
        System.out.println("TestClass.reverse");
        StringBuilder sb = new StringBuilder();
        for (int i = string.length() - 1; i >= 0; i--) {
            sb.append(string.charAt(i));
        }
        return sb.toString();
    }
}
