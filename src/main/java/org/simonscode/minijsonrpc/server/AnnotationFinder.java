package org.simonscode.minijsonrpc.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AnnotationFinder {

    static List<Method> getMethodsAnnotatedWith(Collection<Class<?>> classes, final Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<>();

        for (Class<?> c : classes) {
            final List<Method> allMethods = new ArrayList<>(Arrays.asList(c.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(annotation)) {
                    methods.add(method);
                }
            }
        }
        return methods;
    }
}
