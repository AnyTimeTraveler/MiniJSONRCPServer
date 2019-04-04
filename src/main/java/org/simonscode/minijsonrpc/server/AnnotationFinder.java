package org.simonscode.minijsonrpc.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationFinder {

    static Map<String, Method> getMethodsAnnotatedWith(Collection<Class<?>> classes, final Class<? extends Annotation> annotation) {
        final Map<String, Method> methods = new HashMap<>();

        for (Class<?> c : classes) {
            final List<Method> allMethods = new ArrayList<>(Arrays.asList(c.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(annotation)) {
                    methods.put(method.getName(), method);
                }
            }
        }
        return methods;
    }
}
