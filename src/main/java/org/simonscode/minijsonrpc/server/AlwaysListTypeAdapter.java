package org.simonscode.minijsonrpc.server;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class AlwaysListTypeAdapter<E>
        extends TypeAdapter<List<E>> {

    private final TypeAdapter<E> elementTypeAdapter;

    public AlwaysListTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
        this.elementTypeAdapter = elementTypeAdapter;
    }

    @Override
    public void write(final JsonWriter out, final List<E> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> read(final JsonReader in)
            throws IOException {
        // This is where we detect the list "type"
        final List<E> list = new ArrayList<>();
        final JsonToken token = in.peek();
        switch (token) {
            case BEGIN_ARRAY:
                // If it's a regular list, just consume [, <all elements>, and ]
                in.beginArray();
                while (in.hasNext()) {
                    list.add(elementTypeAdapter.read(in));
                }
                in.endArray();
                break;
            case BEGIN_OBJECT:
            case STRING:
            case NUMBER:
            case BOOLEAN:
                // An object or a primitive? Just add the current value to the result list
                list.add(elementTypeAdapter.read(in));
                break;
            case NULL:
                throw new AssertionError("Must never happen: check if the type adapter configured with .nullSafe()");
            case NAME:
            case END_ARRAY:
            case END_OBJECT:
            case END_DOCUMENT:
                throw new MalformedJsonException("Unexpected token: " + token);
            default:
                throw new AssertionError("Must never happen: " + token);
        }
        return list;
    }

}